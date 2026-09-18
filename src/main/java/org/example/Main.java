package org.example;
/**
 * LUCA PILLITU
 * CLASSE: 5CI
 * programma che genera casualmente due numeri primi
 * calcola il prodott dei numeri primi e il tempo per eseguire il calcolo
 * fattorizza il prodotto, trovando i numeri primi che lo compongono e il tempo per fattorizzarli
 *
 * data: 8 marzo 2024
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {

    private static JTextArea textArea;

    public static void main(String[] args) {
        // Create JFrame for data display
        JFrame dataFrame = new JFrame("Dati di calcolo");
        dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataFrame.setSize(400, 600);
        dataFrame.setLocation(50,50);

        // Create JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Create JScrollPane and add JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        dataFrame.add(scrollPane);

        // Set JFrame visible
        dataFrame.setVisible(true);

        // Create JFrame for chart display
        JFrame chartFrame = new JFrame("Grafico di comparazione tempi");
        chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chartFrame.setSize(800, 600);
        chartFrame.setLocation(700,50);

        // Create a series for product calculation time
        XYSeries productSeries = new XYSeries("Tempo per calcolo del PRODOTTO");

        // Create a series for factorization time
        XYSeries factorizationSeries = new XYSeries("Tempo di FATTORIZZAZIONE");

        // Generate two random prime numbers and measure time for each
        for (int i = 0; i < 5; i++) {
            int prime1 = generatePrime();
            int prime2 = generatePrime();

            long startTimeProduct = System.nanoTime();
            long product = (long) prime1 * prime2;
            long endTimeProduct = System.nanoTime();
            long durationProduct = endTimeProduct - startTimeProduct;

            textArea.append("\nProdotto: " + product + "\n");
            textArea.append("Tempo per il calcolo del PRODOTTO: " + durationProduct + " nanosec.\n");

            long startTimeFactorization = System.nanoTime();
            factorize(product);
            long endTimeFactorization = System.nanoTime();
            long durationFactorization = endTimeFactorization - startTimeFactorization;

            // Add the data points to the series
            productSeries.add(product, durationProduct);
            factorizationSeries.add(product, durationFactorization);

            textArea.append("Numeri trovati dalla Fattorizzazione: " + factorize(product) + "\n");
            textArea.append("Tempo per la FATTORIZZAZIONE del prodotto: " + durationFactorization + " nanosec.\n---------------------------------");

            textArea.setCaretPosition(textArea.getDocument().getLength()); // Scroll JTextArea to the bottom
        }

        // Create a dataset and add the series to it
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(productSeries);
        dataset.addSeries(factorizationSeries);

        // Create the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Tempo comparato per calcolo prodotto numeri primi e fattorizzazione", // Title
                "Valori", // X-axis Label
                "Tempo (nanosec.) [1ns = 1000k millisec.]", // Y-axis Label
                dataset // Dataset
        );

        // Create ChartPanel and add to JFrame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartFrame.add(chartPanel, BorderLayout.CENTER);

        // Set JFrame visible
        chartFrame.setVisible(true);
    }

    //funzione per controllare che sia primo un numero
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0)
                return false;
        }

        return true;
    }

    //generatore di numeri casuali
    public static int generatePrime() {
        Random random = new Random();
        int num;
        do {
            num = random.nextInt(1000) + 1; // Generates a random number between 1 and 1000
        } while (!isPrime(num));
        return num;
    }

    //funzione di fattorizazzione dei prodotti
    public static String factorize(long number) {
        long n = number;
        StringBuilder factors = new StringBuilder();
        for (int i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.append(i).append(" - ");
                n /= i;
            }
        }
        if (n > 1) {
            factors.append(n);
        }
        return factors.toString();
    }

}