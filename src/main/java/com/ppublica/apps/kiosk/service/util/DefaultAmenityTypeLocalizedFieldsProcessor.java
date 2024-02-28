package com.ppublica.apps.kiosk.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;

@Component
public class DefaultAmenityTypeLocalizedFieldsProcessor {
    /*
     * S: type of input field 
     * T: type of kiosk field that will be added to the builder
     */
    public <S,T> void processLocalizedFieldsWithBuilder(Map<Long, DefaultAmenityType.Builder> builders, List<LocalizedInputField<S>> localizedFields, KioskFieldBuilderProcessor<T> processor, ValueConverter<S,T> fieldConverter, KioskFieldCreator<T> fieldCreator) {
        for (LocalizedInputField<S> locField : localizedFields) {
            Long localeId = Long.valueOf(locField.localeId());
            DefaultAmenityType.Builder matchingBuilder = builders.get(localeId);

            if(matchingBuilder == null) {
                matchingBuilder = new DefaultAmenityType.Builder(CollectionType.BATHROOM);
                matchingBuilder.kioskCollectionMetadata(new KioskCollectionMetadata(localeId, null ,Status.PUBLISHED, LocalDate.now(), LocalDateTime.now()));
                matchingBuilder.collectionNameField(new KioskCollectionField<>(CollectionType.BATHROOM.toString(), false));
                builders.put(localeId, matchingBuilder);
            }

            processor.processBuilder(matchingBuilder, fieldCreator.create(fieldConverter.convert(locField.value()), false));
            
        }
        
    }

    /*
     * S: type of input field 
     * T: type of kiosk field that will be added to the builder
     */
    public <S,T> void processParentFieldWithBuilder(Map<Long, DefaultAmenityType.Builder> builders, S inputField, KioskFieldBuilderProcessor<T> processor, ValueConverter<S,T> fieldConverter, KioskFieldCreator<T> fieldCreator) {
        for (DefaultAmenityType.Builder builder : builders.values()) {
            processor.processBuilder(builder, fieldCreator.create(fieldConverter.convert(inputField), false));
        }
        
    }


}
