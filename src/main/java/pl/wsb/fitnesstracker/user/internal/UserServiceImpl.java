package pl.wsb.fitnesstracker.user.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    UserServiceImpl(final UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDto userDto) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID: " + id + " not found"));

        userMapper.updateEntityFromDto(userDto, existingUser); // tu używamy user.setFirstName(dto.firstName());

        return userRepository.save(existingUser);
    }


    public void deleteUser(Long userId) {
        log.info("Deleted User {}", userId);
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}