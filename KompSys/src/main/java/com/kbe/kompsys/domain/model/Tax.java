package com.kbe.kompsys.domain.model;

import lombok.Data;
import org.apache.el.stream.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Tax {
    @Id
    private String countryCodeID;

    private double tax;
}
