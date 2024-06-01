package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerHeadshotImg(String externalId, @JsonProperty("img_url") String imgUrl) {
}
