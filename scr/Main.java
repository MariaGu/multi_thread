package scr;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// Задание 3.10.1
public class Main {

    public static void main(String[] args) {
        double a = 3.0;
        double b = 4.0;
        double c = 10;
        double d = 16.0;

        CompletableFuture<Double> sumOfSquaresFuture = getSumOfSquaresFuture(a, b);
        CompletableFuture<Double> logFuture = getLogFuture(c);
        CompletableFuture<Double> sqrtFuture = getSqrtFuture(d);

        CompletableFuture<Double> finalResultFuture = sumOfSquaresFuture
                .thenCombine(logFuture, (squareSum, log) -> squareSum * log)
                .thenCombine(sqrtFuture, (partialResult, sqrt) -> partialResult / sqrt);

        try {
            double result = finalResultFuture.get();
            System.out.println("Результат вычисления формулы: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Ошибка во время работы программы: " + e.getMessage());
        }
    }

    private static void delayInSec(int delay) {
        try {
            Thread.sleep(delay * 1000L);
        } catch (InterruptedException e) {
            System.out.println("Ошибка во время задержки программы: " + e.getMessage());
        }
    }

    private static CompletableFuture<Double> getSumOfSquaresFuture(double a, double b) {
        return CompletableFuture.supplyAsync(() -> {
            delayInSec(5);
            double squareSum = a * a + b * b;
            System.out.println("Сумма квадратов: " + squareSum);
            return squareSum;
        });
    }

    private static CompletableFuture<Double> getLogFuture(double c) {
        return CompletableFuture.supplyAsync(() -> {
            delayInSec(15);
            double log = Math.log(c);
            System.out.println("Логарифм: " + log);
            return log;
        });
    }

    private static CompletableFuture<Double> getSqrtFuture(double d) {
        return CompletableFuture.supplyAsync(() -> {
            delayInSec(10);
            double sqrt = Math.sqrt(d);
            System.out.println("Квадратный корень: " + sqrt);
            return sqrt;
        });
    }
}