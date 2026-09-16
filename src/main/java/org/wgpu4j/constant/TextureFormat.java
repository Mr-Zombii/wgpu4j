package org.wgpu4j.constant;

import org.wgpu4j.bindings.wgpu_h;

/**
 * Texture formats supported by WGPU.
 */
public enum TextureFormat {
    UNDEFINED(wgpu_h.WGPUTextureFormat_Undefined()),

    R8_UNORM(wgpu_h.WGPUTextureFormat_R8Unorm()),
    R8_SNORM(wgpu_h.WGPUTextureFormat_R8Snorm()),
    R8_UINT(wgpu_h.WGPUTextureFormat_R8Uint()),
    R8_SINT(wgpu_h.WGPUTextureFormat_R8Sint()),

    R16_UNORM(wgpu_h.WGPUTextureFormat_R16Unorm()),
    R16_SNORM(wgpu_h.WGPUTextureFormat_R16Snorm()),
    R16_UINT(wgpu_h.WGPUTextureFormat_R16Uint()),
    R16_SINT(wgpu_h.WGPUTextureFormat_R16Sint()),
    R16_FLOAT(wgpu_h.WGPUTextureFormat_R16Float()),

    RG8_UNORM(wgpu_h.WGPUTextureFormat_RG8Unorm()),
    RG8_SNORM(wgpu_h.WGPUTextureFormat_RG8Snorm()),
    RG8_UINT(wgpu_h.WGPUTextureFormat_RG8Uint()),
    RG8_SINT(wgpu_h.WGPUTextureFormat_RG8Sint()),

    R32_FLOAT(wgpu_h.WGPUTextureFormat_R32Float()),
    R32_UINT(wgpu_h.WGPUTextureFormat_R32Uint()),
    R32_SINT(wgpu_h.WGPUTextureFormat_R32Sint()),

    RG16_UNORM(wgpu_h.WGPUTextureFormat_RG16Unorm()),
    RG16_SNORM(wgpu_h.WGPUTextureFormat_RG16Snorm()),
    RG16_UINT(wgpu_h.WGPUTextureFormat_RG16Uint()),
    RG16_SINT(wgpu_h.WGPUTextureFormat_RG16Sint()),
    RG16_FLOAT(wgpu_h.WGPUTextureFormat_RG16Float()),

    RGBA8_UNORM(wgpu_h.WGPUTextureFormat_RGBA8Unorm()),
    RGBA8_UNORM_SRGB(wgpu_h.WGPUTextureFormat_RGBA8UnormSrgb()),
    RGBA8_SNORM(wgpu_h.WGPUTextureFormat_RGBA8Snorm()),
    RGBA8_UINT(wgpu_h.WGPUTextureFormat_RGBA8Uint()),
    RGBA8_SINT(wgpu_h.WGPUTextureFormat_RGBA8Sint()),

    BGRA8_UNORM(wgpu_h.WGPUTextureFormat_BGRA8Unorm()),
    BGRA8_UNORM_SRGB(wgpu_h.WGPUTextureFormat_BGRA8UnormSrgb()),

    RGB10A2_UINT(wgpu_h.WGPUTextureFormat_RGB10A2Uint()),
    RGB10A2_UNORM(wgpu_h.WGPUTextureFormat_RGB10A2Unorm()),
    RG11B10_UFLOAT(wgpu_h.WGPUTextureFormat_RG11B10Ufloat()),
    RGB9E5_UFLOAT(wgpu_h.WGPUTextureFormat_RGB9E5Ufloat()),

    RG32_FLOAT(wgpu_h.WGPUTextureFormat_RG32Float()),
    RG32_UINT(wgpu_h.WGPUTextureFormat_RG32Uint()),
    RG32_SINT(wgpu_h.WGPUTextureFormat_RG32Sint()),

    RGBA16_UNORM(wgpu_h.WGPUTextureFormat_RGBA16Unorm()),
    RGBA16_SNORM(wgpu_h.WGPUTextureFormat_RGBA16Snorm()),
    RGBA16_UINT(wgpu_h.WGPUTextureFormat_RGBA16Uint()),
    RGBA16_SINT(wgpu_h.WGPUTextureFormat_RGBA16Sint()),
    RGBA16_FLOAT(wgpu_h.WGPUTextureFormat_RGBA16Float()),

    RGBA32_FLOAT(wgpu_h.WGPUTextureFormat_RGBA32Float()),
    RGBA32_UINT(wgpu_h.WGPUTextureFormat_RGBA32Uint()),
    RGBA32_SINT(wgpu_h.WGPUTextureFormat_RGBA32Sint()),

    STENCIL8(wgpu_h.WGPUTextureFormat_Stencil8()),

    DEPTH16_UNORM(wgpu_h.WGPUTextureFormat_Depth16Unorm()),

    DEPTH24_PLUS(wgpu_h.WGPUTextureFormat_Depth24Plus()),
    DEPTH24_PLUS_STENCIL8(wgpu_h.WGPUTextureFormat_Depth24PlusStencil8()),

    DEPTH32_FLOAT(wgpu_h.WGPUTextureFormat_Depth32Float()),
    DEPTH32_FLOAT_STENCIL8(wgpu_h.WGPUTextureFormat_Depth32FloatStencil8()),

    BC1_RGBA_UNORM(wgpu_h.WGPUTextureFormat_BC1RGBAUnorm()),
    BC1_RGBA_UNORM_SRGB(wgpu_h.WGPUTextureFormat_BC1RGBAUnormSrgb()),

    BC2_RGBA_UNORM(wgpu_h.WGPUTextureFormat_BC2RGBAUnorm()),
    BC2_RGBA_UNORM_SRGB(wgpu_h.WGPUTextureFormat_BC2RGBAUnormSrgb()),

    BC3_RGBA_UNORM(wgpu_h.WGPUTextureFormat_BC3RGBAUnorm()),
    BC3_RGBA_UNORM_SRGB(wgpu_h.WGPUTextureFormat_BC3RGBAUnormSrgb()),

    BC4_R_UNORM(wgpu_h.WGPUTextureFormat_BC4RUnorm()),
    BC4_R_SNORM(wgpu_h.WGPUTextureFormat_BC4RSnorm()),

    BC5_RG_UNORM(wgpu_h.WGPUTextureFormat_BC5RGUnorm()),
    BC5_RG_SNORM(wgpu_h.WGPUTextureFormat_BC5RGSnorm()),

    BC6_HRGB_UFLOAT(wgpu_h.WGPUTextureFormat_BC6HRGBUfloat()),
    BC6_HRGB_FLOAT(wgpu_h.WGPUTextureFormat_BC6HRGBFloat()),

    BC7_RGBA_UNORM(wgpu_h.WGPUTextureFormat_BC7RGBAUnorm()),
    BC7_RGBA_UNORM_SRGB(wgpu_h.WGPUTextureFormat_BC7RGBAUnormSrgb()),

    ETC2_RGB8_UNORM(wgpu_h.WGPUTextureFormat_ETC2RGB8Unorm()),
    ETC2_RGB8_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ETC2RGB8UnormSrgb()),

    ETC2_RGB8A1_UNORM(wgpu_h.WGPUTextureFormat_ETC2RGB8A1Unorm()),
    ETC2_RGB8A1_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ETC2RGB8A1UnormSrgb()),

    ETC2_RGBA8_UNORM(wgpu_h.WGPUTextureFormat_ETC2RGBA8Unorm()),
    ETC2_RGBA8_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ETC2RGBA8UnormSrgb()),

    EAC_R11_UNORM(wgpu_h.WGPUTextureFormat_EACR11Unorm()),
    EAC_R11_SNORM(wgpu_h.WGPUTextureFormat_EACR11Snorm()),

    EAC_RG11_UNORM(wgpu_h.WGPUTextureFormat_EACRG11Unorm()),
    EAC_RG11_SNORM(wgpu_h.WGPUTextureFormat_EACRG11Snorm()),

    ASTC_4X4_UNORM(wgpu_h.WGPUTextureFormat_ASTC4x4Unorm()),
    ASTC_4X4_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC4x4UnormSrgb()),

    ASTC_5X4_UNORM(wgpu_h.WGPUTextureFormat_ASTC5x4Unorm()),
    ASTC_5X4_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC5x4UnormSrgb()),

    ASTC_5X5_UNORM(wgpu_h.WGPUTextureFormat_ASTC5x5Unorm()),
    ASTC_5X5_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC5x5UnormSrgb()),

    ASTC_6X5_UNORM(wgpu_h.WGPUTextureFormat_ASTC6x5Unorm()),
    ASTC_6X5_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC6x5UnormSrgb()),

    ASTC_6X6_UNORM(wgpu_h.WGPUTextureFormat_ASTC6x6Unorm()),
    ASTC_6X6_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC6x6UnormSrgb()),

    ASTC_8X5_UNORM(wgpu_h.WGPUTextureFormat_ASTC8x5Unorm()),
    ASTC_8X5_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC8x5UnormSrgb()),

    ASTC_8X6_UNORM(wgpu_h.WGPUTextureFormat_ASTC8x6Unorm()),
    ASTC_8X6_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC8x6UnormSrgb()),

    ASTC_8X8_UNORM(wgpu_h.WGPUTextureFormat_ASTC8x8Unorm()),
    ASTC_8X8_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC8x8UnormSrgb()),

    ASTC_10X5_UNORM(wgpu_h.WGPUTextureFormat_ASTC10x5Unorm()),
    ASTC_10X5_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC10x5UnormSrgb()),

    ASTC_10X6_UNORM(wgpu_h.WGPUTextureFormat_ASTC10x6Unorm()),
    ASTC_10X6_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC10x6UnormSrgb()),

    ASTC_10X8_UNORM(wgpu_h.WGPUTextureFormat_ASTC10x8Unorm()),
    ASTC_10X8_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC10x8UnormSrgb()),

    ASTC_10X10_UNORM(wgpu_h.WGPUTextureFormat_ASTC10x10Unorm()),
    ASTC_10X10_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC10x10UnormSrgb()),

    ASTC_12X10_UNORM(wgpu_h.WGPUTextureFormat_ASTC12x10Unorm()),
    ASTC_12X10_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC12x10UnormSrgb()),

    ASTC_12X12_UNORM(wgpu_h.WGPUTextureFormat_ASTC12x12Unorm()),
    ASTC_12X12_UNORM_SRGB(wgpu_h.WGPUTextureFormat_ASTC12x12UnormSrgb());

    private final int value;

    TextureFormat(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TextureFormat fromValue(int value) {
        for (TextureFormat format : values()) {
            if (format.value == value) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown TextureFormat value: " + value);
    }
}