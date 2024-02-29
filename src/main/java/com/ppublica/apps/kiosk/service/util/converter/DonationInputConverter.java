package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultDonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DonationsType;
import com.ppublica.apps.kiosk.service.payloads.donations.DonationInput;
import com.ppublica.apps.kiosk.service.util.DonationsInfoProcessor;
import com.ppublica.apps.kiosk.service.util.SameTypeConverter;
import com.ppublica.apps.kiosk.service.util.StringKioskFieldCreator;

public class DonationInputConverter {
    private DonationsInfoProcessor donationsInfoProcessor = new DonationsInfoProcessor();
    private AmenityTypeInputConverter amenityTypeConverter = new AmenityTypeInputConverter();


    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public List<DonationsType> toLocalizedDonations(DonationInput adminInput) {
        Map<Long, DefaultAmenityType.Builder> amenityBuilders = amenityTypeConverter.toAmenityTypeBuilders(adminInput, CollectionType.DONATIONS);

        List<DonationsType> donations = new ArrayList<>();
        for(DefaultAmenityType.Builder builder : amenityBuilders.values()) {
            donations.add(new DefaultDonationsType(builder.build(), donationsInfoProcessor.createDonationsPiece(adminInput.paymentTypesAccepted(), new SameTypeConverter<String>(), new StringKioskFieldCreator())));
        }

        return donations;
    }
}
