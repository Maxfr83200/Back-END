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

        app.get("fr/menu/plats", ctx -> ctx.json(menuService.getMenuItemsByCategory("plats", true)));
        app.get("fr/menu/snacks", ctx -> ctx.json(menuService.getMenuItemsByCategory("snacks", true)));
        app.get("fr/menu/desserts", ctx -> ctx.json(menuService.getMenuItemsByCategory("desserts", true)));
        app.get("fr/menu/boissons", ctx -> ctx.json(menuService.getMenuItemsByCategory("boissons", true)));
        app.get("fr/menu/all", ctx -> ctx.json(menuService.getAllMenuItems(true)));

        app.get("eng/menu/plats", ctx -> ctx.json(menuService.getMenuItemsByCategory("plats", false)));
        app.get("eng/menu/snacks", ctx -> ctx.json(menuService.getMenuItemsByCategory("snacks", false)));
        app.get("eng/menu/desserts", ctx -> ctx.json(menuService.getMenuItemsByCategory("desserts", false)));
        app.get("eng/menu/boissons", ctx -> ctx.json(menuService.getMenuItemsByCategory("boissons", false)));
        app.get("eng/menu/all", ctx -> ctx.json(menuService.getAllMenuItems(false)));

        app.get("menu/available", ctx -> ctx.json(menuService.getAvailableMenuItems()));
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

        System.out.println("✅ API Javalin lancée : http://localhost:8080/fr/menu/all");
    }

    // petite classe pour les erreurs JSON
    public static class ErrorResponse {
        public final String error;
        public ErrorResponse(String error) { this.error = error; }
    }
}
