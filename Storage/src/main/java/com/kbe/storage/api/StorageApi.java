package com.kbe.storage.api;

import com.kbe.storage.domain.dto.tax.EditTaxRequest;
import com.kbe.storage.domain.dto.tax.TaxView;
import com.kbe.storage.service.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/storage/")
public class StorageApi {

    private final StorageServiceImpl storageService;

    @Autowired
    public StorageApi(StorageServiceImpl storageService) {
        this.storageService = storageService;
    }


    @PostMapping()
    public TaxView create(@RequestBody @Valid EditTaxRequest request) {
        return storageService.create(request);
    }

    @PutMapping("{id}")
    public TaxView update(@PathVariable String id, @RequestBody @Valid EditTaxRequest request) {
        return storageService.update(id, request);
    }

    @DeleteMapping("{id}")
    public TaxView delete(@PathVariable String id) {
        return storageService.delete(id);
    }

    @GetMapping("{id}")
    public TaxView get(@PathVariable String id) {
        return storageService.get(id);
    }

    @GetMapping()
    public List<TaxView> getAll() {
        return storageService.getAll();
    }
}
