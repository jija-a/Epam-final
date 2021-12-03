package by.alex.testing.domain;

public enum AttendanceStatus {
    NOT_PRESENT(0, "Not present"),
    ATTENDED(1, "Attended"),
    LATE(2, "Was late");

    private final int id;
    private final String name;

    AttendanceStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static AttendanceStatus resolveStatusById(int id) throws UnknownEntityException {
        for (AttendanceStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new UnknownEntityException("Id: " + id + " undefined in attendance status");
    }
}
