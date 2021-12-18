package by.alex.testing.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Attendance extends Entity {

    /**
     * {@link User} student whose attendance is represented by this entity.
     */
    private User student;

    /**
     * Student mark.
     */
    private Integer mark;

    /**
     * Student attendance status.
     *
     * @see AttendanceStatus
     */
    private AttendanceStatus status;

    /**
     * The {@link Lesson} in which the student received the given attendance.
     * Entity contains this field because when it needs to be created in DAO
     * layer, {@link Lesson} id should be provided.
     *
     * @see by.alex.testing.dao.AttendanceDao
     */
    private Long lessonId;
}
