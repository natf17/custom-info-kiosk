package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;

public class KioskCollectionTypeConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();
    
    public KioskCollectionType convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {

        Long id = sharedCmsPiece.id();

        CollectionType type = toKioskCollectionConverter.toCollectionTypeName(sharedCmsPiece.type());

        KioskCollectionField<String> collectionNameField = toKioskCollectionConverter.toStringField(localizedCmsPiece.locCollectionNameField());
        KioskCollectionMetadata kioskCollectionMetadata = KioskCollectionMetadata.fromCollectionInternals(localizedCmsPiece.locCollectionInternals());
        
        return new DefaultKioskCollectionType(id, type, collectionNameField, kioskCollectionMetadata);

    }


    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, KioskCollectionType kioskCollection) {
        CollectionInternals collInternals = kioskCollection.kioskCollectionMetadata().getCollectionInternals();

        sharedCmsBuilder.collectionSharedInternals(new CollectionSharedInternals(collInternals.getStatus(), collInternals.getCreatedOn(), collInternals.getLastModified()));
        sharedCmsBuilder.withId(kioskCollection.collectionId());

        localizedCmsBuilder.collectionInternals(kioskCollection.kioskCollectionMetadata().getCollectionInternals());
        localizedCmsBuilder.collectionNameField(toCmsCollectionConverter.toCollectionNameField(kioskCollection.kioskCollectionNameField()));
        
        
    }

    class DefaultKioskCollectionType implements KioskCollectionType {

        private Long id;
        private CollectionType type;
        private KioskCollectionField<String> collectionNameField;
        private KioskCollectionMetadata kioskCollectionMetadata;

        DefaultKioskCollectionType(Long id, CollectionType type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata) {
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
        public KioskCollectionMetadata kioskCollectionMetadata() {
            return this.kioskCollectionMetadata;
        }
    }
}
