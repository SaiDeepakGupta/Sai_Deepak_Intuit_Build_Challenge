"""
Unit tests for SalesAnalyzer class.
"""

import unittest
from datetime import date
from sales_analyzer import SalesRecord, SalesAnalyzer


class TestSalesAnalyzer(unittest.TestCase):
    """Test cases for SalesAnalyzer class."""
    
    def setUp(self):
        """Set up test fixtures."""
        self.test_records = [
            SalesRecord("2024-01-15", "Laptop", "Electronics", 5, 1200.00, 6000.00, "North", "John Smith"),
            SalesRecord("2024-01-16", "Desk Chair", "Furniture", 10, 250.00, 2500.00, "South", "Mary Johnson"),
            SalesRecord("2024-01-17", "Wireless Mouse", "Electronics", 25, 35.00, 875.00, "East", "John Smith"),
            SalesRecord("2024-01-18", "Office Desk", "Furniture", 8, 450.00, 3600.00, "West", "Robert Brown"),
            SalesRecord("2024-01-19", "Monitor", "Electronics", 12, 300.00, 3600.00, "North", "Mary Johnson"),
        ]
        self.analyzer = SalesAnalyzer(self.test_records)
    
    def test_get_total_sales(self):
        """Test total sales calculation."""
        expected = 6000.00 + 2500.00 + 875.00 + 3600.00 + 3600.00
        self.assertAlmostEqual(expected, self.analyzer.get_total_sales(), places=2)
    
    def test_get_average_sales(self):
        """Test average sales calculation."""
        expected = (6000.00 + 2500.00 + 875.00 + 3600.00 + 3600.00) / 5.0
        self.assertAlmostEqual(expected, self.analyzer.get_average_sales(), places=2)
    
    def test_get_max_sales(self):
        """Test maximum sales calculation."""
        self.assertAlmostEqual(6000.00, self.analyzer.get_max_sales(), places=2)
    
    def test_get_min_sales(self):
        """Test minimum sales calculation."""
        self.assertAlmostEqual(875.00, self.analyzer.get_min_sales(), places=2)
    
    def test_get_sales_by_category(self):
        """Test sales grouping by category."""
        sales_by_category = self.analyzer.get_sales_by_category()
        
        self.assertEqual(2, len(sales_by_category))
        self.assertAlmostEqual(6000.00 + 875.00 + 3600.00, 
                              sales_by_category["Electronics"], places=2)
        self.assertAlmostEqual(2500.00 + 3600.00, 
                              sales_by_category["Furniture"], places=2)
    
    def test_get_sales_by_region(self):
        """Test sales grouping by region."""
        sales_by_region = self.analyzer.get_sales_by_region()
        
        self.assertEqual(4, len(sales_by_region))
        self.assertAlmostEqual(6000.00 + 3600.00, 
                              sales_by_region["North"], places=2)
        self.assertAlmostEqual(2500.00, sales_by_region["South"], places=2)
        self.assertAlmostEqual(875.00, sales_by_region["East"], places=2)
        self.assertAlmostEqual(3600.00, sales_by_region["West"], places=2)
    
    def test_get_sales_by_sales_person(self):
        """Test sales grouping by sales person."""
        sales_by_sales_person = self.analyzer.get_sales_by_sales_person()
        
        self.assertEqual(3, len(sales_by_sales_person))
        self.assertAlmostEqual(6000.00 + 875.00, 
                              sales_by_sales_person["John Smith"], places=2)
        self.assertAlmostEqual(2500.00 + 3600.00, 
                              sales_by_sales_person["Mary Johnson"], places=2)
        self.assertAlmostEqual(3600.00, 
                              sales_by_sales_person["Robert Brown"], places=2)
    
    def test_get_top_products(self):
        """Test top products retrieval."""
        top_products = self.analyzer.get_top_products(3)
        
        self.assertEqual(3, len(top_products))
        # Check that products are sorted in descending order
        values = list(top_products.values())
        self.assertGreaterEqual(values[0], values[1])
        self.assertGreaterEqual(values[1], values[2])
    
    def test_get_quantity_by_category(self):
        """Test quantity grouping by category."""
        quantity_by_category = self.analyzer.get_quantity_by_category()
        
        self.assertEqual(2, len(quantity_by_category))
        self.assertEqual(5 + 25 + 12, quantity_by_category["Electronics"])
        self.assertEqual(10 + 8, quantity_by_category["Furniture"])
    
    def test_get_top_region(self):
        """Test top region retrieval."""
        top_region = self.analyzer.get_top_region()
        self.assertIsNotNone(top_region)
        self.assertEqual("North", top_region)
    
    def test_get_top_sales_person(self):
        """Test top sales person retrieval."""
        top_sales_person = self.analyzer.get_top_sales_person()
        self.assertIsNotNone(top_sales_person)
        self.assertEqual("John Smith", top_sales_person)
    
    def test_get_average_unit_price_by_category(self):
        """Test average unit price by category."""
        avg_price = self.analyzer.get_average_unit_price_by_category()
        
        self.assertEqual(2, len(avg_price))
        expected_electronics = (1200.00 + 35.00 + 300.00) / 3.0
        self.assertAlmostEqual(expected_electronics, 
                              avg_price["Electronics"], places=2)
    
    def test_get_transaction_count_by_region(self):
        """Test transaction count by region."""
        transaction_count = self.analyzer.get_transaction_count_by_region()
        
        self.assertEqual(4, len(transaction_count))
        self.assertEqual(2, transaction_count["North"])
        self.assertEqual(1, transaction_count["South"])
        self.assertEqual(1, transaction_count["East"])
        self.assertEqual(1, transaction_count["West"])
    
    def test_get_sales_by_category_filtered(self):
        """Test filtered sales by category."""
        electronics_sales = self.analyzer.get_sales_by_category_filtered("Electronics")
        self.assertAlmostEqual(6000.00 + 875.00 + 3600.00, 
                              electronics_sales, places=2)
        
        furniture_sales = self.analyzer.get_sales_by_category_filtered("Furniture")
        self.assertAlmostEqual(2500.00 + 3600.00, furniture_sales, places=2)
    
    def test_get_unique_products(self):
        """Test unique products retrieval."""
        unique_products = self.analyzer.get_unique_products()
        self.assertEqual(5, len(unique_products))
        self.assertIn("Laptop", unique_products)
        self.assertIn("Desk Chair", unique_products)
        self.assertIn("Wireless Mouse", unique_products)
        self.assertIn("Office Desk", unique_products)
        self.assertIn("Monitor", unique_products)
    
    def test_get_total_quantity(self):
        """Test total quantity calculation."""
        total_quantity = self.analyzer.get_total_quantity()
        self.assertEqual(5 + 10 + 25 + 8 + 12, total_quantity)
    
    def test_empty_list(self):
        """Test analyzer with empty list."""
        empty_analyzer = SalesAnalyzer([])
        self.assertEqual(0.0, empty_analyzer.get_total_sales())
        self.assertEqual(0.0, empty_analyzer.get_average_sales())
        self.assertEqual(0.0, empty_analyzer.get_max_sales())
        self.assertEqual(0.0, empty_analyzer.get_min_sales())
        self.assertEqual(0, len(empty_analyzer.get_sales_by_category()))


if __name__ == '__main__':
    unittest.main()

