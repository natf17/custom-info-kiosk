package com.ppublica.apps.kiosk.domain.model.collection;

public class LinkedCollectionReference {
    
    private Long linkedCollectionId;

    public LinkedCollectionReference(Long linkedCollectionId) {
        this.linkedCollectionId = linkedCollectionId;
    }

    public Long getLinkedCollectionId() {
        return this.linkedCollectionId;
    }
}
