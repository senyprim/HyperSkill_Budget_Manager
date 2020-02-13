package budget.Commands;

import budget.Account.Purchase;
import budget.Budget;

import java.util.List;

public class Analyze_SortAll extends Command {
    public Analyze_SortAll(Budget invoker) {
        super(invoker);
    }

    @Override
    public void execute() {

        List<Purchase> list = invoker.getListByCategoryDesc(null);
        invoker.printList(list
                ,"All:"
                ,"%s $%.2f"
                ,"Total sum: $%.2f");
    }
}
