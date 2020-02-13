package budget.Commands;

import budget.Budget;

public class Balance extends Command {
    public Balance(Budget invoker) {
        super(invoker);
    }

    @Override
    public void execute() {
        System.out.printf(Budget.OUT_BALANCE,invoker.account.get_money());
        System.out.println();
    }
}
