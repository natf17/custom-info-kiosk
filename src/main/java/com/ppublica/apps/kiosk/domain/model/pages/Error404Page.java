package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

/*
 * Represents an Error 404 Page with the following:
 *  - page title
 *  - error description
 *  - show redirect link boolean
 *  - a redirect link
 */
public class Error404Page extends KioskPage {
    @Id
    private Long id;

    private ErrorDescriptionField errorDescriptionField;
    private ShowRedirectLinkOptionField showRedirectLinkField;
    private RedirectLinkContainer redirectLinkContainer;

    private static final String ERROR_DESCR__FIELD_NAME = "ErrorDescription";
    private static final String SHOW_REDIRECT_LINK_OPTION_FIELD_NAME = "ShowRedirectLink";
    private static final String REDIRECT_LINK_CONTAINER_FIELD_NAME = "Redirect Link";

    // for use by repository classes ONLY
    @PersistenceCreator
    protected Error404Page(KioskPageInternals pageInternals, PageTitleField pageTitle, ErrorDescriptionField errorDescriptionField,
                            ShowRedirectLinkOptionField showRedirectLinkField, RedirectLinkContainer redirectLinkContainer) {
        super(pageInternals, pageTitle);
        
        this.errorDescriptionField = errorDescriptionField;
        this.showRedirectLinkField = showRedirectLinkField;
        this.redirectLinkContainer = redirectLinkContainer;
    }

    public Error404Page(Builder builder) {
        super(builder);

        this.errorDescriptionField = new ErrorDescriptionField(ERROR_DESCR__FIELD_NAME, builder.errorDescription);
        this.showRedirectLinkField = new ShowRedirectLinkOptionField(SHOW_REDIRECT_LINK_OPTION_FIELD_NAME, builder.showRedirectLink);
        this.redirectLinkContainer = builder.redirectLinkContainer;
    }

    public static class Builder extends KioskPage.Builder<Builder, Error404Page> {
        private String errorDescription;
        private boolean showRedirectLink;
        private String redirectUrl;
        private String redirectDisplayText;
        private String redirectDescription;
        private RedirectLinkContainer redirectLinkContainer;


        public Builder errorDescription(String errorDescription) {
            this.errorDescription = errorDescription;

            return self();
        }

        public Builder shouldShowRedirectLink(boolean showRedirectLink) {
            this.showRedirectLink = showRedirectLink;

            return self();
        }

        public Builder redirectUrl(String redirectUrl) {
            this.redirectLinkContainer = null;
            this.redirectUrl = redirectUrl;

            return self();
        }

        public Builder redirectDisplayText(String redirectDisplayText) {
            this.redirectLinkContainer = null;
            this.redirectDisplayText = redirectDisplayText;

            return self();
        }

        public Builder redirectDescription(String redirectDescription) {
            this.redirectLinkContainer = null;
            this.redirectDescription = redirectDescription;

            return self();
        }

        public Builder redirectLinkContainer(RedirectLinkContainer redirectLinkContainer) {
            this.redirectLinkContainer = redirectLinkContainer;            

            return self();
        }

        protected void validateAndPrepareChild() {


            if (errorDescription == null) {
                throw new RuntimeException("Invalid page build!");

            }
            
            if (this.redirectLinkContainer == null) {
                if (this.redirectUrl == null || this.redirectDisplayText == null || redirectDescription == null) {
                    throw new RuntimeException("Invalid page build!");

                }

                // build redirect link container
                this.redirectLinkContainer = new RedirectLinkContainer(REDIRECT_LINK_CONTAINER_FIELD_NAME, 
                                                this.redirectUrl,this.redirectDisplayText
                                                ,this.redirectDescription);
                
            }

            if (this.errorDescription == null) {
                throw new RuntimeException("Invalid page build!");                
            }

        }

        protected Error404Page buildChild() {
            return new Error404Page(this);
        }


        @Override
        protected Builder self() {
            return this;
        }

        
    }
    
}
