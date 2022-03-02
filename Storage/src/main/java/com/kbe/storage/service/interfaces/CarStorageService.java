package com.kbe.storage.service.interfaces;


import com.github.dergil.kompsys.dto.update.UpdateStorage;

import java.io.FileNotFoundException;

public interface CarStorageService {

  boolean updateStorage(UpdateStorage request) throws FileNotFoundException;

}
