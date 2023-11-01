package com.teamk.scoretrack.module.commons.mongo.domain;

import com.teamk.scoretrack.module.commons.domain.IdAware;
import org.springframework.data.annotation.Id;

public class Identifier implements IdAware<String> {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
