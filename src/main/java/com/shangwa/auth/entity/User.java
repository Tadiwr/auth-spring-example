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
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    /** Returns true if the user objects passes the presence check */
    public boolean precenceCheck() {

        boolean email_present = (email != null) && (email != "");
        boolean password_present = (password != null) && (password != "");
        boolean name_present = (name != null) && (name != "");

        return email_present && password_present && name_present;
    }
}
