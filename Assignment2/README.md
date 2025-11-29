Project Structure
Assignment2/
├── data/
│   └── sales_data.csv        # Sales dataset
├── python/
│   ├── sales_analyzer.py     # Main analysis module
│   ├── test_sales_analyzer.py# Unit tests
│   └── requirements.txt      # Python dependencies (optional)
└── README.md                 # Project documentation (this file)

Dataset Description

sales_data.csv contains sales transaction records with fields:
Date – Format: YYYY-MM-DD
Product – Product name
Category – Electronics or Furniture
Quantity – Units sold
UnitPrice – Price per unit
TotalAmount – Total sale value
Region – North, South, East, West
SalesPerson – John Smith, Mary Johnson, or Robert Brown

Dataset Characteristics
40 sales records (Jan–Mar 2024)
15 unique products
4 sales regions
2 categories
No missing values
All values in USD

Prerequisites
Python Requirements
Python 3.7+
No external libraries required (standard library only)

if you want pytest:
pytest

Setup Instructions
Run the Application
Navigate to the Python directory:
cd python

Run the analyzer:
python3 sales_analyzer.py

Run Unit Tests
Using unittest:
python3 -m unittest test_sales_analyzer.py -v

Python Implementation Details
Functional Programming Techniques Used

✔ map() for data transformation
✔ filter() for conditional selection
✔ reduce() (from functools) for aggregation
✔ Lambda expressions
✔ Higher-order functions
✔ Pure, side-effect-free analysis functions
✔ Dictionary and set-based aggregation patterns

Analysis Operations Implemented

Both the main script and tests support the following analytical queries:
1. Basic Statistics
Total sales amount
Average sales amount
Maximum sales amount
Minimum sales amount
Total quantity sold

2. Grouping Operations
Sales by category
Sales by region
Sales by sales person
Quantity by category
Transaction count by region

3. Top Performers
Top N products by total sales
Highest revenue region
Top sales person

4. Advanced Analytics

Average unit price by category
List of unique products
Category-specific filtering

Sample Output
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

Testing

The Python test suite (test_sales_analyzer.py) includes:
15+ test cases
Edge-case validation (empty lists)
Accuracy checks for all aggregations
Grouping operation validation

Run with:
python -m unittest -

Design Decisions
1. Functional Programming Focus
Pure function design
No in-place modifications
Composition of small reusable functions

2. Clean Separation of Concerns

File loading
Data mapping into dictionaries
Analysis operations

3. Reusability
Generic aggregation helpers
Configurable top-N methods
Reusable grouping utilities

4. Error Handling
Safe handling of empty inputs
Graceful fallbacks for edge cases

Performance Considerations
All operations are O(n)
Uses lazy evaluation principles via iterators
No heavy memory usage (dataset is small)