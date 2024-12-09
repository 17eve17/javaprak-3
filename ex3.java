import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ex3 {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5, 10, 13, 15, 17, 19, 23, 24, 29, 31);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        ConcurrentHashMap<Integer, Boolean> results = new ConcurrentHashMap<>();
        for (Integer number : numbers) {
            executor.submit(() -> {
                boolean isPrime = isPrime(number);
                results.put(number, isPrime);
                System.out.println(Thread.currentThread().getName() + " перевірив число " + number + ": " + (isPrime ? "Просте" : "Складне"));
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Виконання потоків було перервано.");
        }
        System.out.println("Результати перевірки:");
        results.forEach((number, isPrime) ->
                System.out.println("Число " + number + " є " + (isPrime ? "простим" : "складним"))
        );
    }
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}

