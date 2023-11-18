package com.teamk.scoretrack.module.commons.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractController {
    public final Logger LOGGER;

    protected AbstractController() {
        LOGGER = LoggerFactory.getLogger(this.getClass());
    }

    protected String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}
