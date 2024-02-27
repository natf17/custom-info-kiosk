package com.ppublica.apps.kiosk.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.util.converter.BathroomInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.BathroomViewsConverter;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

/*
 * See adapter unit tests for adapter/conversion details. The tests in this class use mocks to avoid addressing the conversion details.
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={BathroomsDataService.class})
public class BathroomsDataServiceTest {
    @MockBean
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @MockBean
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @MockBean
    private BathroomViewsConverter bathroomsViewsConverter;

    @MockBean
    private BathroomInputConverter bathroomInputConverter;

    @MockBean
    private AdapterBuilderGenerator<BathroomKioskCollectionAdapter.Builder> adapterBuilderGenerator;

    @Autowired
    private BathroomsDataService bathroomsDataService;

    BathroomAdminView bathroom1AdminView;
    BathroomAdminView bathroom2AdminView;
    LocalizedView<BathroomImage> bathroom1ImageEn;
    LocalizedView<BathroomImage> bathroom1ImageEs;
    LocalizedView<BathroomImage> bathroom2ImageEn;
    LocalizedView<BathroomImage> bathroom2ImageEs;


    @BeforeEach
    public void setup() {
        bathroom1ImageEn = new LocalizedView<>("en", new BathroomImage("url", 1, 2));
        bathroom1ImageEs = new LocalizedView<>("es", new BathroomImage("url_es", 1, 2));
        bathroom1AdminView = new BathroomAdminView("1",
                                                    List.of(new LocalizedField("en", "brName_1"), new LocalizedField("es", "brName_1_es")), 
                                                    "uni", 
                                                    true, 
                                                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                                                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                                                    1L, 
                                                    List.of(bathroom1ImageEn, bathroom1ImageEs));

        bathroom2ImageEn = new LocalizedView<>("en", new BathroomImage("url2", 3, 4));
        bathroom2ImageEs = new LocalizedView<>("es", new BathroomImage("url2_es", 3, 4));
        bathroom2AdminView = new BathroomAdminView("2",
                                                    List.of(new LocalizedField("en", "brName_2"), new LocalizedField("es", "brName_2_es")), 
                                                    "mens", 
                                                    false, 
                                                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                                                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                                                    2L, 
                                                    List.of(bathroom2ImageEn, bathroom2ImageEs));
    }

    @Test
    public void GET_bathroomViewsGivenLocale_returns_bathrooms() {

        // set up mock
        CollectionSharedProperties sharedProps1 = new CollectionSharedPropertiesImpl.Builder("BATHROOM")
                                                            .withId(1L)
                                                            .build();
        CollectionLocalizedProperties locProps1 = new CollectionLocalizedPropertiesImpl.Builder(1L).withId(1L).kioskLocaleId(1L).build();


        CollectionSharedProperties sharedProps2 = new CollectionSharedPropertiesImpl.Builder("BATHROOM")
                                                            .withId(2L)
                                                            .build();
        
        CollectionLocalizedProperties locProps2 = new CollectionLocalizedPropertiesImpl.Builder(2L).withId(2L).kioskLocaleId(1L).build();

        
        BathroomView bathroom1View = new BathroomView("1", "brName_1", "uni", true, "svgElemId", "note", 1L, new BathroomImage("url", 1, 2));
        BathroomView bathroom2View = new BathroomView("2", "brName_2", "mens", false, "svgElemId2", "note2", 2L, new BathroomImage("url2", 3, 4));
       
        when(collSharedPropsRepo.findByCollectionType("BATHROOM", null)).thenReturn(List.of(sharedProps1, sharedProps2));
        when(collLocalizedPropsRepo.findByParentCollectionIdAndLocaleBatch(eq(List.of(1L, 2L)), eq("en"))).thenReturn(List.of(locProps1, locProps2));
        when(adapterBuilderGenerator.newAdapterBuilder()).thenReturn(new TestAdapterBuilder());
        when(bathroomsViewsConverter.buildView(eq(new TestBathroomKioskCollectionAdapter(null, 1L, 1L, 1L)))).thenReturn(bathroom1View);
        when(bathroomsViewsConverter.buildView(eq(new TestBathroomKioskCollectionAdapter(null, 2L, 2L, 2L)))).thenReturn(bathroom2View);
        
        List<BathroomView> views = bathroomsDataService.getBathrooms("en", "");
        Assertions.assertEquals(2, views.size());

        Assertions.assertNotNull(views.get(0));
        Assertions.assertNotNull(views.get(1));
    }

    @Test
    public void GET_bathroomsAdmin_returns_bathroomsAdmin() {

        // set up mock
        CollectionSharedProperties sharedProps1 = new CollectionSharedPropertiesImpl.Builder("BATHROOM")
                                                            .withId(1L)
                                                            .build();
        CollectionLocalizedProperties locProps1 = new CollectionLocalizedPropertiesImpl.Builder(1L).kioskLocaleId(1L).build();
        CollectionLocalizedProperties locProps1_2 = new CollectionLocalizedPropertiesImpl.Builder(1L).kioskLocaleId(2L).build();


        CollectionSharedProperties sharedProps2 = new CollectionSharedPropertiesImpl.Builder("BATHROOM")
                                                            .withId(2L)
                                                            .build();
        
        CollectionLocalizedProperties locProps2 = new CollectionLocalizedPropertiesImpl.Builder(2L).kioskLocaleId(3L).build();
        CollectionLocalizedProperties locProps2_2 = new CollectionLocalizedPropertiesImpl.Builder(2L).kioskLocaleId(4L).build();

        
        
        when(collSharedPropsRepo.findByCollectionType("BATHROOM", null)).thenReturn(List.of(sharedProps1, sharedProps2));
        when(collLocalizedPropsRepo.findByParentCollectionId(1L)).thenReturn(List.of(locProps1, locProps1_2));
        when(collLocalizedPropsRepo.findByParentCollectionId(2L)).thenReturn(List.of(locProps2, locProps2_2));
        when(adapterBuilderGenerator.newAdapterBuilder()).thenReturn(new TestAdapterBuilder());
        when(bathroomsViewsConverter.buildAdminView(eq(List.of(
                                        new TestBathroomKioskCollectionAdapter(null, 1L, 1L, 1L),
                                        new TestBathroomKioskCollectionAdapter(null, 1L, 2L, 1L)
                                    )))
            ).thenReturn(bathroom1AdminView);
        when(bathroomsViewsConverter.buildAdminView(eq(List.of(
                                        new TestBathroomKioskCollectionAdapter(null, 2L, 3L, 2L),
                                        new TestBathroomKioskCollectionAdapter(null, 2L, 4L, 2L)
                                        )))
            ).thenReturn(bathroom2AdminView);   
            
    
    
        List<BathroomAdminView> adminViews = bathroomsDataService.getBathroomsAdmin("");

        Assertions.assertEquals(2, adminViews.size());
        Assertions.assertNotNull(adminViews.get(0));
        Assertions.assertNotNull(adminViews.get(1));
    }

    @Test
    public void POST_bathroomInput_returns_bathroomAdmin() {

        // set up mock
        CollectionSharedProperties sharedProps1 = new CollectionSharedPropertiesImpl.Builder("BATHROOM")
                                                            .withId(1L)
                                                            .build();
        CollectionLocalizedProperties locProps1 = new CollectionLocalizedPropertiesImpl.Builder(1L).kioskLocaleId(1L).build();
        CollectionLocalizedProperties locProps1_2 = new CollectionLocalizedPropertiesImpl.Builder(1L).kioskLocaleId(2L).build();

        BathroomInput input = new BathroomInput(List.of(new LocalizedInputField<>("1", ""), new LocalizedInputField<>("2", "")),
                                                "gender",
                                                true,
                                                List.of(new LocalizedInputField<>("1", ""), new LocalizedInputField<>("2", "")),
                                                List.of(new LocalizedInputField<>("1", ""), new LocalizedInputField<>("2", "")),
                                                10L,
                                                List.of(new LocalizedInputField<>("1", new BathroomImage(null, null, null)), new LocalizedInputField<>("2", new BathroomImage(null, null, null)))
                                                );

        // set an id only to differentiate between the objs 
        List<BathroomType> bathrooms = new ArrayList<>();
        bathrooms.add(new TestBathroomKioskCollectionAdapter(null, null, 1L, null));
        bathrooms.add(new TestBathroomKioskCollectionAdapter(null, null, 2L, null));

        when(bathroomInputConverter.toLocalizedBathrooms(eq(input))).thenReturn(bathrooms);

        when(adapterBuilderGenerator.newAdapterBuilder()).thenReturn(new TestAdapterBuilder());
        when(collSharedPropsRepo.saveInstance(any())).thenReturn(sharedProps1);
        when(collLocalizedPropsRepo.saveLocalizedInstance(eq(1L), eq(new TestBathroomKioskCollectionAdapter(null, null, 1L, null)))).thenReturn(locProps1);
        when(collLocalizedPropsRepo.saveLocalizedInstance(eq(1L), eq(new TestBathroomKioskCollectionAdapter(null, null, 2L, null)))).thenReturn(locProps1_2);

        when(bathroomsViewsConverter.buildAdminView(eq(List.of(
                                        new TestBathroomKioskCollectionAdapter(null, 1L, 1L, 1L),
                                        new TestBathroomKioskCollectionAdapter(null, 1L, 2L, 1L)
                                    )))
            ).thenReturn(bathroom1AdminView); 
            
    
        BathroomAdminView adminView = bathroomsDataService.createBathroom(input);

        Assertions.assertNotNull(adminView);
        Assertions.assertEquals(bathroom1AdminView, adminView);
    }

    @Test
    public void PUT_bathroomInput_returns_bathroomAdmin() {

        // set up mock
        CollectionSharedProperties sharedProps1 = new CollectionSharedPropertiesImpl.Builder("BATHROOM")
                                                            .withId(1L)
                                                            .build();
        CollectionLocalizedProperties locProps1 = new CollectionLocalizedPropertiesImpl.Builder(1L).kioskLocaleId(1L).withId(1L).build();
        CollectionLocalizedProperties locProps1_2 = new CollectionLocalizedPropertiesImpl.Builder(1L).kioskLocaleId(2L).withId(2L).build();

        BathroomInput input = new BathroomInput(List.of(new LocalizedInputField<>("1", ""), new LocalizedInputField<>("2", "")),
                                                "gender",
                                                true,
                                                List.of(new LocalizedInputField<>("1", ""), new LocalizedInputField<>("2", "")),
                                                List.of(new LocalizedInputField<>("1", ""), new LocalizedInputField<>("2", "")),
                                                10L,
                                                List.of(new LocalizedInputField<>("1", new BathroomImage(null, null, null)), new LocalizedInputField<>("2", new BathroomImage(null, null, null)))
                                                );

        List<BathroomType> bathrooms = new ArrayList<>();
        // added kioskCollectionId to differentiate instances in test
        // bathroom kiosk types are set as the kioskCollection on the adpater builder, so we need to set the kioskCollectionId, since
        // it's the only field tied to the underlying kioskCollection
        bathrooms.add(new TestBathroomKioskCollectionAdapter(1L, null, null, null));
        bathrooms.add(new TestBathroomKioskCollectionAdapter(2L, null, null, null));

        when(bathroomInputConverter.toLocalizedBathrooms(eq(input))).thenReturn(bathrooms);
        when(adapterBuilderGenerator.newAdapterBuilder()).thenReturn(new TestAdapterBuilder());
        when(collSharedPropsRepo.updateCollectionInstance(eq(1L), any())).thenReturn(sharedProps1);
        when(collLocalizedPropsRepo.saveLocalizedInstance(eq(1L), eq(new TestBathroomKioskCollectionAdapter(1L, null, null, null)))).thenReturn(locProps1);
        when(collLocalizedPropsRepo.saveLocalizedInstance(eq(1L), eq(new TestBathroomKioskCollectionAdapter(2L, null, null, null)))).thenReturn(locProps1_2);

        when(bathroomsViewsConverter.buildAdminView(eq(List.of(
                                        new TestBathroomKioskCollectionAdapter(null, 1L, 1L, 1L),
                                        new TestBathroomKioskCollectionAdapter(null, 1L, 2L, 1L)
                                    )))
            ).thenReturn(bathroom1AdminView); 
            
    
        BathroomAdminView adminView = bathroomsDataService.updateBathroom(1L, input);

        Assertions.assertNotNull(adminView);
        Assertions.assertEquals(bathroom1AdminView, adminView);
    }










    class TestAdapterBuilder extends BathroomKioskCollectionAdapter.Builder {

        // return any BathroomKioskCollectionAdapter
        @Override
        protected BathroomKioskCollectionAdapter buildChild() {
            Long kioskCollectionId = this.kioskCollection != null ? this.kioskCollection.collectionId() : null;
            Long collSharedPropsId = this.sharedCmsPiece != null ? this.sharedCmsPiece.id() : null;
            Long collLocPropsparentId = this.localizedCmsPiece != null ? this.localizedCmsPiece.parentId() : null;
            Long collLocPropsId = this.localizedCmsPiece != null ? this.localizedCmsPiece.locId() : null;

            return new TestBathroomKioskCollectionAdapter(kioskCollectionId, collSharedPropsId, collLocPropsId, collLocPropsparentId);
        }
    }

    class TestBathroomKioskCollectionAdapter extends BathroomKioskCollectionAdapter {
        Long kioskCollectionId;
        Long collSharedPropsId;
        Long collLocPropsParentId;
        Long collLocPropsId;

        public TestBathroomKioskCollectionAdapter(Long kioskCollectionId, Long collSharedPropsId, Long collLocPropsId, Long collLocPropsParentId) {
            super(null, null, null, null, null);
            this.kioskCollectionId = kioskCollectionId;
            this.collSharedPropsId = collSharedPropsId;
            this.collLocPropsId = collLocPropsId;
            this.collLocPropsParentId = collLocPropsParentId;
        }

        @Override
        public Long collectionId() {
            return this.kioskCollectionId;
        }

        @Override
        public Long locId() {
            return this.collLocPropsId;
        }

        @Override
        public Long parentId() {
            return this.collLocPropsParentId;
        }

        @Override
        public Long id() {
            return null;
        }

        @Override
        public boolean equals(Object other) {
            if(other == this) {
                return true;
            }
            if(!(other instanceof TestBathroomKioskCollectionAdapter)) {
                return false;
            }
            TestBathroomKioskCollectionAdapter otherAdapt = (TestBathroomKioskCollectionAdapter) other;
            
            Long otherKioskCollectionId = otherAdapt.kioskCollectionId;
            Long otherCollSharedPropsId = otherAdapt.collSharedPropsId;
            Long otherCollLocPropsParentId = otherAdapt.collLocPropsParentId;

            boolean kioskCollectionIdEquals = false;
            boolean kioskCollSharedPropsIdEquals = false;
            boolean kioskCollLocPropsParentIdEquals = false;

            if(this.kioskCollectionId == null && otherKioskCollectionId == null || this.kioskCollectionId.equals(otherKioskCollectionId)) {
                kioskCollectionIdEquals = true;
            }

            if(this.collSharedPropsId == null && otherCollSharedPropsId == null || this.collSharedPropsId.equals(otherCollSharedPropsId)) {
                kioskCollSharedPropsIdEquals = true;
            }

            if(this.collLocPropsParentId == null && otherCollLocPropsParentId == null || this.collLocPropsParentId.equals(otherCollLocPropsParentId)) {
                kioskCollLocPropsParentIdEquals = true;
            }
        
            return kioskCollectionIdEquals && kioskCollSharedPropsIdEquals && kioskCollLocPropsParentIdEquals;
        } 
    }
}
