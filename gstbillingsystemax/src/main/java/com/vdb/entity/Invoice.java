package com.vdb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long invoiceId;

    @Pattern(regexp = "[A-Za-z]*", message = "Name should contain only alphabets only")
    private String custName;

    @Range(min = 1000000000, max = 9999999999L, message = "contact should be 10 digits only")
    private Long contact;

    @Email(message = "email should be valid")
    private String email;

    @Size(min = 4, max = 10, message = "Description should be min 4 characters")
    private String productDescription;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;

    @NotNull
    private long quantity;

    @NotNull
    private long pricePerUnit;

    @JsonIgnore
    private double totalAmount;

}
