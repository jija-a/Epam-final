package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CourseUser{

    private final Course course;
    private final User user;
    private UserCourseStatus status;
}
