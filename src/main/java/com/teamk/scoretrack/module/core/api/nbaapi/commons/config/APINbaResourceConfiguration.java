package com.teamk.scoretrack.module.core.api.nbaapi.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerHeadshotImg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class APINbaResourceConfiguration {
    private static final String NBA_API = "nbaapi";
    public static final String PLAYERS_IMGS = NBA_API + "PlayerHeadshotImgs";

    @Bean(PLAYERS_IMGS)
    public Map<String, PlayerHeadshotImg> playerHeadshotImgMap(ObjectMapper mapper) {
        List<PlayerHeadshotImg> imgs = CommonsUtil.readResourcesIntoCollection(mapper, "static/api/nbaapi/players/img_list.json", PlayerHeadshotImg.class);
        return imgs.stream().collect(Collectors.toMap(PlayerHeadshotImg::externalId, Function.identity()));
    }
}
