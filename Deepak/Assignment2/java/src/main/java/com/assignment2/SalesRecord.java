package com.assignment2;

import java.time.LocalDate;

/**
 * Represents a sales record from the CSV file.
 */
public class SalesRecord {
    private LocalDate date;
    private String product;
    private String category;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String region;
    private String salesPerson;

    public SalesRecord(LocalDate date, String product, String category, int quantity,
                      double unitPrice, double totalAmount, String region, String salesPerson) {
        this.date = date;
        this.product = product;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.region = region;
        this.salesPerson = salesPerson;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public String getProduct() { return product; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalAmount() { return totalAmount; }
    public String getRegion() { return region; }
    public String getSalesPerson() { return salesPerson; }

    @Override
    public String toString() {
        return String.format("SalesRecord{date=%s, product='%s', category='%s', quantity=%d, " +
                "unitPrice=%.2f, totalAmount=%.2f, region='%s', salesPerson='%s'}", 
                date, product, category, quantity, unitPrice, totalAmount, region, salesPerson);
    }
}

