package by.clevertec.dto;

import static by.clevertec.util.Constants.Attributes.PHONE_PATTERN;
import static by.clevertec.util.Constants.Messages.ENTER_PHONE_NUMBER;
import static by.clevertec.util.Constants.Messages.INCORRECT_EMAIL_FORMAT;
import static by.clevertec.util.Constants.Messages.NAME_SHOULD_NOT_BE_MORE_30_CHARACTER;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;
    @NotNull
    @Size(min = 1, max = 30, message = NAME_SHOULD_NOT_BE_MORE_30_CHARACTER)
    private String name;
    @Email(message = INCORRECT_EMAIL_FORMAT)
    private String email;
    @Pattern(regexp = PHONE_PATTERN, message = ENTER_PHONE_NUMBER)
    private String phoneNumber;
}
