package budget;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Budget budget = new Budget(scanner);
        budget.run();
    }
}
