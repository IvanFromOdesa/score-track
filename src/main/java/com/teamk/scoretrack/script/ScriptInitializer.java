package com.teamk.scoretrack.script;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.commons.util.SystemUtils;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ScriptInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String SCRIPTS_PATH = "./bin/scripts.json";
    private final SystemUtils.OS operatingSystem;

    public ScriptInitializer() {
        this.operatingSystem = SystemUtils.getOperatingSystem();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println(":: SCRIPTS INITIALIZER ::");
        System.out.println("This initializer runs predefined shell scripts to perform various setup tasks.");
        System.out.println("These tasks might include database backups, data migrations, or other essential operations.");

        if (!getUserConfirmation("Do you want to run the script initializer? (y/n): ")) {
            System.out.println("Skipping script initialization.");
            return;
        }

        if (operatingSystem.isWin() && !SystemUtils.isRunningInWSL()) {
            System.out.println("WSL is not available. Skipping script execution and continuing initialization.");
            return;
        } else if (operatingSystem.isWin()) {
            System.out.println("Running on Windows with WSL.");
        } else if (operatingSystem.isUnix()) {
            System.out.println("Running on a Unix-based OS.");
        } else {
            System.out.println("Unsupported OS to run script initialization.");
            return;
        }

        for (Script script : loadScripts()) {
            System.out.println("Script: " + script.name());
            System.out.println("Description: " + script.description());

            if (getUserConfirmation("Do you want to run this script? (y/n): ")) {
                runScript(script.path());
            }
        }
    }

    private List<Script> loadScripts() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(SCRIPTS_PATH);
            return objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.out.println("Error loading scripts.json: " + e.getMessage());
            return List.of();
        }
    }

    private void runScript(String scriptPath) {
        try {
            ProcessBuilder processBuilder = operatingSystem.isUnix()
                    ? new ProcessBuilder("bash", scriptPath)
                    : new ProcessBuilder("wsl", "bash", "-c", scriptPath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Script exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            MessageLogger.error(e.getMessage());
        }
    }

    private boolean getUserConfirmation(String prompt) {
        String input;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(prompt);
            try {
                input = reader.readLine();
                if ("y".equalsIgnoreCase(input)) {
                    return true;
                } else if ("n".equalsIgnoreCase(input)) {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            } catch (IOException e) {
                MessageLogger.error(e.getMessage());
            }
        }
    }
}
