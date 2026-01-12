package com.vdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADMIN")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long adminId;

    @Pattern(regexp = "[A-Za-z]*", message = "Name should contain only alphabets only")
    private String name;

    @Range(min = 1000000000, max = 9999999999L, message = "contact should be 10 digits only")
    private Long contact;

    @Email(message = "email should be valid")
    private String email;

    @Size(min = 4, message = "pass should be min 4 characters")
    private String pass;

}
