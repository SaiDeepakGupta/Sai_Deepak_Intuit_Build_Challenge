"""
Producer-Consumer Pattern Implementation using queue.Queue
Demonstrates thread synchronization and concurrent programming using Python's queue module
Includes comprehensive edge case handling and performance analysis
"""
import queue
import threading
import time
from typing import List, Optional
from collections import deque
from dataclasses import dataclass
from time import perf_counter
from datetime import datetime
import os


@dataclass
class Statistics:
    """Statistics tracking for producer-consumer pattern"""
    items_produced: int = 0
    items_consumed: int = 0
    producer_wait_time: float = 0.0
    consumer_wait_time: float = 0.0
    max_queue_size: int = 0
    start_time: float = 0.0
    end_time: float = 0.0


class ProducerConsumer:
    def __init__(self, capacity: int):
        """
        Initialize Producer-Consumer with a shared queue of specified capacity
        
        Args:
            capacity: Maximum size of the shared queue (must be > 0)
            
        Raises:
            ValueError: If capacity <= 0
        """
        if capacity <= 0:
            raise ValueError("Queue capacity must be greater than 0")
        
        self.shared_queue = queue.Queue(maxsize=capacity)
        self.source_container: List[int] = []
        self.destination_container: List[int] = []
        self.capacity = capacity
        self.lock = threading.Lock()
        self.stats = Statistics()
        self._producer_finished = False
    
    def initialize_source(self, item_count: int) -> None:
        """
        Initialize source container with sample data
        
        Args:
            item_count: Number of items to generate (must be >= 0)
            
        Raises:
            ValueError: If item_count < 0
        """
        if item_count < 0:
            raise ValueError("Item count must be non-negative")
        
        self.source_container = list(range(1, item_count + 1))
        print(f"Source container initialized with {item_count} items: {self.source_container}")
    
    def initialize_source_custom(self, items: List[int]) -> None:
        """
        Initialize source container with custom data
        
        Args:
            items: List of items to use as source
            
        Raises:
            ValueError: If items is None
        """
        if items is None:
            raise ValueError("Items list cannot be None")
        
        self.source_container = items.copy()
        print(f"Source container initialized with {len(items)} items: {self.source_container}")
    
    def producer(self) -> None:
        """
        Producer thread that reads from source container and places items into shared queue
        Handles edge cases: empty source, null items, interruptions
        """
        try:
            # Edge case: Empty source
            if not self.source_container:
                print("Producer: Source container is empty. Nothing to produce.")
                self._producer_finished = True
                return
            
            for item in self.source_container:
                # Edge case: Handle None items
                if item is None:
                    print("Producer: Warning - None item detected, skipping...")
                    continue
                
                wait_start = perf_counter()
                # Queue.put() will block if queue is full
                self.shared_queue.put(item)
                wait_end = perf_counter()
                self.stats.producer_wait_time += (wait_end - wait_start) * 1000  # Convert to ms
                
                current_size = self.shared_queue.qsize()
                self.stats.max_queue_size = max(self.stats.max_queue_size, current_size)
                
                self.stats.items_produced += 1
                print(f"Producer produced: {item} | Queue size: {current_size}")
                time.sleep(0.1)  # Simulate production time
            
            self._producer_finished = True
            print("Producer finished producing all items")
        except Exception as e:
            print(f"Producer error: {e}")
            import traceback
            traceback.print_exc()
    
    def consumer(self, total_items: int) -> None:
        """
        Consumer thread that reads from queue and stores items in destination container
        Handles edge cases: empty queue, interruptions, timeout scenarios
        
        Args:
            total_items: Total number of items to consume (must be >= 0)
        """
        try:
            # Edge case: No items to consume
            if total_items < 0:
                raise ValueError("Total items must be non-negative")
            
            if total_items == 0:
                print("Consumer: No items to consume.")
                return
            
            consumed = 0
            while consumed < total_items:
                wait_start = perf_counter()
                # Queue.get() will block if queue is empty
                item = self.shared_queue.get()
                wait_end = perf_counter()
                self.stats.consumer_wait_time += (wait_end - wait_start) * 1000  # Convert to ms
                
                # Edge case: Handle None items
                if item is None:
                    print("Consumer: Warning - None item received, skipping...")
                    self.shared_queue.task_done()
                    continue
                
                with self.lock:
                    self.destination_container.append(item)
                
                consumed += 1
                self.stats.items_consumed += 1
                
                current_size = self.shared_queue.qsize()
                print(f"Consumer consumed: {item} | Queue size: {current_size} | Total consumed: {consumed}")
                
                # Mark task as done
                self.shared_queue.task_done()
                time.sleep(0.15)  # Simulate consumption time
            
            print("Consumer finished consuming all items")
        except Exception as e:
            print(f"Consumer error: {e}")
            import traceback
            traceback.print_exc()
    
    def get_destination_container(self) -> List[int]:
        """
        Get a copy of the destination container
        
        Returns:
            Copy of destination container
        """
        with self.lock:
            return self.destination_container.copy()
    
    def get_source_container(self) -> List[int]:
        """
        Get a copy of the source container
        
        Returns:
            Copy of source container
        """
        return self.source_container.copy()
    
    def perform_analysis(self) -> None:
        """
        Perform analysis and print results to console and file
        """
        separator = "=" * 60
        report = []
        
        report.append("\n" + separator)
        report.append("PERFORMANCE ANALYSIS RESULTS")
        report.append("Implementation: ProducerConsumer (Queue)")
        report.append(f"Timestamp: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
        report.append(separator)
        
        # Basic Statistics
        report.append("\n--- Basic Statistics ---")
        report.append(f"Queue Capacity: {self.capacity}")
        report.append(f"Items Produced: {self.stats.items_produced}")
        report.append(f"Items Consumed: {self.stats.items_consumed}")
        report.append(f"Source Container Size: {len(self.source_container)}")
        report.append(f"Destination Container Size: {len(self.destination_container)}")
        report.append(f"Max Queue Size Reached: {self.stats.max_queue_size}")
        
        # Timing Analysis
        report.append("\n--- Timing Analysis ---")
        if self.stats.start_time > 0 and self.stats.end_time > 0:
            total_time = (self.stats.end_time - self.stats.start_time) * 1000  # Convert to ms
            report.append(f"Total Execution Time: {total_time:.2f} ms")
            if self.stats.items_consumed > 0:
                report.append(f"Average Time per Item: {total_time / self.stats.items_consumed:.2f} ms")
        
        report.append(f"Total Producer Wait Time: {self.stats.producer_wait_time:.2f} ms")
        report.append(f"Total Consumer Wait Time: {self.stats.consumer_wait_time:.2f} ms")
        
        if self.stats.items_produced > 0:
            avg_prod_wait = self.stats.producer_wait_time / self.stats.items_produced
            report.append(f"Average Producer Wait per Item: {avg_prod_wait:.2f} ms")
        
        if self.stats.items_consumed > 0:
            avg_cons_wait = self.stats.consumer_wait_time / self.stats.items_consumed
            report.append(f"Average Consumer Wait per Item: {avg_cons_wait:.2f} ms")
        
        # Queue Utilization
        report.append("\n--- Queue Utilization ---")
        utilization_rate = (self.stats.max_queue_size / self.capacity) * 100 if self.capacity > 0 else 0
        report.append(f"Queue Utilization Rate: {utilization_rate:.2f}%")
        
        if utilization_rate > 80:
            efficiency = "High"
        elif utilization_rate > 50:
            efficiency = "Medium"
        else:
            efficiency = "Low"
        report.append(f"Queue Efficiency: {efficiency}")
        
        # Data Integrity
        report.append("\n--- Data Integrity Analysis ---")
        data_match = self.source_container == self.get_destination_container()
        count_match = len(self.source_container) == len(self.destination_container)
        no_data_loss = self.stats.items_produced == self.stats.items_consumed
        
        report.append(f"Data Match: {'✓ PASS' if data_match else '✗ FAIL'}")
        report.append(f"Count Match: {'✓ PASS' if count_match else '✗ FAIL'}")
        report.append(f"No Data Loss: {'✓ PASS' if no_data_loss else '✗ FAIL'}")
        
        # Thread Synchronization Analysis
        report.append("\n--- Thread Synchronization Analysis ---")
        balance_diff = abs(self.stats.items_produced - self.stats.items_consumed)
        is_balanced = balance_diff <= 1
        report.append(f"Producer-Consumer Balance: {'✓ Balanced' if is_balanced else '⚠ Imbalanced'}")
        
        has_blocking = self.stats.producer_wait_time > 0 or self.stats.consumer_wait_time > 0
        blocking_status = "✓ Working (threads blocked when needed)" if has_blocking else "⚠ No blocking detected"
        report.append(f"Blocking Behavior: {blocking_status}")
        
        report.append("\n" + separator + "\n")
        
        # Print to console
        report_text = "\n".join(report)
        print(report_text)
        
        # Write to file
        try:
            # Ensure results directory exists
            os.makedirs("results", exist_ok=True)
            file_path = "results/producer_consumer_results.txt"
            with open(file_path, "a", encoding="utf-8") as f:
                f.write(report_text + "\n")
            print(f"Analysis results saved to: {file_path}")
        except Exception as e:
            print(f"Error writing to file: {e}")
    
    def reset_statistics(self) -> None:
        """Reset statistics for new run"""
        self.stats = Statistics()
        self._producer_finished = False
        with self.lock:
            self.destination_container.clear()
    
    def run(self, item_count: int) -> None:
        """
        Run the producer-consumer pattern demonstration
        
        Args:
            item_count: Number of items to process
        """
        self.initialize_source(item_count)
        self.reset_statistics()
        
        # Create threads
        producer_thread = threading.Thread(target=self.producer, name="Producer-Thread")
        consumer_thread = threading.Thread(target=self.consumer, args=(item_count,), name="Consumer-Thread")
        
        print("Starting Producer-Consumer pattern demonstration...")
        print(f"Queue capacity: {self.capacity}")
        print(f"Total items to process: {item_count}")
        print("-" * 60)
        
        self.stats.start_time = perf_counter()
        producer_thread.start()
        consumer_thread.start()
        
        # Wait for threads to complete
        producer_thread.join()
        consumer_thread.join()
        self.stats.end_time = perf_counter()
        
        print("-" * 60)
        print("All threads completed successfully!")
        print(f"Source container: {self.source_container}")
        print(f"Destination container: {self.get_destination_container()}")
        
        # Verification
        if self.source_container == self.get_destination_container():
            print("Verification: SUCCESS - All items transferred correctly!")
        else:
            print("Verification: FAILED - Items mismatch!")
        
        # Perform and print analysis
        self.perform_analysis()


if __name__ == "__main__":
    queue_capacity = 5
    item_count = 20
    
    pc = ProducerConsumer(queue_capacity)
    pc.run(item_count)
    
    # Test edge cases
    print("\n" + "=" * 60)
    print("TESTING EDGE CASES")
    print("=" * 60)
    
    # Test 1: Empty source
    print("\n--- Edge Case 1: Empty Source ---")
    pc2 = ProducerConsumer(5)
    pc2.run(0)
    
    # Test 2: Single item
    print("\n--- Edge Case 2: Single Item ---")
    pc3 = ProducerConsumer(5)
    pc3.run(1)
    
    # Test 3: Large capacity
    print("\n--- Edge Case 3: Large Capacity ---")
    pc4 = ProducerConsumer(100)
    pc4.run(50)
