import java.util.Scanner;

public class CustomerTransactionManager {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Ask how many customers will be entered.
        int count;

        while (true) {
            System.out.print("Enter Number of Transactions: ");

            if (input.hasNextInt()) {
                count = input.nextInt();
                input.nextLine();

                if (count > 0) {
                    break;
                }
            } else {
                input.nextLine();
            }

            System.out.println("Please enter a valid positive whole number.");
        }

        // 1D ARRAYS:
        // One index is used to access each customer's information.
        String[] transactionIDs = new String[count];
        double[] transactionAmounts = new double[count];

        // 2D ARRAYS:
        // Each row represents one customer.
        // Each column represents an item.
        // Different customers can have different numbers of items.
        String[][] productNames = new String[count][];
        double[][] itemDetails = new double[count][];

        // Stores the number of items entered by each customer.
        int[] itemCounts = new int[count];

        // Enter each customer's transaction details.
        for (int i = 0; i < count; i++) {

            System.out.println("\n========== CUSTOMER " + (i + 1) + " ==========");

            System.out.print("Enter transaction ID: ");
            transactionIDs[i] = input.nextLine();

            // Ask how many items this customer purchased.
            while (true) {
                System.out.print("Enter number of items: ");

                if (input.hasNextInt()) {
                    itemCounts[i] = input.nextInt();
                    input.nextLine();

                    if (itemCounts[i] > 0) {
                        break;
                    }
                } else {
                    input.nextLine();
                }

                System.out.println("Invalid Input. Please Enter A Whole Number Greater Than Zero.");
            }

            // Create the 2D array row based on this customer's item count.
            productNames[i] = new String[itemCounts[i]];
            itemDetails[i] = new double[itemCounts[i]];

            // NESTED LOOP:
            // Outer loop handles customers.
            // Inner loop handles the items of each customer.
            for (int j = 0; j < itemCounts[i]; j++) {

                System.out.print("Enter item " + (j + 1) + " name: ");
                productNames[i][j] = input.nextLine();

                // Validate the item price.
                while (true) {
                    System.out.print("Enter Item Price: ");

                    if (input.hasNextDouble()) {
                        double price = input.nextDouble();
                        input.nextLine();

                        if (Double.isFinite(price) && price >= 0) {
                            itemDetails[i][j] = price;
                            break;
                        }
                    } else {
                        input.nextLine();
                    }

                    System.out.println("Please Enter A Valid Non-Negative Price.");
                }

                // Add the item price to the customer's total.
                transactionAmounts[i] += itemDetails[i][j];
            }
        }

        // Display the transaction summary.
        System.out.println("\n========== TRANSACTION SUMMARY ==========");

        for (int i = 0; i < count; i++) {

            System.out.println("\nCustomer " + (i + 1));
            System.out.println("Transaction ID: " + transactionIDs[i]);

            System.out.println("-----------------------------------------------");
            System.out.printf("%-25s %12s%n", "ITEM NAME", "PRICE");
            System.out.println("-----------------------------------------------");

            // Display all items belonging to the current customer.
            for (int j = 0; j < itemCounts[i]; j++) {
                System.out.printf(
                    "%-25s ₱%11.2f%n",
                    productNames[i][j],
                    itemDetails[i][j]
                );
            }

            System.out.println("-----------------------------------------------");

            System.out.printf(
                "%-25s ₱%11.2f%n",
                "TOTAL PURCHASE",
                transactionAmounts[i]
            );
        }

        //  Let the user choose a sorting algorithm.
        System.out.println("\n========== SORTING MENU ==========");
        System.out.println("1 - BUBBLE SORT");
        System.out.println("2 - SELECTION SORT");
        System.out.println("3 - INSERTION SORT");
        System.out.println("4 - MERGE SORT");

        int choice;

        while (true) {
            System.out.print("Choose A Sorting Algorithm: ");

            if (input.hasNextInt()) {
                choice = input.nextInt();

                if (choice >= 1 && choice <= 4) {
                    break;
                }
            } else {
                input.nextLine();
            }

            System.out.println("Please Choose From 1 to 4");
        }

        // Choose ascending or descending order.
        int order;

        while (true) {
            System.out.println("\nChoose sorting order:");
            System.out.println("1- ASCENDING (Lowest to Highest)");
            System.out.println("2- DESCENDING (Highest to Lowest)");

            System.out.print("Enter Your Choice: ");

            if (input.hasNextInt()) {
                order = input.nextInt();

                if (order == 1 || order == 2) {
                    break;
                }
            } else {
                input.nextLine();
            }

            System.out.println("Please Choose 1 or 2");
        }

        // Run the selected sorting algorithm.
        switch (choice) {
            case 1:
                bubbleSort(transactionAmounts, transactionIDs, order);
                break;

            case 2:
                selectionSort(transactionAmounts, transactionIDs, order);
                break;

            case 3:
                insertionSort(transactionAmounts, transactionIDs, order);
                break;

            case 4:
                mergeSort(transactionAmounts, transactionIDs, 0, count - 1, order);
                break;
        }

        // Display the selected algorithm and sorting order.
        String algorithmName = "";

        switch (choice) {
            case 1:
                algorithmName = "BUBBLE SORT";
                break;
            case 2:
                algorithmName = "SELECTION SORT";
                break;
            case 3:
                algorithmName = "INSERTION SORT";
                break;
            case 4:
                algorithmName = "MERGE SORT";
                break;
        }

        String sortingOrder =
            (order == 1) ? "ASCENDING" : "DESCENDING";

        // Display the sorted transactions.
        System.out.println("\n❀❀❀❀❀❀❀❀❀❀ " + algorithmName + ": " + sortingOrder + " ❀❀❀❀❀❀❀❀❀❀");

        System.out.printf("%-15s %15s%n", "TRANSACTION ID", "TOTAL PURCHASE");
        System.out.println("----------------------------------------");

        for (int i = 0; i < count; i++) {
            System.out.printf(
                "%-15s ₱%14.2f%n",
                transactionIDs[i],
                transactionAmounts[i]
            );
        }

        input.close();
    }

    // ==================================================
    // 1. BUBBLE SORT
    // Compares neighboring values and swaps them if needed.
    // ==================================================
    static void bubbleSort(double[] amounts, String[] ids, int order) {

        for (int i = 0; i < amounts.length - 1; i++) {

            for (int j = 0; j < amounts.length - i - 1; j++) {

                boolean shouldSwap;

                if (order == 1) {
                    shouldSwap = amounts[j] > amounts[j + 1];
                } else {
                    shouldSwap = amounts[j] < amounts[j + 1];
                }

                if (shouldSwap) {

                    double tempAmount = amounts[j];
                    amounts[j] = amounts[j + 1];
                    amounts[j + 1] = tempAmount;

                    // Keep the transaction ID matched with its total.
                    String tempID = ids[j];
                    ids[j] = ids[j + 1];
                    ids[j + 1] = tempID;
                }
            }
        }
    }

    // ==================================================
    // 2. SELECTION SORT
    // Finds the smallest or largest value and puts it
    // in its correct position.
    // ==================================================
    static void selectionSort(double[] amounts, String[] ids, int order) {

        for (int i = 0; i < amounts.length - 1; i++) {

            int selected = i;

            for (int j = i + 1; j < amounts.length; j++) {

                if (order == 1 && amounts[j] < amounts[selected]) {
                    selected = j;
                }

                if (order == 2 && amounts[j] > amounts[selected]) {
                    selected = j;
                }
            }

            double tempAmount = amounts[i];
            amounts[i] = amounts[selected];
            amounts[selected] = tempAmount;

            String tempID = ids[i];
            ids[i] = ids[selected];
            ids[selected] = tempID;
        }
    }

    // ==================================================
    // 3. INSERTION SORT
    // Takes one value and inserts it into its correct position.
    // ==================================================
    static void insertionSort(double[] amounts, String[] ids, int order) {

        for (int i = 1; i < amounts.length; i++) {

            double keyAmount = amounts[i];
            String keyID = ids[i];

            int j = i - 1;

            while (j >= 0 &&
                ((order == 1 && amounts[j] > keyAmount) ||
                 (order == 2 && amounts[j] < keyAmount))) {

                amounts[j + 1] = amounts[j];
                ids[j + 1] = ids[j];

                j--;
            }

            amounts[j + 1] = keyAmount;
            ids[j + 1] = keyID;
        }
    }

    // ==================================================
    // 4. MERGE SORT
    // Divides the array into smaller parts and merges
    // them back together in the correct order.
    // ==================================================
    static void mergeSort(
        double[] amounts,
        String[] ids,
        int left,
        int right,
        int order
    ) {

        if (left < right) {

            int middle = left + (right - left) / 2;

            mergeSort(amounts, ids, left, middle, order);
            mergeSort(amounts, ids, middle + 1, right, order);

            merge(amounts, ids, left, middle, right, order);
        }
    }

    // Combines two sorted parts into one sorted section.
    static void merge(
        double[] amounts,
        String[] ids,
        int left,
        int middle,
        int right,
        int order
    ) {

        int n1 = middle - left + 1;
        int n2 = right - middle;

        double[] leftAmounts = new double[n1];
        double[] rightAmounts = new double[n2];

        String[] leftIDs = new String[n1];
        String[] rightIDs = new String[n2];

        // Copy the left part.
        for (int i = 0; i < n1; i++) {
            leftAmounts[i] = amounts[left + i];
            leftIDs[i] = ids[left + i];
        }

        // Copy the right part.
        for (int j = 0; j < n2; j++) {
            rightAmounts[j] = amounts[middle + 1 + j];
            rightIDs[j] = ids[middle + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        // Compare and merge values in the selected order.
        while (i < n1 && j < n2) {

            boolean takeLeft;

            if (order == 1) {
                takeLeft = leftAmounts[i] <= rightAmounts[j];
            } else {
                takeLeft = leftAmounts[i] >= rightAmounts[j];
            }

            if (takeLeft) {
                amounts[k] = leftAmounts[i];
                ids[k] = leftIDs[i];
                i++;
            } else {
                amounts[k] = rightAmounts[j];
                ids[k] = rightIDs[j];
                j++;
            }

            k++;
        }

        // Copy any remaining values from the left part.
        while (i < n1) {
            amounts[k] = leftAmounts[i];
            ids[k] = leftIDs[i];
            i++;
            k++;
        }

        // Copy any remaining values from the right part.
        while (j < n2) {
            amounts[k] = rightAmounts[j];
            ids[k] = rightIDs[j];
            j++;
            k++;
        }
    }
}
