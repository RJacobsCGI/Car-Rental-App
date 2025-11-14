package com.jacobsrene.carrentalapp.service;

import com.jacobsrene.carrentalapp.client.DrivingLicenseClient;
import com.jacobsrene.carrentalapp.client.PricingClient;
import com.jacobsrene.carrentalapp.dto.BookingDetailsResponse;
import com.jacobsrene.carrentalapp.dto.BookingRequest;
import com.jacobsrene.carrentalapp.entity.Booking;
import com.jacobsrene.carrentalapp.repository.BookingRepository;
import com.jacobsrene.carrentalapp.validator.DrivingLicenseValidator;
import com.jacobsrene.drivinglicense.model.LicenseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final DrivingLicenseValidator drivingLicenseValidator;
    private final PricingClient pricingClient;
    private final DrivingLicenseClient drivingLicenseClient;

    public Optional<BookingDetailsResponse> getBookingDetails(UUID bookingId) {
        return bookingRepository
                .findById(bookingId)
                .map(booking -> new BookingDetailsResponse(
                        booking.getDrivingLicenseNumber(),
                        booking.getCustomerName(),
                        booking.getCustomerAge(),
                        booking.getReservationStart(),
                        booking.getReservationEnd(),
                        booking.getCarSegment(),
                        booking.getRentalPrice()
                ));
    }

    /**
     * @param bookingRequest data needed to get all other info to complete the booking:
     *                              i.e. driving license info and rental price
     * @return Booking which contains all the necessary details
     * This method does not use transactionals, but it could be expanded to do so for robustness
     */
    public Booking confirmBooking(BookingRequest bookingRequest) {
        BigDecimal rentalPrice = pricingClient.getRentalPrice(bookingRequest.carSegment().name());
        LicenseResponse licenseResponse = drivingLicenseClient.getDrivingLicenseInfo(bookingRequest.drivingLicenseNumber());

        drivingLicenseValidator.validateDrivingLicense(licenseResponse);

        UUID id = UUID.randomUUID();
        Booking newBooking = new Booking(
                id,
                bookingRequest.drivingLicenseNumber(),
                licenseResponse.getOwnerName(),
                bookingRequest.customerAge(),
                bookingRequest.reservationStart(),
                bookingRequest.reservationEnd(),
                bookingRequest.carSegment(),
                rentalPrice);

        return bookingRepository.save(newBooking);
    }
}
