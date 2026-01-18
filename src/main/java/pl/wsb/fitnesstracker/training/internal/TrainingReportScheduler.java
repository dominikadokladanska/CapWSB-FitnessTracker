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

import java.util.Calendar;
import java.util.Date;
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
        log.info("Rozpoczynam generowanie raportu tygodniowego...");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date oneWeekAgo = calendar.getTime();

        List<User> users = userProvider.findAllUsers();

        for (User user : users) {
            List<Training> allTrainings = trainingProvider.findTrainingsByUserId(user.getId());

            long weeklyCount = allTrainings.stream()
                    .filter(training -> training.getEndTime().after(oneWeekAgo))
                    .count();

            log.info("Użytkownik ID: {} ({}). Treningów w ost. tygodniu: {}",
                    user.getId(), user.getFirstName(), weeklyCount);

            if (weeklyCount > 0) {
                String reportContent = "Cześć " + user.getFirstName() + ",\n\n" +
                        "W ostatnim tygodniu wykonałeś " + weeklyCount + " treningów. Tak trzymaj!";

                EmailDto email = new EmailDto(
                        user.getEmail(),
                        "Raport Tygodniowy",
                        reportContent
                );
                emailSender.send(email);

            } else {
                String reportContent = "Cześć " + user.getFirstName() + ",\n\n" +
                        "W tym tygodniu nie odnotowaliśmy żadnego treningu.";

                EmailDto email = new EmailDto(
                        user.getEmail(),
                        "Raport Tygodniowy",
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