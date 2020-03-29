package com.tud.aquavi.tables;

public class UserInfo
{
    private int userHeight;
    private int userWeight;
    private int userGoal;

    public UserInfo(int userHeight, int userWeight, int userGoal)
    {
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userGoal = userGoal;
    }

    public int getUserHeight()
    {
        return userHeight;
    }

    public void setUserHeight(int userHeight)
    {
        this.userHeight = userHeight;
    }

    public int getWeight()
    {
        return userWeight;
    }

    public void setWeight(int userWeight)
    {
        this.userWeight = userWeight;
    }

    public int getGoal()
    {
        return userGoal;
    }

    public void setGoal(int userGoal)
    {
        this.userGoal = userGoal;
    }
}
