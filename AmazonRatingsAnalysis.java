import java.io.*;
import java.util.*;

public class AmazonRatingsAnalysis {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter input CSV filename: ");
            String inputFile = sc.nextLine().trim();

            System.out.print("Enter output CSV filename (for cleaned data): ");
            String outputFile = sc.nextLine().trim();

            List<Double> ratings = new ArrayList<>();
            List<String[]> cleanedRows = new ArrayList<>();

            try (Scanner fileScanner = new Scanner(new File(inputFile))) {
                // Skip header
                if (fileScanner.hasNextLine()) {
                    cleanedRows.add(fileScanner.nextLine().split(","));
                }

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");

                    if (parts.length >= 3) {
                        try {
                            double rating = Double.parseDouble(parts[2].trim());
                            ratings.add(rating);
                            cleanedRows.add(parts); // only add valid rows
                        } catch (NumberFormatException e) {
                            // skip bad rating rows
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("ERROR: File not found -> " + inputFile);
                return;
            }

            if (ratings.isEmpty()) {
                System.out.println("No valid ratings found. Exiting.");
                return;
            }

            // Print results
            System.out.println("\n--- Amazon Ratings Analysis ---");
            System.out.println("Valid ratings loaded: " + ratings);
            System.out.printf("Total ratings: %d%n", ratings.size());
            System.out.printf("Average rating: %.2f%n", getAverage(ratings));
            System.out.printf("Median rating: %.2f%n", getMedian(ratings));
            System.out.printf("Std Dev: %.2f%n", getStdDev(ratings));

            // Save cleaned data
            try (PrintWriter writer = new PrintWriter(new File(outputFile))) {
                for (String[] row : cleanedRows) {
                    writer.println(String.join(",", row));
                }
                System.out.println("\nCleaned data saved to: " + outputFile);
            } catch (IOException e) {
                System.out.println("ERROR: Could not write to " + outputFile);
            }
        }
    }

    // --- Helper methods ---
    private static double getAverage(List<Double> list) {
        double sum = 0;
        for (double d : list) sum += d;
        return sum / list.size();
    }

    private static double getMedian(List<Double> list) {
        List<Double> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        int n = sorted.size();
        if (n % 2 == 1) return sorted.get(n / 2);
        return (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
    }

    private static double getStdDev(List<Double> list) {
        double mean = getAverage(list);
        double sumSq = 0;
        for (double d : list) {
            sumSq += Math.pow(d - mean, 2);
        }
        return Math.sqrt(sumSq / list.size());
    }
 {
    
 } 
}
