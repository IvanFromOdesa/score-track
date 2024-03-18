package com.teamk.scoretrack.module.core.entities.io.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.core.entities.io.FileData;
import com.teamk.scoretrack.module.core.entities.io.dao.FileDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileDataEntityService extends AbstractJpaEntityService<FileData, Long, FileDataDao> {
    public Optional<FileData> getByExternalUrl(String externalUrl) {
        return dao.findByExternalUrl(externalUrl);
    }

    public Optional<FileData> getByName(String name) {
        return dao.findByName(name);
    }

    @Override
    @Autowired
    protected void setDao(FileDataDao dao) {
        this.dao = dao;
    }
}
