package com.kbe.kompsys.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Version {
    @Id
    private String versionName;

    private long versionNumber;
}
