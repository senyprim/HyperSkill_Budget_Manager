package budget.acount;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


public class Account implements Serializable {
    private double _money;
    private List<Purchase> _list;

    public Account(double initMoney){
        this._money=initMoney;
        this._list =new ArrayList<>();
    }
    public Account(){this(0);}

    public double get_money() {
        return _money<0?0:_money;
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

    public void addIncome(double money){
        _money+=money;
    }
    public void addPurchase(Purchase purchase){
        _list.add(purchase);
        _money-=purchase.get_price();
    }
}
