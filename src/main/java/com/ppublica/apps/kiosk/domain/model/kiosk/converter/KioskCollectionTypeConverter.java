package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.adapter.ToCmsCollectionConverter;
import com.ppublica.apps.kiosk.domain.model.collection.adapter.ToKioskCollectionConverter;

public class KioskCollectionTypeConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();
    
    public KioskCollectionType convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {

        Long id = sharedCmsPiece.id();

        CollectionTypeName type = toKioskCollectionConverter.toCollectionTypeName(sharedCmsPiece.type());

        KioskCollectionField<String> collectionNameField = toKioskCollectionConverter.toStringField(localizedCmsPiece.locCollectionNameField());
        KioskCollectionMetadata kioskCollectionMetadata = KioskCollectionMetadata.fromCollectionInternals(localizedCmsPiece.locCollectionInternals());
        
        return new DefaultKioskCollectionType(id, type, collectionNameField, kioskCollectionMetadata);

    }


    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, KioskCollectionType kioskCollection) {
        CollectionInternals collInternals = kioskCollection.getKioskCollectionMetadata().getCollectionInternals();

        sharedCmsBuilder.collectionSharedInternals(new CollectionSharedInternals(collInternals.getStatus(), collInternals.getCreatedOn(), collInternals.getLastModified()));
        sharedCmsBuilder.withId(kioskCollection.getId());

        localizedCmsBuilder.collectionInternals(kioskCollection.getKioskCollectionMetadata().getCollectionInternals());
        localizedCmsBuilder.collectionNameField(toCmsCollectionConverter.toCollectionNameField(kioskCollection.getKioskCollectionNameField()));
        
        
    }

    class DefaultKioskCollectionType implements KioskCollectionType {

        private Long id;
        private CollectionTypeName type;
        private KioskCollectionField<String> collectionNameField;
        private KioskCollectionMetadata kioskCollectionMetadata;

        DefaultKioskCollectionType(Long id, CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata) {
            this.id = id;
            this.type = type;
            this.collectionNameField = collectionNameField;
            this.kioskCollectionMetadata = kioskCollectionMetadata;
        }

        @Override
        public Long getId() {
            return this.id;
        }

        @Override
        public CollectionTypeName getKioskCollectionTypeName() {
            return this.type;
        }

        @Override
        public KioskCollectionField<String> getKioskCollectionNameField() {
            return this.collectionNameField;
        }

        @Override
        public KioskCollectionMetadata getKioskCollectionMetadata() {
            return this.kioskCollectionMetadata;
        }
    }
}
