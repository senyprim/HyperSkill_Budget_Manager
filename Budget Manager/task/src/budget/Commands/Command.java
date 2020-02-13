package budget.Commands;

import budget.Budget;

public abstract class Command{
    Budget invoker;
    Command(Budget invoker){
        this.invoker=invoker;
    }
    abstract public void execute();
}
