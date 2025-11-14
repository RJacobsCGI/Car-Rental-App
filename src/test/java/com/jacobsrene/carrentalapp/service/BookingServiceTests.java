package com.jacobsrene.carrentalapp.service;


import com.jacobsrene.carrentalapp.client.DrivingLicenseClient;
import com.jacobsrene.carrentalapp.client.PricingClient;
import com.jacobsrene.carrentalapp.dto.BookingDetailsResponse;
import com.jacobsrene.carrentalapp.dto.CarSegment;
import com.jacobsrene.carrentalapp.dto.BookingRequest;
import com.jacobsrene.carrentalapp.entity.Booking;
import com.jacobsrene.carrentalapp.repository.BookingRepository;
import com.jacobsrene.carrentalapp.validator.DrivingLicenseValidator;
import com.jacobsrene.drivinglicense.model.LicenseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTests {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private PricingClient pricingClient;
    @Mock
    private
    DrivingLicenseClient drivingLicenseClient;
    @Mock
    private DrivingLicenseValidator drivingLicenseValidator;

    @InjectMocks
    private BookingService bookingService;

    private UUID bookingId;
    private Booking booking;

    @BeforeEach
    public void setUp() {
        bookingId = UUID.randomUUID();
        booking = new Booking(
                bookingId,
                "ABC123",
                "John Doe",
                30,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                CarSegment.MEDIUM,
                BigDecimal.TEN
        );
    }

    @Test
    public void getBookingDetailsShouldReturnMappedResponseWhenBookingExists() {
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Optional<BookingDetailsResponse> result = bookingService.getBookingDetails(bookingId);

        assertThat(result).isPresent();
        BookingDetailsResponse response = result.get();
        assertThat(response.drivingLicenseNumber()).isEqualTo("ABC123");
        assertThat(response.customerName()).isEqualTo("John Doe");
        assertThat(response.customerAge()).isEqualTo(30);
        assertThat(response.carSegment()).isEqualTo(CarSegment.MEDIUM);

        verify(bookingRepository).findById(bookingId);
    }

    @Test
    public void getBookingDetailsShouldReturnEmptyWhenBookingNotFound() {
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        Optional<BookingDetailsResponse> result = bookingService.getBookingDetails(bookingId);

        assertThat(result).isEmpty();
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    public void confirmBookingShouldSaveBookingWhenValid() {
        BookingRequest request = new BookingRequest(
                "ABC123",
                30,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                CarSegment.MEDIUM
        );
        LicenseResponse licenseResponse = new LicenseResponse();
        licenseResponse.ownerName("John Doe");
        licenseResponse.expiryDate(LocalDate.now());
        BigDecimal price = BigDecimal.valueOf(99.99);

        when(pricingClient.getRentalPrice("MEDIUM")).thenReturn(price);
        when(drivingLicenseClient.getDrivingLicenseInfo("ABC123")).thenReturn(licenseResponse);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking saved = bookingService.confirmBooking(request);

        assertThat(saved.getBookingId()).isNotNull();
        assertThat(saved.getRentalPrice()).isEqualTo(price);
        assertThat(saved.getCarSegment()).isEqualTo(CarSegment.MEDIUM);
        verify(pricingClient).getRentalPrice("MEDIUM");
        verify(drivingLicenseClient).getDrivingLicenseInfo("ABC123");
        verify(bookingRepository).save(any(Booking.class));
    }
}
