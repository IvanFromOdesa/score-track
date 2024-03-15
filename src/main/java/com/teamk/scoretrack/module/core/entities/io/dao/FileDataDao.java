package com.teamk.scoretrack.module.core.entities.io.dao;

import com.teamk.scoretrack.module.commons.base.dao.AbstractLongJpaDao;
import com.teamk.scoretrack.module.core.entities.io.FileData;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataDao extends AbstractLongJpaDao<FileData> {

}
