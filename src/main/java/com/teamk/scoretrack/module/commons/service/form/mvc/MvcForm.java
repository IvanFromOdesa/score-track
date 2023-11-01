package com.teamk.scoretrack.module.commons.service.form.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public record MvcForm(Model model, String bundleName, Authentication authentication) {
}
