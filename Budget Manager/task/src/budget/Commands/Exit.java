package budget.Commands;

import budget.Budget;

public class Exit extends Command {
    public Exit(Budget invoker){
        super(invoker);
    }
    @Override
    public void execute() {
        invoker.isExit=true;
        System.out.println(Budget.EXIT);
    }
}
