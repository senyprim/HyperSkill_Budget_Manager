package budget.Commands;

import budget.Account.Category;
import budget.Budget;

public class Analyze_SortOneType extends Command {
    public Analyze_SortOneType(Budget invoker) {
        super(invoker);
    }

    @Override
    public void execute() {
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        int chooseItem = Integer.parseInt(invoker.scanner.nextLine());
        Category category=Category.valueOf(chooseItem);
        if (category==null) throw new IllegalArgumentException("Wrong menu number");
        String header=category.getName()==null?"":category.getName()+":";
        System.out.println();
        invoker.printList(
                invoker.getListByCategoryDesc(category)
                ,header
                ,"%s $%.2f"
                ,"Total sum: $%.2f"
        );
    }
}
