package com.kbe.kompsys.repository;

import com.github.dergil.kompsys.dto.exception.NotFoundException;
import com.kbe.kompsys.domain.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
    default Tax getTaxById(String id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Tax.class, id));
    }
}
