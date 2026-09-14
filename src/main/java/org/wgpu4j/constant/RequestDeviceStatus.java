package org.wgpu4j.constant;

import org.wgpu4j.bindings.wgpu_h;

public enum RequestDeviceStatus {
    ERROR(wgpu_h.WGPURequestDeviceStatus_Error()),
    SUCCESS(wgpu_h.WGPURequestDeviceStatus_Success()),
    CALLBACK_CANCELLED(wgpu_h.WGPURequestDeviceStatus_CallbackCancelled());

    private final int value;

    RequestDeviceStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Converts a WGPURequestDeviceStatus value to the corresponding enum.
     *
     * @param value The RequestDeviceStatus value
     * @return The corresponding WGPURequestDeviceStatus enum
     * @throws IllegalArgumentException if the value is not recognized
     */
    public static RequestDeviceStatus fromValue(int value) {
        for (RequestDeviceStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown device request status reason value: " + value);
    }
}