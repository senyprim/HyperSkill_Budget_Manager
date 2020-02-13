package budget.Commands;

import budget.Budget;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Save extends Command {
    public Save(Budget invoker) {
        super(invoker);
    }

    @Override
    public void execute() {
        try(ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(invoker.fileName)))
        {
            write.writeObject(invoker.account);
            System.out.println(Budget.SUCCESS_SAVE);
            System.out.println();
        }
        catch(Exception ex){
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
}
