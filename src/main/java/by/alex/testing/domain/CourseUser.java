package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CourseUser extends Entity{

    private Course course;
    private User user;
    private UserCourseStatus status;
    private Double rating;
}
