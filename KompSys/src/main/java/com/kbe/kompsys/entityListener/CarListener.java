package com.kbe.kompsys.entityListener;


import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Version;
import com.kbe.kompsys.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarListener {

    private VersionRepository versionRepository;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void onPostAction(Car o) {
        if (versionRepository == null) {
            versionRepository = BeanUtil.getBean(VersionRepository.class);
            log.info("init repo" + versionRepository);
        }

        log.info("@Reference: " + versionRepository);
        log.info("Changing Version pre: " + versionRepository.getVersionByName(VersionRepository.CAR_REPO_VERSION));
        Version version = versionRepository.getVersionByName(VersionRepository.CAR_REPO_VERSION);
        log.info("Current Versionnumber: " + version.getVersionNumber());
        version.setVersionNumber(version.getVersionNumber() + 1);
        versionRepository.save(version);
    }

}
