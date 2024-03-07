package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionType;

public class NonLocalizableKioskCollectionTypeConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();
    
    public NonLocalizableKioskCollectionType convert(CollectionSharedProperties sharedCmsPiece) {

        Long id = sharedCmsPiece.id();

        CollectionType type = toKioskCollectionConverter.toCollectionTypeName(sharedCmsPiece.type());

        KioskCollectionField<String> collectionNameField = toKioskCollectionConverter.toStringField(sharedCmsPiece.collectionNameField());
        NonLocalizableKioskCollectionMetadata kioskCollectionMetadata = NonLocalizableKioskCollectionMetadata.fromCollectionInternals(sharedCmsPiece.collectionSharedInternals());
        
        return new DefaultNonLocalizableKioskCollectionType(id, type, collectionNameField, kioskCollectionMetadata);

    }


    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, NonLocalizableKioskCollectionType kioskCollection) {
        CollectionSharedInternals collInternals = kioskCollection.kioskCollectionMetadata().getCollectionInternals();

        sharedCmsBuilder.collectionSharedInternals(new CollectionSharedInternals(collInternals.getStatus(), collInternals.getCreatedOn(), collInternals.getLastModified()));
        sharedCmsBuilder.withId(kioskCollection.collectionId());
        sharedCmsBuilder.type(kioskCollection.kioskCollectionType().toString());
        sharedCmsBuilder.collectionNameField(toCmsCollectionConverter.toCollectionNameField(kioskCollection.kioskCollectionNameField()));
    }

    class DefaultNonLocalizableKioskCollectionType implements NonLocalizableKioskCollectionType {

        private Long id;
        private CollectionType type;
        private KioskCollectionField<String> collectionNameField;
        private NonLocalizableKioskCollectionMetadata kioskCollectionMetadata;

        DefaultNonLocalizableKioskCollectionType(Long id, CollectionType type, KioskCollectionField<String> collectionNameField, NonLocalizableKioskCollectionMetadata kioskCollectionMetadata) {
            this.id = id;
            this.type = type;
            this.collectionNameField = collectionNameField;
            this.kioskCollectionMetadata = kioskCollectionMetadata;
        }

        @Override
        public Long collectionId() {
            return this.id;
        }

        @Override
        public CollectionType kioskCollectionType() {
            return this.type;
        }

        @Override
        public KioskCollectionField<String> kioskCollectionNameField() {
            return this.collectionNameField;
        }

        @Override
        public NonLocalizableKioskCollectionMetadata kioskCollectionMetadata() {
            return this.kioskCollectionMetadata;
        }
    }
}
