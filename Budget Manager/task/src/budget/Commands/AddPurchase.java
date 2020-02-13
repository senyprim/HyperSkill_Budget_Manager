package budget.Commands;

import budget.Account.Category;
import budget.Account.Purchase;
import budget.Budget;

public class AddPurchase extends Command{
    public AddPurchase(Budget invoker){
        super(invoker);
    }

    @Override
    public void execute() {
        while(true){
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");
            int chooseItem=Integer.parseInt(invoker.scanner.nextLine());
            if (chooseItem==5) break;
            System.out.println();
            Category category=Category.valueOf(chooseItem);

            System.out.println(Budget.ENTER_PURCHASE_NAME);
            String name=invoker.scanner.nextLine();

            System.out.println(Budget.ENTER_PURCHASE_PRICE);
            double price=Double.parseDouble(invoker.scanner.nextLine());

            System.out.println(Budget.SUCCESS_PURCHASE);
            invoker.account.addPurchase(new Purchase(name,price,category));
            System.out.println();
        }
        System.out.println();
    }
}
