package com.kbe.storage.service.interfaces;


import com.github.dergil.kompsys.dto.update.UpdateStorage;
import com.github.dergil.kompsys.dto.update.UpdateStorageResponse;

import java.io.FileNotFoundException;

public interface CarStorageService {

  UpdateStorageResponse updateStorage(UpdateStorage request) throws FileNotFoundException;

}
