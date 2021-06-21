/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.service.impl;

import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pwr.app.exception.domain.IncorrectFileTypeException;
import pl.edu.pwr.app.exception.domain.NotAnImageFileException;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.repositories.TrainingRepository;
import static pl.edu.pwr.app.constant.FileConstants.*;
import pl.edu.pwr.app.constant.FileConstants.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.http.MediaType.*;

@Service
public class TrainingService {

    private TrainingRepository trainingRepository;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    public Training addNewTraining(String topic, String description,
                                   String trainer, int durationInMinutes,
                                   MultipartFile trainingImage, MultipartFile trainingFile) throws IOException, NotAnImageFileException, IncorrectFileTypeException {
        Training tempTraining = new Training(topic, description, trainer, durationInMinutes, null, null);
        Training training = trainingRepository.saveAndFlush(tempTraining);
        saveTrainingImage(training, trainingImage);
        saveTrainingFile(training, trainingFile);
        LOGGER.info("New picture added");

        return training;
    }
    public Training updateTraining(long id, String topic, String description,
                                   String trainer, int durationInMinutes,
                                   MultipartFile trainingImage, MultipartFile trainingFile) throws IOException, NotAnImageFileException, IncorrectFileTypeException, NotFoundException {
        Training training = validateTraining(id);
        training.setTopic(topic);
        training.setDescription(description);
        training.setTrainer(trainer);
        training.setDurationInMinutes(durationInMinutes);
        trainingRepository.save(training);
        saveTrainingImage(training, trainingImage);
        saveTrainingFile(training, trainingFile);
        LOGGER.info("New picture added");

        return training;
    }



    private void saveTrainingImage(Training training, MultipartFile trainingImage) throws IOException, NotAnImageFileException {
        if (trainingImage != null) {
            if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(trainingImage.getContentType())) {
                throw new NotAnImageFileException(trainingImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            Path userFolder = Paths.get(USER_FOLDER + training.getId()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + String.valueOf(training.getId()) + DOT + JPG_EXTENSION));
            Files.copy(trainingImage.getInputStream(), userFolder.resolve(String.valueOf(training.getId()) + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            training.setTrainingImageUrl(setTrainingImageUrl(String.valueOf(training.getId())));
            trainingRepository.save(training);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + trainingImage.getOriginalFilename());
        }
    }

    private void saveTrainingFile(Training training, MultipartFile trainingFile) throws IOException, IncorrectFileTypeException {
        if (trainingFile != null) {
            if(!Arrays.asList("text/plain", "application/pdf", "text/csv",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                    "application/vnd.ms-powerpoint",
                    "application/vnd.oasis.opendocument.text",
                    "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document").contains(trainingFile.getContentType())) {
                throw new IncorrectFileTypeException(trainingFile.getOriginalFilename() + INCORRECT_FILE_TYPE);
            }
            Path userFolder = Paths.get(USER_FOLDER + training.getId()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            String fileName = trainingFile.getOriginalFilename();
            Files.deleteIfExists(Paths.get(userFolder + fileName));
            Files.copy(trainingFile.getInputStream(), userFolder.resolve(fileName), REPLACE_EXISTING);

            training.setTrainingFileUrl(setTrainingFileUrl(String.valueOf(training.getId()), fileName));
            trainingRepository.save(training);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + fileName);
        }
    }


    private String setTrainingImageUrl(String trainingId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + trainingId + FORWARD_SLASH
                + trainingId + DOT + JPG_EXTENSION).toUriString();
    }

    private String setTrainingFileUrl(String trainingId, String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_FILE_PATH + trainingId + FORWARD_SLASH
                + fileName).toUriString();
    }

    public void deleteTraining(long id) throws IOException {
        Training training = trainingRepository.findById(id);
        Path userFolder = Paths.get(USER_FOLDER + training.getId()).toAbsolutePath().normalize();
        FileUtils.deleteDirectory(new File(userFolder.toString()));
        trainingRepository.deleteById(training.getId());
    }

    public Training findById(long id) {
        return trainingRepository.findById(id);
    }

    private Training validateTraining(long id) throws NotFoundException {
        Training training = findById(id);

        if(training == null) {
            throw new NotFoundException("Not found");
        }
        return training;

    }


    private String getTemporarytrainingImageUrl(String trainingId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + trainingId).toUriString();
    }
}

