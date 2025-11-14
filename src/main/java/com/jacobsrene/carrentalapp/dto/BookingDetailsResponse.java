package com.jacobsrene.carrentalapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingDetailsResponse(
        @NotBlank
        String drivingLicenseNumber,
        @NotBlank
        String customerName,
        @NotNull(message = "Customer age is required")
        @Min(value = 18, message = "Customer must be at least 18 years old")
        int customerAge,
        @NotNull(message = "Reservation start date is required")
        LocalDate reservationStart,
        @NotNull(message = "Reservation end date is required")
        LocalDate reservationEnd,
        @NotNull(message = "Car segment is required")
        CarSegment carSegment,
        @Positive(message = "Invalid rental price")
        BigDecimal rentalPrice) {
}
