package com.kbe.storage.repository;

import com.github.dergil.kompsys.dto.exception.EntryNotFoundException;
import com.kbe.storage.domain.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
  default Tax getTaxById(String id) {
    return findById(id).orElseThrow(() -> new EntryNotFoundException(Tax.class, id));
  }
}
