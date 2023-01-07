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
        name = "sites"
)
public class Site{

    public Site(double distance, String latitude, String longitude, double percentage, String image) {
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.percentage = percentage;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "distance",nullable = false)
    private double distance;
    @Column(name = "latitude",nullable = false)
    private String latitude;
    @Column(name = "longitude",nullable = false)
    private String longitude;
    @Column(name = "percentage",nullable = false)
    private double percentage;
    @Column(name = "image",nullable = false)
    private String image;
}
