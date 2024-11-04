package com.teamk.scoretrack.module.commons.util;

import com.teamk.scoretrack.module.commons.util.log.MessageLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class SystemUtils {
    public static OS getOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win") ? OS.WIN : (os.contains("nix") || os.contains("nux") || os.contains("mac") ? OS.UNIX : OS.UNDEFINED);
    }

    public static boolean isRunningInWSL() {
        if (System.getenv("WSL_DISTRO_NAME") != null) {
            return true;
        }
        return getWSLVersion() != null;
    }

    public static String getWSLVersion() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("wsl", "--version");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString().trim();
            } else {
                return null;
            }
        } catch (Exception e) {
            MessageLogger.warn(e.getMessage());
            return null;
        }
    }

    public enum OS {
        WIN, UNIX, UNDEFINED;

        public boolean isUnix() {
            return this == UNIX;
        }

        public boolean isWin() {
            return this == WIN;
        }
    }
}
