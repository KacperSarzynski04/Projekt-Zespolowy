/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.reminder;

import lombok.SneakyThrows;
import org.hibernate.annotations.common.reflection.XProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.repositories.TrainingRepository;
import pl.edu.pwr.app.repositories.UserRepository;
import pl.edu.pwr.app.service.MailService;
import pl.edu.pwr.app.service.UserService;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.awt.event.PaintEvent;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class Reminder {

    private TrainingRepository trainingRepository;
    private MailService mailService;

    @Autowired
    public Reminder(TrainingRepository trainingRepository, MailService mailService){
        this.trainingRepository = trainingRepository;
        this.mailService = mailService;
    }

    @Scheduled(cron = "0 00 20 ? * *")
    public void sendMail() throws MessagingException {
        for(Training training : trainingRepository.findAll()){
            Period period = Period.between(training.getDate(), LocalDate.now());
            if(period.getDays() == 1){
                mailService.sendMail("adxyzmin4@gmail.com", "Przypomnienie!", "Szkolenie o nazwie: " + training.getTopic()
                        + "\nodbędzie się: " + training.getDate() + " o godzinie: " + training.getTime() , false);
            }
        }
    }
}
