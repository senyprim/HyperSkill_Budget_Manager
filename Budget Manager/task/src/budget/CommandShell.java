package budget;

import budget.acount.*;
import budget.menu.*;

import java.util.List;
import java.util.Scanner;

public class CommandShell {
    public static final String ENTER_PURCHASE_NAME= "Enter purchase name:";
    public static final String ENTER_PURCHASE_PRICE="Enter its price:";
    public static final String ENTER_INCOME="Enter income:";
    private static final String SUCCESS_INCOME ="Income was added!";
    private static final String UNSUCCESSFUL_INCOME ="Income was not added! Error:%s\n";
    private static final String SUCCESS_PURCHASE ="Purchase was added!";
    private static final String UNSUCCESFULL_PURCHASE ="Purchase was not added! Error:%s\n";
    private static final String EXIT ="Bye!";
    private static final String CHOOSE_ACTION="Choose your action:";

    private static final String OUT_BALANCE ="Balance: $%.2f\n";
    private static final String TOTAL_SUM="Total sum: $%.2f\n";
    private static final String EMPTY_LIST="Purchase list is empty";

    private Scanner _scanner;
    private Acount _account;
    private Menu _menu;
    private boolean isRunning=true;

    private void createMenu(Menu menu){
        menu.add_MenuItem(new MenuItem("1", "Add income", 1, this::command_Income));
        menu.add_MenuItem(new MenuItem("2","Add purchase",2,this::command_Add_Purchase));
        menu.add_MenuItem(new MenuItem("3","Show list of purchases",3,this::command_ShowList));
        menu.add_MenuItem(new MenuItem("4","Balance",4,this::command_Balance));
        menu.add_MenuItem(new MenuItem("0","Exit",5,this::command_Exit));
    }

    public CommandShell(Scanner scanner){
        this._scanner=scanner;
        this._account =new Acount();
        this._menu=new Menu();
        createMenu(this._menu);
    }

    private void command_Balance(){
        System.out.printf(OUT_BALANCE, _account.get_money());
    }

    private void command_Income(){
        System.out.println(ENTER_INCOME);
        try{
            _account.addIncome(Double.parseDouble(_scanner.nextLine()));
            System.out.println(SUCCESS_INCOME);
        }
        catch (Exception e){
            System.out.printf(UNSUCCESSFUL_INCOME,e.getMessage());
        }
    }

    private void command_ShowList(){
        List<Purchase> list= _account.get_list();
        double sum=0;
        if (list.isEmpty()) {
            System.out.println(EMPTY_LIST);
        }
        else {
            for (Purchase item : list) {
                System.out.println(item.toString());
                sum += item.get_price();
            }
            System.out.printf(TOTAL_SUM, sum);
        }
    }

    private void command_Add_Purchase(){
        System.out.println(ENTER_PURCHASE_NAME);
        String name=_scanner.nextLine();
        System.out.println(ENTER_PURCHASE_PRICE);
        try{
            double price=Double.parseDouble(_scanner.nextLine());
            _account.addPurchase(new Purchase(name,price));
            System.out.println(SUCCESS_PURCHASE);
        }
        catch (Exception e){
            System.out.printf(UNSUCCESFULL_PURCHASE,e.getMessage());
        }
    }

    private void command_Exit(){
        System.out.print(EXIT);
        this.isRunning=false;
    }

    public void run(){
        while (isRunning){
            executeAction(chooseAction());
        }
    }

    private void command_printMenu(){
        System.out.println(_menu.toString());
    }

    private String chooseAction(){
        System.out.println(CHOOSE_ACTION);
        command_printMenu();
        String choose=_scanner.nextLine();
        MenuItem item=_menu.get_MenuItem(choose);
        return item==null?null:item.get_name();
    }
    private void executeAction(String actionName){
        System.out.println();
        if (actionName!=null) {
            _menu.get_MenuItem(actionName).get_command().execute();
        }
        System.out.println();
    }
}
