package budget.acount;

import java.io.Serializable;

public class Purchase implements Serializable {

    private final String _name;
    private final double _price;
    private final Category _category;

    public String get_name() {
        return _name;
    }

    public double get_price() {
        return _price;
    }

    public Category get_category() {
        return _category;
    }

    public Purchase(String name, double price, Category category){
        this._name =name;
        this._price =price;
        this._category=category;
    }

    public Purchase(Purchase clone){
        this(clone.get_name(),clone.get_price(),clone.get_category());
    }

    @Override
    public String toString(){
        return String.format("%s $%.2f", get_name(), get_price());
    }
}
