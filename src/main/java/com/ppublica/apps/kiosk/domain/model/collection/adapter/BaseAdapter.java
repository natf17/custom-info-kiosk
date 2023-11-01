package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;

/*
 * An adapter class between the CMS collection type (SimpleCollectionType) and the Kiosk collection type (KioskCollectionType)
 * that keeps an instance of each.
 * 
 * The adapter will always contain either a CMS or Kiosk collection object, using a converter to build the other.
 * 
 * Uses the template pattern when building the CMS collection object by delegating to extending classes to allow for each to
 * process the SimpleCollectionTypeImpl.Builder using its own kiosk collection type "piece".
 * 
 * The Kiosk collection object is built differently: one piece at a time, each kept by one adapter.
 */
public abstract class BaseAdapter implements KioskCollectionType, SimpleCollectionType {
    private KioskCollectionType baseKioskCollection;
    private SimpleCollectionType cmsCollection;

    private KioskCollectionTypeConverter baseConverter = new KioskCollectionTypeConverter();

    public BaseAdapter(KioskCollectionType baseKioskCollection, SimpleCollectionType cmsCollection) {
        this.baseKioskCollection = baseKioskCollection;
        this.cmsCollection = cmsCollection;
    }

    @Override
    public Long getId() {
        return getBaseKioskCollection().getId();
    }

    // kiosk-facing methods
    @Override
    public CollectionTypeName getKioskCollectionTypeName() {
        return getBaseKioskCollection().getKioskCollectionTypeName();
    }

    @Override
    public KioskCollectionField<String> getKioskCollectionNameField() {
        return getBaseKioskCollection().getKioskCollectionNameField();
    }

    @Override
    public KioskCollectionMetadata getKioskCollectionMetadata() {
        return getBaseKioskCollection().getKioskCollectionMetadata();
    }

    // cms-facing methods
    @Override
    public SimpleCollectionType withId(Long id) {
        return getCmsCollection().withId(id);
    }

    @Override
    public String getType() {
        return getCmsCollection().getType();
    }

    @Override
    public CollectionNameField getCollectionNameField() {
        return getCmsCollection().getCollectionNameField();
    }

    @Override
    public List<TextField> getTextFields() {
        return getCmsCollection().getTextFields();
    }

    @Override
    public List<NumericField> getNumericFields() {
        return getCmsCollection().getNumericFields();
    }

    @Override
    public List<BooleanField> getBooleanFields() {
        return getCmsCollection().getBooleanFields();
    }

    @Override
    public List<ImageField> getImageFields() {
        return getCmsCollection().getImageFields();
    }

    @Override
    public CollectionInternals getCollectionInternals() {
        return getCmsCollection().getCollectionInternals();
    }

    @Override
    public List<LinkedCollectionField> getLinkedCollectionFields() {
        return getCmsCollection().getLinkedCollectionFields();
    }

    public final SimpleCollectionType getCmsCollection() {
        if(this.cmsCollection == null) {
            buildAndSetCmsCollection();
        }

        return this.cmsCollection;
    }

    protected final void buildAndSetCmsCollection() {
        SimpleCollectionTypeImpl.Builder collectionBuilder = new SimpleCollectionTypeImpl.Builder();

        baseConverter.transferKioskRep(collectionBuilder, baseKioskCollection);
    
        processCmsBuilder(collectionBuilder);

        this.cmsCollection = collectionBuilder.build();

    }

    // for extending classes
    protected void processCmsBuilder(SimpleCollectionTypeImpl.Builder builder) {}

    protected final KioskCollectionType getBaseKioskCollection() {
        if(this.baseKioskCollection == null) {
            buildAndSetBaseKioskCollection();
        }

        return this.baseKioskCollection;
    }

    /*
     * Extending classes should override this method, and call it to allow this class to build the base kiosk collection.
     */
    protected void buildAndSetBaseKioskCollection() {
        this.baseKioskCollection = baseConverter.convert(this.cmsCollection);
    }
    
}
