package budget;

import budget.Account.Account;
import budget.Account.Category;
import budget.Account.Purchase;
import budget.Commands.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Budget {
    public static final String ENTER_PURCHASE_NAME= "Enter purchase name:";
    public static final String ENTER_PURCHASE_PRICE="Enter its price:";
    public static final String ENTER_INCOME="Enter income:";
    public static final String SUCCESS_INCOME ="Income was added!";
    public static final String UNSUCCESSFUL_INCOME ="Income was not added! Error:%s\n";
    public static final String SUCCESS_PURCHASE ="Purchase was added!";
    public static final String EXIT ="Bye!";
    public static final String OUT_BALANCE="Balance: $%.2f\n";
    public static final String TOTAL_SUM="Total sum: $%.2f\n";
    public static final String EMPTY_LIST="Purchase list is empty";
    public static final String ENTER_CATEGORY="Choose the type of purchase";
    public static final String ENTER_ANALYZE="How do you want to sort?";
    public static final String SUCCESS_SAVE="Purchases were saved!";
    public static final String SUCCESS_LOAD="Purchases were loaded!";

    public final String fileName="purchases.txt";

    public boolean isExit=false;




    public final Account account;
    public final Scanner scanner;
    Budget(Scanner scanner){
        this.account=new Account();
        this.scanner=scanner;
    }

    void run(){
        while(!isExit) {
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit");
            int selectedItem = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (selectedItem) {
                case 1:
                    new AddIncome(this).execute();
                    break;
                case 2:
                    new AddPurchase(this).execute();
                    break;
                case 3:
                    new ShowList(this).execute();
                    break;
                case 4:
                    new Balance(this).execute();
                    break;
                case 5:
                    new Save(this).execute();
                    break;
                case 6:
                    new Load(this).execute();
                    break;
                case 7:
                    new Analyze(this).execute();
                    break;
                case 0: new Exit(this).execute();
                    break;
            }

        }

    }
    public void printList(List<Purchase> list, String header, String linePattern, String total){
        if (list.isEmpty()) System.out.println(EMPTY_LIST);
        else {
            System.out.println(header);
            double sum = 0;
            for (Purchase item : list) {
                sum += item.get_price();
                System.out.printf(linePattern, item.get_name(), item.get_price(), item.get_category());
                System.out.println();
            }
            System.out.printf(total, sum);
            System.out.println();
        }
    }
    public List<Purchase> getListByCategory(Category category){
        return account.get_list(
                category==null
                    ?null
                    :(purchase) -> purchase.get_category()==category
                ,null);
    }
    public List<Purchase> getListByCategoryDesc(Category category){
        return account.get_list(
                category==null
                    ?null
                    :(purchase) -> purchase.get_category()==category
                , Comparator.comparing(Purchase::get_price).reversed());
    }
}
