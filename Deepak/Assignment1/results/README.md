# Results Folder

This folder contains the analysis results from all producer-consumer pattern implementations.

## Result Files

### Java Implementations

1. **ProducerConsumer_results.txt**
   - Implementation: ProducerConsumer using BlockingQueue
   - Contains performance analysis, statistics, and verification results
   - Each run appends results with timestamp

2. **ProducerConsumerWaitNotify_results.txt**
   - Implementation: ProducerConsumerWaitNotify using Wait/Notify mechanism
   - Contains performance analysis, statistics, and verification results
   - Each run appends results with timestamp

### Python Implementations

3. **producer_consumer_results.txt**
   - Implementation: ProducerConsumer using queue.Queue
   - Contains performance analysis, statistics, and verification results
   - Each run appends results with timestamp

4. **producer_consumer_wait_notify_results.txt**
   - Implementation: ProducerConsumerWaitNotify using threading.Condition
   - Contains performance analysis, statistics, and verification results
   - Each run appends results with timestamp

## Analysis Contents

Each result file contains:

1. **Basic Statistics**
   - Queue capacity
   - Items produced/consumed
   - Container sizes
   - Max queue size reached

2. **Timing Analysis**
   - Total execution time
   - Average time per item
   - Producer/consumer wait times
   - Average wait times per item

3. **Queue Utilization**
   - Utilization rate percentage
   - Efficiency rating (High/Medium/Low)

4. **Data Integrity Analysis**
   - Data match verification
   - Count match verification
   - No data loss verification

5. **Thread Synchronization Analysis**
   - Producer-consumer balance
   - Blocking behavior verification
   - Wait/Notify mechanism status (for wait/notify implementations)

## Notes

- Results are appended to files (not overwritten) so you can track multiple runs
- Each result entry includes a timestamp
- Files are created automatically when implementations are run
- Results are also printed to console for immediate viewing

