package com.ppublica.apps.kiosk.repository.collection;

public class DataElemQueryResults {
    private Long dataElemId;
    private String type;

    public DataElemQueryResults(Long dataElemId, String type) {
        this.dataElemId = dataElemId;
        this.type = type;
    }

    public Long getDataElemId() {
        return this.dataElemId;
    }

    public String getType() {
        return this.type;
    }
    
    
}
