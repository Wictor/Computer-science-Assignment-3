package com.wictorsundstrom.Slutuppgift;

import java.util.ArrayList;

public class ArrayLists {
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<Integer> fibonacci = new ArrayList<>();
    private int sum = 0;

    protected ArrayList<String> getList() { return list; }
    protected void setList(ArrayList<String> list){
        this.list = list;
    }

    protected ArrayList<Integer> getFibonacci() {
        return fibonacci;
    }
    protected void setFibonacci(ArrayList<Integer> fibonacci){this.fibonacci = fibonacci;}

    protected final int getSum(){
        return sum;
    }
    protected void setSum(int sum){ this.sum = sum; }

}