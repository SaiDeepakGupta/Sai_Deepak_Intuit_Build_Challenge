"""
Sales Data Analyzer using Python functional programming.
Demonstrates functional programming, data aggregation, and lambda expressions.
"""

from functools import reduce
from collections import defaultdict
from typing import List, Dict, Optional, Tuple
import csv
from datetime import datetime


class SalesRecord:
    """Represents a sales record from the CSV file."""
    
    def __init__(self, date: str, product: str, category: str, quantity: int,
                 unit_price: float, total_amount: float, region: str, sales_person: str):
        self.date = datetime.strptime(date, "%Y-%m-%d").date()
        self.product = product
        self.category = category
        self.quantity = quantity
        self.unit_price = unit_price
        self.total_amount = total_amount
        self.region = region
        self.sales_person = sales_person
    
    def __repr__(self):
        return (f"SalesRecord(date={self.date}, product='{self.product}', "
                f"category='{self.category}', quantity={self.quantity}, "
                f"unit_price={self.unit_price}, total_amount={self.total_amount}, "
                f"region='{self.region}', sales_person='{self.sales_person}')")


def read_sales_data(file_path: str) -> List[SalesRecord]:
    """
    Reads sales records from a CSV file.
    
    Args:
        file_path: Path to the CSV file
        
    Returns:
        List of SalesRecord objects
    """
    records = []
    with open(file_path, 'r', encoding='utf-8') as file:
        reader = csv.DictReader(file)
        for row in reader:
            record = SalesRecord(
                date=row['Date'],
                product=row['Product'],
                category=row['Category'],
                quantity=int(row['Quantity']),
                unit_price=float(row['UnitPrice']),
                total_amount=float(row['TotalAmount']),
                region=row['Region'],
                sales_person=row['SalesPerson']
            )
            records.append(record)
    return records


class SalesAnalyzer:
    """Performs various data analysis operations on sales data using functional programming."""
    
    def __init__(self, sales_records: List[SalesRecord]):
        self.sales_records = sales_records
    
    def get_total_sales(self) -> float:
        """Calculates the total sales amount across all records."""
        return reduce(lambda x, y: x + y, 
                     map(lambda r: r.total_amount, self.sales_records), 0.0)
    
    def get_average_sales(self) -> float:
        """Calculates the average sales amount per transaction."""
        if not self.sales_records:
            return 0.0
        total = self.get_total_sales()
        return total / len(self.sales_records)
    
    def get_max_sales(self) -> float:
        """Finds the maximum sales amount."""
        if not self.sales_records:
            return 0.0
        return max(map(lambda r: r.total_amount, self.sales_records))
    
    def get_min_sales(self) -> float:
        """Finds the minimum sales amount."""
        if not self.sales_records:
            return 0.0
        return min(map(lambda r: r.total_amount, self.sales_records))
    
    def get_sales_by_category(self) -> Dict[str, float]:
        """Groups sales by category and calculates total sales per category."""
        def accumulate_by_category(acc: Dict[str, float], record: SalesRecord) -> Dict[str, float]:
            acc[record.category] = acc.get(record.category, 0.0) + record.total_amount
            return acc
        
        return reduce(accumulate_by_category, self.sales_records, {})
    
    def get_sales_by_region(self) -> Dict[str, float]:
        """Groups sales by region and calculates total sales per region."""
        def accumulate_by_region(acc: Dict[str, float], record: SalesRecord) -> Dict[str, float]:
            acc[record.region] = acc.get(record.region, 0.0) + record.total_amount
            return acc
        
        return reduce(accumulate_by_region, self.sales_records, {})
    
    def get_sales_by_sales_person(self) -> Dict[str, float]:
        """Groups sales by sales person and calculates total sales per person."""
        def accumulate_by_person(acc: Dict[str, float], record: SalesRecord) -> Dict[str, float]:
            acc[record.sales_person] = acc.get(record.sales_person, 0.0) + record.total_amount
            return acc
        
        return reduce(accumulate_by_person, self.sales_records, {})
    
    def get_top_products(self, n: int) -> Dict[str, float]:
        """Finds the top N products by total sales amount."""
        def accumulate_by_product(acc: Dict[str, float], record: SalesRecord) -> Dict[str, float]:
            acc[record.product] = acc.get(record.product, 0.0) + record.total_amount
            return acc
        
        product_sales = reduce(accumulate_by_product, self.sales_records, {})
        sorted_products = sorted(product_sales.items(), key=lambda x: x[1], reverse=True)
        return dict(sorted_products[:n])
    
    def get_quantity_by_category(self) -> Dict[str, int]:
        """Calculates total quantity sold by category."""
        def accumulate_quantity(acc: Dict[str, int], record: SalesRecord) -> Dict[str, int]:
            acc[record.category] = acc.get(record.category, 0) + record.quantity
            return acc
        
        return reduce(accumulate_quantity, self.sales_records, {})
    
    def get_top_region(self) -> Optional[str]:
        """Finds the region with the highest sales."""
        sales_by_region = self.get_sales_by_region()
        if not sales_by_region:
            return None
        return max(sales_by_region.items(), key=lambda x: x[1])[0]
    
    def get_top_sales_person(self) -> Optional[str]:
        """Finds the sales person with the highest sales."""
        sales_by_person = self.get_sales_by_sales_person()
        if not sales_by_person:
            return None
        return max(sales_by_person.items(), key=lambda x: x[1])[0]
    
    def get_average_unit_price_by_category(self) -> Dict[str, float]:
        """Calculates average unit price by category."""
        category_data = defaultdict(lambda: {'total_price': 0.0, 'count': 0})
        
        def accumulate_price(acc, record):
            acc[record.category]['total_price'] += record.unit_price
            acc[record.category]['count'] += 1
            return acc
        
        result = reduce(accumulate_price, self.sales_records, category_data)
        return {cat: data['total_price'] / data['count'] 
                for cat, data in result.items()}
    
    def get_transaction_count_by_region(self) -> Dict[str, int]:
        """Counts the number of transactions per region."""
        def accumulate_count(acc: Dict[str, int], record: SalesRecord) -> Dict[str, int]:
            acc[record.region] = acc.get(record.region, 0) + 1
            return acc
        
        return reduce(accumulate_count, self.sales_records, {})
    
    def get_sales_by_category_filtered(self, category: str) -> float:
        """Filters sales records by category and calculates total."""
        filtered = filter(lambda r: r.category.lower() == category.lower(), self.sales_records)
        return reduce(lambda x, y: x + y, 
                     map(lambda r: r.total_amount, filtered), 0.0)
    
    def get_unique_products(self) -> set:
        """Gets all unique products."""
        return set(map(lambda r: r.product, self.sales_records))
    
    def get_total_quantity(self) -> int:
        """Calculates total quantity sold across all records."""
        return reduce(lambda x, y: x + y, 
                     map(lambda r: r.quantity, self.sales_records), 0)


def main():
    """Main function to demonstrate sales data analysis."""
    import os
    # Get the directory where this script is located
    script_dir = os.path.dirname(os.path.abspath(__file__))
    # Construct path to CSV file relative to script location
    csv_file_path = os.path.join(script_dir, "..", "data", "sales_data.csv")
    
    try:
        # Read sales data from CSV
        sales_records = read_sales_data(csv_file_path)
        print("=" * 80)
        print("SALES DATA ANALYSIS - PYTHON FUNCTIONAL PROGRAMMING")
        print("=" * 80)
        print(f"\nTotal Records Loaded: {len(sales_records)}")
        print("\n" + "=" * 80)
        
        # Create analyzer instance
        analyzer = SalesAnalyzer(sales_records)
        
        # Perform various analyses
        print("\n1. BASIC STATISTICS")
        print("-" * 80)
        print(f"Total Sales Amount: ${analyzer.get_total_sales():.2f}")
        print(f"Average Sales Amount: ${analyzer.get_average_sales():.2f}")
        print(f"Maximum Sales Amount: ${analyzer.get_max_sales():.2f}")
        print(f"Minimum Sales Amount: ${analyzer.get_min_sales():.2f}")
        print(f"Total Quantity Sold: {analyzer.get_total_quantity()} units")
        
        print("\n2. SALES BY CATEGORY")
        print("-" * 80)
        sales_by_category = analyzer.get_sales_by_category()
        for category, sales in sorted(sales_by_category.items(), key=lambda x: x[1], reverse=True):
            print(f"  {category:<20}: ${sales:.2f}")
        
        print("\n3. SALES BY REGION")
        print("-" * 80)
        sales_by_region = analyzer.get_sales_by_region()
        for region, sales in sorted(sales_by_region.items(), key=lambda x: x[1], reverse=True):
            print(f"  {region:<20}: ${sales:.2f}")
        
        print("\n4. SALES BY SALES PERSON")
        print("-" * 80)
        sales_by_sales_person = analyzer.get_sales_by_sales_person()
        for person, sales in sorted(sales_by_sales_person.items(), key=lambda x: x[1], reverse=True):
            print(f"  {person:<20}: ${sales:.2f}")
        
        print("\n5. TOP 5 PRODUCTS BY SALES")
        print("-" * 80)
        top_products = analyzer.get_top_products(5)
        for product, sales in top_products.items():
            print(f"  {product:<20}: ${sales:.2f}")
        
        print("\n6. QUANTITY SOLD BY CATEGORY")
        print("-" * 80)
        quantity_by_category = analyzer.get_quantity_by_category()
        for category, quantity in sorted(quantity_by_category.items(), key=lambda x: x[1], reverse=True):
            print(f"  {category:<20}: {quantity} units")
        
        print("\n7. AVERAGE UNIT PRICE BY CATEGORY")
        print("-" * 80)
        avg_price_by_category = analyzer.get_average_unit_price_by_category()
        for category, avg_price in sorted(avg_price_by_category.items(), key=lambda x: x[1], reverse=True):
            print(f"  {category:<20}: ${avg_price:.2f}")
        
        print("\n8. TRANSACTION COUNT BY REGION")
        print("-" * 80)
        transaction_count = analyzer.get_transaction_count_by_region()
        for region, count in sorted(transaction_count.items(), key=lambda x: x[1], reverse=True):
            print(f"  {region:<20}: {count} transactions")
        
        print("\n9. TOP PERFORMERS")
        print("-" * 80)
        top_region = analyzer.get_top_region()
        if top_region:
            print(f"  Top Region: {top_region}")
        
        top_sales_person = analyzer.get_top_sales_person()
        if top_sales_person:
            print(f"  Top Sales Person: {top_sales_person}")
        
        print("\n10. UNIQUE PRODUCTS")
        print("-" * 80)
        unique_products = analyzer.get_unique_products()
        print(f"  Total Unique Products: {len(unique_products)}")
        for product in sorted(unique_products):
            print(f"    - {product}")
        
        print("\n11. FILTERED ANALYSIS - ELECTRONICS CATEGORY")
        print("-" * 80)
        electronics_sales = analyzer.get_sales_by_category_filtered("Electronics")
        print(f"  Total Electronics Sales: ${electronics_sales:.2f}")
        
        print("\n" + "=" * 80)
        print("ANALYSIS COMPLETE")
        print("=" * 80)
        
    except FileNotFoundError:
        print(f"Error: CSV file not found at {csv_file_path}")
    except Exception as e:
        print(f"Error: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main()

