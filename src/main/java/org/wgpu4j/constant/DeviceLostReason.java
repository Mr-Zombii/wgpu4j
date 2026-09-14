package org.wgpu4j.constant;

import org.wgpu4j.bindings.wgpu_h;

// https://doc.qu1x.dev/bevy_trackball/wgpu/enum.DeviceLostReason.html
public enum DeviceLostReason {
    UNKNOWN(wgpu_h.WGPUDeviceLostReason_Unknown()),
    DESTROYED(wgpu_h.WGPUDeviceLostReason_Destroyed()),
    CALLBACK_CANCELLED(wgpu_h.WGPUDeviceLostReason_CallbackCancelled()),
    FAILED_CREATION(wgpu_h.WGPUDeviceLostReason_FailedCreation());

    private final int value;

    DeviceLostReason(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Converts a WGPUDeviceLostReason value to the corresponding enum.
     *
     * @param value The WWGPUDeviceLostReason value
     * @return The corresponding DeviceLostReason enum
     * @throws IllegalArgumentException if the value is not recognized
     */
    public static DeviceLostReason fromValue(int value) {
        for (DeviceLostReason status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown device lost reason value: " + value);
    }
}