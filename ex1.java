public class ex1 {
    static class Customer {
        private final String name;

        public Customer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    static class Cashier implements Runnable {
        private final Customer customer;

        public Cashier(Customer customer) {
            this.customer = customer;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " почав обслуговування клієнта: " + customer.getName());
            try {
                Thread.sleep((int) (Math.random() * 3000) + 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Обслуговування клієнта " + customer.getName() + " було перервано.");
            }
            System.out.println(Thread.currentThread().getName() + " закінчив обслуговування клієнта: " + customer.getName());
        }
    }

    public static void main(String[] args) {
        Customer[] customers = {
                new Customer("Іван"),
                new Customer("Марія"),
                new Customer("Петро"),
                new Customer("Олена"),
                new Customer("Олександр")
        };

        for (Customer customer : customers) {
            Thread cashierThread = new Thread(new Cashier(customer));
            cashierThread.setName("Каса-" + customer.getName());
            cashierThread.start();
        }
    }
}
