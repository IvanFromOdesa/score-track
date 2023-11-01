package com.teamk.scoretrack.module.commons.mongo.dao;

import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MongoDao<DATA extends Identifier> extends MongoRepository<DATA, String> {
}
