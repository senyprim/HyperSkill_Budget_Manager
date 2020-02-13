package budget.Commands;

import budget.Account.Category;
import budget.Account.Purchase;
import budget.Budget;

import java.util.*;

public class Analyze_SortTypes extends Command {
    public Analyze_SortTypes(Budget invoker) {
        super(invoker);
    }

    @Override
    public void execute() {
        List<Purchase> list = invoker.account.get_list();
        EnumMap<Category,Double> groupList =new EnumMap<>(Category.class);
        groupList.put(Category.Clothes,0.0);
        groupList.put(Category.Entertainment,0.0);
        groupList.put(Category.Food,0.0);
        groupList.put(Category.Other,0.0);
        for(Purchase purchase:list){
            if (!groupList.containsKey(purchase.get_category())){
                groupList.put(purchase.get_category(),0.0);
            }
            double sum = groupList.get(purchase.get_category());
            groupList.put(purchase.get_category(),sum+purchase.get_price());
        }
        List<Purchase> result=new ArrayList<>();
        groupList
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach((group)->result.add(new Purchase(group.getKey().getName(),group.getValue(),null)));

        invoker.printList(result,"Types:","%s - $%.2f","Total sum: $%.2f");
    }
}
