package pl.wsb.fitnesstracker.training.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    /**
     * Creates a new training controller.
     *
     * @param trainingService training service
     * @param trainingMapper  training DTO/entity mapper
     */
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    TrainingController(TrainingServiceImpl trainingService,
                       TrainingMapper trainingMapper) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
    }

    /**
     * Returns all trainings.
     *
     * @return list of trainings as {@link TrainingDto}
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Returns trainings for the given user.
     *
     * @param userId user identifier
     * @return list of trainings as {@link TrainingDto}
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUser(@PathVariable Long userId) {
        return trainingService.findTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
}