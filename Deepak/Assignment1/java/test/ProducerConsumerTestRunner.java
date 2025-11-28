import java.util.List;
import java.util.ArrayList;

/**
 * Standalone Test Runner for ProducerConsumer
 * No external dependencies required - uses simple assertions
 */
public class ProducerConsumerTestRunner {
    private static int testsRun = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("ProducerConsumer Test Suite");
        System.out.println("=".repeat(60));
        System.out.println();
        
        try {
            testConstructorValidCapacity();
            testConstructorInvalidCapacityZero();
            testConstructorInvalidCapacityNegative();
            testInitializeSourceValid();
            testInitializeSourceZero();
            testInitializeSourceNegative();
            testInitializeSourceCustom();
            testInitializeSourceNull();
            testBasicProducerConsumer();
            testEmptySource();
            testSingleItem();
            testLargeNumberOfItems();
            testLargeCapacity();
            testDataIntegrity();
            testThreadSynchronization();
            testGetDestinationContainerReturnsCopy();
            testGetSourceContainerReturnsCopy();
            testResetStatistics();
            testMultipleRuns();
            testAnalysisMethod();
            
            System.out.println();
            System.out.println("=".repeat(60));
            System.out.println("Test Summary");
            System.out.println("=".repeat(60));
            System.out.println("Tests Run: " + testsRun);
            System.out.println("Tests Passed: " + testsPassed);
            System.out.println("Tests Failed: " + testsFailed);
            System.out.println("=".repeat(60));
            
            if (testsFailed == 0) {
                System.out.println("✓ All tests passed!");
                System.exit(0);
            } else {
                System.out.println("✗ Some tests failed!");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Fatal error running tests: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void assertTrue(boolean condition, String message) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println("✓ PASS: " + message);
        } else {
            testsFailed++;
            System.err.println("✗ FAIL: " + message);
        }
    }
    
    private static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }
    
    private static void assertEquals(Object expected, Object actual, String message) {
        testsRun++;
        if (expected == null && actual == null) {
            testsPassed++;
            System.out.println("✓ PASS: " + message);
        } else if (expected != null && expected.equals(actual)) {
            testsPassed++;
            System.out.println("✓ PASS: " + message);
        } else {
            testsFailed++;
            System.err.println("✗ FAIL: " + message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }
    
    private static void assertNotNull(Object obj, String message) {
        testsRun++;
        if (obj != null) {
            testsPassed++;
            System.out.println("✓ PASS: " + message);
        } else {
            testsFailed++;
            System.err.println("✗ FAIL: " + message);
        }
    }
    
    private static void assertThrows(Class<? extends Exception> expectedException, Runnable test, String message) {
        testsRun++;
        try {
            test.run();
            testsFailed++;
            System.err.println("✗ FAIL: " + message + " (Expected exception " + expectedException.getSimpleName() + " but none was thrown)");
        } catch (Exception e) {
            if (expectedException.isInstance(e)) {
                testsPassed++;
                System.out.println("✓ PASS: " + message);
            } else {
                testsFailed++;
                System.err.println("✗ FAIL: " + message + " (Expected " + expectedException.getSimpleName() + " but got " + e.getClass().getSimpleName() + ")");
            }
        }
    }
    
    // Test methods
    private static void testConstructorValidCapacity() {
        ProducerConsumer pc = new ProducerConsumer(5);
        assertNotNull(pc, "Test constructor with valid capacity");
        assertNotNull(pc.getSourceContainer(), "Source container should not be null");
        assertNotNull(pc.getDestinationContainer(), "Destination container should not be null");
    }
    
    private static void testConstructorInvalidCapacityZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProducerConsumer(0);
        }, "Test constructor with invalid capacity (zero)");
    }
    
    private static void testConstructorInvalidCapacityNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProducerConsumer(-1);
        }, "Test constructor with invalid capacity (negative)");
    }
    
    private static void testInitializeSourceValid() {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.initializeSource(10);
        List<Integer> source = pc.getSourceContainer();
        assertEquals(10, source.size(), "Source should have 10 items");
        assertEquals(1, source.get(0), "First item should be 1");
        assertEquals(10, source.get(9), "Last item should be 10");
    }
    
    private static void testInitializeSourceZero() {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.initializeSource(0);
        List<Integer> source = pc.getSourceContainer();
        assertEquals(0, source.size(), "Source should be empty");
    }
    
    private static void testInitializeSourceNegative() {
        ProducerConsumer pc = new ProducerConsumer(5);
        assertThrows(IllegalArgumentException.class, () -> {
            pc.initializeSource(-1);
        }, "Test initializeSource with negative count");
    }
    
    private static void testInitializeSourceCustom() {
        ProducerConsumer pc = new ProducerConsumer(5);
        List<Integer> customList = new ArrayList<>();
        customList.add(100);
        customList.add(200);
        customList.add(300);
        
        pc.initializeSource(customList);
        List<Integer> source = pc.getSourceContainer();
        assertEquals(3, source.size(), "Source should have 3 items");
        assertEquals(customList, source, "Source should match custom list");
    }
    
    private static void testInitializeSourceNull() {
        ProducerConsumer pc = new ProducerConsumer(5);
        assertThrows(IllegalArgumentException.class, () -> {
            pc.initializeSource((List<Integer>) null);
        }, "Test initializeSource with null list");
    }
    
    private static void testBasicProducerConsumer() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.run(10);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        assertEquals(source.size(), dest.size(), "Source and destination should have same size");
        assertEquals(source, dest, "Source and destination should match");
    }
    
    private static void testEmptySource() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.run(0);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        assertEquals(0, source.size(), "Source should be empty");
        assertEquals(0, dest.size(), "Destination should be empty");
    }
    
    private static void testSingleItem() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.run(1);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        assertEquals(1, source.size(), "Source should have 1 item");
        assertEquals(1, dest.size(), "Destination should have 1 item");
        assertEquals(source, dest, "Source and destination should match");
    }
    
    private static void testLargeNumberOfItems() throws InterruptedException {
        ProducerConsumer largePc = new ProducerConsumer(10);
        largePc.run(100);
        
        List<Integer> source = largePc.getSourceContainer();
        List<Integer> dest = largePc.getDestinationContainer();
        
        assertEquals(100, source.size(), "Source should have 100 items");
        assertEquals(100, dest.size(), "Destination should have 100 items");
        assertEquals(source, dest, "Source and destination should match");
    }
    
    private static void testLargeCapacity() throws InterruptedException {
        ProducerConsumer largeCapPc = new ProducerConsumer(100);
        largeCapPc.run(20);
        
        List<Integer> source = largeCapPc.getSourceContainer();
        List<Integer> dest = largeCapPc.getDestinationContainer();
        
        assertEquals(source, dest, "Source and destination should match");
    }
    
    private static void testDataIntegrity() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        int itemCount = 50;
        pc.run(itemCount);
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        
        assertEquals(itemCount, source.size(), "Source should have " + itemCount + " items");
        assertEquals(itemCount, dest.size(), "Destination should have " + itemCount + " items");
        
        for (int i = 0; i < itemCount; i++) {
            assertEquals(source.get(i), dest.get(i), "Item " + i + " should match");
        }
    }
    
    private static void testThreadSynchronization() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        long startTime = System.currentTimeMillis();
        pc.run(20);
        long endTime = System.currentTimeMillis();
        
        long executionTime = endTime - startTime;
        assertTrue(executionTime < 10000, "Execution should complete in reasonable time");
        
        List<Integer> source = pc.getSourceContainer();
        List<Integer> dest = pc.getDestinationContainer();
        assertEquals(source, dest, "Source and destination should match after concurrent execution");
    }
    
    private static void testGetDestinationContainerReturnsCopy() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.run(5);
        
        List<Integer> dest1 = pc.getDestinationContainer();
        List<Integer> dest2 = pc.getDestinationContainer();
        
        assertEquals(dest1, dest2, "Both copies should be equal");
        assertTrue(dest1 != dest2, "Should return different object references (copies)");
    }
    
    private static void testGetSourceContainerReturnsCopy() {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.initializeSource(5);
        
        List<Integer> source1 = pc.getSourceContainer();
        List<Integer> source2 = pc.getSourceContainer();
        
        assertEquals(source1, source2, "Both copies should be equal");
        assertTrue(source1 != source2, "Should return different object references (copies)");
    }
    
    private static void testResetStatistics() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.run(10);
        
        List<Integer> dest1 = pc.getDestinationContainer();
        assertFalse(dest1.isEmpty(), "Destination should not be empty after run");
        
        pc.resetStatistics();
        List<Integer> dest2 = pc.getDestinationContainer();
        assertTrue(dest2.isEmpty(), "Destination should be empty after reset");
    }
    
    private static void testMultipleRuns() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        
        pc.run(5);
        List<Integer> dest1 = pc.getDestinationContainer();
        assertEquals(5, dest1.size(), "First run should have 5 items");
        
        pc.run(10);
        List<Integer> dest2 = pc.getDestinationContainer();
        assertEquals(10, dest2.size(), "Second run should have 10 items");
    }
    
    private static void testAnalysisMethod() throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer(5);
        pc.run(10);
        
        try {
            pc.performAnalysis();
            assertTrue(true, "Analysis method should execute without errors");
        } catch (Exception e) {
            assertTrue(false, "Analysis method should not throw exception: " + e.getMessage());
        }
    }
}

