package org.wgpu4j.constant;

import org.wgpu4j.bindings.webgpu_h;

/**
 * SurfaceTexture Status to tell if its valid or needs resizing.
 */
public enum SurfaceTextureStatus {
    SUCCESS_OPTIMAL(webgpu_h.WGPUSurfaceGetCurrentTextureStatus_SuccessOptimal()),
    SUCCESS_SUB_OPTIMAL(webgpu_h.WGPUSurfaceGetCurrentTextureStatus_SuccessSuboptimal()),
    TIMEOUT(webgpu_h.WGPUSurfaceGetCurrentTextureStatus_Timeout()),
    OUTDATED(webgpu_h.WGPUSurfaceGetCurrentTextureStatus_Outdated()),
    LOST(webgpu_h.WGPUSurfaceGetCurrentTextureStatus_Lost()),
    ERROR(webgpu_h.WGPUSurfaceGetCurrentTextureStatus_Error());

    private final int value;

    SurfaceTextureStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SurfaceTextureStatus fromValue(int value) {
        for (SurfaceTextureStatus op : values()) {
            if (op.value == value) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unknown LoadOp value: " + value);
    }
}