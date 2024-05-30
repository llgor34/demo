package com.demo_app.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
public class UserCurrencyAccess {

    @Id
    @GeneratedValue
    private  Long id;

    private String currency;
    private String name;

    @CreationTimestamp
    private Timestamp date;

    private double value;
}
