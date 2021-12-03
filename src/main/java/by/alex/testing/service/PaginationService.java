package by.alex.testing.service;

public interface PaginationService {

    int defineStartEntityNumber(int page, int entitiesQty);

    int defineNumberOfPages(int entitiesQty, int pageLimit);
}
