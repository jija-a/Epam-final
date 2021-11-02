package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class Entity implements Serializable {

    private Long id;

    protected Entity() {
    }

    protected Entity(Long id) {
        this.id = id;
    }
}
