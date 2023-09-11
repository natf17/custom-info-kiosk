package com.ppublica.apps.kiosk.repository;

public class FieldContainerQueryResults {

    private final Long id;
    private final String containerName;
    private final Boolean hasNestedContainer;

    public FieldContainerQueryResults(Long id, String containerName, Boolean hasNestedContainer) {
        this.id = id;
        this.containerName = containerName;
        this.hasNestedContainer = hasNestedContainer;
    }

    public Long getId() {
        return this.id;
    }

    public String getContainerName() {
        return this.containerName;
    }

    public Boolean hasNestedContainer() {
        return this.hasNestedContainer;
    }
    
}
