# Project Structure

```
Assignment1/
│
├── README.md                          # Root README with quick start guide
├── STRUCTURE.md                       # This file - project structure overview
├── .gitignore                         # Git ignore patterns
│
├── run_java.sh                        # Helper script to run Java implementations
├── run_python.sh                      # Helper script to run Python implementations
│
├── java/                              # Java implementation directory
│   ├── README.md                      # Java-specific documentation
│   ├── src/                           # Java source files
│   │   ├── ProducerConsumer.java
│   │   └── ProducerConsumerWaitNotify.java
│   └── test/                          # Java test files
│       └── ProducerConsumerTest.java
│
├── python/                            # Python implementation directory
│   ├── README.md                      # Python-specific documentation
│   ├── src/                           # Python source files
│   │   ├── producer_consumer.py
│   │   └── producer_consumer_wait_notify.py
│   └── test/                          # Python test files
│       └── test_producer_consumer.py
│
└── docs/                              # Documentation directory
    └── README.md                      # Comprehensive documentation with:
                                        # - Setup instructions
                                        # - Sample output
                                        # - Analysis results
                                        # - Edge cases
                                        # - Troubleshooting
```

## File Descriptions

### Root Files
- **README.md**: Quick start guide and project overview
- **STRUCTURE.md**: This file - detailed project structure
- **.gitignore**: Git ignore patterns for compiled files, cache, etc.
- **run_java.sh**: Interactive script to compile and run Java implementations
- **run_python.sh**: Interactive script to run Python implementations and tests

### Java Directory
- **java/src/**: Contains all Java source code
  - `ProducerConsumer.java`: BlockingQueue implementation
  - `ProducerConsumerWaitNotify.java`: Wait/Notify implementation
- **java/test/**: Contains Java unit tests
  - `ProducerConsumerTest.java`: Comprehensive JUnit 5 tests
- **java/README.md**: Java-specific setup and execution instructions

### Python Directory
- **python/src/**: Contains all Python source code
  - `producer_consumer.py`: Queue implementation
  - `producer_consumer_wait_notify.py`: Wait/Notify implementation
- **python/test/**: Contains Python unit tests
  - `test_producer_consumer.py`: Comprehensive unittest tests
- **python/README.md**: Python-specific setup and execution instructions

### Documentation Directory
- **docs/README.md**: Comprehensive documentation including:
  - Detailed setup instructions
  - Running instructions for all implementations
  - Unit test instructions
  - Edge cases documentation
  - Sample output examples
  - Analysis and results explanation
  - Troubleshooting guide

## Quick Navigation

- **Want to run Java code?** → See `java/README.md` or use `./run_java.sh`
- **Want to run Python code?** → See `python/README.md` or use `./run_python.sh`
- **Want detailed documentation?** → See `docs/README.md`
- **Want quick start?** → See root `README.md`

