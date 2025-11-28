package com.assignment2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Performs various data analysis operations on sales data using Java Streams API.
 * Demonstrates functional programming, stream operations, data aggregation, and lambda expressions.
 */
public class SalesAnalyzer {
    private final List<SalesRecord> salesRecords;

    public SalesAnalyzer(List<SalesRecord> salesRecords) {
        this.salesRecords = salesRecords;
    }

    /**
     * Calculates the total sales amount across all records.
     * 
     * @return Total sales amount
     */
    public double getTotalSales() {
        return salesRecords.stream()
                .mapToDouble(SalesRecord::getTotalAmount)
                .sum();
    }

    /**
     * Calculates the average sales amount per transaction.
     * 
     * @return Average sales amount
     */
    public double getAverageSales() {
        return salesRecords.stream()
                .mapToDouble(SalesRecord::getTotalAmount)
                .average()
                .orElse(0.0);
    }

    /**
     * Finds the maximum sales amount.
     * 
     * @return Maximum sales amount
     */
    public double getMaxSales() {
        return salesRecords.stream()
                .mapToDouble(SalesRecord::getTotalAmount)
                .max()
                .orElse(0.0);
    }

    /**
     * Finds the minimum sales amount.
     * 
     * @return Minimum sales amount
     */
    public double getMinSales() {
        return salesRecords.stream()
                .mapToDouble(SalesRecord::getTotalAmount)
                .min()
                .orElse(0.0);
    }

    /**
     * Groups sales by category and calculates total sales per category.
     * 
     * @return Map of category to total sales amount
     */
    public Map<String, Double> getSalesByCategory() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getCategory,
                    Collectors.summingDouble(SalesRecord::getTotalAmount)
                ));
    }

    /**
     * Groups sales by region and calculates total sales per region.
     * 
     * @return Map of region to total sales amount
     */
    public Map<String, Double> getSalesByRegion() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getRegion,
                    Collectors.summingDouble(SalesRecord::getTotalAmount)
                ));
    }

    /**
     * Groups sales by sales person and calculates total sales per person.
     * 
     * @return Map of sales person to total sales amount
     */
    public Map<String, Double> getSalesBySalesPerson() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getSalesPerson,
                    Collectors.summingDouble(SalesRecord::getTotalAmount)
                ));
    }

    /**
     * Finds the top N products by total sales amount.
     * 
     * @param n Number of top products to return
     * @return Map of product to total sales amount, sorted in descending order
     */
    public Map<String, Double> getTopProducts(int n) {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getProduct,
                    Collectors.summingDouble(SalesRecord::getTotalAmount)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }

    /**
     * Calculates total quantity sold by category.
     * 
     * @return Map of category to total quantity
     */
    public Map<String, Integer> getQuantityByCategory() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getCategory,
                    Collectors.summingInt(SalesRecord::getQuantity)
                ));
    }

    /**
     * Finds the region with the highest sales.
     * 
     * @return Optional containing the region name with highest sales
     */
    public Optional<String> getTopRegion() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getRegion,
                    Collectors.summingDouble(SalesRecord::getTotalAmount)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    /**
     * Finds the sales person with the highest sales.
     * 
     * @return Optional containing the sales person name with highest sales
     */
    public Optional<String> getTopSalesPerson() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getSalesPerson,
                    Collectors.summingDouble(SalesRecord::getTotalAmount)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    /**
     * Calculates average unit price by category.
     * 
     * @return Map of category to average unit price
     */
    public Map<String, Double> getAverageUnitPriceByCategory() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getCategory,
                    Collectors.averagingDouble(SalesRecord::getUnitPrice)
                ));
    }

    /**
     * Counts the number of transactions per region.
     * 
     * @return Map of region to transaction count
     */
    public Map<String, Long> getTransactionCountByRegion() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(
                    SalesRecord::getRegion,
                    Collectors.counting()
                ));
    }

    /**
     * Filters sales records by category and calculates total.
     * 
     * @param category Category to filter by
     * @return Total sales for the specified category
     */
    public double getSalesByCategory(String category) {
        return salesRecords.stream()
                .filter(record -> record.getCategory().equalsIgnoreCase(category))
                .mapToDouble(SalesRecord::getTotalAmount)
                .sum();
    }

    /**
     * Gets all unique products.
     * 
     * @return Set of unique product names
     */
    public Set<String> getUniqueProducts() {
        return salesRecords.stream()
                .map(SalesRecord::getProduct)
                .collect(Collectors.toSet());
    }

    /**
     * Calculates total quantity sold across all records.
     * 
     * @return Total quantity
     */
    public int getTotalQuantity() {
        return salesRecords.stream()
                .mapToInt(SalesRecord::getQuantity)
                .sum();
    }
}

