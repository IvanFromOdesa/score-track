package com.teamk.scoretrack.module.security.io.service;

import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.commands.scan.result.ScanResult;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Sanitizes files via <a href="https://github.com/cdarras/clamav-client?tab=readme-ov-file">ClamAV</a>
 */
//@Service
public class FileSanitizer {
    private final ClamavClient clamavClient;

    @Autowired
    public FileSanitizer(ClamavClient clamavClient) {
        this.clamavClient = clamavClient;
    }

    public boolean runThroughAntivirus(MultipartFile multipartFile) throws IOException {
        ScanResult res = clamavClient.scan(multipartFile.getInputStream());
        if (res instanceof ScanResult.OK) {
            return true;
        } else if (res instanceof ScanResult.VirusFound) {
            Map<String, Collection<String>> viruses = ((ScanResult.VirusFound) res).getFoundViruses();
            MessageLogger.warn("Found viruses on file %s: %s".formatted(multipartFile.getName(), viruses.toString()));
            // TODO: report viruses
        }
        return false;
    }
}
