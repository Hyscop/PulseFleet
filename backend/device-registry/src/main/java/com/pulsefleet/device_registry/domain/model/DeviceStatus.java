package com.pulsefleet.device_registry.domain.model;

/**
 * Device lifecyacle
 * 
 * REGISTERED → ACTIVATED → DEACTIVATED
 * ----------------↑--------------↓
 * ----------------└──────────────┘ (reactivate)
 */

public enum DeviceStatus {
    REGISTERED,
    ACTIVATED,
    DEACTIVATED
}
