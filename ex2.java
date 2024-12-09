import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ex2 {
    static class SupermarketAccount {
        private int balance = 0;

        public synchronized void addToBalance(int amount) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " додав " + amount + " до рахунку. Загальний баланс: " + balance);
        }

        public void addToBalanceWithLock(int amount, Lock lock) {
            lock.lock();
            try {
                balance += amount;
                System.out.println(Thread.currentThread().getName() + " додав " + amount + " до рахунку. Загальний баланс: " + balance);
            } finally {
                lock.unlock();
            }
        }
    }

    static class Customer {
        private final String name;
        private final int purchaseAmount;

        public Customer(String name, int purchaseAmount) {
            this.name = name;
            this.purchaseAmount = purchaseAmount;
        }

        public String getName() {
            return name;
        }

        public int getPurchaseAmount() {
            return purchaseAmount;
        }
    }

    static class Cashier implements Runnable {
        private final Customer customer;
        private final SupermarketAccount account;
        private final Lock lock;

        public Cashier(Customer customer, SupermarketAccount account, Lock lock) {
            this.customer = customer;
            this.account = account;
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " почав обслуговування клієнта: " + customer.getName());
            try {
                Thread.sleep((int) (Math.random() * 3000) + 1000);
                account.addToBalance(customer.getPurchaseAmount());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Обслуговування клієнта " + customer.getName() + " було перервано.");
            }
            System.out.println(Thread.currentThread().getName() + " закінчив обслуговування клієнта: " + customer.getName());
        }
    }
    public static void main(String[] args) {
        SupermarketAccount account = new SupermarketAccount();
        Lock lock = new ReentrantLock();

        Customer[] customers = {
                new Customer("Іван", 100),
                new Customer("Марія", 200),
                new Customer("Петро", 150),
                new Customer("Олена", 120),
                new Customer("Олександр", 300)
        };
        for (Customer customer : customers) {
            Thread cashierThread = new Thread(new Cashier(customer, account, lock));
            cashierThread.setName("Каса-" + customer.getName());
            cashierThread.start();
        }
    }
}

