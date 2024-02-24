package com.ppublica.apps.kiosk.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultBathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.GenderInfo;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
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
        List<BathroomView> bathroomViews = loadAdapters(CollectionType.BATHROOM, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(bathroom -> buildView(bathroom))
                                                .collect(Collectors.toList());

        // TODO: sort

        return bathroomViews;
    }

    public List<BathroomAdminView> getBathroomsAdmin(String sort) {        

        List<BathroomAdminView> adminViews = loadListOfAdaptersList(CollectionType.BATHROOM, collSharedPropsRepo, collLocalizedPropsRepo)
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

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public BathroomAdminView createBathroom(BathroomInput data) {

        // bathroom input to Bathroom type
        List<? extends BathroomType> bathrooms = toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> newBathroomAdapters = save(bathrooms, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return buildAdminView(newBathroomAdapters);
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public BathroomAdminView updateBathroom(Long bathroomId, BathroomInput data) {
        List<? extends BathroomType> bathrooms = toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> updatedBathroomAdapters = update(bathrooms, bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);

        return buildAdminView(updatedBathroomAdapters);
    }

    public void deleteBathroom(Long bathroomId) {
        delete(bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    private BathroomView buildView(BathroomType bathroom) {
        KioskImage featureImage = bathroom.featImg().fieldValue();
        BathroomImage image = featureImage != null ? new BathroomImage(featureImage.location(), featureImage.width(), featureImage.height()): null;

        Long svgElemId = bathroom.svgElemId().fieldValue();

        return new BathroomView(Long.toString(bathroom.collectionId()), 
                                bathroom.name().fieldValue(), 
                                bathroom.gender().fieldValue(),
                                bathroom.isWheelChairAccessible().fieldValue(),
                                svgElemId != null ? Long.toString(svgElemId) : null,
                                bathroom.note().fieldValue(),
                                bathroom.location().fieldValue().linkedCollectionId(),
                                image);
    }

    // expects at least one element in the list
    private BathroomAdminView buildAdminView(List<? extends BathroomType> bathrooms) {
        BathroomType arbitraryBathroom = bathrooms.get(0);

        String id = arbitraryBathroom.collectionId() != null ? Long.toString(arbitraryBathroom.collectionId()): null;

        List<LocalizedField> nameLoc = new ArrayList<>();
        List<LocalizedField> svgElemIdLoc = new ArrayList<>();
        List<LocalizedField> noteLoc = new ArrayList<>();
        List<LocalizedView<BathroomImage>> featImgLoc = new ArrayList<>();

        // process localizable fields
        for (BathroomType bathroom : bathrooms) {
            String locale = bathroom.kioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String name = bathroom.name().fieldValue();
            if(name != null) {
                nameLoc.add(new LocalizedField(locale, name));
            }

            Long svgElem = bathroom.svgElemId().fieldValue();

            if(svgElem != null) {
                svgElemIdLoc.add(new LocalizedField(locale, Long.toString(svgElem)));
            }

            String note = bathroom.note().fieldValue();
            if(note != null) {
                noteLoc.add(new LocalizedField(locale, note));
            }

            KioskImage featureImage = bathroom.featImg().fieldValue();
            
            BathroomImage image = featureImage != null ? new BathroomImage(featureImage.location(), featureImage.width(), featureImage.height()): null;
            featImgLoc.add(new LocalizedView<BathroomImage>(locale, image));

        }

        // process non-locale fields
        String gender = arbitraryBathroom.gender().fieldValue();
        Boolean isWheelchairAccessible = arbitraryBathroom.isWheelChairAccessible().fieldValue();
        Long locationId = arbitraryBathroom.location().fieldValue().linkedCollectionId();


        return new BathroomAdminView(id, nameLoc, gender, isWheelchairAccessible, svgElemIdLoc, noteLoc, locationId, featImgLoc);
    }

    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    private List<? extends BathroomType> toLocalizedBathrooms(BathroomInput adminInput) {
        Map<Long, DefaultAmenityType.Builder> amenityBuilders = new HashMap<>();

        processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.name(), (builder, field) -> builder.name(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.svgElemId(), (builder, field) -> builder.svgElemId(field), new StringToLongTypeConverter(), new LongKioskFieldCreator());
        processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.note(), (builder, field) -> builder.note(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.featImg(), (builder, field) -> builder.featImg(field), new BathroomImageToKioskImageTypeConverter(), new ImageKioskFieldCreator());

        processParentFieldWithBuilder(amenityBuilders, adminInput.isWheelchairAccessible(), (builder, field) -> builder.isWheelChairAccessible(field), new SameTypeConverter<Boolean>(), new BooleanKioskFieldCreator());
        processParentFieldWithBuilder(amenityBuilders, adminInput.locationId(), (builder, field) -> builder.location(field), new LongToLinkedCollectionRefTypeConverter(), new LinkedCollectionReferenceKioskFieldCreator());

        List<DefaultBathroomType> bathrooms = new ArrayList<>();
        for(DefaultAmenityType.Builder builder : amenityBuilders.values()) {
            bathrooms.add(new DefaultBathroomType(builder.build(), createGenderPiece(adminInput.gender(), new SameTypeConverter<String>(), new StringKioskFieldCreator())));
        }

        return bathrooms;
    }

    /*
     * S: type of input field 
     * T: type of output kiosk field 
     */
    private <S> GenderInfo createGenderPiece(S inputField, ValueConverter<S,String> fieldConverter, KioskFieldCreator<String> fieldCreator) {
        return new GenderInfo(fieldCreator.create(fieldConverter.convert(inputField), false));
        
    }

    /*
     * S: type of input field 
     * T: type of kiosk field that will be added to the builder
     */
    private <S,T> void processParentFieldWithBuilder(Map<Long, DefaultAmenityType.Builder> builders, S inputField, KioskFieldBuilderProcessor<T> processor, ValueConverter<S,T> fieldConverter, KioskFieldCreator<T> fieldCreator) {
        for (DefaultAmenityType.Builder builder : builders.values()) {
            processor.processBuilder(builder, fieldCreator.create(fieldConverter.convert(inputField), false));
        }
        
    }

    /*
     * S: type of input field 
     * T: type of kiosk field that will be added to the builder
     */
    private <S,T> void processLocalizedFieldsWithBuilder(Map<Long, DefaultAmenityType.Builder> builders, List<LocalizedInputField<S>> localizedFields, KioskFieldBuilderProcessor<T> processor, ValueConverter<S,T> fieldConverter, KioskFieldCreator<T> fieldCreator) {
        for (LocalizedInputField<S> locField : localizedFields) {
            Long localeId = Long.valueOf(locField.localeId());
            DefaultAmenityType.Builder matchingBuilder = builders.get(localeId);

            if(matchingBuilder == null) {
                matchingBuilder = new DefaultAmenityType.Builder(CollectionType.BATHROOM);
                matchingBuilder.kioskCollectionMetadata(new KioskCollectionMetadata(localeId, Status.PUBLISHED, LocalDate.now(), LocalDateTime.now()));
                matchingBuilder.collectionNameField(new KioskCollectionField<>(CollectionType.BATHROOM.toString(), false));
                builders.put(localeId, matchingBuilder);
            }

            processor.processBuilder(matchingBuilder, fieldCreator.create(fieldConverter.convert(locField.value()), false));
            
        }
        
    }

    
    @Override
    protected BathroomKioskCollectionAdapter.Builder getAdapterBuilder() {
        return new BathroomKioskCollectionAdapter.Builder();
    }

    interface KioskFieldBuilderProcessor<T> {
        public void processBuilder(DefaultAmenityType.Builder builder, KioskCollectionField<T> field);
    }

    interface KioskFieldCreator<T> {
        public KioskCollectionField<T> create(T value, boolean isLocalizable);
    }

    interface ValueConverter<S,T> {
        public T convert(S input);
    }

    class SameTypeConverter<S> implements ValueConverter<S,S> {
        public S convert(S input) {
            return input;
        }
    }

    class StringToLongTypeConverter implements ValueConverter<String,Long> {
        public Long convert(String input) {
            return input != null ? Long.parseLong(input) : null;
        }
    }

    class LongToLinkedCollectionRefTypeConverter implements ValueConverter<Long, LinkedCollectionReference> {
        public LinkedCollectionReference convert(Long input) {
            return input != null ? new  LinkedCollectionReference(input): null;
        }
    }

    class StringKioskFieldCreator implements KioskFieldCreator<String> {
        public KioskCollectionField<String> create(String value, boolean isLocalizable) {
            return new KioskCollectionField<>(value, isLocalizable);
        }
    }

    class ImageKioskFieldCreator implements KioskFieldCreator<KioskImage> {
        public KioskCollectionField<KioskImage> create(KioskImage value, boolean isLocalizable) {
            return new KioskCollectionField<>(value, isLocalizable);
        }
    }

    class LongKioskFieldCreator implements KioskFieldCreator<Long> {
        public KioskCollectionField<Long> create(Long value, boolean isLocalizable) {
            return new KioskCollectionField<>(value, isLocalizable);
        }
    }

    class BooleanKioskFieldCreator implements KioskFieldCreator<Boolean> {
        public KioskCollectionField<Boolean> create(Boolean value, boolean isLocalizable) {
            return new KioskCollectionField<>(value, isLocalizable);
        }
    }

    class LinkedCollectionReferenceKioskFieldCreator implements KioskFieldCreator<LinkedCollectionReference> {
        public KioskCollectionField<LinkedCollectionReference> create(LinkedCollectionReference value, boolean isLocalizable) {
            return new KioskCollectionField<>(value, isLocalizable);
        }
    }

    class BathroomImageToKioskImageTypeConverter implements ValueConverter<BathroomImage,KioskImage> {
        public KioskImage convert(BathroomImage input) {
            return new KioskImage(input.url(), input.width(), input.height());
        }
    }

    
    
}
