package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
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
    private Date startDate;
    private Date endDate;
    private Integer timeToAnswer;
    private Integer maxScore;
    private Long courseId;
}
