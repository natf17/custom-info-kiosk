package com.ppublica.apps.kiosk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

@Service
public class BathroomsDataService extends LocalizedCollectionServiceBase<BathroomKioskCollectionAdapter, BathroomType, BathroomKioskCollectionAdapter.Builder> {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;


    public List<BathroomView> getBathrooms(String locale, String sort) {
        List<BathroomView> bathroomViews = loadAdapters(CollectionTypeName.BATHROOM, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(bathroom -> buildView(bathroom))
                                                .collect(Collectors.toList());

        // TODO: sort

        return bathroomViews;
    }

    public List<BathroomAdminView> getBathroomsAdmin(String sort) {        

        List<BathroomAdminView> adminViews = loadListOfAdaptersList(CollectionTypeName.BATHROOM, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(bathroomAdapters -> buildAdminView(bathroomAdapters))
                                                .collect(Collectors.toList());

        return adminViews;
    }

    public Optional<BathroomAdminView> getBathroomAdmin(Long bathroomId) {
        List<BathroomKioskCollectionAdapter> adapters = loadAdapters(bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildAdminView(adapters));

    }

    public BathroomAdminView createBathroom(BathroomInput data) {

        // bathroom input to Bathroom type
        List<BathroomType> bathrooms = toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> newBathroomAdapters = save(bathrooms, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return buildAdminView(newBathroomAdapters);
    }

    
    public BathroomAdminView updateBathroom(Long bathroomId, BathroomInput data) {
        List<BathroomType> bathrooms = toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> updatedBathroomAdapters = update(bathrooms, bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);

        return buildAdminView(updatedBathroomAdapters);
    }

    public void deleteBathroom(Long bathroomId) {
        delete(bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    private BathroomView buildView(BathroomType bathroom) {
        Image featureImage = bathroom.featImg().getFieldValue();
        BathroomImage image = featureImage != null ? new BathroomImage(featureImage.location(), featureImage.width(), featureImage.height()): null;

        Long svgElemId = bathroom.svgElemId().getFieldValue();

        return new BathroomView(Long.toString(bathroom.getId()), 
                                bathroom.name().getFieldValue(), 
                                bathroom.gender().getFieldValue(),
                                bathroom.isWheelChairAccessible().getFieldValue(),
                                svgElemId != null ? Long.toString(svgElemId) : null,
                                bathroom.note().getFieldValue(),
                                bathroom.location().getFieldValue().getLinkedCollectionId(),
                                image);
    }

    // expects at least one element in the list
    private BathroomAdminView buildAdminView(List<? extends BathroomType> bathrooms) {
        BathroomType arbitraryBathroom = bathrooms.get(0);

        String id = arbitraryBathroom.getId() != null ? Long.toString(arbitraryBathroom.getId()): null;

        List<LocalizedField> nameLoc = new ArrayList<>();
        List<LocalizedField> svgElemIdLoc = new ArrayList<>();
        List<LocalizedField> noteLoc = new ArrayList<>();
        List<LocalizedView<BathroomImage>> featImgLoc = new ArrayList<>();

        // process localizable fields
        for (BathroomType bathroom : bathrooms) {
            String locale = bathroom.getKioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String name = bathroom.name().getFieldValue();
            if(name != null) {
                nameLoc.add(new LocalizedField(locale, name));
            }

            Long svgElem = bathroom.svgElemId().getFieldValue();

            if(svgElem != null) {
                svgElemIdLoc.add(new LocalizedField(locale, Long.toString(svgElem)));
            }

            String note = bathroom.note().getFieldValue();
            if(note != null) {
                noteLoc.add(new LocalizedField(locale, note));
            }

            Image featureImage = bathroom.featImg().getFieldValue();
            
            BathroomImage image = featureImage != null ? new BathroomImage(featureImage.location(), featureImage.width(), featureImage.height()): null;
            featImgLoc.add(new LocalizedView<BathroomImage>(locale, image));

        }

        // process non-locale fields
        String gender = arbitraryBathroom.gender().getFieldValue();
        Boolean isWheelchairAccessible = arbitraryBathroom.isWheelChairAccessible().getFieldValue();
        Long locationId = arbitraryBathroom.location().getFieldValue().getLinkedCollectionId();


        return new BathroomAdminView(id, nameLoc, gender, isWheelchairAccessible, svgElemIdLoc, noteLoc, locationId, featImgLoc);
    }

    // will return list with >= 1 elements
    private List<BathroomType> toLocalizedBathrooms(BathroomInput adminInput) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected BathroomKioskCollectionAdapter.Builder getAdapterBuilder() {
        return new BathroomKioskCollectionAdapter.Builder();
    }
    
}
