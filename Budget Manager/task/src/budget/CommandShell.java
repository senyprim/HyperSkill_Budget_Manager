package budget;

import budget.acount.Account;
import budget.acount.Category;
import budget.acount.Purchase;
import budget.menu.Menu;
import budget.menu.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.function.Predicate;

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
    private static final String ENTER_ANALYZE="How do you want to sort?";
    private static final String SUCCESS_SAVE="Purchases were saved!\n";
    private static final String SUCCESS_LOAD="Purchases were loaded!\n";

    private final String fileName="purchases.txt";

    private Scanner _scanner;
    private Account _account;
    private Menu _menu;
    private Menu _categoryMenu;
    private Menu _listMenu;
    private Menu _analyzeMenu;
    private Menu _analyzeCategoryMenu;
    private boolean isRunning=true;

    private Menu createMainMenu(){
        Menu menu=new Menu("MainMenu");
        menu.add_MenuItem(new MenuItem("1", "Add income", 1, this::command_Income));
        menu.add_MenuItem(new MenuItem("2","Add purchase",2,this::command_AddPurchases));
        menu.add_MenuItem(new MenuItem("3","Show list of purchases",3,this::command_ShowList));
        menu.add_MenuItem(new MenuItem("4","Balance",4,this::command_Balance));
        menu.add_MenuItem(new MenuItem("5","Save",6,this::command_Save));
        menu.add_MenuItem(new MenuItem("6","Load",6,this::command_Load));
        menu.add_MenuItem(new MenuItem("7","Analyze (Sort)",7,this::command_Analyze));
        menu.add_MenuItem(new MenuItem("0","Exit",8,this::command_Exit));
        return menu;
    }

    private Menu createCategoryMenu(){
        Menu menu=new Menu("CategoryMenu");
        menu.add_MenuItem(new MenuItem("1","Food", 1, null));
        menu.add_MenuItem(new MenuItem("2","Clothes",2,null));
        menu.add_MenuItem(new MenuItem("3","Entertainment",3,null));
        menu.add_MenuItem(new MenuItem("4","Other",4,null));
        menu.add_MenuItem(new MenuItem("5","Back",5,null));
        return menu;
    }

    private Menu createListMenu(){
        Menu menu=new Menu("ListMenu");
        menu.add_MenuItem(new MenuItem("1","Food", 1, null));
        menu.add_MenuItem(new MenuItem("2","Clothes",2,null));
        menu.add_MenuItem(new MenuItem("3","Entertainment",3,null));
        menu.add_MenuItem(new MenuItem("4","Other",4,null));
        menu.add_MenuItem(new MenuItem("5","All",5,null));
        menu.add_MenuItem(new MenuItem("6","Back",6,null));
        return menu;
    }

    private Menu createAnalyzeMenu(){
        Menu menu=new Menu("AnalyzeMenu");
        menu.add_MenuItem(new MenuItem("1","Sort all purchases", 1, null));
        menu.add_MenuItem(new MenuItem("2","Sort by type",2,null));
        menu.add_MenuItem(new MenuItem("3","Sort certain type",3,null));
        menu.add_MenuItem(new MenuItem("4","Back",4,null));
        return menu;
    }

    private Menu createAnalyzeCategoryMenu(){
        Menu menu=new Menu("AnalyzeCategoryMenu");
        menu.add_MenuItem(new MenuItem("1","Food", 1, null));
        menu.add_MenuItem(new MenuItem("2","Clothes",2,null));
        menu.add_MenuItem(new MenuItem("3","Entertainment",3,null));
        menu.add_MenuItem(new MenuItem("4","Other",4,null));
        return menu;
    }

    public CommandShell(Scanner scanner){
        this._scanner=scanner;
        this._account =new Account();
        this._menu=createMainMenu();
        this._categoryMenu=createCategoryMenu();
        this._listMenu=createListMenu();
        this._analyzeMenu=createAnalyzeMenu();
        this._analyzeCategoryMenu=createAnalyzeCategoryMenu();
    }

    private void command_Save(){
        try(ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            write.writeObject(_account);
            System.out.println(SUCCESS_SAVE);
        }
        catch(Exception ex){
            System.out.println("ERROR: "+ex.getMessage());
        }
    }

    private void command_Load(){
        try(ObjectInputStream read = new ObjectInputStream(new FileInputStream(fileName)))
        {
            _account=(Account)read.readObject();
            System.out.println(SUCCESS_LOAD);
        }
        catch(Exception ex){
            System.out.println("ERROR: "+ex.getMessage());
        }
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

    private void command_Exit(){
        System.out.print(EXIT);
        this.isRunning=false;
    }

    private Purchase createPurchase(Category category){
        System.out.println(ENTER_PURCHASE_NAME);
        String name = _scanner.nextLine();
        System.out.println(ENTER_PURCHASE_PRICE);
        try {
            double price = Double.parseDouble(_scanner.nextLine());
            return new Purchase(name, price, category);
        } catch (Exception e) {
            return null;
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
            Purchase purchase=createPurchase(category);
            if (purchase!=null) {
                _account.addPurchase(purchase);
                System.out.println(SUCCESS_PURCHASE);
            }
            System.out.println();
        }
    }

    private void printPurchaseList(List<Purchase> list){
        double sum=0;
        if (list==null || list.isEmpty()) {
            System.out.print(EMPTY_LIST);
        }
        else {
            for (Purchase item : list) {
                    System.out.println(item.toString());
                    sum += item.get_price();
            }
            System.out.printf(TOTAL_SUM, sum);
        }
    }
    private void printFilteredPurchaseList(MenuItem menuItem,Comparator<Purchase> comparator){
        Category category=null;
        if (!menuItem.get_title().equals("All")){
            category=getSelectedCategory(menuItem);
        }
        System.out.printf("%s:\n",menuItem.get_title());
        final Category forLambda=category;
        List<Purchase> list= _account.get_list(category==null?null: purchase ->purchase.get_category()==forLambda,comparator);
        printPurchaseList(list);
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
            printFilteredPurchaseList(menuItem,null);
            System.out.println();
        }
    }

    private void command_Analyze(){
        while(true){
            System.out.println(ENTER_ANALYZE);
            MenuItem menuItem=getSelectMenuItem(_analyzeMenu);
            if (menuItem.get_title().equals("Back")){
                break;
            }
            else if (menuItem.get_name().equals("1")) analyzeSortAll();
            else if (menuItem.get_name().equals("2")) analyzeSortByType();
            else if (menuItem.get_name().equals("3")) analyzeType();
            else throw new IllegalArgumentException("");
            System.out.println();
        }
    }

    private void analyzeSortAll(){
        printFilteredPurchaseList(new MenuItem("","All",0,null),Comparator.comparing(Purchase::get_price).reversed());
    }

    private void analyzeSortByType(){
        Map<Category,Double> temp= new HashMap<>();
        List<Purchase> list=_account.get_list();
        for(Purchase item:list){
            if (!temp.containsKey(item.get_category())){
                temp.put(item.get_category(),0.0);
            }
            double currentValue=temp.get(item.get_category());
            temp.put(item.get_category(),currentValue+item.get_price());
        }
        Map<Category,Double> result=new LinkedHashMap<>();
        temp.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(item->result.put(item.getKey(),item.getValue()));
        double sum=0;
        System.out.println("Types:");
        for (Map.Entry<Category,Double>item:result.entrySet()){
            System.out.printf("%s - $%.2f\n",item.getKey().name(),item.getValue());
            sum+=item.getValue();
        }
        System.out.printf(TOTAL_SUM,sum);

    }

    private void analyzeType(){
        MenuItem menuItem=getSelectMenuItem(_analyzeCategoryMenu);
        printFilteredPurchaseList(menuItem,Comparator.comparing(Purchase::get_price).reversed());
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
