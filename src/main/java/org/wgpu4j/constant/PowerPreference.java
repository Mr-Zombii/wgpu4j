package org.wgpu4j.constant;

import org.wgpu4j.bindings.wgpu_h;

/**
 * Preference for power vs performance when selecting a graphics adapter.
 */
public enum PowerPreference {
    /**
     * No preference specified
     */
    UNDEFINED(wgpu_h.WGPUPowerPreference_Undefined()),

    /**
     * Prefer low power consumption (integrated GPU)
     */
    LOW_POWER(wgpu_h.WGPUPowerPreference_LowPower()),

    /**
     * Prefer high performance (discrete GPU)
     */
    HIGH_PERFORMANCE(wgpu_h.WGPUPowerPreference_HighPerformance());

    private final int value;

    PowerPreference(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PowerPreference fromValue(int value) {
        for (PowerPreference preference : values()) {
            if (preference.value == value) {
                return preference;
            }
        }
        throw new IllegalArgumentException("Unknown PowerPreference value: " + value);
    }
}