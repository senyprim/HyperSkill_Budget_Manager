package budget.Commands;

import budget.Account.Category;
import budget.Account.Purchase;
import budget.Budget;

import java.util.function.Predicate;

public class ShowList extends Command{
    public ShowList(Budget invoker){
        super(invoker);
    }

    @Override
    public void execute() {
        if (invoker.account.isPurchaseEmpty()){
            System.out.println(Budget.EMPTY_LIST);
            return;
        }
        while(true) {
            System.out.println("Choose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            int chooseItem = Integer.parseInt(invoker.scanner.nextLine());
            if (chooseItem==6) break;
            System.out.println();
            Category category=Category.valueOf(chooseItem);
            Predicate<Purchase> predicate = category==null
                    ?null
                    :(purchase)->purchase.get_category()==category;
            String header=category==null?"All":category.getName();

            invoker.printList(
                    invoker.getListByCategory(category)
                    ,header
                    ,"%s $%.2f"
                    ,"Total sum: $%.2f"
            );
            System.out.println();
        }
        System.out.println();
    }

}
