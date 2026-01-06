package fr.isen.api;


import java.util.List;
import io.javalin.Javalin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {

        MenuService menuService = new MenuServiceImpl();

        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost()); // pratique pour JavaFX/localhost
            });
        }).start(8080);

        app.get("/health", ctx -> ctx.result("OK"));

        app.get("/menu/plats", ctx -> ctx.json(menuService.getMenuItemsByCategory("plats")));
        app.get("/menu/snacks", ctx -> ctx.json(menuService.getMenuItemsByCategory("snacks")));
        app.get("/menu/desserts", ctx -> ctx.json(menuService.getMenuItemsByCategory("desserts")));
        app.get("/menu/boissons", ctx -> ctx.json(menuService.getMenuItemsByCategory("boissons")));

        app.get("/menu", ctx -> ctx.json(menuService.getAllMenuItems()));

        app.get("/menu/available", ctx -> ctx.json(menuService.getAvailableMenuItems()));

        app.get("/menu/{id}", ctx -> {
            int id;
            try {
                id = Integer.parseInt(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorResponse("Invalid id"));
                return;
            }

            var item = menuService.getMenuItemById(id);
            if (item == null) {
                ctx.status(404).json(new ErrorResponse("Not found"));
            } else {
                ctx.json(item);
            }
        });

        System.out.println("✅ API Javalin lancée : http://localhost:8080/menu");
    }

    // petite classe pour les erreurs JSON
    public static class ErrorResponse {
        public final String error;
        public ErrorResponse(String error) { this.error = error; }
    }
}
