package budget.menu;
import budget.acount.ICommand;

public class MenuItem {
    private final int _index;
    private final String _name;
    private final String _title;
    private final ICommand _command;

    public  MenuItem(String name, String title, int index, ICommand command){
        this._name=name;
        this._title=title;
        this._index=index;
        this._command=command;
    }

    public String get_name() {
        return _name;
    }

    public String get_title() {
        return _title;
    }

    public int get_index() {return _index;}

    public ICommand get_command(){return _command;}

    @Override
    public String toString(){
        return String.format("%s) %s",get_name(),get_title());
    }


}
