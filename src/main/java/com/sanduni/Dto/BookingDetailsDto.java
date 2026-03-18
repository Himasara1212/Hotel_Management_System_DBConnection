package com.sanduni.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingDetailsDto {
    private String bookingId;
    private String bookingDate;
    private String bookingTime;
    private String guestId;
    private String checkInDate;
    private String checkOutDate;
    private double price;
}