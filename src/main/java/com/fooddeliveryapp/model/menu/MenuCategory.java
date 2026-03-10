package com.fooddeliveryapp.model.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuCategory extends MenuComponent{
    private int id;
    private String name;
    private List<MenuComponent> menuComponents;

    public MenuCategory(String name)
    {
        this.name = name;
        menuComponents = new ArrayList<>();
        this.id = ++counter;
    }

    public List<MenuComponent> getMenuComponents()
    {
        return menuComponents;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
