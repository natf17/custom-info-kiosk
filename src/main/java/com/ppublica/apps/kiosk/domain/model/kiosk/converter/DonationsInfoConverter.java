package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultDonationsPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.Donations;

public class DonationsInfoConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    public static final String PAYMENT_TYPES_FIELD_TYPE = "paymentTypesAccepted";

    public Donations convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        // extract gender
        List<TextField> textFields = sharedCmsPiece.textFields();


        for (TextField textField : textFields) {
            if(textField.getFieldType().equals(PAYMENT_TYPES_FIELD_TYPE)) {
                return new DefaultDonationsPiece(toKioskCollectionConverter.toStringField(textField));
            }
        }

        throw new RuntimeException("No paymentTypesAccepted field found");
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, Donations donationsInfo) {
        sharedCmsBuilder.addTextField(toCmsCollectionConverter.toTextField(donationsInfo.paymentTypesAccepted(), PAYMENT_TYPES_FIELD_TYPE));
    }


}
