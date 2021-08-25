package com.computeprice.cc916647.computeprice;

import java.io.Serializable;

/**
 * Created by cc916647 on 2/14/18.
 */

public class Item implements Serializable {

    private String itemName;
    private String itemPrice;
    private String itemQuantity;

    public Item( String itemName, String itemPrice, String itemQuantity)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }


}
