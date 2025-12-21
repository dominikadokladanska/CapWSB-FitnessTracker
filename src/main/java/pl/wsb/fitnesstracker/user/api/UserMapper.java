package pl.wsb.fitnesstracker.user.api;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Converts a {@link User} entity to a full {@link UserDto}.
     *
     * @param user user entity
     * @return DTO with user data
     */

    public UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }
    /**
     * Converts a {@link User} entity to a simplified {@link UserSimpleDto}.
     *
     * @param user user entity
     * @return simplified DTO with basic user data
     */

    public UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * Converts a {@link UserDto} to a {@link User} entity.
     * Used when creating new users.
     *
     * @param userDto user data transfer object
     * @return new user entity created from the DTO
     */
    public User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );
    }

    /**
     * Updates an existing {@link User} entity using values from a DTO.
     *
     * @param userDto  DTO containing new values
     * @param user entity to be updated
     */
    public void updateEntityFromDto(UserDto userDto, User user) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthdate(userDto.birthdate());
        user.setEmail(userDto.email());
    }
}
