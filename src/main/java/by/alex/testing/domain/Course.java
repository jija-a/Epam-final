package by.alex.testing.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Course extends Entity {

    /**
     * Course name.
     */
    private String name;

    /**
     * Course owner.
     *
     * @see User
     */
    private User owner;

    /**
     * Course students.
     *
     * @see User
     * @see UserCourseStatus
     */
    private Map<User, UserCourseStatus> students;

    /**
     * Course category.
     *
     * @see CourseCategory
     */
    private CourseCategory category;

    /**
     * Constructor.
     *
     * @param name    course title
     * @param owner    course owner
     * @param category course category
     * @see CourseCategory
     * @see User
     */
    public Course(final String name, final User owner,
                  final CourseCategory category) {
        this.name = name;
        this.owner = owner;
        this.category = category;
    }
}
