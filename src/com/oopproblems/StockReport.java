package com.oopproblems;

import java.util.Scanner;

public class StockReport {
    String stockName;
    int  numberOfShares;
    int sharePrice;
    public StockReport(String stockName,int numberOfShares,int sharePrice){
        this.stockName=stockName;
        this.numberOfShares=numberOfShares;
        this.sharePrice=sharePrice;
    }
    public static void main(String[] args){
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter number of stocks");
        int numberOfShares=scanner.nextInt();
        StockReport[] stocks =new StockReport[numberOfShares];
        for(int i=0;i<numberOfShares;i++){
            System.out.println("Enter the stock name : ");
            String stockName=scanner.next();
            System.out.println("Enter the number of shares : ");
            int noOfShares=scanner.nextInt();
            System.out.println("Enter the share price : ");
            int sharePrice=scanner.nextInt();
            stocks[i]=new StockReport(stockName,noOfShares,sharePrice);
        }
        displayReport(stocks);
    }
    public static void displayReport(StockReport[] stock){
        for(int i=0;i<stock.length;i++){
            System.out.println("Stock name = "+stock[i].stockName);
            System.out.println("Number of stock = "+stock[i].numberOfShares);
            System.out.println("Share price = "+stock[i].sharePrice);
            System.out.println("Total price = "+(stock[i].numberOfShares*stock[i].sharePrice));

        }
    }
}