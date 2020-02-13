package budget.Account;

import org.jetbrains.annotations.NotNull;

public enum Category
{
    Food("Food",1),
    Clothes("Clothes",2),
    Entertainment("Entertainment",3),
    Other("Other",4);

    private String name;
    private int index;
    Category( @NotNull String name,int index){
        this.name=name;
        this.index=index;
    }

    public String getName(){
        return this.name;
    }
    public int getIndex(){
        return this.index;
    }
    public static Category valueOf(int index){
        for(Category category : Category.values()){
            if (category.getIndex()==index) return category;
        }
        return null;
    }
}
