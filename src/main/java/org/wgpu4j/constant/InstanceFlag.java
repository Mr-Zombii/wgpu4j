package org.wgpu4j.constant;

/**
 * WebGPU instance configuration flags.
 * Based on WGPUInstanceFlag from wgpu.h
 */
public enum InstanceFlag { // WGPUInstanceFlag
    EMPTY(0x00000000),
    DEBUG(1 << 0),
    VALIDATION(1 << 1),
    DISCARD_HAL_LABELS(1 << 2),
    ALLOW_UNDERLYING_NONCOMPLIANT_ADAPTER(1 << 3),
    GPU_BASED_VALIDATION(1 << 4),
    VALIDATION_INDIRECT_CALL(1 << 5),
    AUTOMATIC_TIMESTAMP_NORMALIZATION(1 << 6),
    DEFAULT(1 << 24),
    DEBUGGING(1 << 25),
    ADVANCED_DEBUGGING(1 << 26),
    WITH_ENV(1 << 27);

    private final int value;

    InstanceFlag(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static InstanceFlag fromValue(int value) {
        for (InstanceFlag flag : values()) {
            if (flag.value == value) {
                return flag;
            }
        }
        throw new IllegalArgumentException("Unknown InstanceFlag value: " + value);
    }
}