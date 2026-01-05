package fr.isen.api;


public class MenuItem {
    private final int id;
    private final String name;
    private final String description;
    private final double price;
    private final String imageUrl;
    private final int calories;
    private final boolean isAvailable;
    private final boolean isSpicy;
    private final boolean isVegetarian;
    private final boolean proteinRequired;

    public MenuItem(int id, String name, String description, double price, String imageUrl, int calories,
                    boolean isAvailable, boolean isSpicy, boolean isVegetarian, boolean proteinRequired) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.calories = calories;
        this.isAvailable = isAvailable;
        this.isSpicy = isSpicy;
        this.isVegetarian = isVegetarian;
        this.proteinRequired = proteinRequired;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public int getCalories() { return calories; }
    public boolean isAvailable() { return isAvailable; }
    public boolean isSpicy() { return isSpicy; }
    public boolean isVegetarian() { return isVegetarian; }
    public boolean isProteinRequired() { return proteinRequired; }
}
