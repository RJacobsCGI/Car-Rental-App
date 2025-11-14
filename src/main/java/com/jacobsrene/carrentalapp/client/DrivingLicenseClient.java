package com.jacobsrene.carrentalapp.client;

import com.jacobsrene.drivinglicense.model.LicenseResponse;

public interface DrivingLicenseClient {
    LicenseResponse getDrivingLicenseInfo(String drivingLicenseNumber);
}
