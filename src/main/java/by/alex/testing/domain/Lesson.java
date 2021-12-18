package by.alex.testing.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    /**
     * Lesson title.
     */
    private String title;

    /**
     * Lesson start date.
     */
    private LocalDateTime startDate;

    /**
     * Lesson end date.
     */
    private LocalDateTime endDate;

    /**
     * Lesson teacher.
     */
    private User teacher;

    /**
     * Lesson attendances.
     *
     * @see Attendance
     */
    private List<Attendance> attendances;

    /**
     * The {@link Course} on which was current lesson.
     * Entity contains this field because when it needs to be created in DAO
     * layer, {@link Course} id should be provided.
     *
     * @see by.alex.testing.dao.LessonDao
     */
    private final Long courseId;
}
