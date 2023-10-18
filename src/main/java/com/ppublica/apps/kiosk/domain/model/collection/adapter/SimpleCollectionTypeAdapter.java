package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.List;

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
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionTypeImpl;

/*
 * An adapter class between the CMS collection type (SimpleCollectionType) and the Kiosk collection type (KioskCollectionType)
 * that keeps an instance of each.
 * 
 * Extending classes must ensure that there is always either a CMS or Kiosk collection object.
 * 
 * Uses the template pattern when building the CMS collection object by delegating to extending classes to obtain the 
 * Kiosk collection type and by providing a SimpleCollectionTypeImpl.Builder.
 * 
 * The Kiosk collection object is built differently to allow calling classes to obtain a particular implementation of 
 * KioskCollectionType and not just the parent type. Thus, extending classes must call this adapter's processKioskRep()
 * method so that this class can copy from the SimpleCollectionType to the KioskCollectionType builder.
 */
public abstract class SimpleCollectionTypeAdapter implements KioskCollectionType, SimpleCollectionType {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    // cms-facing methods

    @Override
    public SimpleCollectionType withId(Long id) {
        return getOrBuildAndSetCmsRep().withId(id);
    }

    @Override
    public Long getId() {
        if(getCmsRepInstance() != null) {
            return getCmsRepInstance().getId();
        } else {
            return getKioskRepInstance().getId();
        }
    }

    @Override
    public String getType() {
        return getOrBuildAndSetCmsRep().getType();
    }

    @Override
    public CollectionNameField getCollectionNameField() {
        return getOrBuildAndSetCmsRep().getCollectionNameField();
    }

    @Override
    public List<TextField> getTextFields() {
        return getOrBuildAndSetCmsRep().getTextFields();
    }

    @Override
    public List<NumericField> getNumericFields() {
        return getOrBuildAndSetCmsRep().getNumericFields();
    }

    @Override
    public List<ImageField> getImageFields() {
        return getOrBuildAndSetCmsRep().getImageFields();
    }

    @Override
    public CollectionInternals getCollectionInternals() {
        return getOrBuildAndSetCmsRep().getCollectionInternals();
    }

    @Override
    public List<LinkedCollectionField> getLinkedCollectionFields() {
        return getOrBuildAndSetCmsRep().getLinkedCollectionFields();
    }

    // kiosk-facing methods
    @Override
    public CollectionTypeName getKioskCollectionTypeName() {
        return getOrBuildAndSetKioskRep().getKioskCollectionTypeName();
    }

    @Override
    public KioskCollectionField<String> getKioskCollectionNameField() {
        return getOrBuildAndSetKioskRep().getKioskCollectionNameField();
    }

    @Override
    public KioskCollectionMetadata getKioskCollectionMetadata() {
        return getOrBuildAndSetKioskRep().getKioskCollectionMetadata();
    }
    
    protected abstract SimpleCollectionType getCmsRepInstance();
    protected abstract void processAndSetCmsRep(SimpleCollectionTypeImpl.Builder cmsRepBuilder);
    
    protected SimpleCollectionType getOrBuildAndSetCmsRep() {
        SimpleCollectionType cmsRep = getCmsRepInstance();
        
        if(cmsRep != null) {
            return cmsRep;
        }

        // make use of "both cmsRep and kioskRep cannot be null" invariant
        KioskCollectionType kioskRep = getOrBuildAndSetKioskRep();
        SimpleCollectionTypeImpl.Builder collectionBuilder = new SimpleCollectionTypeImpl.Builder()
                                                                .withId(kioskRep.getId())
                                                                .type(kioskRep.getKioskCollectionTypeName().toString())
                                                                .collectionInternals(kioskRep.getKioskCollectionMetadata().getCollectionInternals())
                                                                .collectionNameField(toCmsCollectionConverter.toCollectionNameField(kioskRep.getKioskCollectionNameField()));
    

        processAndSetCmsRep(collectionBuilder);
        return getCmsRepInstance();
    }

    protected abstract KioskCollectionType getKioskRepInstance();
    protected abstract KioskCollectionType getOrBuildAndSetKioskRep();

    protected <B extends KioskCollectionTypeImpl.Builder<B,M>, M extends KioskCollectionTypeImpl> void processKioskRep(KioskCollectionTypeImpl.Builder<B, M> kioskRepBuilder) {
        SimpleCollectionType cmsRep = getCmsRepInstance();
        CollectionNameField collNameField = cmsRep.getCollectionNameField();

        kioskRepBuilder.id(cmsRep.getId());
        kioskRepBuilder.collectionNameField(toKioskCollectionConverter.toStringField(collNameField));
        kioskRepBuilder.kioskCollectionMetadata(KioskCollectionMetadata.fromCollectionInternals(cmsRep.getCollectionInternals()));

    }
    

    
}
