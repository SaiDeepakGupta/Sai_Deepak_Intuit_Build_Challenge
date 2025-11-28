package com.assignment2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for reading and parsing CSV files.
 */
public class CSVReader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Reads sales records from a CSV file.
     * 
     * @param filePath Path to the CSV file
     * @return List of SalesRecord objects
     * @throws IOException if file cannot be read
     */
    public static List<SalesRecord> readSalesData(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                .skip(1) // Skip header row
                .filter(line -> !line.trim().isEmpty()) // Filter empty lines
                .map(CSVReader::parseLine)
                .collect(Collectors.toList());
        }
    }

    /**
     * Parses a single CSV line into a SalesRecord object.
     * 
     * @param line CSV line to parse
     * @return SalesRecord object
     */
    private static SalesRecord parseLine(String line) {
        String[] fields = line.split(",");
        
        LocalDate date = LocalDate.parse(fields[0].trim(), DATE_FORMATTER);
        String product = fields[1].trim();
        String category = fields[2].trim();
        int quantity = Integer.parseInt(fields[3].trim());
        double unitPrice = Double.parseDouble(fields[4].trim());
        double totalAmount = Double.parseDouble(fields[5].trim());
        String region = fields[6].trim();
        String salesPerson = fields[7].trim();
        
        return new SalesRecord(date, product, category, quantity, unitPrice, 
                              totalAmount, region, salesPerson);
    }
}

