# About

The objective of this project is to build a headless CMS that is flexible, simple, has multilanguage support, and doesn't need to be rebuilt when its data is updated. To demonstrate its capabilities, a kiosk app was built that uses all the features of the underlying CMS system. In the rest of this document, 'CMS' will refer to the headless CMS and 'kiosk' will refer to the app built on top of the headless CMS.

A CMS usually stores two main types of data: data for pages (text fields, images, buttons, ...) and data collections that require some kind of fast data manipulation like sorting and searching. For this reason, this CMS provides the `Page` and `SimpleCollectionType`/`DataCollectionType` abstractions.

The components of each are described below:

`Page`:
- id
- page type (a key to create different page types) 
- page name (to further subdivide within a page type)
- page title field (the page title)
- page internals (encapsulates internal settings and metadata such as page status, creation date, language, ...)
- field containers (these contain all fields in the page, and can be used to organize the content) 

`SimpleCollectionType`:
- id
- type (a key to create different collection types)
- collection name field (the collection name)
- collection internals (the collection type equivalent of page internals)
- fields (text, numeric, boolean, image)
- linked collection fields (to store a reference to other collections)

`DataCollectionType`:
Similar to `SimpleCollectionType`... description COMING SOON.

Each field has a type, name, and value. Since the name and value depend on the language of the Page or collection type, the type can be used to define and enforce the uniqueness of a field.

## Multi-language support

The language of the page/collection title and fields is represented by the locale id found in their respective `PageInternals/CollectionInternals` object. 

## How to use

A user-facing cms can be built on top of the cms described above. You can construct custom types and objects by composing the base cms objects. As long as your types can be transformed into a `Page` or a `SimpleCollectionType` or a `DataCollectionType`, you can use the corresponding repository class to persist to a database (currently only PostgreSQL). See below for an example. 

## An example

To test out the CMS, we built a custom info kiosk with a variety of pages, collection types, and field types. Two different techniques are used. Each kiosk page contains the logic to build a CMS page in its `buildPage()` method, and given a CMS page, you can build a kiosk page by using the corresponding kiosk page `Builder`. On the other hand, kiosk collection implementations use the Adapter Pattern so that they can operate in both "domains": the headless cms layer and the user-facing kiosk layer. 

The table below summarizes how all the pieces in the kiosk layer ultimately map to a headless cms representation:

Kiosk Pages -> CMS `Page`s
- AboutPage
- Error404Page
- EventsPage
- HomePage
- MapPage
- MenuPage

Kiosk Containers -> CMS `FieldContainer`s 
- AmenityContainer
- EventSectionContainer
- GeneralEventStringsContainer
- ImageContainer
- MapContainer
- MapViewConfigContainer
- MenuCategoryContainer
- MenuItem
- RedirectUrlContainer
- TapWidgetContainer

Kiosk Fields -> CMS `PageField`s
- KioskPageField<T>
- KioskCollectionField<T>

Kiosk Collections -> CMS `SimpleCollectionType`s/`DataCollectionType`s
- AmenityType/AmenityWithBaseAdapter/GenderAwareAmenityAdapter
- BathroomType/BathroomAdapter
- DonationType/DonationAdapter
- EventSeason/EventSeasonAdapter
- FirstAidType/FirstAidAdapter
- LocationType/LocationWithBaseAdapter
- WaterFountainType/WaterFountainAdapter

TODOS:
- determine usefulness of the isLocalizable prop
- verify that when not provided, the builder will generate a kiosk field with the correct isLocalizable value
- imporove ugly conversion in event season and location converters (e.g. Integer to Long numeric types)
- add validation for event season types
- handle batch locations and event seasons more efficiently
- add test for new cms shared props method (adapter tests)
- LocalizedFieldsProcessor processParent method should not expect list of builders to be empty
- the app should have a default locale and locale id
- transition converters to LocalizedFieldsProcessor
- test NonLocalizedCollectionServiceBase