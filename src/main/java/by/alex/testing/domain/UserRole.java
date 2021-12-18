package by.alex.testing.domain;

public enum UserRole implements BaseEntity {
    /**
     * {@link User} role, administrator.
     */
    ADMIN(0, "admin"),

    /**
     * {@link User} role, teacher.
     */
    TEACHER(1, "teacher"),

    /**
     * {@link User} role, student.
     */
    STUDENT(2, "student"),

    /**
     * {@link User} role, guest.
     */
    GUEST(3, "guest");

    /**
     * Entity identity.
     */
    private final int id;

    /**
     * Role name that sees {@link User}.
     */
    private final String name;

    UserRole(final int id, final String name) {
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
     * @return resolved {@link UserRole}
     * @throws UnknownEntityException if such entity does not exist
     */
    public static UserRole resolveRoleById(final int id)
            throws UnknownEntityException {
        for (UserRole role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new UnknownEntityException("Id: " + id
                + " undefined in user role");
    }
}
