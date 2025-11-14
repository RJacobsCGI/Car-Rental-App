package com.jacobsrene.carrentalapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacobsrene.carrentalapp.dto.BookingDetailsResponse;
import com.jacobsrene.carrentalapp.dto.BookingRequest;
import com.jacobsrene.carrentalapp.dto.CarSegment;
import com.jacobsrene.carrentalapp.entity.Booking;
import com.jacobsrene.carrentalapp.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getShouldReturnBookingDetails() throws Exception {
        BookingDetailsResponse bookingDetails = new BookingDetailsResponse(
                "licenseNumber",
                "name",
                42,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                CarSegment.MEDIUM,
                BigDecimal.ONE
        );

        when(bookingService.getBookingDetails(any(UUID.class))).thenReturn(Optional.of(bookingDetails));

        this.mockMvc.perform(get("/booking/{id}", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.drivingLicenseNumber").value("licenseNumber"))
                .andExpect(jsonPath("$.customerName").value("name"))
                .andExpect(jsonPath("$.customerAge").value(42))
                .andExpect(jsonPath("$.carSegment").value("MEDIUM"))
                .andExpect(jsonPath("$.rentalPrice").value(1));
    }

    @Test
    public void getShouldReturnNotFoundWhenBookingDoesNotExist() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(bookingService.getBookingDetails(uuid)).thenReturn(Optional.empty());

        mockMvc.perform(get("/booking/{id}", uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void confirmBookingShouldReturnCreatedWithBookingId() throws Exception {
        UUID bookingId = UUID.randomUUID();
        BookingRequest request = new BookingRequest(
                "licenseNumber",
                42,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                CarSegment.MEDIUM
        );

        Booking confirmedBooking = new Booking(
                bookingId,
                request.drivingLicenseNumber(),
                "John Doe",
                request.customerAge(),
                request.reservationStart(),
                request.reservationEnd(),
                request.carSegment(),
                BigDecimal.ONE);

        when(bookingService.confirmBooking(request))
                .thenReturn(confirmedBooking);

        mockMvc.perform(post("/booking/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/booking/" + bookingId)))
                .andExpect(jsonPath("$").value(bookingId.toString()));
    }
}
