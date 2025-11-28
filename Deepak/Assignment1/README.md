# Assignment 1: Producer-Consumer Pattern with Thread Synchronization

## Project Structure

```
Assignment1/
├── java/
│   ├── src/
│   │   ├── ProducerConsumer.java              # BlockingQueue implementation
│   │   └── ProducerConsumerWaitNotify.java    # Wait/Notify implementation
│   └── test/
│       └── ProducerConsumerTest.java          # Unit tests
│
├── python/
│   ├── src/
│   │   ├── producer_consumer.py               # Queue implementation
│   │   └── producer_consumer_wait_notify.py  # Wait/Notify implementation
│   └── test/
│       └── test_producer_consumer.py          # Unit tests
│
└── docs/
    └── README.md                               # Detailed documentation
```

## Quick Start

### Java

#### Compile and Run:
```bash
cd java/src
javac *.java
java ProducerConsumer
```

#### Run Tests:
```bash
cd java/test
# Use your IDE or build tool (Maven/Gradle) to run ProducerConsumerTest.java
```

### Python

#### Run:
```bash
cd python/src
python3 producer_consumer.py
```

#### Run Tests:
```bash
cd python
python3 -m unittest test.test_producer_consumer -v
```

## Documentation

For detailed documentation, setup instructions, sample output, and analysis results, see [docs/README.md](docs/README.md).

## Features

- ✅ Thread synchronization using BlockingQueue and Wait/Notify
- ✅ Comprehensive edge case handling
- ✅ Performance analysis and statistics
- ✅ Unit tests with full coverage
- ✅ Both Java and Python implementations
