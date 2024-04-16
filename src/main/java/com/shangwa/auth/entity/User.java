package com.shangwa.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(name = "email", unique = true, nullable = false)
    private String email = null;

    @Column(name = "name", nullable = false)
    private String name = null;

    @Column(name = "password", nullable = false)
    private String password = null;
}
