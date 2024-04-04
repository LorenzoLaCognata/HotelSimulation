package Entity;

import Enum.TransactionType;
import IO.Log;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private final TransactionType transactionType;
    private final LocalDate date;
    private final BigDecimal amount;

    // Constructor

    public Transaction(TransactionType transactionType, LocalDate date, BigDecimal amount) {
        this.transactionType = transactionType;
        this.date = date;
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // Override

    @Override
    public String toString() {
        return "Transaction | " + transactionType + " | " + date + " | " + Log.currencyString(amount);
    }

}

