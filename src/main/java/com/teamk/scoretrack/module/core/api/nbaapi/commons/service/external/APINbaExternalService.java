package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.external;

import com.teamk.scoretrack.module.core.api.commons.rate_limiter.ApiRateLimiterService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.controller.APINba;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaResponseDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class APINbaExternalService {
    @Qualifier(APINba.NAME)
    protected final RestTemplate nbaApi;
    private final ApiRateLimiterService rateLimiterService;
    @Value("${nbaapi.rate.limit}")
    private int requestPerMinuteLimit;
    @Value("${nbaapi.request.limit}")
    private int limitQuotas;
    private final String apiName = "apiNba";

    @Autowired
    public APINbaExternalService(RestTemplate nbaApi, ApiRateLimiterService rateLimiterService) {
        this.nbaApi = nbaApi;
        this.rateLimiterService = rateLimiterService;
    }

    public <DTO extends APINbaResponseDto<?>> DTO callApi(String endpoint, Class<DTO> clazz, Logger logger) {
        return callApiSafe(endpoint, clazz, null, logger);
    }

    public <DTO extends APINbaResponseDto<?>> DTO callApiSafe(String endpoint, Class<DTO> clazz, DTO dto, Logger logger) {
        try {
            dto = rateLimiterService.withRateLimiter(() -> nbaApi.getForObject(endpoint, clazz), requestPerMinuteLimit, apiName, logger);
            logger.info(String.format("Response from API: %s", dto));
            if (dto.getResponse().isEmpty()) {
                dto.getErrorsAsMap().forEach((k, v) -> logger.error("Error - %s: %s".formatted(k, v)));
            }
            return dto;
        } catch (Exception e) {
            logger.error(String.format("Error calling API: %s", Arrays.toString(e.getStackTrace())));
            logger.error("Error msg: %s. Endpoint: %s".formatted(e.getMessage(), endpoint));
        }
        return dto;
    }

    public boolean isEnoughQuotasLeft(int requiredQuotas) {
        return rateLimiterService.isEnoughQuotasLeft(apiName, requiredQuotas, limitQuotas);
    }
}
