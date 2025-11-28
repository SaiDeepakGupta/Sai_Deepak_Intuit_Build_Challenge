# Java Implementation

## Source Files

### ProducerConsumer.java
- **Location**: `src/ProducerConsumer.java`
- **Description**: Producer-Consumer pattern using `BlockingQueue`
- **Compile**: `javac src/ProducerConsumer.java`
- **Run**: `java -cp src ProducerConsumer`

### ProducerConsumerWaitNotify.java
- **Location**: `src/ProducerConsumerWaitNotify.java`
- **Description**: Producer-Consumer pattern using `wait()` and `notify()`
- **Compile**: `javac src/ProducerConsumerWaitNotify.java`
- **Run**: `java -cp src ProducerConsumerWaitNotify`

## Test Files

### ProducerConsumerTest.java
- **Location**: `test/ProducerConsumerTest.java`
- **Description**: Comprehensive unit tests using JUnit 5
- **Requirements**: JUnit 5 (Jupiter) must be in classpath
- **Run**: Use your IDE or build tool (Maven/Gradle)
- **Note**: If JUnit 5 is not available, use ProducerConsumerTestRunner.java instead

### ProducerConsumerTestRunner.java
- **Location**: `test/ProducerConsumerTestRunner.java`
- **Description**: Standalone test runner with no external dependencies
- **Requirements**: None (uses only standard Java libraries)
- **Run**:
  ```bash
  cd java/test
  javac -cp ../src ProducerConsumerTestRunner.java
  java -cp ../src:../test ProducerConsumerTestRunner
  ```

## Compilation and Execution

### From project root:
```bash
# Compile all source files
javac java/src/*.java

# Run ProducerConsumer
java -cp java/src ProducerConsumer

# Run ProducerConsumerWaitNotify
java -cp java/src ProducerConsumerWaitNotify
```

### From java directory:
```bash
cd java

# Compile
javac src/*.java

# Run
java -cp src ProducerConsumer
java -cp src ProducerConsumerWaitNotify
```

## Requirements
- Java Development Kit (JDK) 8 or higher
- JUnit 5 (for running tests)

