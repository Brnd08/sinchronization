package org.yourcompany.yourproject.race_condition_synchronized_statements;

import static java.lang.System.out;
import java.util.HashMap;

public class Bank {
    private static final HashMap<String, Account> accounts_by_owner = new HashMap<>();

    public static void createAccount(String owner, int balance){
        accounts_by_owner.put(owner, new Account(owner, balance));
    }

    public static int getBalance(String account_owner) {
        return accounts_by_owner.get(account_owner).balance;
    }

    public static void transfer(String from_owner, String to_owner, int amount) {
        // Leé el balance de ambas cuentas
        var originBalance = accounts_by_owner.get(from_owner).balance;
        var destinationBalance = accounts_by_owner.get(to_owner).balance;

        out.printf("%s | Transfer requested. available: %d, amount: %d, destination: %d. %n", 
            Thread.currentThread().getName(), originBalance, amount, destinationBalance);

        // Verifica que el balance sea suficiente
        if (originBalance < amount) {
            out.printf("%s | Transfer not possible. %n", Thread.currentThread().getName());
            return;
        }

        // incrementa y escribe el nuevo balance en cuenta destino
        accounts_by_owner.get(to_owner).balance = destinationBalance + amount;

        // disminuye y escribe el nuevo balance en cuenta origen 
        accounts_by_owner.get(from_owner).balance = originBalance - amount;

        out.printf("%s | Tranfer done. %n", Thread.currentThread().getName());
    }


    private static class Account {
        private final String owner;
        private int balance;

        Account(String owner, int initBalance) {
            this.owner = owner;
            this.balance = initBalance;
        }
    }
}
