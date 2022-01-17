package com.kbe.storage.service;

import com.kbe.storage.domain.dto.tax.EditTaxRequest;
import com.kbe.storage.domain.dto.tax.TaxView;
import com.kbe.storage.domain.mapper.TaxEditMapper;
import com.kbe.storage.domain.mapper.TaxViewMapper;
import com.kbe.storage.domain.model.Tax;
import com.kbe.storage.repository.TaxRepository;
import com.kbe.storage.service.interfaces.CsvImportService;
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


    public TaxStorageServiceImpl(TaxRepository taxRepository, TaxEditMapper taxEditMapper, TaxViewMapper taxViewMapper, CsvImportService csvImportService) {
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
    public TaxView create(EditTaxRequest request) {
        Tax tax = taxEditMapper.create(request);
        taxRepository.save(tax);
        return taxViewMapper.toTaxView(tax);
    }

    @Override
    public TaxView delete(String id) {
        Tax tax = taxRepository.getTaxById(id);
        taxRepository.delete(tax);
        return taxViewMapper.toTaxView(tax);
    }

    @Override
    public TaxView get(String id) {
        Tax tax = taxRepository.getTaxById(id);
        return taxViewMapper.toTaxView(tax);
    }

    @Override
    public List<TaxView> getAll() {
        List<Tax> taxs = taxRepository.findAll();
        List<TaxView> taxViews = new ArrayList<>();
        for (Tax tax : taxs) {
            taxViews.add(taxViewMapper.toTaxView(tax));
        }
        return taxViews;
    }
}
