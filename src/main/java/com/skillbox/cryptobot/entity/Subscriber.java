package com.skillbox.cryptobot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID uuid;
    private Long id;
    private Double price;
}
