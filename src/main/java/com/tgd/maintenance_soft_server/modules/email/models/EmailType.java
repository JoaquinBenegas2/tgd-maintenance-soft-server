package com.tgd.maintenance_soft_server.modules.email.models;

import lombok.Getter;

@Getter
public enum EmailType {
    CRITICAL_MAINTENANCE("critical-maintenance"),
    DELAYED_ROUTES("delayed-routes"),
    REPORT_A_PROBLEM("report-a-problem");

    private final String templateName;

    EmailType(String templateName) {
        this.templateName = templateName;
    }
}

