package com.ppublica.apps.kiosk.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;


@JdbcTest
@TestPropertySource("classpath:test.properties")
public class PageRespositoryImplTest {
    @Autowired
    AboutPageRepository repo;

    @Autowired
    JdbcTemplate template;

    @Test
    public void givenNewPageWithNoNesting_saveAndRead_success() {


    }
    
}
