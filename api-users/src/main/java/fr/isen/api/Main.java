package fr.isen.api;

import io.javalin.Javalin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Classe principale qui démarre le serveur API
 */
public class Main {
    private static UserService userService = new UserService();

    public static void main(String[] args) {
        // Créer et configurer l'application Javalin
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
            });
        }).start(7000);

        // Message de démarrage
        System.out.println("🚀 Serveur démarré sur http://localhost:7000");
        System.out.println("📋 Essayez : http://localhost:7000/users");

        // Route GET /users - Récupère tous les utilisateurs
        app.get("/users", ctx -> {
            ctx.json(userService.getAllUsers());
        });

        // Route GET /users/:id - Récupère un utilisateur par ID
        app.get("/users/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            userService.getUserById(id)
                    .ifPresentOrElse(
                            user -> ctx.json(user),
                            () -> ctx.status(404).result("Utilisateur non trouvé")
                    );
        });

        // Route POST /users - Ajoute un utilisateur
        app.post("/users", ctx -> {
            User newUser = ctx.bodyAsClass(User.class);
            User created = userService.addUser(newUser);
            ctx.status(201).json(created);
        });

        app.put("/users/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            User updatedUser = ctx.bodyAsClass(User.class);
            userService.updateUser(id, updatedUser)
                    .ifPresentOrElse(
                            user -> ctx.json(user),
                            () -> ctx.status(404).result("Utilisateur non trouvé")
                    );
        });

        app.delete("/users/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                ctx.status(204);
            } else {
                ctx.status(404).result("Utilisateur non trouvé");
            }
        });

        // Route GET / - Page d'accueil
        app.get("/", ctx -> {
            ctx.html(getWelcomeHTML());
        });
    }

    /**
     * Charge la page HTML d'accueil depuis les ressources
     */
    private static String getWelcomeHTML() {
        try {
            InputStream inputStream = Main.class.getClassLoader()
                    .getResourceAsStream("welcome.html");

            if (inputStream == null) {
                return "<h1>Erreur : Page non trouvée</h1>";
            }

            return new String(inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            return "<h1>Erreur de chargement</h1>";
        }
    }
}