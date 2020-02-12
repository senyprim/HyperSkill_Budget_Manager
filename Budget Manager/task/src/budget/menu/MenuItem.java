package budget.menu;
import budget.acount.ICommand;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return _index == menuItem._index &&
                _name.equals(menuItem._name) &&
                _title.equals(menuItem._title) &&
                Objects.equals(_command, menuItem._command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_index, _name, _title, _command);
    }

    @Override
    public String toString(){
        return String.format("%s) %s",get_name(),get_title());
    }


}
