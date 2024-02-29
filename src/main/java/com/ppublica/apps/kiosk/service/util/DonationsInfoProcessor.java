package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultDonationsPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.Donations;

public class DonationsInfoProcessor {
    /*
     * S: type of input field 
     * T: type of output kiosk field 
     */
    public <S> Donations createDonationsPiece(S inputField, ValueConverter<S,String> fieldConverter, KioskFieldCreator<String> fieldCreator) {
        return new DefaultDonationsPiece(fieldCreator.create(fieldConverter.convert(inputField), false));
        
    }
}
