package com.kbe.storage.service;

import com.github.dergil.kompsys.dto.tax.*;
import com.kbe.storage.domain.mapper.TaxEditMapper;
import com.kbe.storage.domain.mapper.TaxViewMapper;
import com.kbe.storage.domain.model.Tax;
import com.kbe.storage.repository.TaxRepository;
import com.kbe.storage.service.interfaces.TaxStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaxStorageServiceImpl implements TaxStorageService {

  @Autowired
  private final TaxRepository taxRepository;
  @Autowired
  private final TaxEditMapper taxEditMapper;
  @Autowired
  private final TaxViewMapper taxViewMapper;


  public TaxStorageServiceImpl(TaxRepository taxRepository, TaxEditMapper taxEditMapper, TaxViewMapper taxViewMapper) {
    this.taxRepository = taxRepository;
    this.taxEditMapper = taxEditMapper;
    this.taxViewMapper = taxViewMapper;
  }

  @Override
  public TaxView update(String id, EditTaxRequest request) {
    Tax tax = taxRepository.getTaxById(id);
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
    return taxViewMapper.toTaxView(tax);
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
