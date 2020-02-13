package budget.Commands;

import budget.Budget;

public class Analyze extends Command {
    public Analyze(Budget invoker) {
        super(invoker);
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println(
                    "How do you want to sort?\n" +
                            "1) Sort all purchases\n" +
                            "2) Sort by type\n" +
                            "3) Sort certain type\n" +
                            "4) Back");
            int chooseItem = Integer.parseInt(invoker.scanner.nextLine());
            if (chooseItem==4) break;
            else {
                System.out.println();
                switch (chooseItem) {
                    case 1:
                        new Analyze_SortAll(invoker).execute();
                        break;
                    case 2:
                        new Analyze_SortTypes(invoker).execute();
                        break;
                    case 3:
                        new Analyze_SortOneType(invoker).execute();
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong menu number");
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}
