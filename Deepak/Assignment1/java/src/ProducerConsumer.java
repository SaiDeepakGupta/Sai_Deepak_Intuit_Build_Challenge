import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Producer-Consumer Pattern Implementation using BlockingQueue
 * Demonstrates thread synchronization and concurrent programming
 * Includes comprehensive edge case handling and performance analysis
 */
public class ProducerConsumer {
    private final BlockingQueue<Integer> sharedQueue;
    private final List<Integer> sourceContainer;
    private final List<Integer> destinationContainer;
    private final int capacity;
    
    // Statistics tracking
    private final AtomicInteger itemsProduced = new AtomicInteger(0);
    private final AtomicInteger itemsConsumed = new AtomicInteger(0);
    private final AtomicLong producerWaitTime = new AtomicLong(0);
    private final AtomicLong consumerWaitTime = new AtomicLong(0);
    private final AtomicInteger maxQueueSize = new AtomicInteger(0);
    private long startTime;
    private long endTime;
    
    /**
     * Constructor with validation
     * @param capacity Queue capacity (must be > 0)
     * @throws IllegalArgumentException if capacity <= 0
     */
    public ProducerConsumer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Queue capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.sharedQueue = new LinkedBlockingQueue<>(capacity);
        this.sourceContainer = new ArrayList<>();
        this.destinationContainer = new ArrayList<>();
    }
    
    /**
     * Producer thread that reads from source container and places items into shared queue
     * Handles edge cases: empty source, null items, interruptions
     */
    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                if (sourceContainer == null || sourceContainer.isEmpty()) {
                    System.out.println("Producer: Source container is empty. Nothing to produce.");
                    return;
                }
                
                for (Integer item : sourceContainer) {
                    // Edge case: Handle null items
                    if (item == null) {
                        System.err.println("Producer: Warning - Null item detected, skipping...");
                        continue;
                    }
                    
                    long waitStart = System.currentTimeMillis();
                    // BlockingQueue.put() will block if queue is full
                    sharedQueue.put(item);
                    long waitEnd = System.currentTimeMillis();
                    producerWaitTime.addAndGet(waitEnd - waitStart);
                    
                    int currentSize = sharedQueue.size();
                    maxQueueSize.updateAndGet(current -> Math.max(current, currentSize));
                    
                    itemsProduced.incrementAndGet();
                    System.out.println("Producer produced: " + item + " | Queue size: " + currentSize);
                    Thread.sleep(100); // Simulate production time
                }
                System.out.println("Producer finished producing all items");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer interrupted: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Producer error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Consumer thread that reads from queue and stores items in destination container
     * Handles edge cases: empty queue, interruptions, timeout scenarios
     */
    class Consumer implements Runnable {
        private final int totalItems;
        
        public Consumer(int totalItems) {
            if (totalItems < 0) {
                throw new IllegalArgumentException("Total items must be non-negative");
            }
            this.totalItems = totalItems;
        }
        
        @Override
        public void run() {
            try {
                // Edge case: No items to consume
                if (totalItems == 0) {
                    System.out.println("Consumer: No items to consume.");
                    return;
                }
                
                int consumed = 0;
                while (consumed < totalItems) {
                    long waitStart = System.currentTimeMillis();
                    // BlockingQueue.take() will block if queue is empty
                    Integer item = sharedQueue.take();
                    long waitEnd = System.currentTimeMillis();
                    consumerWaitTime.addAndGet(waitEnd - waitStart);
                    
                    // Edge case: Handle null items
                    if (item == null) {
                        System.err.println("Consumer: Warning - Null item received, skipping...");
                        continue;
                    }
                    
                    synchronized (destinationContainer) {
                        destinationContainer.add(item);
                    }
                    consumed++;
                    itemsConsumed.incrementAndGet();
                    
                    int currentSize = sharedQueue.size();
                    System.out.println("Consumer consumed: " + item + " | Queue size: " + currentSize + " | Total consumed: " + consumed);
                    Thread.sleep(150); // Simulate consumption time
                }
                System.out.println("Consumer finished consuming all items");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer interrupted: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Consumer error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Initialize source container with sample data
     * @param itemCount Number of items to generate (must be >= 0)
     * @throws IllegalArgumentException if itemCount < 0
     */
    public void initializeSource(int itemCount) {
        if (itemCount < 0) {
            throw new IllegalArgumentException("Item count must be non-negative");
        }
        sourceContainer.clear();
        for (int i = 1; i <= itemCount; i++) {
            sourceContainer.add(i);
        }
        System.out.println("Source container initialized with " + itemCount + " items: " + sourceContainer);
    }
    
    /**
     * Initialize source container with custom data
     * @param items List of items to use as source
     * @throws IllegalArgumentException if items is null
     */
    public void initializeSource(List<Integer> items) {
        if (items == null) {
            throw new IllegalArgumentException("Items list cannot be null");
        }
        sourceContainer.clear();
        sourceContainer.addAll(items);
        System.out.println("Source container initialized with " + items.size() + " items: " + sourceContainer);
    }
    
    /**
     * Get destination container (for verification)
     * @return Copy of destination container
     */
    public List<Integer> getDestinationContainer() {
        synchronized (destinationContainer) {
            return new ArrayList<>(destinationContainer);
        }
    }
    
    /**
     * Get source container
     * @return Copy of source container
     */
    public List<Integer> getSourceContainer() {
        return new ArrayList<>(sourceContainer);
    }
    
    /**
     * Perform analysis and print results to console and file
     */
    public void performAnalysis() {
        StringBuilder report = new StringBuilder();
        String separator = "=".repeat(60);
        
        report.append("\n").append(separator).append("\n");
        report.append("PERFORMANCE ANALYSIS RESULTS\n");
        report.append("Implementation: ProducerConsumer (BlockingQueue)\n");
        report.append("Timestamp: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        report.append(separator).append("\n");
        
        // Basic Statistics
        report.append("\n--- Basic Statistics ---\n");
        report.append("Queue Capacity: ").append(capacity).append("\n");
        report.append("Items Produced: ").append(itemsProduced.get()).append("\n");
        report.append("Items Consumed: ").append(itemsConsumed.get()).append("\n");
        report.append("Source Container Size: ").append(sourceContainer.size()).append("\n");
        report.append("Destination Container Size: ").append(destinationContainer.size()).append("\n");
        report.append("Max Queue Size Reached: ").append(maxQueueSize.get()).append("\n");
        
        // Timing Analysis
        report.append("\n--- Timing Analysis ---\n");
        if (startTime > 0 && endTime > 0) {
            long totalTime = endTime - startTime;
            report.append("Total Execution Time: ").append(totalTime).append(" ms\n");
            report.append("Average Time per Item: ").append(String.format("%.2f", totalTime / (double) itemsConsumed.get())).append(" ms\n");
        }
        report.append("Total Producer Wait Time: ").append(producerWaitTime.get()).append(" ms\n");
        report.append("Total Consumer Wait Time: ").append(consumerWaitTime.get()).append(" ms\n");
        report.append("Average Producer Wait per Item: ").append(
            String.format("%.2f", itemsProduced.get() > 0 ? producerWaitTime.get() / (double) itemsProduced.get() : 0)).append(" ms\n");
        report.append("Average Consumer Wait per Item: ").append(
            String.format("%.2f", itemsConsumed.get() > 0 ? consumerWaitTime.get() / (double) itemsConsumed.get() : 0)).append(" ms\n");
        
        // Queue Utilization
        report.append("\n--- Queue Utilization ---\n");
        double utilizationRate = maxQueueSize.get() / (double) capacity * 100;
        report.append("Queue Utilization Rate: ").append(String.format("%.2f", utilizationRate)).append("%\n");
        report.append("Queue Efficiency: ").append(
            utilizationRate > 80 ? "High" : utilizationRate > 50 ? "Medium" : "Low").append("\n");
        
        // Data Integrity
        report.append("\n--- Data Integrity Analysis ---\n");
        boolean dataMatch = sourceContainer.equals(getDestinationContainer());
        boolean countMatch = sourceContainer.size() == destinationContainer.size();
        report.append("Data Match: ").append(dataMatch ? "✓ PASS" : "✗ FAIL").append("\n");
        report.append("Count Match: ").append(countMatch ? "✓ PASS" : "✗ FAIL").append("\n");
        report.append("No Data Loss: ").append(itemsProduced.get() == itemsConsumed.get() ? "✓ PASS" : "✗ FAIL").append("\n");
        
        // Thread Synchronization Analysis
        report.append("\n--- Thread Synchronization Analysis ---\n");
        report.append("Producer-Consumer Balance: ").append(
            Math.abs(itemsProduced.get() - itemsConsumed.get()) <= 1 ? "✓ Balanced" : "⚠ Imbalanced").append("\n");
        report.append("Blocking Behavior: ").append(
            producerWaitTime.get() > 0 || consumerWaitTime.get() > 0 ? "✓ Working (threads blocked when needed)" : "⚠ No blocking detected").append("\n");
        
        report.append("\n").append(separator).append("\n\n");
        
        // Print to console
        System.out.print(report.toString());
        
        // Write to file
        try {
            // Create results directory if it doesn't exist
            java.io.File resultsDir = new java.io.File("results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }
            String fileName = "results/ProducerConsumer_results.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                writer.print(report.toString());
            }
            System.out.println("Analysis results saved to: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    /**
     * Reset statistics for new run
     */
    public void resetStatistics() {
        itemsProduced.set(0);
        itemsConsumed.set(0);
        producerWaitTime.set(0);
        consumerWaitTime.set(0);
        maxQueueSize.set(0);
        startTime = 0;
        endTime = 0;
        synchronized (destinationContainer) {
            destinationContainer.clear();
        }
    }
    
    /**
     * Run the producer-consumer pattern with timing
     */
    public void run(int itemCount) throws InterruptedException {
        initializeSource(itemCount);
        resetStatistics();
        
        // Create and start producer thread
        Thread producerThread = new Thread(new Producer(), "Producer-Thread");
        
        // Create and start consumer thread
        Thread consumerThread = new Thread(new Consumer(itemCount), "Consumer-Thread");
        
        System.out.println("Starting Producer-Consumer pattern demonstration...");
        System.out.println("Queue capacity: " + capacity);
        System.out.println("Total items to process: " + itemCount);
        System.out.println("-".repeat(60));
        
        startTime = System.currentTimeMillis();
        producerThread.start();
        consumerThread.start();
        
        // Wait for both threads to complete
        producerThread.join();
        consumerThread.join();
        endTime = System.currentTimeMillis();
        
        System.out.println("-".repeat(60));
        System.out.println("All threads completed successfully!");
        System.out.println("Source container: " + sourceContainer);
        System.out.println("Destination container: " + getDestinationContainer());
        System.out.println("Verification: " + 
            (sourceContainer.equals(getDestinationContainer()) ? "SUCCESS - All items transferred correctly!" : "FAILED - Items mismatch!"));
        
        // Perform and print analysis
        performAnalysis();
    }
    
    /**
     * Main method to demonstrate producer-consumer pattern
     */
    public static void main(String[] args) {
        try {
            int queueCapacity = 5;
            int itemCount = 20;
            
            ProducerConsumer pc = new ProducerConsumer(queueCapacity);
            pc.run(itemCount);
            
            // Test edge cases
            System.out.println("\n" + "=".repeat(60));
            System.out.println("TESTING EDGE CASES");
            System.out.println("=".repeat(60));
            
            // Test 1: Empty source
            System.out.println("\n--- Edge Case 1: Empty Source ---");
            ProducerConsumer pc2 = new ProducerConsumer(5);
            pc2.run(0);
            
            // Test 2: Single item
            System.out.println("\n--- Edge Case 2: Single Item ---");
            ProducerConsumer pc3 = new ProducerConsumer(5);
            pc3.run(1);
            
            // Test 3: Large capacity
            System.out.println("\n--- Edge Case 3: Large Capacity ---");
            ProducerConsumer pc4 = new ProducerConsumer(100);
            pc4.run(50);
            
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

