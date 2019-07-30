package com.phpinnacle.redoc;

import com.google.gson.Gson;
import com.phpinnacle.redoc.settings.RedocSettings;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RedocPanel {
    private WebEngine webEngine;
    private JPanel jPanel;
    private JFXPanel fPanel;
    private boolean ready = false;

    RedocPanel() {
        jPanel = new JPanel(new BorderLayout(), true);
        fPanel = new JFXPanel();

        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WebView webView = new WebView();

                AnchorPane anchorPane = new AnchorPane();
                AnchorPane.setTopAnchor(webView, 0.0);
                AnchorPane.setBottomAnchor(webView, 0.0);
                AnchorPane.setLeftAnchor(webView, 0.0);
                AnchorPane.setRightAnchor(webView, 0.0);
                anchorPane.getChildren().add(webView);

                fPanel.setScene(new Scene(anchorPane));

                webEngine = webView.getEngine();
                webEngine.setJavaScriptEnabled(true);
                webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        ready = true;
                    }
                });
                webEngine.loadContent(renderHTML());

                jPanel.add(fPanel, BorderLayout.CENTER);
            }
        });
    }

    @NotNull
    public synchronized JComponent getComponent() {
        return jPanel;
    }

    public void render(@NotNull final String spec, @NotNull final RedocSettings settings) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (ready) {
                    String options = new Gson().toJson(settings.getState());

                    webEngine.executeScript("render('" + spec + "', '" + options + "')");
                } else  {
                    render(spec, settings);
                }
            }
        });
    }

    private String renderHTML() {
        Template template = new Template("assets/index.html");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("js:redoc", getClass().getResource("assets/redoc.js").toExternalForm());
        parameters.put("css:redoc", getClass().getResource("assets/fonts.css").toExternalForm());

        return template.render(parameters);
    }
}
