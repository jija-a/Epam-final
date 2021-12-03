package by.alex.testing.service.impl;

import by.alex.testing.service.PaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaginationServiceImpl implements PaginationService {

    private static final Logger logger =
            LoggerFactory.getLogger(PaginationServiceImpl.class);

    private static final PaginationServiceImpl instance = new PaginationServiceImpl();

    public static PaginationServiceImpl getInstance() {
        return instance;
    }

    @Override
    public int defineStartEntityNumber(int page, int pageLimit) {
        logger.info("Defining start number, page - {}, page limit - {}", page, pageLimit);
        int start = page - 1;
        if (page > 1) {
            start = start * pageLimit;
        }
        logger.info("Start number is: {}", start);
        return start;
    }

    @Override
    public int defineNumberOfPages(int entitiesQty, int pageLimit) {
        logger.info("Defining number of pages, entity qty - {}, pageLimit - {}", entitiesQty, pageLimit);
        int pages = entitiesQty % pageLimit != 0 ? entitiesQty / pageLimit + 1 : entitiesQty / pageLimit;
        logger.info("Number of pages is: {}", pages);
        return pages;
    }
}
