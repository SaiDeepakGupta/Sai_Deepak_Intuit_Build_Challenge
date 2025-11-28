#!/bin/bash
# Helper script to run Python implementations

cd "$(dirname "$0")"

echo "Select implementation to run:"
echo "1) producer_consumer.py (Queue)"
echo "2) producer_consumer_wait_notify.py (Wait/Notify)"
echo "3) Run all tests"
read -p "Enter choice (1, 2, or 3): " choice

case $choice in
    1)
        echo "Running producer_consumer.py..."
        python3 python/src/producer_consumer.py
        ;;
    2)
        echo "Running producer_consumer_wait_notify.py..."
        python3 python/src/producer_consumer_wait_notify.py
        ;;
    3)
        echo "Running all tests..."
        python3 -m unittest python.test.test_producer_consumer -v
        ;;
    *)
        echo "Invalid choice"
        exit 1
        ;;
esac

