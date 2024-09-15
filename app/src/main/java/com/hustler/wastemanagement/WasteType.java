package com.hustler.wastemanagement;

import java.util.List;

public class WasteType {
    private String title;
    private String description;
    private List<String> tips;
    private String recyclingProcess;

    public WasteType(String title, String description, List<String> tips, String recyclingProcess) {
        this.title = title;
        this.description = description;
        this.tips = tips;
        this.recyclingProcess = recyclingProcess;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }

    public String getRecyclingProcess() {
        return recyclingProcess;
    }

    public void setRecyclingProcess(String recyclingProcess) {
        this.recyclingProcess = recyclingProcess;
    }
// Getters and setters
}

