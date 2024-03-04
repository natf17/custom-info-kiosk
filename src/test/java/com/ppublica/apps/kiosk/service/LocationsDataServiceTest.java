package com.ppublica.apps.kiosk.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.LocationKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.util.LocationKey;
import com.ppublica.apps.kiosk.service.util.converter.LocationInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.LocationViewsConverter;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.locations.LocationAdminView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={LocationsDataService.class})
public class LocationsDataServiceTest {
    @MockBean
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @MockBean
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @MockBean
    private LocationViewsConverter locationViewsConverter;

    @MockBean
    private LocationInputConverter locationInputConverter;

    @MockBean
    private AdapterBuilderGenerator<LocationKioskCollectionAdapter.Builder> adapterBuilderGenerator;

    @Autowired
    private LocationsDataService locationsDataService;

    LocationAdminView location1AdminView;
    LocationAdminView location2AdminView;
    LocalizedView<MapImage> map1ImageEn;
    LocalizedView<MapImage> map1ImageEs;
    LocalizedView<MapImage> map2ImageEn;
    LocalizedView<MapImage> map2ImageEs;


    @BeforeEach
    public void setup() {
        map1ImageEn = new LocalizedView<>("en", new MapImage(1, 2, "url"));
        map1ImageEs = new LocalizedView<>("es", new MapImage(1, 2, "url_es"));
        location1AdminView = new LocationAdminView("1",
                                                    List.of(new LocalizedField("en", "fullName_1"), new LocalizedField("es", "fullName_1_es")), 
                                                    3, 
                                                    List.of(new LocalizedField("en", "levelName_1"), new LocalizedField("es", "levelName_1_es")), 
                                                    List.of(map1ImageEn, map1ImageEs));

        map2ImageEn = new LocalizedView<>("en", new MapImage(3, 4, "url2"));
        map2ImageEs = new LocalizedView<>("es", new MapImage(3, 4, "url2_es"));
        location2AdminView = new LocationAdminView("2",
                                                    List.of(new LocalizedField("en", "fullName_2"), new LocalizedField("es", "fullName_2_es")), 
                                                    3, 
                                                    List.of(new LocalizedField("en", "levelName_2"), new LocalizedField("es", "levelName_2_es")), 
                                                    List.of(map2ImageEn, map2ImageEs));
    }

    @Test
    public void GET_batchLocations_returns_map() {

        // set up mock
        CollectionSharedProperties sharedProps1 = new CollectionSharedPropertiesImpl.Builder("LOCATION")
                                                            .withId(1L)
                                                            .build();
        CollectionLocalizedProperties locProps1 = new CollectionLocalizedPropertiesImpl.Builder(1L).withId(1L).kioskLocaleId(1L).build();
        CollectionLocalizedProperties locProps1_2 = new CollectionLocalizedPropertiesImpl.Builder(1L).kioskLocaleId(2L).build();


        CollectionSharedProperties sharedProps2 = new CollectionSharedPropertiesImpl.Builder("LOCATION")
                                                            .withId(2L)
                                                            .build();
        
        CollectionLocalizedProperties locProps2 = new CollectionLocalizedPropertiesImpl.Builder(2L).withId(2L).kioskLocaleId(3L).build();
        CollectionLocalizedProperties locProps2_2 = new CollectionLocalizedPropertiesImpl.Builder(2L).kioskLocaleId(4L).build();

    
        when(collSharedPropsRepo.findById(1L)).thenReturn(sharedProps1);
        when(collSharedPropsRepo.findById(2L)).thenReturn(sharedProps2);
        when(collLocalizedPropsRepo.findByParentCollectionId(1L)).thenReturn(List.of(locProps1, locProps1_2));
        when(collLocalizedPropsRepo.findByParentCollectionId(2L)).thenReturn(List.of(locProps2, locProps2_2));
        when(adapterBuilderGenerator.newAdapterBuilder()).thenReturn(new TestAdapterBuilder());
        when(locationViewsConverter.buildAdminView(eq(List.of(
                                        new TestLocationKioskCollectionAdapter(null, 1L, 1L, 1L),
                                        new TestLocationKioskCollectionAdapter(null, 1L, 2L, 1L)
                                    )))
            ).thenReturn(location1AdminView);
        when(locationViewsConverter.buildAdminView(eq(List.of(
                                        new TestLocationKioskCollectionAdapter(null, 2L, 3L, 2L),
                                        new TestLocationKioskCollectionAdapter(null, 2L, 4L, 2L)
                                        )))
            ).thenReturn(location2AdminView); 
        
        Map<LocationKey, LocationView> views = locationsDataService.getBatchLocations(Set.of(new LocationKey(1L, "es"), new LocationKey(2L, "es")));
        Assertions.assertEquals(2, views.size());

        Assertions.assertNotNull(views.get(new LocationKey(1L, "es")));
        Assertions.assertNotNull(views.get(new LocationKey(2L, "es")));
        Assertions.assertEquals(new LocationView("1", "fullName_1_es", 3, "levelName_1_es", map1ImageEs.value()), views.get(new LocationKey(1L, "es")));
        Assertions.assertEquals(new LocationView("2", "fullName_2_es", 3, "levelName_2_es", map2ImageEs.value()), views.get(new LocationKey(2L, "es")));

    }








    class TestAdapterBuilder extends LocationKioskCollectionAdapter.Builder {

        // return any LocationKioskCollectionAdapter
        @Override
        protected LocationKioskCollectionAdapter buildChild() {
            Long kioskCollectionId = this.kioskCollection != null ? this.kioskCollection.collectionId() : null;
            Long collSharedPropsId = this.sharedCmsPiece != null ? this.sharedCmsPiece.id() : null;
            Long collLocPropsparentId = this.localizedCmsPiece != null ? this.localizedCmsPiece.parentId() : null;
            Long collLocPropsId = this.localizedCmsPiece != null ? this.localizedCmsPiece.locId() : null;

            return new TestLocationKioskCollectionAdapter(kioskCollectionId, collSharedPropsId, collLocPropsId, collLocPropsparentId);
        }
    }

    class TestLocationKioskCollectionAdapter extends LocationKioskCollectionAdapter {
        Long kioskCollectionId;
        Long collSharedPropsId;
        Long collLocPropsParentId;
        Long collLocPropsId;

        public TestLocationKioskCollectionAdapter(Long kioskCollectionId, Long collSharedPropsId, Long collLocPropsId, Long collLocPropsParentId) {
            super(null, null, null, null);
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
            if(!(other instanceof TestLocationKioskCollectionAdapter)) {
                return false;
            }
            TestLocationKioskCollectionAdapter otherAdapt = (TestLocationKioskCollectionAdapter) other;
            
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
