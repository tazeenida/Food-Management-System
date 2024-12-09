package com.acs560.FoodManagementSystem.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.acs560.FoodManagementSystem.config.LoggingCacheManager;
import jakarta.annotation.security.PermitAll;
import com.acs560.FoodManagementSystem.views.MainLayout;
import com.vaadin.flow.component.button.Button;

/**
 * The {@link CacheLogsView} class provides a view for displaying and refreshing
 * the cache logs within the Food Management System. It allows users to view
 * the latest log messages from the {@link LoggingCacheManager} in a read-only
 * text area, with an option to refresh the displayed logs.
 *
 * <p>
 * The view includes a {@link TextArea} to display the cache logs and a {@link Button}
 * to trigger the log refresh action.
 * </p>
 */
@PermitAll
@Route(value = "cache-logs", layout = MainLayout.class)
public class CacheLogsView extends VerticalLayout {

    private TextArea logArea;

    /**
     * Constructs a new {@link CacheLogsView} instance.
     * Initializes the view with a {@link TextArea} for displaying the cache logs
     * and a {@link Button} to refresh the logs.
     */
    public CacheLogsView() {
        logArea = new TextArea("Cache Logs");
        logArea.setWidth("100%");
        logArea.setHeight("300px");
        logArea.setReadOnly(true);

        Button refreshButton = new Button("Refresh Logs", event -> refreshLogs());

        add(logArea, refreshButton);
    }

    /**
     * Refreshes the displayed cache logs by fetching the latest log messages
     * from the {@link LoggingCacheManager} and updating the {@link TextArea}
     * with the new content.
     */
    private void refreshLogs() {
        // Get the latest log messages from the LoggingCacheManager
        StringBuilder logContent = new StringBuilder();
        for (String logMessage : LoggingCacheManager.getLogMessages()) {
            logContent.append(logMessage).append("\n");
        }
        logArea.setValue(logContent.toString());
    }
}
