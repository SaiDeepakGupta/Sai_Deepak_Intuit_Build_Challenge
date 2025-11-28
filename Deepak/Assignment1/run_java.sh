#!/bin/bash
# Helper script to compile and run Java implementations

cd "$(dirname "$0")/java/src"

echo "Compiling Java files..."
javac *.java

if [ $? -eq 0 ]; then
    echo ""
    echo "Select implementation to run:"
    echo "1) ProducerConsumer (BlockingQueue)"
    echo "2) ProducerConsumerWaitNotify (Wait/Notify)"
    read -p "Enter choice (1 or 2): " choice
    
    case $choice in
        1)
            echo "Running ProducerConsumer..."
            java ProducerConsumer
            ;;
        2)
            echo "Running ProducerConsumerWaitNotify..."
            java ProducerConsumerWaitNotify
            ;;
        *)
            echo "Invalid choice"
            exit 1
            ;;
    esac
else
    echo "Compilation failed!"
    exit 1
fi

