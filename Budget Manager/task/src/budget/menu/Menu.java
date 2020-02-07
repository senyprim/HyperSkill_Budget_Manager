package budget.menu;

import java.util.*;

public class Menu {
    private String _selectedName;
    private Map<String, MenuItem> _menu;

    public Menu(){
        this._menu=new HashMap<>();
        this._selectedName=null;
    }

    public void add_MenuItem(MenuItem item){
        _menu.put(item.get_name(),item);
    }

    public MenuItem get_MenuItem(String name){
        return _menu.get(name);
    }

    public void set_selectedName(String name){
        if (_menu.containsKey(name)){
            this._selectedName=name;
        }
        else throw new IllegalArgumentException("Wrong selected menu item name");
    }

    public String get_selectedName(){
        return this._selectedName;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder=new StringBuilder();
        List<MenuItem> list= new ArrayList<>(this._menu.values());
        list.sort(Comparator.comparing(MenuItem::get_index));

        for (MenuItem item:list){
            stringBuilder.append(item.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);
    }

}
