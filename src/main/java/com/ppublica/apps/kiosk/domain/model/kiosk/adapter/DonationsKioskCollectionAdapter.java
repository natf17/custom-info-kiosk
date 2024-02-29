package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.Donations;
import com.ppublica.apps.kiosk.domain.model.kiosk.DonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.DonationsInfoConverter;

public class DonationsKioskCollectionAdapter extends AmenityKioskCollectionAdapter implements DonationsType {
    private Donations donationsInfo;
    private DonationsInfoConverter donationsInfoConverter = new DonationsInfoConverter();
    
    protected DonationsKioskCollectionAdapter(Donations donationsInfo, Amenity amenity, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(amenity, baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.donationsInfo = donationsInfo;
    }

    protected DonationsKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, null, localizedCmsPiece, sharedCmsPiece);
    }

    protected DonationsKioskCollectionAdapter(Donations donationsInfo, Amenity amenity, KioskCollectionType baseKioskCollection) {
        this(donationsInfo, amenity, baseKioskCollection, null, null);
    }

    @Override
    public KioskCollectionField<String> paymentTypesAccepted() {
        return getDonationsInfo().paymentTypesAccepted();
    }

    @Override
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        super.processCmsBuilders(sharedCmsBuilder, localizedCmsBuilder);
        donationsInfoConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.donationsInfo);

    }

    protected Donations getDonationsInfo() {
        if(this.donationsInfo == null) {
            buildAndSetDonationsInfo();
        }

        return this.donationsInfo;
    }

    protected void buildAndSetDonationsInfo() {
        this.donationsInfo = donationsInfoConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }



    public static class Builder extends CmsCollectionAdapterBuilder<Builder, DonationsType, DonationsKioskCollectionAdapter> {

        @Override
        protected DonationsKioskCollectionAdapter buildChild() {
            return new DonationsKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.kioskCollection, super.localizedCmsPiece, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
