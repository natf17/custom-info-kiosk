package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.test.context.TestPropertySource;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.pages.Image;

@DataJdbcTest
@TestPropertySource("classpath:test.properties")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AboutPageRepositoryTest {

    @Autowired
    AboutPageRepository repo;

    @Autowired
    JdbcTemplate template;


    AboutPage pageEn;
    AboutPage pageSp;

    @BeforeEach
    public void setup() {

        
        Long enLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "EN").get(0);
        Long esLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "ES").get(0);

        

        pageEn = new AboutPage.Builder()
                            .withLocaleId(enLocaleId)
                            .pageTitle("sampleTitle")
                            .featureImage(new Image("sample_url", 1, 1))
                            .featureImageAltText("Sample image alt text")
                            .richDescription("Sample rich description")
                            .build();

        pageSp = new AboutPage.Builder()
                            .withLocaleId(esLocaleId)
                            .pageTitle("titulo")
                            .featureImage(new Image("direccion", 1, 1))
                            .featureImageAltText("texto de imagen")
                            .richDescription("descripcion de imagen")
                            .build();
    }

    @Test
    public void givenPageInternalsLocaleAbbrev_returns_pageId() {
        AboutPage result = repo.save(pageEn);

        List<Long> returnedPageIds = repo.getAboutPageIdsforLocale("EN");
        
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

    class IdRowMapper implements RowMapper<Long>{
    
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        
            return rs.getLong("id");

        }
    }    




    
}
