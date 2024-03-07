package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionRelationship;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.NonLocalizableKioskCollectionTypeConverter;

/*
 * The non localizable equivalent of KioskCollectionTypeBaseAdapter. CollectionLocalizedProperties are not needed.
 */
public class NonLocalizableKioskCollectionTypeBaseAdapter implements NonLocalizableKioskCollectionType, CollectionSharedProperties {
    private NonLocalizableKioskCollectionType baseKioskCollection;
    private CollectionSharedProperties sharedCmsPiece;

    private NonLocalizableKioskCollectionTypeConverter baseConverter = new NonLocalizableKioskCollectionTypeConverter();


    public NonLocalizableKioskCollectionTypeBaseAdapter(NonLocalizableKioskCollectionType baseKioskCollection, CollectionSharedProperties sharedCmsPiece) {
        this.baseKioskCollection = baseKioskCollection;
        this.sharedCmsPiece = sharedCmsPiece;
    }

    @Override
    public List<CollectionRelationship> collectionRelationships() {
        return getSharedCmsPiece().collectionRelationships();
    }

    @Override
    public CollectionSharedProperties withId(Long id) {
        return getSharedCmsPiece().withId(id);
    }

    @Override
    public String type() {
        return getSharedCmsPiece().type();
    }

    @Override
    public String subType() {
        return getSharedCmsPiece().subType();
    }

    @Override
    public CollectionSharedInternals collectionSharedInternals() {
        return getSharedCmsPiece().collectionSharedInternals();
    }

    @Override
    public List<LinkedCollectionField> linkedCollectionFields() {
        return getSharedCmsPiece().linkedCollectionFields();
    }

    @Override
    public Long id() {
        return getSharedCmsPiece().id();
    }

    @Override
    public CollectionNameField collectionNameField() {
        return getSharedCmsPiece().collectionNameField();
    }

    @Override
    public List<TextField> textFields() {
        return getSharedCmsPiece().textFields();
    }

    @Override
    public List<NumericField> numericFields() {
        return getSharedCmsPiece().numericFields();
    }

    @Override
    public List<BooleanField> booleanFields() {
        return getSharedCmsPiece().booleanFields();
    }

    @Override
    public List<ImageField> imageFields() {
        return getSharedCmsPiece().imageFields();
    }

    @Override
    public Long collectionId() {
        return getBaseKioskCollection().collectionId();
    }

    @Override
    public CollectionType kioskCollectionType() {
        return getBaseKioskCollection().kioskCollectionType();
    }

    @Override
    public KioskCollectionField<String> kioskCollectionNameField() {
        return getBaseKioskCollection().kioskCollectionNameField();
    }

    @Override
    public NonLocalizableKioskCollectionMetadata kioskCollectionMetadata() {
        return getBaseKioskCollection().kioskCollectionMetadata();
    }

    public final NonLocalizableKioskCollectionType getBaseKioskCollection() {
        if(this.baseKioskCollection == null) {
            buildAndSetBaseKioskCollection();
        }

        return this.baseKioskCollection;
    }

    /*
     * Extending classes should override this method, and call it to allow this class to build the base kiosk collection.
     */
    protected void buildAndSetBaseKioskCollection() {
        this.baseKioskCollection = baseConverter.convert(this.sharedCmsPiece);
    }

    // for extending classes
    protected void processCmsBuilder(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder) {}

    public final CollectionSharedProperties getSharedCmsPiece() {
        if(this.sharedCmsPiece == null) {
            buildAndSetCmsPiece();
        }
        return this.sharedCmsPiece;
    }

    protected final void buildAndSetCmsPiece() {
        CollectionSharedPropertiesImpl.Builder sharedCmsPieceBuilder = new CollectionSharedPropertiesImpl.Builder(this.baseKioskCollection.kioskCollectionType().toString());

        baseConverter.transferKioskRepToCmsBuilders(sharedCmsPieceBuilder, baseKioskCollection);
    
        processCmsBuilder(sharedCmsPieceBuilder);

        this.sharedCmsPiece = sharedCmsPieceBuilder.build();

    }

}
