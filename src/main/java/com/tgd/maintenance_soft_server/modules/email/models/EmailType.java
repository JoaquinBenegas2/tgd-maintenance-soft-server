package com.tgd.maintenance_soft_server.modules.email.models;

import lombok.Getter;

@Getter
public enum EmailType {
    NOTIFY_TO_SUPERVISOR("notify-supervisor");

    private final String templateName;

    EmailType(String templateName) {
        this.templateName = templateName;
    }
}

