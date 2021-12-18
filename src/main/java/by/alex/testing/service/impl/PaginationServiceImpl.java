package by.alex.testing.service.impl;

import by.alex.testing.service.PaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaginationServiceImpl implements PaginationService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(PaginationServiceImpl.class);

    /**
     * {@link PaginationService} instance. Singleton pattern.
     */
    private static final PaginationService SERVICE =
            new PaginationServiceImpl();

    /**
     * @return {@link PaginationService} instance.
     */
    public static PaginationService getInstance() {
        return SERVICE;
    }

    @Override
    public final int defineStartNumber(final int page,
                                       final int recordsPerPage) {
        LOGGER.info("Defining start number, page: {}, rec: {}",
                page, recordsPerPage);
        int start = page - 1;
        if (page > 1) {
            start = start * recordsPerPage;
        }
        return start;
    }

    @Override
    public final int defineNumberOfPages(final int qty,
                                   final int recordsPerPage) {
        LOGGER.info("Defining number of pages, entity qty: {}, rec: {}",
                qty, recordsPerPage);
        return qty % recordsPerPage != 0
                ? qty / recordsPerPage + 1 : qty / recordsPerPage;
    }
}
