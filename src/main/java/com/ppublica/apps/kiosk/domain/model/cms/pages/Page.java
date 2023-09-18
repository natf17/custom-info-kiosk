package com.ppublica.apps.kiosk.domain.model.cms.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Page {

    private Long id;

    private String pageType;
    private String pageName;

    private PageInternals pageInternals;

    private PageTitleField pageTitleField;
    
    private List<FieldContainer> fieldContainers;

    public Page(Long id, String pageType, String pageName, PageInternals pageInternals, PageTitleField pageTitleField, List<FieldContainer> fieldContainers) {
        this.id = id;
        this.pageType = pageType;
        this.pageName = pageName;
        this.pageInternals = pageInternals;
        this.pageTitleField = pageTitleField;
        this.fieldContainers = fieldContainers;
    }
    
    public Page(String pageType, String pageName, PageInternals pageInternals, PageTitleField pageTitleField, List<FieldContainer> fieldContainers) {
        this(null, pageType, pageName, pageInternals, pageTitleField, fieldContainers);
    }

    public Long getId() {
        return this.id;
    }

    public String getPageType() {
        return this.pageType;
    }

    public String getPageName() {
        return this.pageName;
    }

    public PageInternals getPageInternals() {
        return this.pageInternals;
    }

    public PageTitleField getPageTitleField() {
        return this.pageTitleField;
    }

    public List<FieldContainer> getFieldContainers() {
        return this.fieldContainers;
    }

    public Page withId(Long id) {
        return new Page(id, this.pageType, this.pageName, this.pageInternals, this.pageTitleField, this.fieldContainers);
    }

    public Page withTitleField(PageTitleField pageTitleField) {
        return new Page(id, this.pageType, this.pageName, this.pageInternals, pageTitleField, fieldContainers);
    }

    public Page withFieldContainers(List<FieldContainer> fieldContainers) {
        return new Page(id, this.pageType, this.pageName, this.pageInternals, this.pageTitleField, fieldContainers);
    }

    public static class Builder {
        private Long id;

        private String pageType;
        private String pageName;

        private PageInternals pageInternals;
        private Long kioskLocaleId;

        private PageTitleField pageTitleField;
        private String title;
        
        private List<FieldContainer> fieldContainers;


        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder pageType(String pageType) {
            this.pageType = pageType;
            return this;
        }

        public Builder pageName(String pageName) {
            this.pageName = pageName;
            return this;
        }

        public Builder pageInternals(PageInternals pageInternals) {
            this.pageInternals = pageInternals;
            return this;
        }

        public Builder kioskLocaleId(Long kioskLocaleId) {
            this.kioskLocaleId = kioskLocaleId;
            return this;
        }

        public Builder titleField(PageTitleField pageTitleField) {
            this.pageTitleField = pageTitleField;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder fieldContainers(List<FieldContainer> fieldContainers) {
            this.fieldContainers = fieldContainers;
            return this;
        }

        public Page build() {

            if(pageInternals == null) {
                if(kioskLocaleId == null) {
                    throw new RuntimeException("The kioskLocaleId is required");
                }

                pageInternals = new PageInternals(kioskLocaleId, PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
            }

            if(pageTitleField == null) {
                if(title == null) {
                    throw new RuntimeException("A page title is required");
                }
                pageTitleField = new PageTitleField("page_title", title);
            }

            if(fieldContainers == null) {
                fieldContainers = new ArrayList<>();
            }

            return new Page(id, pageType, pageName, pageInternals, pageTitleField, fieldContainers);
        }

    }


}
