# Python Implementation

## Source Files

### producer_consumer.py
- **Location**: `src/producer_consumer.py`
- **Description**: Producer-Consumer pattern using `queue.Queue`
- **Run**: `python3 src/producer_consumer.py`

### producer_consumer_wait_notify.py
- **Location**: `src/producer_consumer_wait_notify.py`
- **Description**: Producer-Consumer pattern using `threading.Condition`
- **Run**: `python3 src/producer_consumer_wait_notify.py`

## Test Files

### test_producer_consumer.py
- **Location**: `test/test_producer_consumer.py`
- **Description**: Comprehensive unit tests using unittest
- **Run**: `python3 -m unittest test.test_producer_consumer -v`

## Execution

### From project root:
```bash
# Run ProducerConsumer
python3 python/src/producer_consumer.py

# Run ProducerConsumerWaitNotify
python3 python/src/producer_consumer_wait_notify.py

# Run tests
python3 -m unittest python.test.test_producer_consumer -v
```

### From python directory:
```bash
cd python

# Run
python3 src/producer_consumer.py
python3 src/producer_consumer_wait_notify.py

# Run tests
python3 -m unittest test.test_producer_consumer -v
```

## Requirements
- Python 3.7 or higher
- No external dependencies (uses only standard library)

