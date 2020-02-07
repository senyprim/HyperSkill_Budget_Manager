package budget.acount;


import java.util.ArrayList;
import java.util.List;


public class Acount {
    private double _money;
    private List<Purchase> _list;

    public Acount(double initMoney){
        this._money=initMoney;
        this._list =new ArrayList<>();
    }
    public Acount(){this(0);}

    public double get_money() {
        return _money<0?0:_money;
    }

    public List<Purchase> get_list() {
        List<Purchase> clone=new ArrayList<>();
        for(Purchase item : _list){
            clone.add(new Purchase(item));
        }
        return clone;
    }

    public void addIncome(double money){
        _money+=money;
    }
    public void addPurchase(Purchase purchase){
        _list.add(purchase);
        _money-=purchase.get_price();
    }

}
