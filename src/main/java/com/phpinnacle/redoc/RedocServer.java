package com.phpinnacle.redoc;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Disposer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

class RedocServer implements Disposable {
    private RootHandler handler = new RootHandler();
    private HttpServer server;

    public RedocServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/", handler);
        server.start();
    }

    public static RedocServer getInstance() {
        return ServiceManager.getService(RedocServer.class);
    }

    void attach(@NotNull String spec, @NotNull Document document) {
        handler.attach(spec, document);
    }

    int getPort()
    {
        return server.getAddress().getPort();
    }

    @Override
    public void dispose() {
        Disposer.dispose(this);

        server.stop(0);
    }

    private static class RootHandler implements HttpHandler {
        private Map<String, Document> documents = new HashMap<>();

        void attach(String spec, @NotNull Document document) {
            documents.put(spec, document);
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String text = "";
            int status = 200;

            switch (httpExchange.getRequestMethod()) {
                case "OPTIONS":
                    httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET");
                    httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept");

                    break;
                case "GET":
                    httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

                    String path = httpExchange.getRequestURI().getPath();

                    if (documents.containsKey(path)) {
                        text = documents.get(path).getText();
                    } else {
                        status = 404;
                    }

                    break;
            }

            byte[] response = text.getBytes(StandardCharsets.UTF_8);

            httpExchange.sendResponseHeaders(status, response.length);

            OutputStream os = httpExchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}
