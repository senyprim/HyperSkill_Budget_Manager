package budget;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandShell shell=new CommandShell(new Scanner(System.in));
        shell.run();
    }
}
