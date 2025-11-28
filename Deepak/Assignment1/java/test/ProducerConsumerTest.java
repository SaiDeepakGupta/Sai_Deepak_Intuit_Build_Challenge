/**
 * Comprehensive Unit Tests for ProducerConsumer using JUnit 5
 * 
 * ⚠️ WARNING: This file REQUIRES JUnit 5 (Jupiter) to compile and run.
 * 
 * If you see compilation errors about missing JUnit packages, you have two options:
 * 
 * OPTION 1 (Recommended): Use the standalone test runner (no dependencies)
 *   cd java/test
 *   javac -cp ../src ProducerConsumerTestRunner.java
 *   java -cp ../src:../test ProducerConsumerTestRunner
 * 
 * OPTION 2: Install JUnit 5 and use this file
 *   - Use an IDE with JUnit 5 support (IntelliJ IDEA, Eclipse, VS Code)
 *   - Or add JUnit 5 via Maven/Gradle
 *   - See java/test/README.md for detailed instructions
 * 
 * The standalone test runner (ProducerConsumerTestRunner.java) provides
 * the same test coverage without any external dependencies.
 */

// JUnit 5 imports - will fail to compile if JUnit 5 is not in classpath
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

public class ProducerConsumerTest {
    private ProducerConsumer pc;
    
    @BeforeEach
    void setUp() {
        pc = new ProducerConsumer(5);
    }
    
    @Test
    @DisplayName("Test constructor with valid capacity")
    void testConstructorValidCapacity() {
        assertNotNull(pc);
        assertNotNull(pc.getSourceContainer());
        assertNotNull(pc.getDestinationContainer());
    }
    
    @Test
    @DisplayName("Test constructor with invalid capacity (zero)")
    void testConstructorInvalidCapacityZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProducerConsumer(0);
        });
    }
    
    @Test
    @DisplayName("Test constructor with invalid capacity (negative)")
    void testConstructorInvalidCapacityNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProducerConsumer(-1);
        });
    }
    
    @Test
    @DisplayName("Test initializeSource with valid item count")
    void testInitializeSourceValid() {
        pc.initializeSource(10);
        List<Integer> source = pc.getSourceContainer();
        assertEquals(10, source.size());
        assertEquals(1, source.get(0));
        assertEquals(10, source.get(9));
    }
    
    @Test
    @DisplayName("Test initializeSource with zero items")
    void testInitializeSourceZero() {
        pc.initializeSource(0);
        List<Integer> source = pc.getSourceContainer();
        assertEquals(0, source.size());
    }
    
    @Test
    @DisplayName("Test initializeSource with negative count")
    void testInitializeSourceNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            pc.initializeSource(-1);
        });
    }
    
    @Test
    @DisplayName("Test initializeSource with custom list")
    void testInitializeSourceCustom() {
        List<Integer> customList = new ArrayList<>();
        customList.add(100);
        customList.add(200);
        customList.add(300);
        
        pc.initializeSource(customList);
        List<Integer> source = pc.getSourceContainer();
        assertEquals(3, source.size());
        assertEquals(customList, source);
    }
    
    @Test
    @DisplayName("Test initializeSource with null list")
    void testInitializeSourceNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            pc.initializeSource((List<Integer>) null);
        });
    }
    
    @Test
    @DisplayName("Test basic producer-consumer pattern")
    void testBasicProducerConsumer() throws InterruptedException {
        pc.run(10);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        assertEquals(source.size(), dest.size());
        assertEquals(source, dest);
    }
    
    @Test
    @DisplayName("Test with empty source")
    void testEmptySource() throws InterruptedException {
        pc.run(0);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        assertEquals(0, source.size());
        assertEquals(0, dest.size());
    }
    
    @Test
    @DisplayName("Test with single item")
    void testSingleItem() throws InterruptedException {
        pc.run(1);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        assertEquals(1, source.size());
        assertEquals(1, dest.size());
        assertEquals(source, dest);
    }
    
    @Test
    @DisplayName("Test with large number of items")
    void testLargeNumberOfItems() throws InterruptedException {
        ProducerConsumer largePc = new ProducerConsumer(10);
        largePc.run(100);
        
        List<Integer> source = largePc.getSourceContainer();
        List<Integer> dest = largePc.getDestinationContainer();
        
        assertEquals(100, source.size());
        assertEquals(100, dest.size());
        assertEquals(source, dest);
    }
    
    @Test
    @DisplayName("Test with queue capacity larger than items")
    void testLargeCapacity() throws InterruptedException {
        ProducerConsumer largeCapPc = new ProducerConsumer(100);
        largeCapPc.run(20);
        
        List<Integer> source = largeCapPc.getSourceContainer();
        List<Integer> dest = largeCapPc.getDestinationContainer();
        
        assertEquals(source, dest);
    }
    
    @Test
    @DisplayName("Test data integrity - no data loss")
    void testDataIntegrity() throws InterruptedException {
        int itemCount = 50;
        pc.run(itemCount);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        // Verify all items transferred
        assertEquals(itemCount, source.size());
        assertEquals(itemCount, dest.size());
        
        // Verify order and values
        for (int i = 0; i < itemCount; i++) {
            assertEquals(source.get(i), dest.get(i));
        }
    }
    
    @Test
    @DisplayName("Test thread synchronization - concurrent execution")
    void testThreadSynchronization() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        pc.run(20);
        long endTime = System.currentTimeMillis();
        
        // Verify threads ran concurrently (should be faster than sequential)
        long executionTime = endTime - startTime;
        assertTrue(executionTime < 10000, "Execution should complete in reasonable time");
        
        // Verify data integrity
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        assertEquals(source, dest);
    }
    
    @Test
    @DisplayName("Test getDestinationContainer returns copy")
    void testGetDestinationContainerReturnsCopy() throws InterruptedException {
        pc.run(5);
        
        List<Integer> dest1 = pc.getDestinationContainer();
        List<Integer> dest2 = pc.getDestinationContainer();
        
        // Should be equal but not same reference
        assertEquals(dest1, dest2);
        assertNotSame(dest1, dest2);
    }
    
    @Test
    @DisplayName("Test getSourceContainer returns copy")
    void testGetSourceContainerReturnsCopy() {
        pc.initializeSource(5);
        
        List<Integer> source1 = pc.getSourceContainer();
        List<Integer> source2 = pc.getSourceContainer();
        
        // Should be equal but not same reference
        assertEquals(source1, source2);
        assertNotSame(source1, source2);
    }
    
    @Test
    @DisplayName("Test resetStatistics")
    void testResetStatistics() throws InterruptedException {
        pc.run(10);
        
        // Verify statistics were collected
        List<Integer> dest1 = pc.getDestinationContainer();
        assertFalse(dest1.isEmpty());
        
        // Reset and verify cleared
        pc.resetStatistics();
        List<Integer> dest2 = pc.getDestinationContainer();
        assertTrue(dest2.isEmpty());
    }
    
    @Test
    @DisplayName("Test multiple runs with same instance")
    void testMultipleRuns() throws InterruptedException {
        // First run
        pc.run(5);
        List<Integer> dest1 = pc.getDestinationContainer();
        assertEquals(5, dest1.size());
        
        // Second run
        pc.run(10);
        List<Integer> dest2 = pc.getDestinationContainer();
        assertEquals(10, dest2.size());
    }
    
    @Test
    @DisplayName("Test analysis method executes without errors")
    void testAnalysisMethod() throws InterruptedException {
        pc.run(10);
        
        // Should not throw exception
        assertDoesNotThrow(() -> {
            pc.performAnalysis();
        });
    }
}

