package com.kbe.storage.api;

import com.kbe.storage.domain.dto.car.EditCarRequest;
import com.kbe.storage.domain.model.Car;
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
    public Car create(@RequestBody @Valid EditCarRequest request) {
        return storageService.create(request);
    }

    @PutMapping("{id}")
    public Car update(@PathVariable long id, @RequestBody @Valid EditCarRequest request) {
        return storageService.update(id, request);
    }

    @DeleteMapping("{id}")
    public Car delete(@PathVariable long id) {
        return storageService.delete(id);
    }

    @GetMapping("{id}")
    public Car get(@PathVariable long id) {
        return storageService.get(id);
    }

    @GetMapping()
    public List<Car> getAll() {
        return storageService.getAll();
    }
}
