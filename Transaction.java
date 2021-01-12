import java.util.Date;

public class Transaction {
    private long amount;
    private String customerId; // For simplicity I have used names for customerId in test data, it is normally the random UUID for customer.
    private Date transactionDate;

    public Transaction(long amount, String customerId, Date transactionDate) {
        this.amount = amount;
        this.customerId = customerId;
        this.transactionDate = transactionDate;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public int getTransactionMonth() {
        return this.transactionDate.getMonth();
    }

    public long getAmount() {
        return this.amount;
    }
}
