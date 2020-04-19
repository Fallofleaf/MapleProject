package com.zxk147.maple.data;

public class TypeAccount {
    private int id;
    private long date;
    private boolean type;
    private int kind;
    private String note;
    private String amount;
    private boolean isTitle;

    private String cost;
    private String income;

    public TypeAccount(int id, long date, boolean type, int kind, String note, String amount, boolean isTitle, String cost, String income) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.kind = kind;
        this.note = note;
        this.amount = amount;
        this.isTitle = isTitle;
        this.cost = cost;
        this.income = income;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}
