package com.kbe.kompsys.repository;

import com.github.dergil.kompsys.dto.exception.NotFoundException;
import com.kbe.kompsys.domain.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {
    String CAR_REPO_VERSION = "CAR_REPO_VERSION";

    default Version getVersionByName(String name) {
        return findById(name).orElseThrow(() -> new NotFoundException(Version.class, name));
    }


}
