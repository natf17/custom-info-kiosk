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
    private ToCmsCollectionConverter toCmsConverter = new ToCmsCollectionConverter();

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

    public BathroomType save(BathroomType bathroom) {
        SimpleCollectionType savedBathroom = repo.saveInstance(new BathroomAdapter(bathroom));

        return new BathroomAdapter(savedBathroom);
    }

    public WaterFountainType save(WaterFountainType waterFountain) {
        SimpleCollectionType savedWaterFountain = repo.saveInstance(new WaterFountainAdapter(waterFountain));

        return new WaterFountainAdapter(savedWaterFountain);
    }

    public DonationType save(DonationType donation) {
        SimpleCollectionType savedDonation = repo.saveInstance(new DonationAdapter(donation));

        return new DonationAdapter(savedDonation);
    }

    public FirstAidType save(FirstAidType firstAid) {
        SimpleCollectionType savedFirstAid =  repo.saveInstance(new FirstAidAdapter(firstAid));

        return new FirstAidAdapter(savedFirstAid);
    }

    public BathroomType update(Long id, BathroomType bathroom) {
        SimpleCollectionType savedBathroom = repo.updateInstance(id, new BathroomAdapter(bathroom));

        return new BathroomAdapter(savedBathroom);
    }

    public WaterFountainType update(Long id, WaterFountainType waterFountain) {
        SimpleCollectionType savedWaterFountain = repo.updateInstance(id, new WaterFountainAdapter(waterFountain));

        return new WaterFountainAdapter(savedWaterFountain);
    }

    public DonationType update(Long id, DonationType donation) {
        SimpleCollectionType savedDonation = repo.updateInstance(id, new DonationAdapter(donation));

        return new DonationAdapter(savedDonation);
    }

    public FirstAidType update(Long id, FirstAidType firstAid) {    
        SimpleCollectionType savedFirstAid = repo.updateInstance(id, new FirstAidAdapter(firstAid));

        return new FirstAidAdapter(savedFirstAid);
    } 

    public void deleteAllBathroomInstancesOfLocale(String locale) {
        repo.deleteCollectionTypeInstancesWithLocale(toCmsConverter.toType(CollectionTypeName.BATHROOM), locale);
    }

    public void deleteAllWateFountainInstancesOfLocale(String locale) {
        repo.deleteCollectionTypeInstancesWithLocale(toCmsConverter.toType(CollectionTypeName.WATER_FOUNTAIN), locale);
    }

    public void deleteAllDonationInstancesOfLocale(String locale) {
        repo.deleteCollectionTypeInstancesWithLocale(toCmsConverter.toType(CollectionTypeName.DONATIONS), locale);
    }

    public void deleteAllFirstAidInstancesOfLocale(String locale) {
        repo.deleteCollectionTypeInstancesWithLocale(toCmsConverter.toType(CollectionTypeName.FIRST_AID), locale);
    }

    public void deleteCollectionInstance(Long id) {
        repo.deleteCollectionInstance(id);
    }



    
    
}
