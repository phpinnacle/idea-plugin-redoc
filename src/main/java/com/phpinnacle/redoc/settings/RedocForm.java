package com.phpinnacle.redoc.settings;

import com.intellij.openapi.options.ConfigurationException;

import javax.swing.*;

public class RedocForm {
    private JPanel mainPanel;
    private JCheckBox hideHostname;
    private JCheckBox requiredPropertiesFirst;
    private JCheckBox sortPropertiesAlphabetically;
    private JCheckBox showExtensions;
    private JCheckBox pathInMiddlePanel;
    private JCheckBox hideLoading;
    private JCheckBox hideDownloadButton;
    private JCheckBox disableSearch;
    private JCheckBox onlyRequiredInSamples;
    private JTextField expandResponses;
    private JTextField jsonSampleExpandLevel;

    public JComponent getComponent() {
        return mainPanel;
    }

    public boolean isModified(RedocSettings settings) {
        return
            isHideHostname() != settings.isHideHostname() ||
            isRequiredPropertiesFirst() != settings.isRequiredPropertiesFirst() ||
            isSortPropertiesAlphabetically() != settings.isSortPropertiesAlphabetically() ||
            isShowExtensions() != settings.isShowExtensions() ||
            isPathInMiddlePanel() != settings.isPathInMiddlePanel() ||
            isHideLoading() != settings.isHideLoading() ||
            isHideDownloadButton() != settings.isHideDownloadButton() ||
            isDisableSearch() != settings.isDisableSearch() ||
            isOnlyRequiredInSamples() != settings.isOnlyRequiredInSamples() ||
            !getExpandResponses().equalsIgnoreCase(settings.getExpandResponses()) ||
            !getJsonSampleExpandLevel().equalsIgnoreCase(settings.getJsonSampleExpandLevel());
    }

    boolean isHideHostname() {
        return hideHostname.isSelected();
    }

    void setHideHostname(boolean value) {
        hideHostname.setSelected(value);
    }

    boolean isRequiredPropertiesFirst() {
        return requiredPropertiesFirst.isSelected();
    }

    void setRequiredPropertiesFirst(boolean value) {
        requiredPropertiesFirst.setSelected(value);
    }

    boolean isSortPropertiesAlphabetically() {
        return sortPropertiesAlphabetically.isSelected();
    }

    void setSortPropertiesAlphabetically(boolean value) {
        sortPropertiesAlphabetically.setSelected(value);
    }

    boolean isShowExtensions() {
        return showExtensions.isSelected();
    }

    void setShowExtensions(boolean value) {
        showExtensions.setSelected(value);
    }

    boolean isPathInMiddlePanel() {
        return pathInMiddlePanel.isSelected();
    }

    void setPathInMiddlePanel(boolean value) {
        pathInMiddlePanel.setSelected(value);
    }

    boolean isHideLoading() {
        return hideLoading.isSelected();
    }

    void setHideLoading(boolean value) {
        hideLoading.setSelected(value);
    }

    boolean isHideDownloadButton() {
        return hideDownloadButton.isSelected();
    }

    void setHideDownloadButton(boolean value) {
        hideDownloadButton.setSelected(value);
    }

    boolean isDisableSearch() {
        return disableSearch.isSelected();
    }

    void setDisableSearch(boolean value) {
        disableSearch.setSelected(value);
    }

    boolean isOnlyRequiredInSamples() {
        return onlyRequiredInSamples.isSelected();
    }

    void setOnlyRequiredInSamples(boolean value) {
        onlyRequiredInSamples.setSelected(value);
    }

    String getExpandResponses() {
        return expandResponses.getText();
    }

    void setExpandResponses(String value) {
        expandResponses.setText(value);
    }

    String getJsonSampleExpandLevel() {
        return jsonSampleExpandLevel.getText();
    }

    void setJsonSampleExpandLevel(String value) {
        jsonSampleExpandLevel.setText(value);
    }

    public void validate() throws ConfigurationException {
    }
}
