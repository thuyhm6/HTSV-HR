package com.ait.test;

//class_getmethod.jsl

import java.lang.reflect.Method;
import java.util.Date;

public class Program
{

    public Program()
    {
    }

    public String sayHello()
    {
        return "Hello!";
    }

    public void setStr(String str)
    {
        this.str = str;
    }

    private void setDate(Date date)
    {
        this.date = date;
    }

    private void setI(int i)
    {
        this.i = i;
    }

    public String str = "Hello";
    private Date date = new Date();
    protected int i = 0;
	
    public static void main(String[] args)
    {
        // Get the Class object associated with this class.
        Program program = new Program();
        Class progClass = program.getClass();

        try
        {
            // Get the method named sayHello.
            Method helloMethod = progClass.getMethod(
                "sayHello", null);
            System.out.println("Public method found: " +
                helloMethod.toString());
        }
        catch (NoSuchMethodException ex)
        {
            System.out.println("Method either doesn't exist " +
                "or is not public: " + ex.toString());
        }

        try
        {
            // Get the method named setStr.
            Class[] args1 = new Class[1];
            args1[0] = String.class;
            Method strMethod = progClass.getMethod(
                "setStr", args1);
            System.out.println("Public method found: " +
                strMethod.toString());
        }
        catch (NoSuchMethodException ex)
        {
            System.out.println("Method either doesn't exist " +
                "or is not public: " + ex.toString());
        }

        try
        {
            // Get the method named setDate.
            Class[] args2 = new Class[1];
            args2[0] = Date.class;
            Method dateMethod = progClass.getMethod(
                "setDate", args2);
            System.out.println("Public method found: " +
                dateMethod.toString());
        }
        catch (NoSuchMethodException ex)
        {
            System.out.println("Method either doesn't exist " +
                "or is not public: " + ex.toString());
        }

        try
        {
            // Get the method named setI.
            Class[] args3 = new Class[1];
            args3[0] = Integer.TYPE;
            Method iMethod = progClass.getMethod(
                "setI", args3);
            System.out.println("Public method found: " +
                iMethod.toString());
        }
        catch (NoSuchMethodException ex)
        {
            System.out.println("Method either doesn't exist " +
                "or is not public: " + ex.toString());
        }
    }
}

/*
Output:
Public method found: public java.lang.String Program.sayHello()
Public method found: public void Program.setStr(java.lang.String)
Method either doesn't exist or is not public: java.lang.NoSuchMethodException: setDate
Method either doesn't exist or is not public: java.lang.NoSuchMethodException: setI
*/

