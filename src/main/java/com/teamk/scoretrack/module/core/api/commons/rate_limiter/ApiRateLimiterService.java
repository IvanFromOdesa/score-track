package com.teamk.scoretrack.module.core.api.commons.rate_limiter;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class ApiRateLimiterService {
    private final ApiDailyRequestService dailyRequestService;

    @Autowired
    public ApiRateLimiterService(ApiDailyRequestService dailyRequestService) {
        this.dailyRequestService = dailyRequestService;
    }

    /**
     * Limits the rate of requests per minute to the external api.
     * @param apiCall actual api call
     * @param rpmLimit request per minute limit
     * @param apiName name of the api to call
     * @param logger execution logger
     * @return api call resulting dto, with rate limiting
     * @param <DTO> dto type
     * @throws InterruptedException if the current thread was interrupted
     * @apiNote <a href="https://stackoverflow.com/questions/78398818/java-schedule-to-run-periodically-vs-thread-sleep/78399798#78399798">Details</a>
     */
    public synchronized <DTO> DTO withRateLimiter(Supplier<DTO> apiCall, int rpmLimit, String apiName, Logger logger) throws InterruptedException {
        Optional<ApiDailyRequest> wrapper = dailyRequestService.get(apiName);
        if (wrapper.isPresent()) {
            ApiDailyRequest request = wrapper.get();
            int totalReqCount = request.getTotalReqCount();
            if (totalReqCount % rpmLimit == 0) {
                int toSleep = 60;
                logger.warn("Sleeping for %s s".formatted(toSleep));
                TimeUnit.SECONDS.sleep(60);
            }
            request.setTotalReqCount(totalReqCount + 1);
            dailyRequestService.cache(request);
        } else {
            dailyRequestService.cache(new ApiDailyRequest(apiName));
        }
        return apiCall.get();
    }

    public boolean isEnoughQuotasLeft(String apiName, int requiredQuotas, int limitQuotas) {
        Optional<ApiDailyRequest> request = dailyRequestService.get(apiName);
        return request.map(apiDailyRequest -> limitQuotas - apiDailyRequest.getTotalReqCount() >= requiredQuotas).orElse(true);
    }

    /**
     * Reset every day at 00:00 UTC
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetQuotas() {
        dailyRequestService.evictAll();
    }
}
