package budget.Commands;

import budget.Account.Account;
import budget.Account.Purchase;
import budget.Budget;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Load extends Command {
    public Load(Budget invoker) {
        super(invoker);
    }

    @Override
    public void execute() {
        try(ObjectInputStream read = new ObjectInputStream(new FileInputStream(invoker.fileName)))
        {
            Account newAccount=(Account)read.readObject();
            importAccount(newAccount,invoker.account);
            System.out.println(Budget.SUCCESS_LOAD);
            System.out.println();
        }
        catch(Exception ex){
            System.out.println("ERROR: "+ex.getMessage());
        }
    }

    private void importAccount(Account from,Account to){
        to.clear();
        for (double item : from.getIncome()) to.addIncome(item);
        for (Purchase item : from.get_list()) to.addPurchase(new Purchase(item));
    }
}
