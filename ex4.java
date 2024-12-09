import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ex4 {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 7, 10, 3, 4, 8);

        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {

            List<Future<String>> futures = new ArrayList<>();

            for (Integer number : numbers) {
                Callable<String> task = () -> {
                    long factorial = calculateFactorial(number);
                    return "Факторіал числа " + number + " = " + factorial;
                };
                Future<String> future = executor.submit(task);
                futures.add(future);
            }

            for (Future<String> future : futures) {
                try {
                    String result = future.get();
                    System.out.println(result);
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("Помилка під час виконання задачі: " + e.getMessage());
                }
            }
        }
    }
    public static long calculateFactorial(int number) {
        if (number == 0 || number == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}

