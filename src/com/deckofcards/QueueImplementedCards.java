package com.deckofcards;

import java.util.Random;

class MyQueue<T> {

    MyLinkedList<T> myLinkedList;
    public MyQueue() {
        myLinkedList = new MyLinkedList<T>();
    }
    public void enqueue(T data) {
        myLinkedList.add(data);
    }
    public T dequeue() {
        return myLinkedList.pop(0);
    }

}

class MyLinkedList<T> {

    MyNode<T> head;
    MyNode<T> current;
    int position;

    public MyLinkedList() {
        head = null;
        current = null;
        position = -1;
    }
    public void add(T data){
        MyNode<T> node = new MyNode<T>(data);
        if(head == null){
            head = node;
            current = head;
        }
        else{
            current.next = node;
            current = current.next;
        }
        position++;
    }

    public T pop(int location){
        MyNode<T> tempCurrent = head;
        MyNode<T> tempPrev = null;
        int tempPosition = 0;
        position--;
        while(tempPosition != location){
            tempPrev = tempCurrent;
            tempCurrent = tempCurrent.next;
            tempPosition++;
        }
        if(tempCurrent == head){
            head = head.next;
            return tempCurrent.data;
        }
        else if(tempCurrent == current){
            current = tempPrev;
            tempPrev.next = tempCurrent.next;
            return tempCurrent.data;
        }
        else{
            tempPrev.next = tempCurrent.next;
            return tempCurrent.data;
        }
    }

}

class MyNode<T>{
    T data;
    MyNode<T> next;

    MyNode(T data){
        this.data = data;
        next = null;
    }
}

class Player {
    MyQueue<Card> cardsQue;
    int[][] playerCards;

    public Player() {
        cardsQue = new MyQueue<Card>();
        playerCards = new int[4][13];
    }

    public void addCard(int suit, int rank) {
        playerCards[suit][rank] = 1;
    }

    public Card getCard() {
        return cardsQue.dequeue();
    }
    public void enqueueCards() {
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 0; rank < 13; rank++) {
                if(playerCards[suit][rank] == 1) {
                    cardsQue.enqueue(new Card(suit, rank));
                }
            }
        }
    }
}

class Card {
    String suit;
    String rank;

    public Card(int suit, int rank) {
        this.suit = getSuit(suit);
        this.rank = getRank(rank);
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }
    String getRank(int rankNumber) {
        switch(rankNumber) {
            case 0:
                return "2";
            case 1:
                return "3";
            case 2:
                return "4";
            case 3:
                return "5";
            case 4:
                return "6";
            case 5:
                return "7";
            case 6:
                return "8";
            case 7:
                return "9";
            case 8:
                return "10";
            case 9:
                return "Jack";
            case 10:
                return "Queen";
            case 11:
                return "King";
            case 12:
                return "Ace";
            default:
                return "";
        }
    }
    String getSuit(int suitNumber) {
        switch(suitNumber) {
            case 0:
                return "Clubs";
            case 1:
                return "Diamonds";
            case 2:
                return "Hearts";
            case 3:
                return "Spades";
            default:
                return "";
        }
    }
}
public class QueueImplementedCards {
    MyQueue<Player> playerQueue;
    int[][] cardsArray;

    void start() {
        playerQueue = new MyQueue<Player>();
        cardsArray = new int[4][13];
        for(int i = 0; i < 4; i++) {
            addPlayers();
        }
    }
    void addPlayers() {
        Player player = new Player();
        for (int i = 0; i < 9; i++) {
            allotCard(player);
        }
        player.enqueueCards();
        playerQueue.enqueue(player);
    }
    void allotCard(Player player) {
        Random random = new Random();
        int suit = random.nextInt(4);
        int rank = random.nextInt(13);
        if(cardsArray[suit][rank] == 0)
        {
            player.addCard(suit, rank);
            cardsArray[suit][rank] = 1;
        }
        else
        {
            allotCard(player);
        }
    }
    void dequeuePlayers() {
        for(int i = 0; i < 4; i++) {
            Player player = playerQueue.dequeue();
            System.out.println("Player " + (i+1) + " cards:");
            printPlayerCards(player);
            System.out.println();
        }
    }
    void printPlayerCards(Player player) {
        for (int i = 0; i < 9; i++) {
            Card card = player.getCard();
            System.out.print(card.getSuit() + " " + card.getRank());
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        QueueImplementedCards extended = new QueueImplementedCards();
        extended.start();
        extended.dequeuePlayers();
    }
}

