package com.example.appuser;

public class ModelItem {
    private String name;
    private String price;

    public ModelItem(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}

