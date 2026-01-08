package fr.isen.api;

import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {

        MenuService menuService = new MenuServiceImpl();

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableRouteOverview("/routes");
            config.http.defaultContentType = "application/json";
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        }).start(8080);

        app.exception(NumberFormatException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST); // 400
            ctx.json(new ErrorResponse("L'identifiant (ID) doit être un nombre entier valide."));
        });

        app.exception(com.fasterxml.jackson.core.JsonProcessingException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST); // 400
            ctx.json(new ErrorResponse("Le format du JSON envoyé est invalide."));
        });

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace(); // On garde la trace dans la console serveur
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR); // 500
            ctx.json(new ErrorResponse("Une erreur interne est survenue : " + e.getMessage()));
        });

        app.error(HttpStatus.NOT_FOUND, ctx -> {
            ctx.json(new ErrorResponse("Cette route n'existe pas."));
        });


        app.get("/health", ctx -> ctx.result("OK"));


        app.get("fr/menu/plats", ctx -> ctx.json(menuService.getMenuItemsByCategory("plats", true)));
        app.get("fr/menu/snacks", ctx -> ctx.json(menuService.getMenuItemsByCategory("snacks", true)));
        app.get("fr/menu/desserts", ctx -> ctx.json(menuService.getMenuItemsByCategory("desserts", true)));
        app.get("fr/menu/boissons", ctx -> ctx.json(menuService.getMenuItemsByCategory("boissons", true)));
        app.get("fr/menu/all", ctx -> ctx.json(menuService.getAllMenuItems(true)));

        app.get("fr/menu/{id}", ctx -> handleGetItem(ctx, menuService, true));

        app.put("fr/menu/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));

            if (menuService.getMenuItemById(id, true) == null) {
                throw new NotFoundException("Item introuvable pour mise à jour");
            }

            MenuItemUpdateRequest body = ctx.bodyAsClass(MenuItemUpdateRequest.class);

            // Validation simple des données
            if(body.name == null || body.name.isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse("Le nom ne peut pas être vide"));
                return;
            }

            menuService.updateMenuItemBasic(
                    id,
                    body.name,
                    body.description,
                    body.price,
                    body.available);

            ctx.status(HttpStatus.NO_CONTENT);
        });

        app.get("eng/menu/plats", ctx -> ctx.json(menuService.getMenuItemsByCategory("plats", false)));
        app.get("eng/menu/snacks", ctx -> ctx.json(menuService.getMenuItemsByCategory("snacks", false)));
        app.get("eng/menu/desserts", ctx -> ctx.json(menuService.getMenuItemsByCategory("desserts", false)));
        app.get("eng/menu/boissons", ctx -> ctx.json(menuService.getMenuItemsByCategory("boissons", false)));
        app.get("eng/menu/all", ctx -> ctx.json(menuService.getAllMenuItems(false)));

        app.get("eng/menu/{id}", ctx -> handleGetItem(ctx, menuService, false));

        app.get("menu/available", ctx -> ctx.json(menuService.getAvailableMenuItems()));

        System.out.println("✅ API Javalin lancée : http://localhost:8080/fr/menu/all");
    }

    private static void handleGetItem(Context ctx, MenuService service, boolean isFrench) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        var item = service.getMenuItemById(id, isFrench);

        if (item == null) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse("Item non trouvé"));
        } else {
            ctx.json(item);
        }
    }

    public static class ErrorResponse {
        public final String error;
        public ErrorResponse(String error) { this.error = error; }
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) { super(message); }
    }
}
