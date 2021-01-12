import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RewardsProgram {

    public static void main(String[] args) throws ParseException {
        Map<String, RewardsPerCustomer> rewards = new HashMap<>();
        List<Transaction> transactionList = getTransactionsList();
        calculateRewardsForCustomer(rewards, transactionList);

        for (Map.Entry<String, RewardsPerCustomer> rewardsMap : rewards.entrySet()) {
            System.out.println("rewards earned by customer: " + rewardsMap.getKey() + " is as follows:");
            RewardsPerCustomer rewardsPerCustomer = rewardsMap.getValue();
            for (Map.Entry<Integer, Long> rewardsEarnedPerMonth : rewardsPerCustomer.getRewardsPerMonthMap().entrySet()) {
                System.out.println("rewards earned in month," + rewardsEarnedPerMonth.getKey() + ":" + rewardsEarnedPerMonth.getValue());
            }
            System.out.println("Total rewards earned: " + rewardsPerCustomer.getTotalRewardsEarned());
            System.out.println();
        }
    }

    private static void calculateRewardsForCustomer(Map<String, RewardsPerCustomer> rewards,
                                                    List<Transaction> transactionList) {

        // Group the transactions based on customerId
        Map<String, List<Transaction>> transactionsPerPerson = transactionList.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));

        for (Map.Entry<String, List<Transaction>> entry : transactionsPerPerson.entrySet()) {

            List<Transaction> personTransactions = entry.getValue();
            RewardsPerCustomer rewardsPerCustomer = new RewardsPerCustomer();

            rewardsPerCustomer.setTotalRewardsEarned(personTransactions.stream()
                    .map(transaction -> rewardsForAmount(transaction.getAmount())).reduce(0L, Long::sum));

            //Group the personTransactions based on month
            Map<Integer, List<Transaction>> transactionsPerPersonPerMonth = personTransactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getTransactionMonth));

            Map<Integer, Long> rewardsPerMonthMap = new HashMap<>();
            for (Map.Entry<Integer, List<Transaction>> entry1 : transactionsPerPersonPerMonth.entrySet()) {
                List<Transaction> personTransactionsPerMonth = entry1.getValue();
                rewardsPerMonthMap.put(entry1.getKey() + 1, personTransactionsPerMonth.stream()
                        .map(transaction -> rewardsForAmount(transaction.getAmount())).reduce(0L, Long::sum));
            }
            rewardsPerCustomer.setRewardsEarnedPerMonthMap(rewardsPerMonthMap);
            rewards.put(entry.getKey(), rewardsPerCustomer);
        }
    }

    private static long rewardsForAmount(long amount) {
        if (amount <= 50) {
            return 0;
        } else if (amount <= 100) {
            return amount - 50;
        } else {
            return 2 * (amount - 100) + 50;
        }
    }

    private static List<Transaction> getTransactionsList() throws ParseException {

        //Placeholder to provide test Data, this can be easily enhanced to fetch the data from text file if needed.
        List<Transaction> transactionsList = new ArrayList<>();

        transactionsList.add(new Transaction(20, "Sebastian", new SimpleDateFormat("dd/MM/yyyy")
                .parse("10/10/2020")));
        transactionsList.add(new Transaction(90, "Sebastian", new SimpleDateFormat("dd/MM/yyyy")
                .parse("28/11/2020")));
        transactionsList.add(new Transaction(150, "Mary", new SimpleDateFormat("dd/MM/yyyy")
                .parse("30/12/2020")));
        transactionsList.add(new Transaction(780, "Sebastian", new SimpleDateFormat("dd/MM/yyyy")
                .parse("12/12/2020")));
        transactionsList.add(new Transaction(30, "Mary", new SimpleDateFormat("dd/MM/yyyy")
                .parse("03/10/2020")));
        transactionsList.add(new Transaction(100, "Mary", new SimpleDateFormat("dd/MM/yyyy")
                .parse("07/10/2020")));
        transactionsList.add(new Transaction(12, "Sebastian", new SimpleDateFormat("dd/MM/yyyy")
                .parse("25/12/2020")));
        transactionsList.add(new Transaction(80, "Sebastian", new SimpleDateFormat("dd/MM/yyyy")
                .parse("22/10/2020")));
        transactionsList.add(new Transaction(50, "Karen", new SimpleDateFormat("dd/MM/yyyy")
                .parse("03/10/2020")));
        transactionsList.add(new Transaction(34, "Karen", new SimpleDateFormat("dd/MM/yyyy")
                .parse("17/11/2020")));
        transactionsList.add(new Transaction(76, "Karen", new SimpleDateFormat("dd/MM/yyyy")
                .parse("19/11/2020")));
        transactionsList.add(new Transaction(192, "Sebastian", new SimpleDateFormat("dd/MM/yyyy")
                .parse("11/11/2020")));
        transactionsList.add(new Transaction(232, "Mary", new SimpleDateFormat("dd/MM/yyyy")
                .parse("08/11/2020")));
        transactionsList.add(new Transaction(76, "Mary", new SimpleDateFormat("dd/MM/yyyy")
                .parse("23/11/2020")));
        transactionsList.add(new Transaction(143, "Karen", new SimpleDateFormat("dd/MM/yyyy")
                .parse("29/10/2020")));
        transactionsList.add(new Transaction(21, "Mary", new SimpleDateFormat("dd/MM/yyyy")
                .parse("22/12/2020")));
        transactionsList.add(new Transaction(543, "Karen", new SimpleDateFormat("dd/MM/yyyy")
                .parse("01/12/2020")));
        transactionsList.add(new Transaction(476, "Karen", new SimpleDateFormat("dd/MM/yyyy")
                .parse("31/12/2020")));

        return transactionsList;
    }
}
