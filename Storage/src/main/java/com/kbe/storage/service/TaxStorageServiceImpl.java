package com.kbe.storage.service;

import com.github.dergil.kompsys.dto.tax.*;
import com.kbe.storage.domain.mapper.TaxEditMapper;
import com.kbe.storage.domain.mapper.TaxViewMapper;
import com.kbe.storage.domain.model.Tax;
import com.kbe.storage.repository.TaxRepository;
import com.kbe.storage.service.interfaces.TaxStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaxStorageServiceImpl implements TaxStorageService {


  private final TaxRepository taxRepository;

  private final TaxEditMapper taxEditMapper;

  private final TaxViewMapper taxViewMapper;

  @Autowired
  public TaxStorageServiceImpl(TaxRepository taxRepository, TaxEditMapper taxEditMapper, TaxViewMapper taxViewMapper) {
    this.taxRepository = taxRepository;
    this.taxEditMapper = taxEditMapper;
    this.taxViewMapper = taxViewMapper;
  }

  @Override
  public TaxView update(EditTaxRequest request) {
    Tax tax = taxRepository.getTaxById(request.getCountryCodeID());
    taxEditMapper.update(request, tax);
    tax = taxRepository.save(tax);
    return taxViewMapper.toTaxView(tax);
  }

  @Override
  public TaxView create(CreateTaxRequest request) {
    Tax tax = new Tax();
    tax.setCountryCodeID(request.getCountryCodeID());
    tax.setTax(request.getTax());
    taxRepository.save(tax);
    return taxViewMapper.toTaxView(tax);
  }

  @Override
  public TaxView delete(DeleteTaxRequest request) {
    Tax tax = taxRepository.getTaxById(request.getCountryCodeID());
    taxRepository.delete(tax);
    return taxViewMapper.toTaxView(tax);
  }

  @Override
  public TaxView get(ReadTaxRequest request) {
    Tax tax = taxRepository.getTaxById(request.getCountryCodeID());
    TaxView taxView = taxViewMapper.toTaxView(tax);
    log.info("Returning: " + taxView.toString());
    return taxView;
  }

  @Override
  public TaxViewList getAll(ReadAllTaxesRequest request) {
    List<Tax> taxes = taxRepository.findAll();
    List<TaxView> taxViews = new ArrayList<>();
    for (Tax tax : taxes) {
      taxViews.add(taxViewMapper.toTaxView(tax));
    }
    return new TaxViewList(taxViews);
  }


}
