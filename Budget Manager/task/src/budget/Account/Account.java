package budget.Account;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


public class Account implements Serializable {
    private double _money=0;
    private List<Double> _income;
    private List<Purchase> _list;

    public Account(){
        this._list =new ArrayList<>();
        this._income=new ArrayList<>();
    }

    public double get_money() {
        return _money;
    }

    public List<Purchase> get_list(Predicate<Purchase> filter, Comparator<Purchase> comparator) {
        List<Purchase> clone=new ArrayList<>();
        for(Purchase item : _list){
            if (filter==null || filter.test(item)) clone.add(new Purchase(item));
        }
        if (comparator!=null) clone.sort(comparator);
        return clone;
    }

    public List<Purchase> get_list() { return get_list(null,null);}

    public List<Double> getIncome() {
        return new ArrayList<>(this._income);
    }

    public boolean isPurchaseEmpty(){
        return _list.isEmpty();
    }

    public void addIncome(double money){
        _income.add(money);
        _money+=money;
    }

    public void addPurchase(Purchase purchase){
        _list.add(purchase);
        _money-=purchase.get_price();
    }

    public void clear(){
        _money=0;
        _list.clear();
        _income.clear();
    }
}
