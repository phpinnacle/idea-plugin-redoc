package com.phpinnacle.redoc.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.phpinnacle.redoc.RedocBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RedocConfigurable implements SearchableConfigurable {
    private final RedocForm form = new RedocForm();
    private final RedocSettings settings;

    public RedocConfigurable(@NotNull RedocSettings settings) {
        this.settings = settings;
    }

    @NotNull
    @Override
    public String getId() {
        return "settings.Redoc";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return RedocBundle.message("redoc.settings.name");
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return form.getComponent();
    }

    @Override
    public boolean isModified() {
        return form.isModified(settings);
    }

    @Override
    public void apply() throws ConfigurationException {
        form.validate();

        settings.setHideHostname(form.isHideHostname());
        settings.setRequiredPropertiesFirst(form.isRequiredPropertiesFirst());
        settings.setSortPropertiesAlphabetically(form.isSortPropertiesAlphabetically());
        settings.setShowExtensions(form.isShowExtensions());
        settings.setPathInMiddlePanel(form.isPathInMiddlePanel());
        settings.setHideLoading(form.isHideLoading());
        settings.setHideDownloadButton(form.isHideDownloadButton());
        settings.setDisableSearch(form.isDisableSearch());
        settings.setOnlyRequiredInSamples(form.isOnlyRequiredInSamples());
        settings.setExpandResponses(form.getExpandResponses());
        settings.setJsonSampleExpandLevel(form.getJsonSampleExpandLevel());

        ApplicationManager.getApplication().getMessageBus()
            .syncPublisher(RedocSettings.ChangeListener.TOPIC)
            .settingsChanged(settings);
    }

    @Override
    public void reset() {
        form.setHideHostname(settings.isHideHostname());
        form.setRequiredPropertiesFirst(settings.isRequiredPropertiesFirst());
        form.setSortPropertiesAlphabetically(settings.isSortPropertiesAlphabetically());
        form.setShowExtensions(settings.isShowExtensions());
        form.setPathInMiddlePanel(settings.isPathInMiddlePanel());
        form.setHideLoading(settings.isHideLoading());
        form.setHideDownloadButton(settings.isHideDownloadButton());
        form.setDisableSearch(settings.isDisableSearch());
        form.setOnlyRequiredInSamples(settings.isOnlyRequiredInSamples());
        form.setExpandResponses(settings.getExpandResponses());
        form.setJsonSampleExpandLevel(settings.getJsonSampleExpandLevel());
    }
}
