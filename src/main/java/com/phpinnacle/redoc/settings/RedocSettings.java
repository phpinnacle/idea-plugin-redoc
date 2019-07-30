package com.phpinnacle.redoc.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.messages.Topic;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@State(
    name = "RedocSettings",
    storages = @Storage("redoc.xml")
)
public final class RedocSettings implements PersistentStateComponent<RedocSettings.State> {
    private State state = new State();
    private static final ArrayList<String> names =  new ArrayList<>();

    RedocSettings() {
        names.add("openapi.yml");
        names.add("openapi.yaml");
        names.add("openapi.json");
        names.add("swagger.yml");
        names.add("swagger.yaml");
        names.add("swagger.json");
    }

    @NotNull
    public static RedocSettings getInstance() {
        return ServiceManager.getService(RedocSettings.class);
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    boolean isHideHostname() {
        return state.hideHostname;
    }

    void setHideHostname(boolean value) {
        state.hideHostname = value;
    }

    boolean isRequiredPropertiesFirst() {
        return state.requiredPropertiesFirst;
    }

    void setRequiredPropertiesFirst(boolean value) {
        state.requiredPropertiesFirst = value;
    }

    boolean isSortPropertiesAlphabetically() {
        return state.sortPropertiesAlphabetically;
    }

    void setSortPropertiesAlphabetically(boolean value) {
        state.sortPropertiesAlphabetically = value;
    }

    boolean isShowExtensions() {
        return state.showExtensions;
    }

    void setShowExtensions(boolean value) {
        state.showExtensions = value;
    }

    boolean isPathInMiddlePanel() {
        return state.pathInMiddlePanel;
    }

    void setPathInMiddlePanel(boolean value) {
        state.pathInMiddlePanel = value;
    }

    boolean isHideLoading() {
        return state.hideLoading;
    }

    void setHideLoading(boolean value) {
        state.hideLoading = value;
    }

    boolean isHideDownloadButton() {
        return state.hideDownloadButton;
    }

    void setHideDownloadButton(boolean value) {
        state.hideDownloadButton = value;
    }

    boolean isDisableSearch() {
        return state.disableSearch;
    }

    void setDisableSearch(boolean value) {
        state.disableSearch = value;
    }

    boolean isOnlyRequiredInSamples() {
        return state.onlyRequiredInSamples;
    }

    void setOnlyRequiredInSamples(boolean value) {
        state.onlyRequiredInSamples = value;
    }

    String getExpandResponses() {
        return state.expandResponses;
    }

    void setExpandResponses(String value) {
        state.expandResponses = value;
    }

    String getJsonSampleExpandLevel() {
        return state.jsonSampleExpandLevel;
    }

    void setJsonSampleExpandLevel(String value) {
        state.jsonSampleExpandLevel = value;
    }

    public ArrayList<String> getFileNames() {
        return names;
    }

    static final class State {
        @Attribute("hideHostname")
        private boolean hideHostname = false;
        @Attribute("hideHostname")
        private boolean requiredPropertiesFirst = false;
        @Attribute("hideHostname")
        private boolean sortPropertiesAlphabetically = false;
        @Attribute("ShowExtensions")
        private boolean showExtensions = true;
        @Attribute("PathInMiddlePanel")
        private boolean pathInMiddlePanel = false;
        @Attribute("HideLoading")
        private boolean hideLoading = false;
        @Attribute("HideDownloadButton")
        private boolean hideDownloadButton = false;
        @Attribute("DisableSearch")
        private boolean disableSearch = false;
        @Attribute("OnlyRequiredInSamples")
        private boolean onlyRequiredInSamples = false;
        @Attribute("ExpandResponses")
        private String expandResponses = "all";
        @Attribute("JsonSampleExpandLevel")
        private String jsonSampleExpandLevel = "all";
    }

    public interface ChangeListener {
        Topic<ChangeListener> TOPIC = Topic.create("RedocSettingsChanged", ChangeListener.class);

        default void settingsChanged(@NotNull RedocSettings settings) { }
    }
}
