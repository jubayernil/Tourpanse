package com.compiler.tourpanse.models;


public class Expense {


    private int expenseId;
    private String expensePurpose;
    private double amount;
    private int eventId;
    private int userId;

    public Expense(int expenseId, String expensePurpose, double amount, int eventId, int userId) {
        this.expenseId = expenseId;
        this.expensePurpose = expensePurpose;
        this.amount = amount;
        this.eventId = eventId;
        this.userId = userId;
    }

    public Expense(String expensePurpose, double amount, int eventId, int userId) {
        this.expensePurpose = expensePurpose;
        this.amount = amount;
        this.eventId = eventId;
        this.userId = userId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpensePurpose() {
        return expensePurpose;
    }

    public void setExpensePurpose(String expensePurpose) {
        this.expensePurpose = expensePurpose;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
