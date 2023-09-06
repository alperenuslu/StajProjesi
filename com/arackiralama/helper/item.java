package com.arackiralama.helper;

public class item {
    private int key;
    private String key1;
    private String value;

    public item(String key1, String value){
        this.key=key;
        this.key1=key1;
        this.value=value;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String toString(){
        return this.value;
    }
}
