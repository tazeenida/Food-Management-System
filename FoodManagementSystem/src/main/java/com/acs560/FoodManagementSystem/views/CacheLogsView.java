package com.acs560.FoodManagementSystem.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.acs560.FoodManagementSystem.config.LoggingCacheManager;
import jakarta.annotation.security.PermitAll;
import com.acs560.FoodManagementSystem.views.MainLayout;
import com.vaadin.flow.component.button.Button;

@PermitAll
@Route(value = "cache-logs", layout = MainLayout.class)
public class CacheLogsView extends VerticalLayout {

    private TextArea logArea;

    public CacheLogsView() {
        logArea = new TextArea("Cache Logs");
        logArea.setWidth("100%");
        logArea.setHeight("300px");
        logArea.setReadOnly(true);

        Button refreshButton = new Button("Refresh Logs", event -> refreshLogs());

        add(logArea, refreshButton);
    }

    private void refreshLogs() {
        // Get the latest log messages from the LoggingCacheManager
        StringBuilder logContent = new StringBuilder();
        for (String logMessage : LoggingCacheManager.getLogMessages()) {
            logContent.append(logMessage).append("\n");
        }
        logArea.setValue(logContent.toString());
    }
}
