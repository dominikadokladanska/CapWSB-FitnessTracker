package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrainingReportScheduler {

    private final UserProvider userProvider;
    private final EmailSender emailSender;
    private final TrainingProvider trainingProvider;

    @Scheduled(cron = "0/10 * * * * *")
    public void generateTotalReport() {
        log.info("Rozpoczynam generowanie raportu...");

        List<User> users = userProvider.findAllUsers();

        for (User user : users) {

            List<Training> userTrainings = trainingProvider.findTrainingsByUserId(user.getId());

            long count = userTrainings.size();
            log.info("Sprawdzam użytkownika ID: {} ({}). Znaleziono treningów: {}",
                    user.getId(), user.getFirstName(), count);

            if (count > 0) {
                String reportContent = "Cześć " + user.getFirstName() + ",\n\n" +
                        "Łącznie masz zarejestrowanych " + count + " treningów.";

                EmailDto email = new EmailDto(
                        user.getEmail(),
                        "Raport Treningowy",
                        reportContent
                );
                emailSender.send(email);
            } else {
                String reportContent = "Cześć " + user.getFirstName() + ",\n\n" +
                        "W tym tygodniu nie wykonałeś/wykonałaś żadnego treningu";

                EmailDto email = new EmailDto(
                        user.getEmail(),
                        "Raport Treningowy",
                        reportContent
                );
                emailSender.send(email);
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}