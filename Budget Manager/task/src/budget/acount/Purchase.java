package budget.acount;

public class Purchase {

    private final String _name;
    private final double _price;

    public String get_name() {
        return _name;
    }

    public double get_price() {
        return _price;
    }

    public Purchase(String name, double price){
        this._name =name;
        this._price =price;
    }

    public Purchase(Purchase clone){
        this(clone.get_name(),clone.get_price());
    }

    @Override
    public String toString(){
        return String.format("%s $%.2f", get_name(), get_price());
    }
}
