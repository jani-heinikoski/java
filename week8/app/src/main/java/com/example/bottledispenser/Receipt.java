package com.example.bottledispenser;

public class Receipt {
    private String item;
    private double itemPrice;
    private String date;

    public Receipt(String item, double itemPrice, String date) {
        this.item = item;
        this.itemPrice = itemPrice;
        this.date = date;
    }

    @Override
    public String toString() {
        String r = String.format("%s;%s;%.2f", date.toLowerCase(), item, itemPrice);
        if (r != null) {
            return r;
        } else {
            return "";
        }
    }
}
