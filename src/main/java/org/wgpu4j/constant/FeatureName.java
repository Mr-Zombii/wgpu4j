package org.wgpu4j.constant;

import org.wgpu4j.bindings.wgpu_h;

/**
 * Optional features that can be requested when creating a device.
 */
public enum FeatureName {

    CORE_FEATURES_AND_LIMITS(wgpu_h.WGPUFeatureName_CoreFeaturesAndLimits()),
    DEPTH_CLIP_CONTROL(wgpu_h.WGPUFeatureName_DepthClipControl()),
    DEPTH32_FLOAT_STENCIL8(wgpu_h.WGPUFeatureName_Depth32FloatStencil8()),
    TEXTURE_COMPRESSION_BC(wgpu_h.WGPUFeatureName_TextureCompressionBC()),
    TEXTURE_COMPRESSION_BC_SLICED3_D(wgpu_h.WGPUFeatureName_TextureCompressionBCSliced3D()),
    TEXTURE_COMPRESSION_ETC2(wgpu_h.WGPUFeatureName_TextureCompressionETC2()),
    TEXTURE_COMPRESSION_ASTC(wgpu_h.WGPUFeatureName_TextureCompressionASTC()),
    TEXTURE_COMPRESSION_ASTC_SLICED3_D(wgpu_h.WGPUFeatureName_TextureCompressionASTCSliced3D()),
    TIMESTAMP_QUERY(wgpu_h.WGPUFeatureName_TimestampQuery()),
    INDIRECT_FIRST_INSTANCE(wgpu_h.WGPUFeatureName_IndirectFirstInstance()),
    SHADER_F16(wgpu_h.WGPUFeatureName_ShaderF16()),
    RG11_B10_UFLOAT_RENDERABLE(wgpu_h.WGPUFeatureName_RG11B10UfloatRenderable()),
    BGRA8_UNORM_STORAGE(wgpu_h.WGPUFeatureName_BGRA8UnormStorage()),
    FLOAT32_FILTERABLE(wgpu_h.WGPUFeatureName_Float32Filterable()),
    FLOAT32_BLENDABLE(wgpu_h.WGPUFeatureName_Float32Blendable()),
    CLIP_DISTANCES(wgpu_h.WGPUFeatureName_ClipDistances()),
    DUAL_SOURCE_BLENDING(wgpu_h.WGPUFeatureName_DualSourceBlending()),
    SUBGROUPS(wgpu_h.WGPUFeatureName_Subgroups()),
    TEXTURE_FORMATS_TIER1(wgpu_h.WGPUFeatureName_TextureFormatsTier1()),
    TEXTURE_FORMATS_TIER2(wgpu_h.WGPUFeatureName_TextureFormatsTier2()),
    PRIMITIVE_INDEX(wgpu_h.WGPUFeatureName_PrimitiveIndex()),
    TEXTURE_COMPONENT_SWIZZLE(wgpu_h.WGPUFeatureName_TextureComponentSwizzle());

    private final int value;

    FeatureName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FeatureName fromValue(int value) {
        for (FeatureName feature : values()) {
            if (feature.value == value) {
                return feature;
            }
        }
        throw new IllegalArgumentException("Unknown FeatureName value: " + value);
    }
}