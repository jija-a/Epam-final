package by.alex.testing.domain;

import java.io.Serializable;

public enum UserCourseStatus implements Cloneable, Serializable {
    REQUESTED(0),
    ON_COURSE(1),
    FINISHED(2);

    private final int id;

    UserCourseStatus(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static UserCourseStatus resolveStatusById(int id) {
        for (UserCourseStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new RuntimeException();
    }
}
