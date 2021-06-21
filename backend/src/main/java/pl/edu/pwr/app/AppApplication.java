/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import pl.edu.pwr.app.controllers.ProposalController;
import pl.edu.pwr.app.controllers.UserController;
import pl.edu.pwr.app.models.ProposalHost;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.reminder.Reminder;
import pl.edu.pwr.app.repositories.ProposalHostRepository;

import java.util.Arrays;
import java.util.Collections;
import java.io.File;

import static pl.edu.pwr.app.constant.FileConstants.USER_FOLDER;

@EnableScheduling
@SpringBootApplication
public class AppApplication {

    @Value("${angularAppLink}")
    String app;


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        new File(USER_FOLDER).mkdirs();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList(app));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}