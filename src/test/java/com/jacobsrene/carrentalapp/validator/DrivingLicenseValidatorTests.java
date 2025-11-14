package com.jacobsrene.carrentalapp.validator;

import com.jacobsrene.carrentalapp.exception.BookingValidationException;
import com.jacobsrene.drivinglicense.model.LicenseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DrivingLicenseValidatorTests {
    private DrivingLicenseValidator drivingLicenseValidator;

    @BeforeEach
    public void setUp() {
        drivingLicenseValidator = new DrivingLicenseValidator();
    }

    @Test
    public void validateDrivingLicenseShouldPassForValidLicense() {
        LicenseResponse licenseResponse = new LicenseResponse()
                .expiryDate(LocalDate.of(2030, 1, 1));

        assertDoesNotThrow(() -> drivingLicenseValidator.validateDrivingLicense(licenseResponse));
    }

    @Test
    public void validateDrivingLicenseShouldThrowIfLicenseTooNew() {
        LicenseResponse licenseResponse = new LicenseResponse()
                .expiryDate(LocalDate.now().plusYears(10)); // issued today

        BookingValidationException ex = assertThrows(
                BookingValidationException.class,
                () -> drivingLicenseValidator.validateDrivingLicense(licenseResponse)
        );

        assertEquals("Driving license is not older than one year.", ex.getMessage());
    }
}
