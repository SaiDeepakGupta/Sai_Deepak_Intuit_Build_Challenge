package com.assignment2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Main class to demonstrate sales data analysis using Java Streams API.
 */
public class Main {
    public static void main(String[] args) {
        // CSV file path - adjust relative to project root
        String csvFilePath = args.length > 0 ? args[0] : "../data/sales_data.csv";
        
        try {
            // Read sales data from CSV
            List<SalesRecord> salesRecords = CSVReader.readSalesData(csvFilePath);
            System.out.println("=".repeat(80));
            System.out.println("SALES DATA ANALYSIS - JAVA STREAMS API");
            System.out.println("=".repeat(80));
            System.out.println("\nTotal Records Loaded: " + salesRecords.size());
            System.out.println("\n" + "=".repeat(80));
            
            // Create analyzer instance
            SalesAnalyzer analyzer = new SalesAnalyzer(salesRecords);
            
            // Perform various analyses
            System.out.println("\n1. BASIC STATISTICS");
            System.out.println("-".repeat(80));
            System.out.printf("Total Sales Amount: $%.2f%n", analyzer.getTotalSales());
            System.out.printf("Average Sales Amount: $%.2f%n", analyzer.getAverageSales());
            System.out.printf("Maximum Sales Amount: $%.2f%n", analyzer.getMaxSales());
            System.out.printf("Minimum Sales Amount: $%.2f%n", analyzer.getMinSales());
            System.out.printf("Total Quantity Sold: %d units%n", analyzer.getTotalQuantity());
            
            System.out.println("\n2. SALES BY CATEGORY");
            System.out.println("-".repeat(80));
            Map<String, Double> salesByCategory = analyzer.getSalesByCategory();
            salesByCategory.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.printf("  %-20s: $%.2f%n", entry.getKey(), entry.getValue())
                    );
            
            System.out.println("\n3. SALES BY REGION");
            System.out.println("-".repeat(80));
            Map<String, Double> salesByRegion = analyzer.getSalesByRegion();
            salesByRegion.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.printf("  %-20s: $%.2f%n", entry.getKey(), entry.getValue())
                    );
            
            System.out.println("\n4. SALES BY SALES PERSON");
            System.out.println("-".repeat(80));
            Map<String, Double> salesBySalesPerson = analyzer.getSalesBySalesPerson();
            salesBySalesPerson.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.printf("  %-20s: $%.2f%n", entry.getKey(), entry.getValue())
                    );
            
            System.out.println("\n5. TOP 5 PRODUCTS BY SALES");
            System.out.println("-".repeat(80));
            Map<String, Double> topProducts = analyzer.getTopProducts(5);
            topProducts.forEach((product, sales) -> 
                System.out.printf("  %-20s: $%.2f%n", product, sales)
            );
            
            System.out.println("\n6. QUANTITY SOLD BY CATEGORY");
            System.out.println("-".repeat(80));
            Map<String, Integer> quantityByCategory = analyzer.getQuantityByCategory();
            quantityByCategory.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.printf("  %-20s: %d units%n", entry.getKey(), entry.getValue())
                    );
            
            System.out.println("\n7. AVERAGE UNIT PRICE BY CATEGORY");
            System.out.println("-".repeat(80));
            Map<String, Double> avgPriceByCategory = analyzer.getAverageUnitPriceByCategory();
            avgPriceByCategory.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.printf("  %-20s: $%.2f%n", entry.getKey(), entry.getValue())
                    );
            
            System.out.println("\n8. TRANSACTION COUNT BY REGION");
            System.out.println("-".repeat(80));
            Map<String, Long> transactionCount = analyzer.getTransactionCountByRegion();
            transactionCount.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.printf("  %-20s: %d transactions%n", entry.getKey(), entry.getValue())
                    );
            
            System.out.println("\n9. TOP PERFORMERS");
            System.out.println("-".repeat(80));
            Optional<String> topRegion = analyzer.getTopRegion();
            topRegion.ifPresent(region -> 
                System.out.printf("  Top Region: %s%n", region)
            );
            
            Optional<String> topSalesPerson = analyzer.getTopSalesPerson();
            topSalesPerson.ifPresent(person -> 
                System.out.printf("  Top Sales Person: %s%n", person)
            );
            
            System.out.println("\n10. UNIQUE PRODUCTS");
            System.out.println("-".repeat(80));
            System.out.println("  Total Unique Products: " + analyzer.getUniqueProducts().size());
            analyzer.getUniqueProducts().stream()
                    .sorted()
                    .forEach(product -> System.out.println("    - " + product));
            
            System.out.println("\n11. FILTERED ANALYSIS - ELECTRONICS CATEGORY");
            System.out.println("-".repeat(80));
            double electronicsSales = analyzer.getSalesByCategory("Electronics");
            System.out.printf("  Total Electronics Sales: $%.2f%n", electronicsSales);
            
            System.out.println("\n" + "=".repeat(80));
            System.out.println("ANALYSIS COMPLETE");
            System.out.println("=".repeat(80));
            
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

