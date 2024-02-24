package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.GenderInfo;

@Component
public class GenderInfoProcessor {
    /*
     * S: type of input field 
     * T: type of output kiosk field 
     */
    public <S> GenderInfo createGenderPiece(S inputField, ValueConverter<S,String> fieldConverter, KioskFieldCreator<String> fieldCreator) {
        return new GenderInfo(fieldCreator.create(fieldConverter.convert(inputField), false));
        
    }
}
