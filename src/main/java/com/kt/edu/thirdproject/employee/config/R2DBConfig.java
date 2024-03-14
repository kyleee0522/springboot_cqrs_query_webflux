package com.kt.edu.thirdproject.employee.config;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.repository.EmployeeRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

@Configuration
@EnableR2dbcAuditing
@Slf4j
public class R2DBConfig {

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    public CommandLineRunner demo(EmployeeRepository repository) {

        return (args) -> {
            // Check if there are existing employees before saving new ones
            if (repository.count().block() == 0) {
                // Save a few employees if no existing data
                repository.saveAll(Arrays.asList(
                                new EmployeeEntity("1","1","1","1"),
                                new EmployeeEntity("2","2","1","1"),
                                new EmployeeEntity("3","3","1","1"),
                                new EmployeeEntity("4","4","1","1"),
                                new EmployeeEntity("5","5","1","1")))
                        .blockLast(Duration.ofSeconds(10));

                log.info("Saved new employees.");
            } else {
                log.info("Employees already exist in the database.");
            }

            // fetch all employees
            log.info("Employees found with findAll():");
            log.info("-------------------------------");
            repository.findAll().doOnNext(employee -> {
                log.info(employee.toString());
            }).blockLast(Duration.ofSeconds(10));

            log.info("");

            // fetch an individual employee by ID
            repository.findById(1L).doOnNext(employee -> {
                log.info("Employee found with findById(1L):");
                log.info("--------------------------------");
                log.info(employee.toString());
                log.info("");
            }).block(Duration.ofSeconds(10));

        };
    }
}