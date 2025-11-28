# Java Test Files

## Test Files Overview

This directory contains two test implementations:

### 1. ProducerConsumerTest.java (JUnit 5)
- **Framework**: JUnit 5 (Jupiter)
- **Status**: Requires JUnit 5 dependency
- **Use Case**: For IDEs or projects with JUnit 5 already configured
- **Compilation**: Will fail if JUnit 5 is not in classpath

### 2. ProducerConsumerTestRunner.java (Standalone)
- **Framework**: None (pure Java)
- **Status**: ✅ Ready to use - no dependencies
- **Use Case**: Recommended for command-line testing
- **Compilation**: Works with standard JDK only

## Running Tests

### Option 1: Standalone Test Runner (Recommended)

```bash
cd java/test
javac -cp ../src ProducerConsumerTestRunner.java
java -cp ../src:../test ProducerConsumerTestRunner
```

**Advantages:**
- No external dependencies
- Works immediately
- All 87 tests pass
- Clear console output

### Option 2: JUnit 5 Tests

#### Prerequisites
You need JUnit 5 in your classpath. Options:

**A. Using Maven:**
Create `pom.xml` in project root:
```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>producer-consumer</artifactId>
    <version>1.0</version>
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

Then run: `mvn test`

**B. Using IDE:**
- IntelliJ IDEA: Right-click test file → Run
- Eclipse: Right-click test file → Run As → JUnit Test
- VS Code: Install Java Test Runner extension

**C. Manual Setup:**
1. Download JUnit 5 JARs
2. Add to classpath when compiling:
```bash
javac -cp "../src:junit-jupiter-api-5.9.2.jar:junit-jupiter-engine-5.9.2.jar" ProducerConsumerTest.java
```

## Test Coverage

Both test files cover:
- ✅ Constructor validation
- ✅ Source initialization (valid, zero, negative, custom, null)
- ✅ Basic producer-consumer pattern
- ✅ Edge cases (empty, single item, large datasets)
- ✅ Data integrity verification
- ✅ Thread synchronization
- ✅ Container copy verification
- ✅ Statistics reset
- ✅ Multiple runs
- ✅ Analysis method execution

## Test Results

The standalone test runner shows:
```
Tests Run: 87
Tests Passed: 87
Tests Failed: 0
✓ All tests passed!
```

## Troubleshooting

**Issue**: `ProducerConsumerTest.java` won't compile
**Solution**: Use `ProducerConsumerTestRunner.java` instead, or add JUnit 5 to classpath

**Issue**: `ClassNotFoundException: ProducerConsumer`
**Solution**: Compile with `-cp ../src` to include source directory in classpath

**Issue**: Tests fail with assertion errors
**Solution**: Ensure ProducerConsumer.java is compiled and in the classpath

