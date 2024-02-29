package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.DonationsKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.donations.DonationInput;
import com.ppublica.apps.kiosk.service.util.converter.DonationInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.DonationViewsConverter;
import com.ppublica.apps.kiosk.service.views.donations.DonationAdminView;
import com.ppublica.apps.kiosk.service.views.donations.DonationView;

@Service
public class DonationsDataService extends LocalizedCollectionServiceBase<DonationsKioskCollectionAdapter, DonationsType, DonationsKioskCollectionAdapter.Builder>{
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @Autowired
    private DonationViewsConverter donationViewsConverter;

    @Autowired
    private DonationInputConverter donationInputConverter;

    @Autowired
    private AdapterBuilderGenerator<DonationsKioskCollectionAdapter.Builder> adapterBuilderGenerator;

    public List<DonationView> getDonations(String locale, String sort) {
        List<DonationView> donationViews = loadAdapters(CollectionType.DONATIONS, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(donation -> donationViewsConverter.buildView(donation))
                                                .collect(Collectors.toList());

        // TODO: sort

        return donationViews;
    }

    public List<DonationAdminView> getDonationsAdmin(String sort) {
        List<DonationAdminView> adminViews = loadListOfAdaptersList(CollectionType.DONATIONS, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(donationAdapters -> donationViewsConverter.buildAdminView(donationAdapters))
                                                .collect(Collectors.toList());

        return adminViews;
    }

    public Optional<DonationAdminView> getDonationAdmin(Long donationId) {
        List<DonationsKioskCollectionAdapter> adapters = loadAdapters(donationId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(donationViewsConverter.buildAdminView(adapters));
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public DonationAdminView createDonation(DonationInput data) {
        // donation input to Donations type
        List<? extends DonationsType> donations = donationInputConverter.toLocalizedDonations(data);

        List<DonationsKioskCollectionAdapter> newDonationAdapters = save(donations, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return donationViewsConverter.buildAdminView(newDonationAdapters);
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public DonationAdminView updateDonation(Long donationId, DonationInput data) {
        List<? extends DonationsType> donations = donationInputConverter.toLocalizedDonations(data);

        List<? extends DonationsType> updatedDonationAdapters = update(donations, donationId, collSharedPropsRepo, collLocalizedPropsRepo);

        return donationViewsConverter.buildAdminView(updatedDonationAdapters);
    }

    public void deleteDonation(Long donationId) {
        delete(donationId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    @Override
    protected DonationsKioskCollectionAdapter.Builder getAdapterBuilder() {
        return adapterBuilderGenerator.newAdapterBuilder();
    }
}
