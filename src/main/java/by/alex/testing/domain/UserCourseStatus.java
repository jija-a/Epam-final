package by.alex.testing.domain;

import java.io.Serializable;

public enum UserCourseStatus implements Cloneable, Serializable {
    REQUESTED(0, "Requested"),
    ON_COURSE(1, "On course"),
    FINISHED(2, "Finished");

    private final int id;
    private final String name;

    UserCourseStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static UserCourseStatus resolveStatusById(int id) throws UnknownEntityException {
        for (UserCourseStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new UnknownEntityException("Id: " + id + " undefined in user course status");
    }
}
