package org.wgpu4j.utils;

import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.macosx.ObjCRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for creating CAMetalLayer on macOS.
 * This class uses reflection to optionally use LWJGL's Objective-C runtime bindings.
 * If LWJGL is not available, it will throw an exception indicating the dependency is needed.
 */
public class MacOSSurfaceHelper {

    private static final Logger logger = LoggerFactory.getLogger(MacOSSurfaceHelper.class);

    /**
     * Creates a CAMetalLayer from an NSWindow handle.
     * This method requires LWJGL to be on the classpath for Objective-C runtime support.
     *
     * @param nsWindow The NSWindow handle from glfwGetCocoaWindow
     * @return The CAMetalLayer handle
     * @throws RuntimeException if LWJGL is not available or if layer creation fails
     */
    public static long createCAMetalLayer(long nsWindow) {
        if (nsWindow == 0) {
            throw new IllegalArgumentException("NSWindow handle cannot be null/zero");
        }

        try {
            long objc_msgSend = ObjCRuntime.getLibrary().getFunctionAddress("objc_msgSend");

            long sel_alloc = ObjCRuntime.sel_getUid("alloc");
            long sel_init = ObjCRuntime.sel_getUid("init");
            long sel_contentView = ObjCRuntime.sel_getUid("contentView");
            long sel_setLayer = ObjCRuntime.sel_getUid("setLayer:");
            long sel_setWantsLayer = ObjCRuntime.sel_getUid("setWantsLayer:");
            long class_CAMetalLayer = ObjCRuntime.objc_getClass("CAMetalLayer");

            if (class_CAMetalLayer == MemoryUtil.NULL) {
                throw new RuntimeException("CAMetalLayer class not found - QuartzCore framework not available");
            }
            if (objc_msgSend == MemoryUtil.NULL) {
                throw new RuntimeException("objc_msgSend function not found - Objective-C runtime not available");
            }

            long metalLayer = JNI.invokePPP(
                    JNI.invokePPP(class_CAMetalLayer, sel_alloc, objc_msgSend),
                    sel_init,
                    objc_msgSend
            );

            if (metalLayer == MemoryUtil.NULL) {
                throw new RuntimeException("Failed to create CAMetalLayer");
            }

            long contentView = JNI.invokePPP(nsWindow, sel_contentView, objc_msgSend);
            if (contentView == MemoryUtil.NULL) {
                throw new RuntimeException("Failed to get NSWindow content view");
            }

            JNI.invokePPPV(contentView, sel_setWantsLayer, 1L, objc_msgSend);
            JNI.invokePPPV(contentView, sel_setLayer, metalLayer, objc_msgSend);

            logger.info("Created CAMetalLayer: 0x{} for NSWindow: 0x{}",
                    Long.toHexString(metalLayer), Long.toHexString(nsWindow));

            return metalLayer;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create CAMetalLayer using Objective-C runtime", e);
        }
    }
}