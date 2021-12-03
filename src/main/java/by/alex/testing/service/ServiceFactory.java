package by.alex.testing.service;

import by.alex.testing.service.impl.*;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private ServiceFactory() {
    }

    public CommonService getCommonService() {
        return CommonServiceImpl.getInstance();
    }

    public TeacherService getTeacherService() {
        return TeacherServiceImpl.getInstance();
    }

    public PaginationService getPaginationService() {
        return PaginationServiceImpl.getInstance();
    }

    public StudentService getStudentService() {
        return StudentServiceImpl.getInstance();
    }

    public AdminService getAdminService() {
        return AdminServiceImpl.getInstance();
    }
}
