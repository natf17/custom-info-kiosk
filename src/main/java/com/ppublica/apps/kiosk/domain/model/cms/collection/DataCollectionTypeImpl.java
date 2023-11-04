package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class DataCollectionTypeImpl implements DataCollectionType {
    private Long id;
    private String type;
    private String subType;
    private LocalizedFields localizedFields;
    private CollectionInternals collectionInternals;
    private List<LinkedDataElement> linkedDataElements = new ArrayList<>();

    protected DataCollectionTypeImpl(Long id, String type, String subType, LocalizedFields localizedFields, List<LinkedDataElement> linkedDataElements, CollectionInternals collectionInternals) {
        this.id = id;
        this.type = type;
        this.subType = subType;
        this.localizedFields = localizedFields;
        this.linkedDataElements = linkedDataElements;
        this.collectionInternals = collectionInternals;
    }

    @Override
    public DataCollectionType withId(Long id) {
        return new DataCollectionTypeImpl(id, type, subType, localizedFields, linkedDataElements, collectionInternals);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getSubType() {
        return this.subType;
    }

    @Override
    public LocalizedFields getLocalizedFields() {
        return this.localizedFields;
    }

    @Override
    public CollectionInternals getCollectionInternals() {
        return this.collectionInternals;
    }

    @Override
    public List<LinkedDataElement> getLinkedDataElements() {
        return this.linkedDataElements;
    }

    public static class Builder {
        private Long id;
        private String type;
        private String subType;
        private LocalizedFields localizedFields;
        private CollectionInternals collectionInternals;
        private List<LinkedDataElement> linkedDataElements = new ArrayList<>();
        private Long kioskLocaleId;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder subType(String subType) {
            this.subType = subType;
            return this;
        }

        public Builder localizedFields(LocalizedFields localizedFields) {
            this.localizedFields = localizedFields;
            return this;
        }

        public Builder linkedDataElements(List<LinkedDataElement> linkedDataElements) {
            if(linkedDataElements == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.linkedDataElements = linkedDataElements;
            return this;
        }

        public Builder addLinkedDataElements(LinkedDataElement linkedDataElement) {
            this.linkedDataElements.add(linkedDataElement);
            return this;
        }

        public Builder collectionInternals(CollectionInternals collectionInternals) {
            this.collectionInternals = collectionInternals;
            return this;
        }

        public Builder kioskLocaleId(Long kioskLocaleId) {
            this.kioskLocaleId = kioskLocaleId;
            return this;
        }

        public DataCollectionType build() {

            if(collectionInternals == null) {
                if(kioskLocaleId == null) {
                    throw new RuntimeException("The kioskLocaleId is required");
                }

                collectionInternals = new CollectionInternals(kioskLocaleId, PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
            }

            if(type == null) {
                throw new RuntimeException("A collection type is required");
            }

            return new DataCollectionTypeImpl(id, type, subType, localizedFields, linkedDataElements, collectionInternals);
        }
    }
    
}
