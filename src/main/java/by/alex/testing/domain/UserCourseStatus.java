package by.alex.testing.domain;

public enum UserCourseStatus implements BaseEntity {

    /**
     * {@link User} status when he signs on course.
     */
    REQUESTED(0, "Requested"),

    /**
     * {@link User} status when teacher accept student request.
     */
    ON_COURSE(1, "On course"),

    /**
     * {@link User} status when he is not related to course.
     * Needed to find all available to sign {@link Course}'s for
     * {@link User}
     */
    NOT_RELATED(2, "Not related");

    /**
     * Entity identity.
     */
    private final int id;

    /**
     * Status that see {@link User}.
     */
    private final String name;

    UserCourseStatus(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return {@link Long} id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return {@link String} name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Method resolves status by id in DB.
     *
     * @param id identity in DB
     * @return resolved {@link UserCourseStatus}
     * @throws UnknownEntityException if such entity does not exist
     */
    public static UserCourseStatus resolveStatusById(final int id)
            throws UnknownEntityException {
        for (UserCourseStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new UnknownEntityException("Id: " + id
                + " undefined in user course status");
    }
}
