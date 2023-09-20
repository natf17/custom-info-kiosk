package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;

abstract class KioskPage {
    public static final String PAGE_TITLE_FIELD_NAME = "PageTitle";

    //public <B extends Builder<B,M>, M extends KioskPage> KioskPage(Builder<B, M> builder) { }

    // getters

    public String getPageType() {
        return getPageRep().getPageType();
    }

    public String getPageTitle() {
        return getPageRep().getPageTitleField().getFieldValue();
    }

    protected abstract Page getPageRep();



    // builder

    static abstract class Builder<B extends Builder<B, M>, M extends KioskPage> {
        protected String pageTitle;
        protected PageStatus pageStatus;
        protected LocalDate createdOn;
        protected LocalDateTime lastModified;
        protected Long localeId;
        protected KioskPageType kioskPageType;
        protected String pageName;

        protected Builder(KioskPageType kioskPageType, String pageName) {
            this.kioskPageType = kioskPageType;
            this.pageName = pageName;
        }
        

        public B pageName(String pageName) {
            this.pageName = pageName;
            return self();
        }

        public B pageStatus(PageStatus pageStatus) {
            this.pageStatus = pageStatus;
            return self();
        }

        public B pageTitle(String title) {
            this.pageTitle = title;
            return self();
        }

        public B pageType(KioskPageType kioskPageType) {
            this.kioskPageType = kioskPageType;
            return self();
        }

        public B createdOn(LocalDate createdOn) {
            this.createdOn = createdOn;
            return self();
        }

        public B lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return self();
        }

        public B withLocaleId(Long localeId) {
            this.localeId = localeId;
            return self();
        }

        public M build() {
            validateAndPrepare();
            
            Page.Builder pageRepBuilder = new Page.Builder().pageInternals(new PageInternals(localeId, pageStatus, createdOn, lastModified))
                                .titleField(new PageTitleField(PAGE_TITLE_FIELD_NAME, pageTitle))
                                .pageType(kioskPageType.toString())
                                .pageName(pageName);

            
            return buildChild(pageRepBuilder);
        }

        private void validateAndPrepare() {
            if(this.kioskPageType == null) {
                throw new RuntimeException("The page type is required");
            }

            if(this.pageName == null) {
                throw new RuntimeException("The page name is required");
            }

            if(this.pageStatus == null) {
                throw new RuntimeException("PageStatus is required");
            }

            if(this.createdOn == null) {
                throw new RuntimeException("CreatedOn is required");
            }

            if(this.lastModified == null) {
                throw new RuntimeException("LastModified is required");
            }

            LocalDateTime createdOnTime = this.createdOn.atStartOfDay();
            if(createdOnTime.isAfter(this.lastModified)) {
                throw new RuntimeException("CreatedOn cannot be after lastModified");
            }

            validateAndPrepareChild();

        }

        protected abstract void validateAndPrepareChild();

        protected abstract M buildChild(Page.Builder pageRepBuilder);

        protected abstract B self();        

    }

}
