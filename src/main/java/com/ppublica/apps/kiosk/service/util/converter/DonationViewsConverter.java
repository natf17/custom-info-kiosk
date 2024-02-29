package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.kiosk.DonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.donations.DonationAdminView;
import com.ppublica.apps.kiosk.service.views.donations.DonationImage;
import com.ppublica.apps.kiosk.service.views.donations.DonationView;

public class DonationViewsConverter {
    public DonationView buildView(DonationsType donation) {
        KioskImage featureImage = donation.featImg().fieldValue();
        DonationImage image = featureImage != null ? new DonationImage(featureImage.location(), featureImage.width(), featureImage.height()): null;

        return new DonationView(Long.toString(donation.collectionId()), 
                                donation.name().fieldValue(), 
                                donation.isWheelChairAccessible().fieldValue(),
                                donation.svgElemId().fieldValue(),
                                donation.note().fieldValue(),
                                donation.location().fieldValue().linkedCollectionId(),
                                donation.paymentTypesAccepted().fieldValue(),
                                image);
    }

    // expects at least one element in the list
    public DonationAdminView buildAdminView(List<? extends DonationsType> donations) {
        DonationsType arbitraryDonation = donations.get(0);

        String id = arbitraryDonation.collectionId() != null ? Long.toString(arbitraryDonation.collectionId()): null;

        List<LocalizedField> nameLoc = new ArrayList<>();
        List<LocalizedField> svgElemIdLoc = new ArrayList<>();
        List<LocalizedField> noteLoc = new ArrayList<>();
        List<LocalizedView<DonationImage>> featImgLoc = new ArrayList<>();

        // process localizable fields
        for (DonationsType donation : donations) {
            String locale = donation.kioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String name = donation.name().fieldValue();
            if(name != null) {
                nameLoc.add(new LocalizedField(locale, name));
            }

            String svgElem = donation.svgElemId().fieldValue();

            if(svgElem != null) {
                svgElemIdLoc.add(new LocalizedField(locale, svgElem));
            }

            String note = donation.note().fieldValue();
            if(note != null) {
                noteLoc.add(new LocalizedField(locale, note));
            }

            KioskImage featureImage = donation.featImg().fieldValue();
            
            DonationImage image = featureImage != null ? new DonationImage(featureImage.location(), featureImage.width(), featureImage.height()): null;
            featImgLoc.add(new LocalizedView<DonationImage>(locale, image));

        }

        // process non-locale fields
        String paymentTypesAccepted = arbitraryDonation.paymentTypesAccepted().fieldValue();
        Boolean isWheelchairAccessible = arbitraryDonation.isWheelChairAccessible().fieldValue();
        Long locationId = arbitraryDonation.location().fieldValue().linkedCollectionId();


        return new DonationAdminView(id, nameLoc, isWheelchairAccessible, svgElemIdLoc, noteLoc, locationId, paymentTypesAccepted, featImgLoc);
    }
}
