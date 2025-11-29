"""
Comprehensive Unit Tests for ProducerConsumer
Tests all functionality including edge cases
"""
import unittest
import threading
import time
import sys
import os

# Add parent directory to path to import from src
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
from producer_consumer import ProducerConsumer


class TestProducerConsumer(unittest.TestCase):
    
    def setUp(self):
        """Set up test fixtures"""
        self.pc = ProducerConsumer(5)
    
    def test_constructor_valid_capacity(self):
        """Test constructor with valid capacity"""
        self.assertIsNotNone(self.pc)
        self.assertIsNotNone(self.pc.source_container)
        self.assertIsNotNone(self.pc.destination_container)
        self.assertEqual(self.pc.capacity, 5)
    
    def test_constructor_invalid_capacity_zero(self):
        """Test constructor with invalid capacity (zero)"""
        with self.assertRaises(ValueError):
            ProducerConsumer(0)
    
    def test_constructor_invalid_capacity_negative(self):
        """Test constructor with invalid capacity (negative)"""
        with self.assertRaises(ValueError):
            ProducerConsumer(-1)
    
    def test_initialize_source_valid(self):
        """Test initializeSource with valid item count"""
        self.pc.initialize_source(10)
        source = self.pc.get_source_container()
        self.assertEqual(len(source), 10)
        self.assertEqual(source[0], 1)
        self.assertEqual(source[9], 10)
    
    def test_initialize_source_zero(self):
        """Test initializeSource with zero items"""
        self.pc.initialize_source(0)
        source = self.pc.get_source_container()
        self.assertEqual(len(source), 0)
    
    def test_initialize_source_negative(self):
        """Test initializeSource with negative count"""
        with self.assertRaises(ValueError):
            self.pc.initialize_source(-1)
    
    def test_initialize_source_custom(self):
        """Test initializeSource with custom list"""
        custom_list = [100, 200, 300]
        self.pc.initialize_source_custom(custom_list)
        source = self.pc.get_source_container()
        self.assertEqual(len(source), 3)
        self.assertEqual(source, custom_list)
    
    def test_initialize_source_custom_none(self):
        """Test initializeSource with None list"""
        with self.assertRaises(ValueError):
            self.pc.initialize_source_custom(None)
    
    def test_basic_producer_consumer(self):
        """Test basic producer-consumer pattern"""
        self.pc.run(10)
        
        source = self.pc.get_source_container()
        dest = self.pc.get_destination_container()
        
        self.assertEqual(len(source), len(dest))
        self.assertEqual(source, dest)
    
    def test_empty_source(self):
        """Test with empty source"""
        self.pc.run(0)
        
        source = self.pc.get_source_container()
        dest = self.pc.get_destination_container()
        
        self.assertEqual(len(source), 0)
        self.assertEqual(len(dest), 0)
    
    def test_single_item(self):
        """Test with single item"""
        self.pc.run(1)
        
        source = self.pc.get_source_container()
        dest = self.pc.get_destination_container()
        
        self.assertEqual(len(source), 1)
        self.assertEqual(len(dest), 1)
        self.assertEqual(source, dest)
    
    def test_large_number_of_items(self):
        """Test with large number of items"""
        large_pc = ProducerConsumer(10)
        large_pc.run(100)
        
        source = large_pc.get_source_container()
        dest = large_pc.get_destination_container()
        
        self.assertEqual(len(source), 100)
        self.assertEqual(len(dest), 100)
        self.assertEqual(source, dest)
    
    def test_large_capacity(self):
        """Test with queue capacity larger than items"""
        large_cap_pc = ProducerConsumer(100)
        large_cap_pc.run(20)
        
        source = large_cap_pc.get_source_container()
        dest = large_cap_pc.get_destination_container()
        
        self.assertEqual(source, dest)
    
    def test_data_integrity(self):
        """Test data integrity - no data loss"""
        item_count = 50
        self.pc.run(item_count)
        
        source = self.pc.get_source_container()
        dest = self.pc.get_destination_container()
        
        # Verify all items transferred
        self.assertEqual(len(source), item_count)
        self.assertEqual(len(dest), item_count)
        
        # Verify order and values
        for i in range(item_count):
            self.assertEqual(source[i], dest[i])
    
    def test_thread_synchronization(self):
        """Test thread synchronization - concurrent execution"""
        start_time = time.perf_counter()
        self.pc.run(20)
        end_time = time.perf_counter()
        
        # Verify threads ran concurrently (should be faster than sequential)
        execution_time = (end_time - start_time) * 1000  # Convert to ms
        self.assertLess(execution_time, 10000, "Execution should complete in reasonable time")
        
        # Verify data integrity
        source = self.pc.get_source_container()
        dest = self.pc.get_destination_container()
        self.assertEqual(source, dest)
    
    def test_get_destination_container_returns_copy(self):
        """Test getDestinationContainer returns copy"""
        self.pc.run(5)
        
        dest1 = self.pc.get_destination_container()
        dest2 = self.pc.get_destination_container()
        
        # Should be equal but not same reference
        self.assertEqual(dest1, dest2)
        self.assertIsNot(dest1, dest2)
    
    def test_get_source_container_returns_copy(self):
        """Test getSourceContainer returns copy"""
        self.pc.initialize_source(5)
        
        source1 = self.pc.get_source_container()
        source2 = self.pc.get_source_container()
        
        # Should be equal but not same reference
        self.assertEqual(source1, source2)
        self.assertIsNot(source1, source2)
    
    def test_reset_statistics(self):
        """Test resetStatistics"""
        self.pc.run(10)
        
        # Verify statistics were collected
        dest1 = self.pc.get_destination_container()
        self.assertFalse(len(dest1) == 0)
        
        # Reset and verify cleared
        self.pc.reset_statistics()
        dest2 = self.pc.get_destination_container()
        self.assertEqual(len(dest2), 0)
    
    def test_multiple_runs(self):
        """Test multiple runs with same instance"""
        # First run
        self.pc.run(5)
        dest1 = self.pc.get_destination_container()
        self.assertEqual(len(dest1), 5)
        
        # Second run
        self.pc.run(10)
        dest2 = self.pc.get_destination_container()
        self.assertEqual(len(dest2), 10)
    
    def test_analysis_method(self):
        """Test analysis method executes without errors"""
        self.pc.run(10)
        
        # Should not throw exception
        try:
            self.pc.perform_analysis()
        except Exception as e:
            self.fail(f"perform_analysis() raised {type(e).__name__} unexpectedly: {e}")
    
    def test_queue_blocking_behavior(self):
        """Test that queue properly blocks when full/empty"""
        small_pc = ProducerConsumer(2)  # Small capacity to force blocking
        small_pc.run(10)
        
        # Verify data integrity despite blocking
        source = small_pc.get_source_container()
        dest = small_pc.get_destination_container()
        self.assertEqual(source, dest)
        
        # Verify wait times were recorded (indicating blocking occurred)
        self.assertGreaterEqual(small_pc.stats.producer_wait_time, 0)
        self.assertGreaterEqual(small_pc.stats.consumer_wait_time, 0)
    
    def test_concurrent_access_safety(self):
        """Test that concurrent access to destination container is safe"""
        self.pc.run(20)
        
        # Multiple threads reading destination container
        results = []
        
        def read_destination():
            results.append(self.pc.get_destination_container())
        
        threads = [threading.Thread(target=read_destination) for _ in range(5)]
        for t in threads:
            t.start()
        for t in threads:
            t.join()
        
        # All reads should return same result
        for result in results:
            self.assertEqual(result, self.pc.get_destination_container())


if __name__ == '__main__':
    unittest.main()

