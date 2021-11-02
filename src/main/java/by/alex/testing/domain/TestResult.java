package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestResult extends Entity {

    private User user;
    private Test test;
    private Map<Answer, Boolean> answers;
    private Double percent;
    private Date testStarted;
    private Date testEnded;
}
