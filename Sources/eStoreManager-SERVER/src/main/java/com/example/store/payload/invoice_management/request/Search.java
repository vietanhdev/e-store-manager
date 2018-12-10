package com.example.store.payload.invoice_management.request;

public class Search {
    
    private String value;

    private String purpose;

    private String description;

    private String start;

    private String end;

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getPurpose(){
        return purpose;
    }

    public void setPurpose(String purpose){
        this.purpose = purpose;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getStart(){
        return start;
    }

    public String getEnd(){
        return end;
    }

}