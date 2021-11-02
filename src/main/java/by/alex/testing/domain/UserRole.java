package by.alex.testing.domain;

public enum UserRole {
    ADMIN(0, "admin"),
    TEACHER(1, "teacher"),
    STUDENT(2, "student");

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

    public static UserRole resolveRoleById(int id) {
        for (UserRole role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new RuntimeException();
    }
}
