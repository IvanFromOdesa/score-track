package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.external;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.controller.APINba;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class APINbaExternalService {
    @Qualifier(APINba.NAME)
    protected final RestTemplate nbaApi;

    @Autowired
    public APINbaExternalService(RestTemplate nbaApi) {
        this.nbaApi = nbaApi;
    }

    public <DTO> DTO callApi(String endpoint, Class<DTO> clazz, Logger logger) {
        return callApiSafe(endpoint, clazz, null, logger);
    }

    public <DTO> DTO callApiSafe(String endpoint, Class<DTO> clazz, DTO dto, Logger logger) {
        try {
            dto = nbaApi.getForObject(endpoint, clazz);
            logger.info(String.format("Response from API: %s", dto));
            return dto;
        } catch (Exception e) {
            logger.error(String.format("Error calling API: %s", Arrays.toString(e.getStackTrace())));
        }
        return dto;
    }
}
