package com.ppublica.apps.kiosk.service.collection;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.BathroomType;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.DonationType;
import com.ppublica.apps.kiosk.domain.model.collection.FirstAidType;
import com.ppublica.apps.kiosk.domain.model.collection.WaterFountainType;
import com.ppublica.apps.kiosk.domain.model.collection.adapter.BathroomAdapter;
import com.ppublica.apps.kiosk.domain.model.collection.adapter.DonationAdapter;
import com.ppublica.apps.kiosk.domain.model.collection.adapter.FirstAidAdapter;
import com.ppublica.apps.kiosk.domain.model.collection.adapter.ToCmsCollectionConverter;
import com.ppublica.apps.kiosk.domain.model.collection.adapter.WaterFountainAdapter;
import com.ppublica.apps.kiosk.repository.collection.SimpleCollectionTypeRepository;

public class AmenityService {
    ToCmsCollectionConverter toCmsConverter = new ToCmsCollectionConverter();

    @Autowired
    private SimpleCollectionTypeRepository repo;

    public List<BathroomType> getBathrooms(String locale) {
        List<SimpleCollectionType> bathroomsCms = repo.findByCollectionTypeAndLocale(toCmsConverter.toType(CollectionTypeName.BATHROOM), locale);
        
        List<BathroomType> bathrooms = bathroomsCms.stream()
                                                    .map(i -> new BathroomAdapter(i))
                                                    .collect(Collectors.toList());
        return bathrooms;
    }

    public List<WaterFountainType> getWaterFountains(String locale) {
        List<SimpleCollectionType> waterFountainsCms = repo.findByCollectionTypeAndLocale(toCmsConverter.toType(CollectionTypeName.WATER_FOUNTAIN), locale);
        
        List<WaterFountainType> waterFountains = waterFountainsCms.stream()
                                                    .map(i -> new WaterFountainAdapter(i))
                                                    .collect(Collectors.toList());
        return waterFountains;
    }

    public List<DonationType> getDonations(String locale) {
        List<SimpleCollectionType> donationsCms = repo.findByCollectionTypeAndLocale(toCmsConverter.toType(CollectionTypeName.DONATIONS), locale);
        
        List<DonationType> donations = donationsCms.stream()
                                                    .map(i -> new DonationAdapter(i))
                                                    .collect(Collectors.toList());
        return donations;
    }

    public List<FirstAidType> getFirstAids(String locale) {
        List<SimpleCollectionType> firstAidsCms = repo.findByCollectionTypeAndLocale(toCmsConverter.toType(CollectionTypeName.DONATIONS), locale);
        
        List<FirstAidType> firstAids = firstAidsCms.stream()
                                                    .map(i -> new FirstAidAdapter(i))
                                                    .collect(Collectors.toList());
        return firstAids;
    }

    
    
}
