package by.alex.testing.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends Entity {

    private String login;
    private String firstName;
    private String lastName;
    private char[] password;
    private UserRole role;
}
