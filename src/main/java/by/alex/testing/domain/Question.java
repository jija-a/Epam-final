package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Question extends Entity {

    private String title;
    private String body;
    private QuestionType type;
    private List<Answer> answers;
    private Integer points;
    private Long testId;
}
