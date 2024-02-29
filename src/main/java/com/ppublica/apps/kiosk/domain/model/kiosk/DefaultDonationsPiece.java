package com.ppublica.apps.kiosk.domain.model.kiosk;


public record DefaultDonationsPiece(KioskCollectionField<String> paymentTypesAccepted) implements Donations {}