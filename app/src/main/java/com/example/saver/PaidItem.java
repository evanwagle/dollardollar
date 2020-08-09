package com.example.saver;

/*
 * Paid Item Class Object which is added to ArrayList
 */
public class PaidItem {
    private String cost;
    private String description;
    private String date;
    private String category;

    PaidItem(String description, String cost, String date, String category) {
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.category = category;
    }

    public String getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}
