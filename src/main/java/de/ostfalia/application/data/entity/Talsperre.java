package de.ostfalia.application.data.entity;

import de.ostfalia.application.data.records.TalsperreRecord;

import java.util.List;

public class Talsperre {
    private String name;
    List<TalsperreRecord> outflowValues;
    List<TalsperreRecord> inflowValues;
    List<TalsperreRecord> storageValues;
    public Talsperre(){}
    public Talsperre(String name){
        this.name =name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TalsperreRecord> getOutflowValues() {
        return outflowValues;
    }

    public void setOutflowValues(List<TalsperreRecord> outflowValues) {
        this.outflowValues = outflowValues;
    }

    public List<TalsperreRecord> getInflowValues() {
        return inflowValues;
    }

    public void setInflowValues(List<TalsperreRecord> inflowValues) {
        this.inflowValues = inflowValues;
    }

    public List<TalsperreRecord> getStorageValues() {
        return storageValues;
    }

    public void setStorageValues(List<TalsperreRecord> storageValues) {
        this.storageValues = storageValues;
    }
    public String captializedName(){
        // name in gross gesschrieben
        return this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
    }
}
