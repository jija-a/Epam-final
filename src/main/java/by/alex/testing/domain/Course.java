package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Course extends Entity {

    private String name;
    private User owner;
    private Map<User, UserCourseStatus> students;
    private List<Test> tests;
    private CourseCategory category;
}
