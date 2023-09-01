package com.ppublica.apps.kiosk.domain.model.pages;


/*
 * Represents a page entity that represents a container or list of PageFields. 
 */
public class PageFieldContainer {

    private String containerName;

    public PageFieldContainer(String containerName) {
        this.containerName = containerName;
    }

    public String getContainerName() {
        return this.containerName;
    }

}
