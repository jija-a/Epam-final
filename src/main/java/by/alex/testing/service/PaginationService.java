package by.alex.testing.service;

public interface PaginationService {

    /**
     * Method to define start limit DB number.
     *
     * @param page        current page
     * @param entitiesQty all entities quantity
     * @return {@link Integer} DB limit start number
     */
    int defineStartNumber(int page, int entitiesQty);

    /**
     * Method to define number of pages.
     *
     * @param entitiesQty all entities quantity
     * @param pageLimit   entities quantity on page
     * @return {@link Integer} number of pages
     */
    int defineNumberOfPages(int entitiesQty, int pageLimit);
}
