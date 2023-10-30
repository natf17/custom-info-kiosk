package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;

public class KioskCollectionTypeConverter implements KioskAndCmsTypeConverter<KioskCollectionType> {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    @Override
    public KioskCollectionType convert(SimpleCollectionType cmsRep) {

        Long id = cmsRep.getId();

        CollectionTypeName type = toKioskCollectionConverter.toCollectionTypeName(cmsRep.getType());

        KioskCollectionField<String> collectionNameField = toKioskCollectionConverter.toStringField(cmsRep.getCollectionNameField());
        KioskCollectionMetadata kioskCollectionMetadata = KioskCollectionMetadata.fromCollectionInternals(cmsRep.getCollectionInternals());
        
        return new DefaultKioskCollectionType(id, type, collectionNameField, kioskCollectionMetadata);

    }

    @Override
    public void transferKioskRep(SimpleCollectionTypeImpl.Builder builder, KioskCollectionType kioskCollection) {

        builder.withId(kioskCollection.getId());
        builder.type(kioskCollection.getKioskCollectionTypeName().toString());
        builder.collectionInternals(kioskCollection.getKioskCollectionMetadata().getCollectionInternals());
        builder.collectionNameField(toCmsCollectionConverter.toCollectionNameField(kioskCollection.getKioskCollectionNameField()));
        
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
