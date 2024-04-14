package Entity;

import Enum.TransactionType;
import IO.Log;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 */
public class Transaction {

    private final TransactionType transactionType;
    private final LocalDate date;
    private final BigDecimal amount;

    /**
     *
     * @param transactionType
     * @param date
     * @param amount
     */
    public Transaction(TransactionType transactionType, LocalDate date, BigDecimal amount) {
        this.transactionType = transactionType;
        this.date = date;
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     *
     * @return
     */
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction | " + transactionType + " | " + date + " | " + Log.currencyToString(amount);
    }

}

