package by.alex.testing.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class Entity implements BaseEntity {

    /**
     * Entity identity.
     */
    private Long id;

    protected Entity() {
    }

    protected Entity(final Long id) {
        this.id = id;
    }
}
