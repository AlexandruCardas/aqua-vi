package com.tud.aquavi.tables;

public class DrinkInfo
{
    private String drink_id;
    private String description;

    public DrinkInfo(String drink_id, String description)
    {
        this.drink_id = drink_id;
        this.description = description;
    }

    public String getDrink_id()
    {
        return drink_id;
    }

    public void setDrink_id(String name)
    {
        this.drink_id = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    // Reference to the other table.
    private String getCompareKey()
    {
        return drink_id + description;
    }

    @Override
    public String toString()
    {
        return getCompareKey();
    }
}