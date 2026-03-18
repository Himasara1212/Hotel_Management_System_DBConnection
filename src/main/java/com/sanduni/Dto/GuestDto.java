package com.sanduni.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GuestDto {
    private String guestId;
    private String name;
    private String address;
    private String country;
    private String nic;
    private String email;
    private String contact;
    private int memberCount;
}