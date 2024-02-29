package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * Represents a full donations type that can be instantiated.
 */
public class DefaultDonationsType extends DefaultAmenityType implements DonationsType {

    private Donations donationsPiece;

    public static final CollectionType KIOSK_COLLECTION_TYPE = CollectionType.DONATIONS;

    public DefaultDonationsType(AmenityType amenity, Donations donationsPiece) {
        super(amenity, amenity);
        this.donationsPiece = donationsPiece;
    }

    @Override
    public CollectionType kioskCollectionType() {
        return KIOSK_COLLECTION_TYPE;
    }

    @Override
    public KioskCollectionField<String> paymentTypesAccepted() {
        return this.donationsPiece.paymentTypesAccepted();
    }
    
}
