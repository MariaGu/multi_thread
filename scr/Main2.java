package scr;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Задание 3.10.2
public class Main2 {
    static class Transaction {
        final int fromId;
        final int toId;
        final int amount;

        public Transaction(int fromId, int toId, int amount) {
            this.fromId = fromId;
            this.toId = toId;
            this.amount = amount;
        }

    }

    private static int[] balances;

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество пользователей: ");
//        int n = scanner.nextInt();
        int n = 4;
        System.out.println("Введите их начальные балансы: ");
        String input = "500 200 300 400";
//        String input = scanner.nextLine();

        balances = Arrays.stream(input.trim().split(" ")).mapToInt(Integer::parseInt).toArray();

        System.out.println("Введите количество транзакций: ");
        int m = 3;
//        int m = scanner.nextInt();
        Transaction[] transactions = new Transaction[m];

        System.out.println("Введите содержимое транзакций: ");
        for (int i = 0; i < m; i++) {
//            input = scanner.next();
            if (i == 0) {
                input = "0 - 100 - 1";
            } else if (i == 1) {
                input = "2 - 50 - 3";
            } else if (i == 2) {
                input = "1 - 100 - 3";
            }
            String[] transParts = input.trim().split(" - ");
            int fromId = Integer.parseInt(transParts[0]);
            int amount = Integer.parseInt(transParts[1]);
            int toId = Integer.parseInt(transParts[2]);
            transactions[i] = new Transaction(fromId, toId, amount);
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (Transaction transaction : transactions) {
            executor.execute(() -> makeTransaction(transaction));
        }

        while (!executor.isTerminated()) {
            executor.shutdown();
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Пользователь " + i + " баланс: " + balances[i]);
        }
//        scanner.close();
    }

    private synchronized static void makeTransaction(Transaction transaction) {
        if (transaction.amount <= balances[transaction.fromId]) {
            balances[transaction.fromId] -= transaction.amount;
            balances[transaction.toId] += transaction.amount;
        }
    }
}
