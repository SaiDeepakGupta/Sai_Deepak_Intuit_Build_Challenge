package com.assignment2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Unit tests for SalesAnalyzer class.
 */
public class SalesAnalyzerTest {
    private SalesAnalyzer analyzer;
    private List<SalesRecord> testRecords;

    @BeforeEach
    void setUp() {
        testRecords = new ArrayList<>();
        testRecords.add(new SalesRecord(
            LocalDate.of(2024, 1, 15), "Laptop", "Electronics", 5, 1200.00, 6000.00, "North", "John Smith"
        ));
        testRecords.add(new SalesRecord(
            LocalDate.of(2024, 1, 16), "Desk Chair", "Furniture", 10, 250.00, 2500.00, "South", "Mary Johnson"
        ));
        testRecords.add(new SalesRecord(
            LocalDate.of(2024, 1, 17), "Wireless Mouse", "Electronics", 25, 35.00, 875.00, "East", "John Smith"
        ));
        testRecords.add(new SalesRecord(
            LocalDate.of(2024, 1, 18), "Office Desk", "Furniture", 8, 450.00, 3600.00, "West", "Robert Brown"
        ));
        testRecords.add(new SalesRecord(
            LocalDate.of(2024, 1, 19), "Monitor", "Electronics", 12, 300.00, 3600.00, "North", "Mary Johnson"
        ));
        
        analyzer = new SalesAnalyzer(testRecords);
    }

    @Test
    void testGetTotalSales() {
        double expected = 6000.00 + 2500.00 + 875.00 + 3600.00 + 3600.00;
        assertEquals(expected, analyzer.getTotalSales(), 0.01);
    }

    @Test
    void testGetAverageSales() {
        double expected = (6000.00 + 2500.00 + 875.00 + 3600.00 + 3600.00) / 5.0;
        assertEquals(expected, analyzer.getAverageSales(), 0.01);
    }

    @Test
    void testGetMaxSales() {
        assertEquals(6000.00, analyzer.getMaxSales(), 0.01);
    }

    @Test
    void testGetMinSales() {
        assertEquals(875.00, analyzer.getMinSales(), 0.01);
    }

    @Test
    void testGetSalesByCategory() {
        Map<String, Double> salesByCategory = analyzer.getSalesByCategory();
        
        assertEquals(2, salesByCategory.size());
        assertEquals(6000.00 + 875.00 + 3600.00, salesByCategory.get("Electronics"), 0.01);
        assertEquals(2500.00 + 3600.00, salesByCategory.get("Furniture"), 0.01);
    }

    @Test
    void testGetSalesByRegion() {
        Map<String, Double> salesByRegion = analyzer.getSalesByRegion();
        
        assertEquals(4, salesByRegion.size());
        assertEquals(6000.00 + 3600.00, salesByRegion.get("North"), 0.01);
        assertEquals(2500.00, salesByRegion.get("South"), 0.01);
        assertEquals(875.00, salesByRegion.get("East"), 0.01);
        assertEquals(3600.00, salesByRegion.get("West"), 0.01);
    }

    @Test
    void testGetSalesBySalesPerson() {
        Map<String, Double> salesBySalesPerson = analyzer.getSalesBySalesPerson();
        
        assertEquals(3, salesBySalesPerson.size());
        assertEquals(6000.00 + 875.00, salesBySalesPerson.get("John Smith"), 0.01);
        assertEquals(2500.00 + 3600.00, salesBySalesPerson.get("Mary Johnson"), 0.01);
        assertEquals(3600.00, salesBySalesPerson.get("Robert Brown"), 0.01);
    }

    @Test
    void testGetTopProducts() {
        Map<String, Double> topProducts = analyzer.getTopProducts(3);
        
        assertEquals(3, topProducts.size());
        // Check that products are sorted in descending order
        List<Double> values = new ArrayList<>(topProducts.values());
        assertTrue(values.get(0) >= values.get(1));
        assertTrue(values.get(1) >= values.get(2));
    }

    @Test
    void testGetQuantityByCategory() {
        Map<String, Integer> quantityByCategory = analyzer.getQuantityByCategory();
        
        assertEquals(2, quantityByCategory.size());
        assertEquals(5 + 25 + 12, quantityByCategory.get("Electronics"));
        assertEquals(10 + 8, quantityByCategory.get("Furniture"));
    }

    @Test
    void testGetTopRegion() {
        Optional<String> topRegion = analyzer.getTopRegion();
        assertTrue(topRegion.isPresent());
        assertEquals("North", topRegion.get());
    }

    @Test
    void testGetTopSalesPerson() {
        Optional<String> topSalesPerson = analyzer.getTopSalesPerson();
        assertTrue(topSalesPerson.isPresent());
        assertEquals("John Smith", topSalesPerson.get());
    }

    @Test
    void testGetAverageUnitPriceByCategory() {
        Map<String, Double> avgPrice = analyzer.getAverageUnitPriceByCategory();
        
        assertEquals(2, avgPrice.size());
        double expectedElectronics = (1200.00 + 35.00 + 300.00) / 3.0;
        assertEquals(expectedElectronics, avgPrice.get("Electronics"), 0.01);
    }

    @Test
    void testGetTransactionCountByRegion() {
        Map<String, Long> transactionCount = analyzer.getTransactionCountByRegion();
        
        assertEquals(4, transactionCount.size());
        assertEquals(2, transactionCount.get("North"));
        assertEquals(1, transactionCount.get("South"));
        assertEquals(1, transactionCount.get("East"));
        assertEquals(1, transactionCount.get("West"));
    }

    @Test
    void testGetSalesByCategoryFiltered() {
        double electronicsSales = analyzer.getSalesByCategory("Electronics");
        assertEquals(6000.00 + 875.00 + 3600.00, electronicsSales, 0.01);
        
        double furnitureSales = analyzer.getSalesByCategory("Furniture");
        assertEquals(2500.00 + 3600.00, furnitureSales, 0.01);
    }

    @Test
    void testGetUniqueProducts() {
        Set<String> uniqueProducts = analyzer.getUniqueProducts();
        assertEquals(5, uniqueProducts.size());
        assertTrue(uniqueProducts.contains("Laptop"));
        assertTrue(uniqueProducts.contains("Desk Chair"));
        assertTrue(uniqueProducts.contains("Wireless Mouse"));
        assertTrue(uniqueProducts.contains("Office Desk"));
        assertTrue(uniqueProducts.contains("Monitor"));
    }

    @Test
    void testGetTotalQuantity() {
        int totalQuantity = analyzer.getTotalQuantity();
        assertEquals(5 + 10 + 25 + 8 + 12, totalQuantity);
    }

    @Test
    void testEmptyList() {
        SalesAnalyzer emptyAnalyzer = new SalesAnalyzer(new ArrayList<>());
        assertEquals(0.0, emptyAnalyzer.getTotalSales(), 0.01);
        assertEquals(0.0, emptyAnalyzer.getAverageSales(), 0.01);
        assertEquals(0.0, emptyAnalyzer.getMaxSales(), 0.01);
        assertEquals(0.0, emptyAnalyzer.getMinSales(), 0.01);
        assertTrue(emptyAnalyzer.getSalesByCategory().isEmpty());
    }
}

