# Assignment 2: Sales Data Analysis

## Overview

This project demonstrates proficiency with functional programming, stream operations, data aggregation, and lambda expressions by performing various analytical queries on sales data provided in CSV format. The solution is implemented in both **Java** (using Streams API) and **Python** (using functional programming paradigms).

## Project Structure

```
Assignment2/
├── data/
│   └── sales_data.csv          # Sales dataset
├── java/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── assignment2/
│   │   │               ├── Main.java           # Main application
│   │   │               ├── SalesRecord.java    # Data model
│   │   │               ├── CSVReader.java      # CSV parsing utility
│   │   │               └── SalesAnalyzer.java  # Analysis operations
│   │   └── test/
│   │       └── java/
│   │           └── com/
│   │               └── assignment2/
│   │                   └── SalesAnalyzerTest.java  # Unit tests
│   └── pom.xml                 # Maven configuration
├── python/
│   ├── sales_analyzer.py       # Main analysis module
│   ├── test_sales_analyzer.py  # Unit tests
│   └── requirements.txt        # Python dependencies
└── README.md                   # This file
```

## Dataset Description

The CSV dataset (`data/sales_data.csv`) contains sales transaction records with the following fields:

- **Date**: Transaction date (YYYY-MM-DD format)
- **Product**: Product name
- **Category**: Product category (Electronics, Furniture)
- **Quantity**: Number of units sold
- **UnitPrice**: Price per unit
- **TotalAmount**: Total transaction amount
- **Region**: Sales region (North, South, East, West)
- **SalesPerson**: Name of the sales person

**Dataset Characteristics:**
- 40 sales records spanning 3 months (January-March 2024)
- 2 product categories: Electronics and Furniture
- 4 sales regions: North, South, East, West
- 3 sales persons: John Smith, Mary Johnson, Robert Brown
- 15 unique products

**Assumptions:**
- All monetary values are in USD
- Dates are in ISO format (YYYY-MM-DD)
- CSV file uses comma as delimiter
- First row contains column headers
- No missing or null values in the dataset

## Prerequisites

### Java Requirements
- **JDK 11** or higher
- **Maven 3.6+** (for building and running tests)

### Python Requirements
- **Python 3.7+**
- No external dependencies (uses only standard library)

## Setup Instructions

### Java Setup

1. **Navigate to the Java directory:**
   ```bash
   cd java
   ```

2. **Compile the project:**
   ```bash
   mvn clean compile
   ```

3. **Run the application:**
   ```bash
   mvn exec:java
   ```
   
   Or compile and run manually:
   ```bash
   javac -d target/classes src/main/java/com/assignment2/*.java
   java -cp target/classes:../../data com.assignment2.Main
   ```

4. **Run unit tests:**
   ```bash
   mvn test
   ```

### Python Setup

1. **Navigate to the Python directory:**
   ```bash
   cd python
   ```

2. **Run the application:**
   ```bash
   python3 sales_analyzer.py
   ```

3. **Run unit tests:**
   ```bash
   python3 -m unittest test_sales_analyzer.py -v
   ```
   
   Or using pytest (if installed):
   ```bash
   pytest test_sales_analyzer.py -v
   ```

## Features Implemented

### Java Implementation (Streams API)

The Java implementation demonstrates:

1. **Stream Operations:**
   - `map()`, `filter()`, `reduce()`, `collect()`
   - `mapToDouble()`, `mapToInt()` for primitive streams
   - `sorted()`, `limit()` for ordering and limiting

2. **Data Aggregation:**
   - `summingDouble()`, `summingInt()` for sum aggregations
   - `averagingDouble()` for average calculations
   - `counting()` for count aggregations
   - `groupingBy()` for grouping operations

3. **Lambda Expressions:**
   - Method references (`SalesRecord::getTotalAmount`)
   - Predicate lambdas for filtering
   - Comparator lambdas for sorting

4. **Functional Programming:**
   - Immutable data structures
   - Pure functions (no side effects)
   - Higher-order functions

### Python Implementation (Functional Programming)

The Python implementation demonstrates:

1. **Functional Programming:**
   - `map()`, `filter()`, `reduce()` functions
   - Lambda expressions for transformations
   - Higher-order functions

2. **Data Aggregation:**
   - Custom reduction functions
   - Dictionary accumulation patterns
   - Set operations for uniqueness

3. **Functional Patterns:**
   - Immutable operations
   - Pure functions
   - Function composition

## Analysis Operations

Both implementations provide the following analytical queries:

1. **Basic Statistics:**
   - Total sales amount
   - Average sales amount
   - Maximum sales amount
   - Minimum sales amount
   - Total quantity sold

2. **Grouping Operations:**
   - Sales by category
   - Sales by region
   - Sales by sales person
   - Quantity by category
   - Transaction count by region

3. **Top Performers:**
   - Top N products by sales
   - Top region by sales
   - Top sales person by sales

4. **Advanced Analytics:**
   - Average unit price by category
   - Unique products list
   - Filtered analysis by category

## Sample Output

### Java Output

```
================================================================================
SALES DATA ANALYSIS - JAVA STREAMS API
================================================================================

Total Records Loaded: 40

================================================================================

1. BASIC STATISTICS
--------------------------------------------------------------------------------
Total Sales Amount: $127,425.00
Average Sales Amount: $3,185.63
Maximum Sales Amount: $11,200.00
Minimum Sales Amount: $750.00
Total Quantity Sold: 550 units

2. SALES BY CATEGORY
--------------------------------------------------------------------------------
  Electronics          : $75,125.00
  Furniture            : $52,300.00

3. SALES BY REGION
--------------------------------------------------------------------------------
  East                 : $32,125.00
  North                : $31,800.00
  West                 : $32,000.00
  South                : $31,500.00

[... additional analysis sections ...]

================================================================================
ANALYSIS COMPLETE
================================================================================
```

### Python Output

```
================================================================================
SALES DATA ANALYSIS - PYTHON FUNCTIONAL PROGRAMMING
================================================================================

Total Records Loaded: 40

================================================================================

1. BASIC STATISTICS
--------------------------------------------------------------------------------
Total Sales Amount: $127425.00
Average Sales Amount: $3185.63
Maximum Sales Amount: $11200.00
Minimum Sales Amount: $750.00
Total Quantity Sold: 550 units

2. SALES BY CATEGORY
--------------------------------------------------------------------------------
  Electronics          : $75125.00
  Furniture            : $52300.00

[... additional analysis sections ...]

================================================================================
ANALYSIS COMPLETE
================================================================================
```

## Testing

### Java Tests

The Java test suite (`SalesAnalyzerTest.java`) includes:
- 15+ test cases covering all analysis methods
- Edge case testing (empty lists)
- Validation of aggregation results
- Verification of grouping operations

Run tests with:
```bash
cd java
mvn test
```

### Python Tests

The Python test suite (`test_sales_analyzer.py`) includes:
- 15+ test cases covering all analysis methods
- Edge case testing (empty lists)
- Validation of aggregation results
- Verification of grouping operations

Run tests with:
```bash
cd python
python -m unittest test_sales_analyzer.py -v
```

## Key Design Decisions

1. **Separation of Concerns:**
   - `CSVReader` handles file I/O and parsing
   - `SalesRecord` represents data model
   - `SalesAnalyzer` contains all analysis logic
   - `Main` orchestrates execution

2. **Functional Programming Principles:**
   - Immutable data structures
   - Pure functions without side effects
   - Higher-order functions and lambda expressions
   - Stream-based processing (Java) and functional composition (Python)

3. **Code Reusability:**
   - Generic aggregation methods
   - Configurable top-N queries
   - Reusable grouping operations

4. **Error Handling:**
   - Proper exception handling for file I/O
   - Null/empty list checks
   - Graceful degradation for edge cases

## Performance Considerations

- **Stream Processing:** Both implementations use lazy evaluation where possible
- **Memory Efficiency:** Streams process data without loading entire dataset into memory
- **Time Complexity:** Most operations are O(n) where n is the number of records

## Future Enhancements

Potential improvements:
- Support for date range filtering
- Additional statistical measures (median, standard deviation)
- Export results to different formats (JSON, Excel)
- Interactive query interface
- Performance benchmarking

## Author

Assignment 2 - Data Analysis using Streams and Functional Programming

## License

This project is created for educational purposes.

