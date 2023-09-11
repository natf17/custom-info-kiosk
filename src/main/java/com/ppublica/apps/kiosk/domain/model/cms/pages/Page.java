package com.ppublica.apps.kiosk.domain.model.cms.pages;

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


}
