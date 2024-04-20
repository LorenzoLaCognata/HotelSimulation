package Manager;

import Entity.Employee;
import Entity.Transaction;
import Enum.TransactionType;
import IO.Log;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FinancialManager {

    private BigDecimal rent;
    private BigDecimal balance = new BigDecimal(0);
    private final List<Transaction> transactions = new ArrayList<>();

    /**
     *
     * @return
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     *
     * @return
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param rent
     */
    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    /**
     *
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        balance = balance.add(transaction.getAmount());
    }

    /**
     *
     * @param transactionType
     * @return
     */
    public BigDecimal getTransactionsAmount(TransactionType transactionType) {

        BigDecimal amount = new BigDecimal(0);

        for (Transaction item: getTransactions()) {
            if (item.getTransactionType() == transactionType) {
                amount = amount.add(item.getAmount());
            }
        }

        return amount;

    }

    /**
     * @param date
     */
    public void payRent(LocalDate date) {

        Log.print("RENT:",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);

        addTransaction(new Transaction(TransactionType.RENT, date, rent.negate()));
        Log.print("\tPaid rent of " + Log.currencyToString(rent));
        Log.print("");

    }

    /**
     * @param date
     */
    public void paySalaries(LocalDate date, List<Employee> employees) {

        Log.print("SALARIES:",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);

        for(Employee item: employees) {
            addTransaction(new Transaction(TransactionType.SALARY, date, item.getSalary().negate()));
            Log.print("\tPaid salary of " + Log.currencyToString(item.getSalary()) + " to " + item);
        }

        Log.print("");

    }

    /**
     *
     */
    public void financialSummary() {
        Log.print("FINANCIAL SUMMARY",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
        Log.print("\tRevenues: " + Log.currencyToString(getTransactionsAmount(TransactionType.RESERVATION)));
        Log.print("\tRent: " + Log.currencyToString(getTransactionsAmount(TransactionType.RENT)));
        Log.print("\tSalaries: " + Log.currencyToString(getTransactionsAmount(TransactionType.SALARY)));
        Log.print("\tBalance: " + Log.currencyToString(getBalance()));
    }

}