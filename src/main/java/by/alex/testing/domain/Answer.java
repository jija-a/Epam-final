package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Answer extends Entity {

    private String title;
    private Boolean isRight;
    private Double percent;
    //todo javadoc
    private Long questionId;
}
