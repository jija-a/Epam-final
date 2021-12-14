package by.alex.testing.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class Entity implements BaseEntity {

    private Long id;

    protected Entity() {
    }

    protected Entity(Long id) {
        this.id = id;
    }
}
