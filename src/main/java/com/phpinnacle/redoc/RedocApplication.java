package com.phpinnacle.redoc;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.vfs.VirtualFile;
import com.phpinnacle.redoc.settings.RedocSettings;
import com.phpinnacle.redoc.settings.RedocSettings.ChangeListener;
import org.jetbrains.annotations.NotNull;

class RedocApplication implements ApplicationComponent {
    private RedocServer server = RedocServer.getInstance();
    private RedocSettings settings = RedocSettings.getInstance();
    private FileDocumentManager manager = FileDocumentManager.getInstance();

    @Override
    public void initComponent() {
        ChangeListener listener = new ChangeListener() {
            @Override
            public void settingsChanged(@NotNull RedocSettings settings) {
            }
        };

        ApplicationManager.getApplication().getMessageBus().connect().subscribe(ChangeListener.TOPIC, listener);
    }

    @Override
    public void disposeComponent() {
        server.dispose();
    }

    boolean isAcceptable(@NotNull VirtualFile file) {
        return settings.getFileNames().contains(file.getName());
    }

    @NotNull
    FileEditor createEditor(@NotNull VirtualFile file) {
        Document document = manager.getDocument(file);

        server.attach(file.getPath(), document);

        return new RedocEditor(server, settings, file, document);
    }
}
