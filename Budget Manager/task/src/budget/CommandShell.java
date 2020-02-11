package budget;

import budget.acount.Account;
import budget.acount.Category;
import budget.acount.Purchase;
import budget.menu.Menu;
import budget.menu.MenuItem;

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

    private static final String OUT_BALANCE="Balance: $%.2f\n\n";
    private static final String TOTAL_SUM="Total sum: $%.2f\n";
    private static final String EMPTY_LIST="Purchase list is empty\n";
    private static final String ENTER_CATEGORY="Choose the type of purchase";

    private Scanner _scanner;
    private Account _account;
    private Menu _menu;
    private Menu _categoryMenu;
    private Menu _listMenu;
    private boolean isRunning=true;

    private void createMenu(Menu menu){
        menu.add_MenuItem(new MenuItem("1", "Add income", 1, this::command_Income));
        menu.add_MenuItem(new MenuItem("2","Add purchase",2,this::command_AddPurchases));
        menu.add_MenuItem(new MenuItem("3","Show list of purchases",3,this::command_ShowList));
        menu.add_MenuItem(new MenuItem("4","Balance",4,this::command_Balance));
        menu.add_MenuItem(new MenuItem("0","Exit",5,this::command_Exit));
    }

    private void createCategoryMenu(Menu menu){
        menu.add_MenuItem(new MenuItem("1","Food", 1, null));
        menu.add_MenuItem(new MenuItem("2","Clothes",2,null));
        menu.add_MenuItem(new MenuItem("3","Entertainment",3,null));
        menu.add_MenuItem(new MenuItem("4","Other",4,null));
        menu.add_MenuItem(new MenuItem("5","Back",5,null));
    }

    private void createListMenu(Menu menu){
        menu.add_MenuItem(new MenuItem("1","Food", 1, null));
        menu.add_MenuItem(new MenuItem("2","Clothes",2,null));
        menu.add_MenuItem(new MenuItem("3","Entertainment",3,null));
        menu.add_MenuItem(new MenuItem("4","Other",4,null));
        menu.add_MenuItem(new MenuItem("5","All",5,null));
        menu.add_MenuItem(new MenuItem("6","Back",6,null));
    }

    public CommandShell(Scanner scanner){
        this._scanner=scanner;
        this._account =new Account();
        this._menu=new Menu();
        createMenu(this._menu);
        this._categoryMenu=new Menu();
        createCategoryMenu(_categoryMenu);
        this._listMenu=new Menu();
        createListMenu(_listMenu);
    }

    private void command_Balance(){
        System.out.printf(OUT_BALANCE, _account.get_money());
    }

    private void command_Income(){
        System.out.println(ENTER_INCOME);
        try{
            _account.addIncome(Double.parseDouble(_scanner.nextLine()));
            System.out.println(SUCCESS_INCOME);
            System.out.println();
        }
        catch (Exception e){
            System.out.printf(UNSUCCESSFUL_INCOME,e.getMessage());
        }
    }

    private void command_ShowList(){
        if (_account.get_list().isEmpty()) {
            System.out.println(EMPTY_LIST);
            return;
        }
        while(true){
            System.out.println(ENTER_CATEGORY);
            MenuItem menuItem=getSelectMenuItem(_listMenu);
            Category category=null;
            if (menuItem.get_title().equals("Back")){
                break;
            }
            else if (!menuItem.get_title().equals("All")){
                category=getSelectedCategory(menuItem);
            }
            System.out.printf("%s:\n",menuItem.get_title());
            final Category forLambda=category;
            List<Purchase> list= _account.get_list(category==null?null: purchase ->purchase.get_category()==forLambda);

            double sum=0;
            if (list.isEmpty()) {
                System.out.print(EMPTY_LIST);
            }
            else {
                for (Purchase item : list) {
                    System.out.println(item.toString());
                    sum += item.get_price();
                }
                System.out.printf(TOTAL_SUM, sum);
            }
            System.out.println();
        }
    }

    private void command_AddPurchases(){
        while (true) {
            System.out.println(ENTER_CATEGORY);
            MenuItem menuItem=getSelectMenuItem(_categoryMenu);
            if (menuItem.get_title().equals("Back")){
                break;
            }
            Category category=getSelectedCategory(menuItem);
            System.out.println(ENTER_PURCHASE_NAME);
            String name = _scanner.nextLine();
            System.out.println(ENTER_PURCHASE_PRICE);
            try {
                double price = Double.parseDouble(_scanner.nextLine());
                _account.addPurchase(new Purchase(name, price, category));
                System.out.println(SUCCESS_PURCHASE);
                System.out.println();

            } catch (Exception e) {
                System.out.printf(UNSUCCESFULL_PURCHASE, e.getMessage());
            }
        }
    }

    private void command_Exit(){
        System.out.print(EXIT);
        this.isRunning=false;
    }

    public void run(){
        while (isRunning){
            System.out.println(CHOOSE_ACTION);
            MenuItem item=getSelectMenuItem(_menu);

            if (item.get_command()!=null){
                item.get_command().execute();
            }
        }
    }

    private MenuItem getSelectMenuItem(Menu menu){
        System.out.println(menu.toString());
        menu.set_selectedName(_scanner.nextLine());
        System.out.println();
        return menu.get_MenuItem(menu.get_selectedName());
    }

    private Category getSelectedCategory(MenuItem item){
        String name=item.get_title();
        try{
            return Category.valueOf(name);
        }
        catch (Exception e){
            return null;
        }
    }
}
