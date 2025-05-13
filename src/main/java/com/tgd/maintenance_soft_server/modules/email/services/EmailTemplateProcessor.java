package com.tgd.maintenance_soft_server.modules.email.services;

import java.util.Map;

public interface EmailTemplateProcessor {

    String processTemplate(String template, Map<String, Object> variables);
}
