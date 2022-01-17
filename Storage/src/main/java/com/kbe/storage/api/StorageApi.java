package com.kbe.storage.api;

import com.kbe.storage.domain.dto.tax.EditTaxRequest;
import com.kbe.storage.domain.dto.tax.TaxView;
import com.kbe.storage.service.CarStorageServiceImpl;
import com.kbe.storage.service.TaxStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/storage/")
public class StorageApi {
    @Autowired
    private final TaxStorageServiceImpl taxStorageService;
    @Autowired
    private final CarStorageServiceImpl carStorageService;

    @Autowired
    public StorageApi(TaxStorageServiceImpl storageService, CarStorageServiceImpl carStorageService) {
        this.taxStorageService = storageService;
        this.carStorageService = carStorageService;
    }


    @PostMapping()
    public TaxView create(@RequestBody @Valid EditTaxRequest request) {
        return taxStorageService.create(request);
    }

    @PutMapping("{id}")
    public TaxView update(@PathVariable String id, @RequestBody @Valid EditTaxRequest request) {
        return taxStorageService.update(id, request);
    }

    @DeleteMapping("{id}")
    public TaxView delete(@PathVariable String id) {
        return taxStorageService.delete(id);
    }

    @GetMapping("{id}")
    public TaxView get(@PathVariable String id) {
        return taxStorageService.get(id);
    }

    @GetMapping()
    public List<TaxView> getAll() {
        return taxStorageService.getAll();
    }

    @GetMapping("/taxes")
    public boolean getTaxes() throws IOException, NoSuchAlgorithmException {
        carStorageService.testChecksum("");
        return false;
        //storageService.updateDatabaseByCsv();
    }
}
