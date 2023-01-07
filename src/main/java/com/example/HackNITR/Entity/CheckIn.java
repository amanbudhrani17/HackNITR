package com.example.HackNITR.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "checkIn"
)
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "latitude",nullable = false)
    private String latitude;
    @Column(name = "longitude",nullable = false)
    private String longitude;
    @Column(name = "cleanerUserId",nullable = true)
    private String cleaner;
    @Column(name = "droneVerified",nullable = true)
    private String drone;
}
