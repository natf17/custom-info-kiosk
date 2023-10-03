package com.ppublica.apps.kiosk.domain.model.pages;

public class IdFieldContainerNameGenerator {
    public String generateFieldContainerName(IdKioskContainer kioskContainer) {
        return kioskContainer.getId().getFieldValue().toString();
    }
}
