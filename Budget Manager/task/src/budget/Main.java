package budget;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        List<String> purchases=new ArrayList<>();
        while(scanner.hasNext()){
            purchases.add(scanner.nextLine());
        }
        System.out.println();
        Double sum=0d;
        for(String purchase :purchases){
            System.out.println(purchase);
            sum+=Double.parseDouble(purchase.split("\\$")[1]);
        }
        System.out.println(String.format("\nTotal: $%.2f",sum));
    }
}
