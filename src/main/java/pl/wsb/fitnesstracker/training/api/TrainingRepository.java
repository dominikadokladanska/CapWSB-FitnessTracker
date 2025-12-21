package pl.wsb.fitnesstracker.training.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    default List<Training> findAllByUserId(Long userId) {
        return findAll().stream()
                .filter(t -> t.getUser() != null && Objects.equals(t.getUser().getId(), userId))
                .toList();
    }
}
