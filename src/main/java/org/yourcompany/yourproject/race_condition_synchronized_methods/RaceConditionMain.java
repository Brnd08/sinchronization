package org.yourcompany.yourproject.race_condition_synchronized_methods;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.yourcompany.yourproject.NumberedThreadFactory;

public class RaceConditionMain{
    public static void main(String... args) throws InterruptedException{
        // Creamos cuentas con balances iniciales
        Bank.createAccount("Julie", 5000);
        Bank.createAccount("Brandon", 0);

        synchronized (System.class) {
            System.out.println("Hello World");
        }

        // Creamos un pool de hilos
        NumberedThreadFactory.resetCounter();
        ExecutorService executor = Executors.newFixedThreadPool(10, new NumberedThreadFactory());

        // Definimos el Runnable que executaremos de manera concurrente
        Runnable requestTranferRunnable = () -> {
            Bank.transfer("Julie", "Brandon", 500);
        };
        
        System.out.printf("Balance before operations: Julie: $%d, Brandon: $%d. %n", Bank.getBalance("Julie"),
             Bank.getBalance("Brandon"));

        // Solicitamos multiples transferencias
        for (int i = 0; i < 10; i++) {
            executor.execute(requestTranferRunnable);
        }

        // wait termination
        executor.shutdown();
        while (!executor.isTerminated()){
        }

        // El balance final esperado es Julie: 0, Brandon 5000
        System.out.printf("Balance after operations: Julie: $%d, Brandon: $%d. %n", Bank.getBalance("Julie"), 
            Bank.getBalance("Brandon"));

    }
}
