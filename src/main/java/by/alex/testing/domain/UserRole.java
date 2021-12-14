package by.alex.testing.domain;

public enum UserRole implements BaseEntity {
    ADMIN(0, "admin"),
    TEACHER(1, "teacher"),
    STUDENT(2, "student"),
    GUEST(3, "guest");

    private final int id;
    private final String name;

    UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public static UserRole resolveRoleById(int id) throws UnknownEntityException {
        for (UserRole role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new UnknownEntityException("Id: " + id + " undefined in user role");
    }
}
