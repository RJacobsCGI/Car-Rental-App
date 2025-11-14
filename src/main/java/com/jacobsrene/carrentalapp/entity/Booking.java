package com.jacobsrene.carrentalapp.entity;

import com.jacobsrene.carrentalapp.dto.CarSegment;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "booking_id")
    private UUID bookingId;
    @Column(name = "driving_license_number")
    private String drivingLicenseNumber;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_age")
    private int customerAge;
    @Column(name = "reservation_start")
    private LocalDate reservationStart;
    @Column(name = "reservation_end")
    private LocalDate reservationEnd;
    @Enumerated(EnumType.STRING)
    @Column(name = "car_segment")
    private CarSegment carSegment;
    @Column(name = "rental_price")
    private BigDecimal rentalPrice;

    public Booking(
            UUID bookingId,
            String drivingLicenseNumber,
            String customerName,
            int customerAge,
            LocalDate reservationStart,
            LocalDate reservationEnd,
            CarSegment carSegment,
            BigDecimal rentalPrice) {
        this.bookingId = bookingId;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.carSegment = carSegment;
        this.rentalPrice = rentalPrice;
    }

    public Booking() {
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) {
        this.customerAge = customerAge;
    }

    public LocalDate getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDate reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDate getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDate reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public CarSegment getCarSegment() {
        return carSegment;
    }

    public void setCarSegment(CarSegment carSegment) {
        this.carSegment = carSegment;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Booking) obj;
        return Objects.equals(this.bookingId, that.bookingId) &&
                Objects.equals(this.drivingLicenseNumber, that.drivingLicenseNumber) &&
                Objects.equals(this.customerName, that.customerName) &&
                this.customerAge == that.customerAge &&
                Objects.equals(this.reservationStart, that.reservationStart) &&
                Objects.equals(this.reservationEnd, that.reservationEnd) &&
                Objects.equals(this.carSegment, that.carSegment) &&
                Objects.equals(this.rentalPrice, that.rentalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, drivingLicenseNumber, customerName, customerAge, reservationStart, reservationEnd, carSegment, rentalPrice);
    }

    @Override
    public String toString() {
        return "Booking[" +
                "bookingId=" + bookingId + ", " +
                "drivingLicenseNumber=" + drivingLicenseNumber + ", " +
                "customerName=" + customerName + ", " +
                "customerAge=" + customerAge + ", " +
                "reservationStart=" + reservationStart + ", " +
                "reservationEnd=" + reservationEnd + ", " +
                "carSegment=" + carSegment + ", " +
                "rentalPrice=" + rentalPrice + ']';
    }

}
