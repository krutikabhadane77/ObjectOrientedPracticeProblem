package com.oopproblems;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
class Queue<T> {
    LinkedList<T> list=new LinkedList<T>();
    public void enqueue(T data){
        list.add(data);
    }

}

public class StockAccount {
    Scanner scanner =new Scanner(System.in);
    LinkedList<CompanyShares> companylist =new LinkedList<>();
    Stack<String> transactionStack=new Stack<>();
    Queue<String> dateTimeQueue=new Queue<>();
    private static double availableBalance = 100000.00;

    public StockAccount(String file){
        String data[]=readFile(file);
        for(int i=0;i<data.length;i++){
            String symbol=data[i++];
            int noOfshares=Integer.parseInt(data[i++]);
            double  sharePrice=Double.parseDouble(data[i++]);
            String dateTime=data[i];
            CompanyShares shares=new CompanyShares(symbol,noOfshares,sharePrice,dateTime);
            companylist.add(shares);
        }
    }
    public double valueOf(){
        double valueOfAccount=0.0;
        for(int i=0;i<companylist.size();i++){
            valueOfAccount=valueOfAccount+companylist.get(i).getValue();
        }
        return valueOfAccount;
    }

    public void buy(int amount,String symbol){

        System.out.println("Enter price per share for "+symbol);
        double price=scanner.nextDouble();
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy-hh:mm");
        String dateTime=format.format(date);
        CompanyShares share=new CompanyShares(symbol,amount,price,dateTime);
        transactionStack.push("purchased");
        dateTimeQueue.enqueue(dateTime);
        companylist.add(share);
    }

    public void sell(int amount,String symbol){
        int index=0;
        for(index=0;index<companylist.size();index++){
            if(companylist.get(index).getSymbol().equals(symbol)){
                break;
            }
        }
        int numberOfShares=companylist.get(index).getNumberOfShares()-amount;
        if(numberOfShares>=0){
            companylist.get(index).setNumberOfShares(numberOfShares);
            Date date=new Date();
            SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy-hh:mm");
            String dateTime=format.format(date);
            companylist.get(index).setDateTime(dateTime);
            dateTimeQueue.enqueue(dateTime);
            transactionStack.push("Sold");
        }else{
            System.out.println("no of shares to sell is greater than actual shares");
        }
    }

    public static void setAvailableBalance(double availableBalance) {

    }

    public static void credit(double creditValue)
    {
        double balance = availableBalance + creditValue;
        setAvailableBalance(balance);
        System.out.println("Amount Added Successfully");

    }

    public static boolean debit(double debitValue)
    {
        if (debitValue > availableBalance)
        {
            System.out.println("Insufficient Balance");
            return false;
        }
        else
        {
            System.out.println("Withdraw successfully");
            double balance = availableBalance - debitValue;
            setAvailableBalance(availableBalance);
        }
        return true;
    }

    public void save(String file){
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<companylist.size();i++){
            list.add(companylist.get(i).getSymbol());
            list.add(String.valueOf(companylist.get(i).getNumberOfShares()));
            list.add(String.valueOf(companylist.get(i).getPrice()));
            list.add(companylist.get(i).getDateTime());
        }
        String data[]= list.toArray(new String[list.size()]);
        writeFile(data, file);
    }
    public void printReport(){
        for(int index=0;index<companylist.size();index++){
            System.out.println("Stock symbol ="+companylist.get(index).getSymbol());
            System.out.println("Number of shares ="+companylist.get(index).getNumberOfShares());
            System.out.println("Last transaction date and time="+companylist.get(index).getDateTime());
            System.out.println("Share price ="+companylist.get(index).getPrice());
            System.out.println("Total value ="+companylist.get(index).getValue());
            System.out.println(".......................................");
        }
    }
    public static void main(String args[]){
        String file="Report";

        char ans='y';
        while(ans=='y'||ans=='Y'){
            StockAccount account=new StockAccount(file);
            System.out.println("Enter your operatons \n1.Buy \n2.Sell \n3.Credit \n4.Debit \n5.Report ");
            int choice=account.scanner.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Enter stock symbol : ");
                    String symbol=account.scanner.next();
                    System.out.println("Enter the no of stock : ");
                    int numberOfStock=account.scanner.nextInt();
                    account.buy(numberOfStock, symbol);
                    account.save(file);
                    System.out.println("After buying");
                    account.printReport();
                    break;
                case 2:
                    System.out.println("Enter stock symbol : ");
                    String symbol1=account.scanner.next();
                    System.out.println("Enter the number of shares to sell : ");
                    int amount=account.scanner.nextInt();
                    account.sell(amount, symbol1);
                    account.save(file);
                    System.out.println("After selling");
                    account.printReport();
                    break;
                case 3:
                    System.out.println("Enter the amount : ");
                    amount=account.scanner.nextInt();
                    account.credit(amount);
                    System.out.println("Thank you ");
                    break;
                case 4:
                    System.out.println("Enter the amount : ");
                    amount=account.scanner.nextInt();
                    account.debit(amount);
                    System.out.println("Thank you ");
                    break;
                case 5:
                    account.printReport();
                    break;
                default :
                    System.out.println("Invalid choice");
            }
            System.out.println("Do you want to continue [y/n]");
            ans=account.scanner.next().charAt(0);
        }
    }

    public static String[] readFile(String filePath) {
        String words[] = {};
        ArrayList<String> lines = new ArrayList<String>();
        String line, temp[] = {};
        BufferedReader bufferedReader;
        FileReader file;
        int index = 0;
        try {
            file = new FileReader(filePath);
            bufferedReader = new BufferedReader(file);
            while ((line = bufferedReader.readLine()) != null) {
                temp = line.split(",|\\s");
                for (int i = 0; i < temp.length; i++) {
                    lines.add(temp[i]);

                }
            }
            words = lines.toArray(new String[lines.size()]);
            bufferedReader.close();
        }  catch (IOException e) {

            e.printStackTrace();//auto generated catch block
        }

        return words;
    }

    public static void writeFile(String word[], String filePath) {

        try {
            FileWriter writer = new FileWriter(filePath, false);
            PrintWriter out = new PrintWriter(writer);
            for (int i = 0; i < word.length; i++) {
                out.write(word[i] + " ");
            }
            out.close();
            writer.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();//auto generated catch block
        }
    }
}
