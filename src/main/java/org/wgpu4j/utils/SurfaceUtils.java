package org.wgpu4j.utils;

import org.lwjgl.glfw.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wgpu4j.WgpuException;
import org.wgpu4j.bindings.*;
import org.wgpu4j.resource.Instance;
import org.wgpu4j.resource.Surface;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.reflect.Constructor;

/**
 * Utility class for creating WebGPU surfaces from various window systems.
 * Provides both convenience methods for common cases and advanced methods for custom window handling.
 */
public class SurfaceUtils {
    private static final Logger logger = LoggerFactory.getLogger(SurfaceUtils.class);

    /**
     * Platform types for surface creation.
     */
    public enum Platform {
        WINDOWS,
        MACOS,
        LINUX_X11,
        LINUX_WAYLAND,
        ANDROID
    }

    /**
     * Creates a surface from a GLFW window handle (convenience method).
     * Automatically detects the platform and uses appropriate native calls.
     *
     * @param instance   The WebGPU instance
     * @param glfwWindow The GLFW window handle
     * @return A new Surface instance
     * @throws WgpuException if surface creation fails
     */
    public static Surface createFromGLFWWindow(Instance instance, long glfwWindow) {
        if (glfwWindow == 0) {
            throw new IllegalArgumentException("GLFW window handle cannot be null/zero");
        }

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment surfaceDesc = WGPUSurfaceDescriptor.allocate(arena);
            MemorySegment surfaceSource;

            switch (org.lwjgl.system.Platform.get()) {
                case null -> throw new IllegalStateException("Tried using null platform!");
                case FREEBSD, LINUX -> {
                    int platform = GLFW.glfwGetPlatform();
                    if (platform == GLFW.GLFW_PLATFORM_X11) {
                        try {
                            long x11Window = GLFWNativeX11.glfwGetX11Window(glfwWindow);
                            long x11Display = GLFWNativeX11.glfwGetX11Display();

                            surfaceSource = LinuxSurfaceHelper.createX11SurfaceSource(arena, x11Window, x11Display);
                        } catch (Exception e) {
                            throw new WgpuException("Failed to get X11 window handle from GLFW", e);
                        }
                        break;
                    }
                    if (platform == GLFW.GLFW_PLATFORM_WAYLAND) {
                        try {
                            long waylandWindow = GLFWNativeWayland.glfwGetWaylandWindow(glfwWindow);
                            long waylandDisplay = GLFWNativeWayland.glfwGetWaylandDisplay();

                            surfaceSource = LinuxSurfaceHelper.createWaylandSurfaceSource(arena, waylandWindow, waylandDisplay);
                        } catch (Exception e) {
                            throw new WgpuException("Failed to get Wayland window handle from GLFW", e);
                        }
                        break;
                    }
                    throw new IllegalStateException("Surface not supported! GLFW-ID: " + platform);
                }
                case MACOSX -> {
                    try {
                        long nsWindow = GLFWNativeCocoa.glfwGetCocoaWindow(glfwWindow);
                        long metalLayer = MacOSSurfaceHelper.createCAMetalLayer(nsWindow);

                        surfaceSource = WGPUSurfaceSourceMetalLayer.allocate(arena);
                        MemorySegment chain = WGPUSurfaceSourceMetalLayer.chain(surfaceSource);
                        WGPUChainedStruct.next(chain, MemorySegment.NULL);
                        WGPUChainedStruct.sType(chain, webgpu_h.WGPUSType_SurfaceSourceMetalLayer());
                        WGPUSurfaceSourceMetalLayer.layer(surfaceSource, MemorySegment.ofAddress(metalLayer));
                    } catch (Exception e) {
                        throw new WgpuException("Failed to get macOS window handle from GLFW", e);
                    }
                }
                case WINDOWS -> {
                    try {
                        long hwnd = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);

                        surfaceSource = WindowsSurfaceHelper.createWindowsSurfaceSource(arena, hwnd);
                    } catch (Exception e) {
                        throw new WgpuException("Failed to get Windows window handle from GLFW", e);
                    }
                }
            }

            return createSurfaceInternal(instance, arena, surfaceDesc, surfaceSource, "GLFW Surface");
        }
    }

    /**
     * Creates a surface from native window handles (advanced method).
     * Allows users to provide their own window system integration.
     *
     * @param instance     The WebGPU instance
     * @param platform     The target platform
     * @param windowHandle The native window handle
     * @param extraHandle  Additional handle (HINSTANCE for Windows, X11 display for Linux, unused for macOS)
     * @return A new Surface instance
     * @throws WgpuException if surface creation fails
     */
    public static Surface createFromNativeHandle(Instance instance, Platform platform,
                                                 long windowHandle, long extraHandle) {
        if (windowHandle == 0) {
            throw new IllegalArgumentException("Window handle cannot be null/zero");
        }

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment surfaceDesc = WGPUSurfaceDescriptor.allocate(arena);
            MemorySegment surfaceSource;

            switch (platform) {
                case WINDOWS -> {
                    surfaceSource = WindowsSurfaceHelper.createWindowsSurfaceSource(arena, windowHandle, extraHandle);
                }
                case MACOS -> {
                    long metalLayer = MacOSSurfaceHelper.createCAMetalLayer(windowHandle);
                    surfaceSource = WGPUSurfaceSourceMetalLayer.allocate(arena);
                    MemorySegment chain = WGPUSurfaceSourceMetalLayer.chain(surfaceSource);
                    WGPUChainedStruct.next(chain, MemorySegment.NULL);
                    WGPUChainedStruct.sType(chain, webgpu_h.WGPUSType_SurfaceSourceMetalLayer());
                    WGPUSurfaceSourceMetalLayer.layer(surfaceSource, MemorySegment.ofAddress(metalLayer));
                }
                case LINUX_X11 -> {
                    if (extraHandle == 0) {
                        throw new IllegalArgumentException("X11 display handle is required for Linux X11");
                    }
                    surfaceSource = LinuxSurfaceHelper.createX11SurfaceSource(arena, windowHandle, extraHandle);
                }
                case LINUX_WAYLAND -> {
                    if (extraHandle == 0) {
                        throw new IllegalArgumentException("Wayland display handle is required for Linux Wayland");
                    }
                    surfaceSource = LinuxSurfaceHelper.createWaylandSurfaceSource(arena, windowHandle, extraHandle);
                }
                case ANDROID -> {
                    surfaceSource = LinuxSurfaceHelper.createAndroidSurfaceSource(arena, windowHandle);
                }
                default -> throw new IllegalArgumentException("Unsupported platform: " + platform);
            }

            return createSurfaceInternal(instance, arena, surfaceDesc, surfaceSource, "Native Surface");
        }
    }

    /**
     * Internal method to create the surface from prepared descriptors.
     */
    private static Surface createSurfaceInternal(Instance instance, Arena arena,
                                                 MemorySegment surfaceDesc, MemorySegment surfaceSource,
                                                 String label) {
        try {
            WGPUSurfaceDescriptor.nextInChain(surfaceDesc, surfaceSource);
            MemorySegment labelData = arena.allocateFrom(label);
            MemorySegment labelView = WGPUSurfaceDescriptor.label(surfaceDesc);
            WGPUStringView.data(labelView, labelData);
            WGPUStringView.length(labelView, label.length());

            MemorySegment surfaceHandle = webgpu_h.wgpuInstanceCreateSurface(
                    instance.getHandle(), surfaceDesc);

            if (surfaceHandle.equals(MemorySegment.NULL)) {
                throw new WgpuException("Failed to create surface");
            }

            Constructor<Surface> constructor = Surface.class.getDeclaredConstructor(MemorySegment.class);
            constructor.setAccessible(true);
            Surface surface = constructor.newInstance(surfaceHandle);

            logger.info("Surface created successfully with label: {}", label);
            return surface;

        } catch (WgpuException e) {
            throw e;
        } catch (Exception e) {
            throw new WgpuException("Failed to create surface", e);
        }
    }
}