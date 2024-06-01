package com.teamk.scoretrack.module.core.api.nbaapi.commons.dao.config;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.stereotype.Component;

/**
 * Configure mongo indexes
 */
@Component
public class APINbaMongoIndexing implements CommandLineRunner {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public APINbaMongoIndexing(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private void createIndexes() {
        IndexOperations indexOperations = mongoTemplate.indexOps(PlayerData.class);
        Sort.Direction asc = Sort.Direction.ASC;
        indexOperations.ensureIndex(new Index().on("externalId", asc));
        indexOperations.ensureIndex(new Index().on("teamBySeason.%s".formatted(SupportedSeasons.getOngoingSeason().name()), asc));
    }

    @Override
    public void run(String... args) throws Exception {
        createIndexes();
    }
}
