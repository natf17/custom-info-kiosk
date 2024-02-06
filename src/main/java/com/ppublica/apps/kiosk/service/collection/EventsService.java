package com.ppublica.apps.kiosk.service.collection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.collection.DefaultEventSeason;
import com.ppublica.apps.kiosk.repository.collection.DataCollectionTypeRepository;

public class EventsService {

    @Autowired
    private DataCollectionTypeRepository repo;

    public List<DefaultEventSeason> getREGSeasons(String locale) {
        return null;
        /*
        List<DataCollectionType> regSeasonCms = repo.findByCollectionTypeAndLocale(toCmsConverter.toType(EventTypeName.REG), locale);
        
        List<DefaultEventSeason> regSeasons = regSeasonCms.stream()
                                                    .map(i -> new REGSeasonAdapter(i))
                                                    .collect(Collectors.toList());
        return regSeasons; */
    }

    
}
