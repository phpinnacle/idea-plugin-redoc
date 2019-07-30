package com.phpinnacle.redoc;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.phpinnacle.redoc.settings.RedocSettings;
import com.phpinnacle.redoc.settings.RedocSettings.ChangeListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class RedocEditor extends UserDataHolderBase implements FileEditor, Disposable {
    private boolean update = true;

    private final RedocPanel panel = new RedocPanel();

    private final RedocServer server;
    private final RedocSettings settings;
    private final VirtualFile file;
    private final Document document;

    RedocEditor(@NotNull RedocServer server, @NotNull RedocSettings settings, @NotNull VirtualFile file, @NotNull Document document) {
        this.server = server;
        this.settings = settings;
        this.file = file;
        this.document = document;

        setupListeners();
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return panel.getComponent();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return getComponent();
    }

    @NotNull
    @Override
    public String getName() {
        return "Redoc";
    }

    @NotNull
    public FileEditorState getState(@NotNull FileEditorStateLevel fileEditorStateLevel) {
        return FileEditorState.INSTANCE;
    }

    @Override
    public void setState(@NotNull FileEditorState state) {
        // empty
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void selectNotify() {
        if (update) {
            render();
        }
    }

    @Override
    public void deselectNotify() {

    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {
        Disposer.dispose(this);
    }

    private void render() {
        String spec = "http://localhost:" + server.getPort() + file.getPath();

        panel.render(spec, settings);

        update = false;
    }

    private void setupListeners() {
        this.document.addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                update = true;
            }
        }, this);

        ApplicationManager.getApplication().getMessageBus()
            .connect(this)
            .subscribe(ChangeListener.TOPIC, new ChangeListener() {
                @Override
                public void settingsChanged(@NotNull RedocSettings settings) {
                    render();
                }
            });
    }
}
