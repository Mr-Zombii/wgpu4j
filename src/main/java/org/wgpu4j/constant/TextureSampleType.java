package org.wgpu4j.constant;

import org.wgpu4j.bindings.webgpu_h;

/**
 * Sample type for texture binding layouts.
 * Defines how the texture data should be interpreted when sampled.
 */
public enum TextureSampleType {

    BINDING_NOT_USED(webgpu_h.WGPUTextureSampleType_BindingNotUsed()),
    UNDEFINED(webgpu_h.WGPUTextureSampleType_Undefined()),

    /**
     * Floating-point texture data.
     * Most common texture sample type for color textures.
     */
    FLOAT(webgpu_h.WGPUTextureSampleType_Float()),

    /**
     * Unfilterable floating-point data.
     * Float data that cannot be linearly filtered.
     */
    UNFILTERABLE_FLOAT(webgpu_h.WGPUTextureSampleType_UnfilterableFloat()),

    /**
     * Depth texture data for comparison operations.
     * Used for shadow mapping and depth testing.
     */
    DEPTH(webgpu_h.WGPUTextureSampleType_Depth()),

    /**
     * Signed integer texture data.
     * Used for textures containing signed integer values.
     */
    SINT(webgpu_h.WGPUTextureSampleType_Sint()),

    /**
     * Unsigned integer texture data.
     * Used for textures containing integer values.
     */
    UINT(webgpu_h.WGPUTextureSampleType_Uint());

    private final int value;

    TextureSampleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Converts a WGPU texture sample type value to the corresponding enum.
     *
     * @param value The WGPU texture sample type value
     * @return The corresponding TextureSampleType enum
     * @throws IllegalArgumentException if the value is not recognized
     */
    public static TextureSampleType fromValue(int value) {
        for (TextureSampleType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown texture sample type value: " + value);
    }
}