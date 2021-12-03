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
public class Lesson extends Entity {

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private User teacher;
    private List<Attendance> attendances;

    private final Long courseId;
}
