package com.teamk.scoretrack.module.commons.mongo.service;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoDao;
import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;
import com.teamk.scoretrack.module.commons.base.service.AbstractEntityService;

public abstract class AbstractMongoEntityService<ENTITY extends Identifier, DAO extends MongoDao<ENTITY>> extends AbstractEntityService<ENTITY, String, DAO> {
}
