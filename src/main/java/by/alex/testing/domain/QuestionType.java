package by.alex.testing.domain;

import java.io.Serializable;

public enum QuestionType implements Cloneable, Serializable {
    YES_NO(1, "Yes/No"),
    MULTIPLE(2, "Multiple"),
    SHORT(3, "Short"),
    CHOICE(4, "Choice");

    private final int id;
    private final String name;

    QuestionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public static QuestionType resolveTypeById(int id) {
        for (QuestionType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new RuntimeException();
    }
}
