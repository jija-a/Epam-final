package by.alex.testing.domain;

public enum AttendanceStatus implements BaseEntity {
    /**
     * Status when {@link User} not present on {@link Lesson}.
     */
    NOT_PRESENT(0, "Not present"),

    /**
     * Status when {@link User} present on {@link Lesson}.
     */
    PRESENT(1, "Present");

    /**
     * Entity identity.
     */
    private final int id;

    /**
     * Name that shows to {@link User}.
     */
    private final String name;

    AttendanceStatus(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return {@link Long} entity identity.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return {@link String} entity name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method resolves status by id in DB.
     *
     * @param id identity in DB
     * @return resolved {@link AttendanceStatus}
     * @throws UnknownEntityException if such entity does not exist
     */
    public static AttendanceStatus resolveStatusById(final int id)
            throws UnknownEntityException {
        for (AttendanceStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new UnknownEntityException("Id: " + id
                + " undefined in attendance status");
    }
}
