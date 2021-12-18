package by.alex.testing.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CourseUser implements BaseEntity {

    /**
     * {@link User} {@link Course}.
     */
    private Course course;

    /**
     * {@link Course} {@link User}.
     */
    private User user;

    /**
     * Course user status.
     */
    private UserCourseStatus status;

    /**
     * Course user rating.
     */
    private Double rating;

    /**
     * Course user attendance percent.
     */
    private Double attendancePercent;
}
