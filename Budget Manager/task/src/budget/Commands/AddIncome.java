package budget.Commands;

import budget.Budget;

public class AddIncome extends  Command{

    public AddIncome(Budget invoker){
        super(invoker);
    }

    @Override
    public void execute() {
        System.out.println(Budget.ENTER_INCOME);
        try{
            invoker.account.addIncome(Double.parseDouble(invoker.scanner.nextLine()));
            System.out.println(Budget.SUCCESS_INCOME);
            System.out.println();
        }
        catch (Exception e){
            System.out.printf(Budget.UNSUCCESSFUL_INCOME,e.getMessage());
        }
    }
}
