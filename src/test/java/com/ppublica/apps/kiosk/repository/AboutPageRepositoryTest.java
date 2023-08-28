package com.ppublica.apps.kiosk.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.pages.Image;

import graphql.Assert;

@DataJdbcTest
@TestPropertySource("classpath:test.properties")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AboutPageRepositoryTest {

    @Autowired
    AboutPageRepository repo;

    AboutPage pageEn;
    AboutPage pageSp;

    @BeforeEach
    public void setup() {
        pageEn = new AboutPage.Builder()
                            .pageTitle("sampleTitle")
                            .featureImage(new Image("sample_url", 1, 1))
                            .featureImageAltText("Sample image alt text")
                            .richDescription("Sample rich description")
                            .build();

        pageSp = new AboutPage.Builder()
                            .pageTitle("titulo")
                            .featureImage(new Image("direccion", 1, 1))
                            .featureImageAltText("texto de imagen")
                            .richDescription("descripcion de imagen")
                            .build();
    }

    @Test
    public void givenPageInternalsLocaleAbbrev_returns_pageId() {
        AboutPage result = repo.save(pageEn);

        List<Long> returnedPageIds =  repo.getAboutPageIdsforLocale("EN");
        
        Assertions.assertTrue(returnedPageIds.size() == 1);

    }

    @Test
    public void givenPageId_returnsPage() {
        AboutPage result = repo.save(pageEn);
        repo.save(pageSp);

        Optional<AboutPage> aboutPageOpt = repo.findById(result.getId());
        
        Assertions.assertTrue(aboutPageOpt.isPresent());

        AboutPage repoResult = aboutPageOpt.get();
        Assertions.assertEquals(result.getId(), repoResult.getId());



    }




    
}
