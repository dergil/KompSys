package com.kbe.kompsys.repository;

import com.kbe.kompsys.domain.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax,String> {
}
