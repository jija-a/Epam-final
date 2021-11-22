package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Quiz extends Entity {

    private String title;
    private List<Question> questions;
    private Integer attempts;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer timeToAnswer;
    private Integer maxScore;
    private Long courseId;
}
