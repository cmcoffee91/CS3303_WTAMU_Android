package com.mystore.cc916647.mystore;

/**
 * Created by cmcoffee91 on 4/9/18.
 */

public class StoreItem {

    private double price;
    private String name;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getItemPriceByName(String name)
    {
        Double itemPrice = 2.00;

        switch (name)
        {
            case "Dasani":
                itemPrice = 2.00;
                break;

            case "Fruit Oatmeal":
                itemPrice = 2.00;
                break;

            case "Hotcakes":
                itemPrice = 2.00;
                break;

            case "Sausage Biscuit":
                itemPrice = 1.99;
                break;

            case "Bacon Egg Biscuit":
                itemPrice = 2.00;
                break;

            case "Egg Sausage":
                itemPrice = 2.00;
                break;

            case "Sausage Burrito":
                itemPrice = 1.75;
                break;
        }

        return itemPrice;
    }

}
