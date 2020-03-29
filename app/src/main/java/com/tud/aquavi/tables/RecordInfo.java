package com.tud.aquavi.tables;

public class RecordInfo
{
    private String dateId;
    private String drinkId;
    private String quantity;

    public RecordInfo(String dateId, String drinkId, String quantity)
    {
        this.dateId = dateId;
        this.drinkId = drinkId;
        this.quantity = quantity;
    }

    public String getDateId()
    {
        return dateId;
    }

    public void setDateId(String dateId)
    {
        this.dateId = dateId;
    }

    public String getDrinkId()
    {
        return drinkId;
    }

    public void setDrinkId(String drinkId)
    {
        this.drinkId = drinkId;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
}
