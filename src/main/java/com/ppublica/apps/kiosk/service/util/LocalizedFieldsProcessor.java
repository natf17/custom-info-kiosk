package com.ppublica.apps.kiosk.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionTypeBase;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;

/*
 * Base type for classes that process a list of LocalizedInputFields and call the supplied method on the builder.
 * 
 * Extending classes must provide the builder instance, and also provide the K and N generic parameters.
 * 
 * K: type of the collection type builder
 * N: type of the default collection type
 */
public abstract class LocalizedFieldsProcessor<K extends KioskCollectionTypeBase.Builder<K, N>, N extends KioskCollectionTypeBase> {
    /*
     * S: type of input field 
     * T: type of kiosk field that will be added to the builder
     */
    public <S,T> void processLocalizedFieldsWithBuilder(Map<Long, K> builders, List<LocalizedInputField<S>> localizedFields, KioskFieldToBuilderAction<T, K> action, ValueConverter<S,T> fieldConverter, KioskFieldCreator<T> fieldCreator, CollectionType collType) {
        for (LocalizedInputField<S> locField : localizedFields) {
            Long localeId = Long.valueOf(locField.localeId());
            K matchingBuilder = builders.get(localeId);

            if(matchingBuilder == null) {
                matchingBuilder = getBuilder(collType);
                matchingBuilder.kioskCollectionMetadata(new KioskCollectionMetadata(localeId, null, Status.PUBLISHED, LocalDate.now(), LocalDateTime.now()));
                matchingBuilder.collectionNameField(new KioskCollectionField<>(collType.toString(), false));
                builders.put(localeId, matchingBuilder);
            }

            action.callOnBuilder(matchingBuilder, fieldCreator.create(fieldConverter.convert(locField.value()), true));
            
        }
        
    }

    /*
     * 
     * S: type of input field 
     * T: type of kiosk field that will be added to the builder
     */
    public <S,T> void processParentFieldWithBuilder(Map<Long, K> builders, S inputField, KioskFieldToBuilderAction<T, K> action, ValueConverter<S,T> fieldConverter, KioskFieldCreator<T> fieldCreator) {
        for (K builder : builders.values()) {
            action.callOnBuilder(builder, fieldCreator.create(fieldConverter.convert(inputField), false));
        }
        
    }

    abstract public K getBuilder(CollectionType colltype);

    
}
