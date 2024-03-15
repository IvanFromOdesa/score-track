package com.teamk.scoretrack.module.security.commons.config;

import com.teamk.scoretrack.module.security.io.service.FileSanitizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import xyz.capybara.clamav.ClamavClient;

@Configuration
public class MissingBeanConfiguration {
    @Bean
    @ConditionalOnMissingBean(FileSanitizer.class)
    public FileSanitizer missingFileSanitizer(ClamavClient clamavClient) {
        return new FileSanitizer(clamavClient) {
            @Override
            public boolean runThroughAntivirus(MultipartFile multipartFile) {
                return true;
            }
        };
    }
}
