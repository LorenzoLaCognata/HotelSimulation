package Manager;

import Entity.Transaction;

import Enum.TransactionType;
import IO.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FinancialManager {

    private BigDecimal balance = new BigDecimal(0);
    private final List<Transaction> transactions = new ArrayList<>();

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        balance = balance.add(transaction.getAmount());
    }

    public BigDecimal getTransactionsAmount(TransactionType transactionType) {

        BigDecimal amount = new BigDecimal(0);

        for (Transaction item: getTransactions()) {
            if (item.getTransactionType() == transactionType) {
                amount = amount.add(item.getAmount());
            }
        }

        return amount;

    }

    public void financialSummary() {
        Log.printColor(Log.WHITE_UNDERLINED, "FINANCIAL SUMMARY");
        Log.print("\tRevenues: " + Log.currencyString(getTransactionsAmount(TransactionType.RESERVATION)));
        Log.print("\tRent: " + Log.currencyString(getTransactionsAmount(TransactionType.RENT)));
        Log.print("\tSalaries: " + Log.currencyString(getTransactionsAmount(TransactionType.SALARY)));
        Log.print("\tBalance: " + Log.currencyString(getBalance()));
    }

}
