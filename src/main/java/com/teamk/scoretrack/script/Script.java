package com.teamk.scoretrack.script;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Script(String name, String description, String path) {
}
