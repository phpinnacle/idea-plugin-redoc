package com.phpinnacle.redoc;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class Template {
    @NotNull
    private final String name;

    Template(@NotNull String name) {
        this.name = name;
    }

    public String render(Map<String, String> parameters) {
        InputStream stream = getClass().getResourceAsStream(name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String template = reader.lines().collect(Collectors.joining());

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return template;
    }
}
