package org.sdia.batch_use.config;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.sdia.batch_use.service.ApiReader;
import org.sdia.batch_use.service.ApiWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration //car c une classe de configuration
@EnableBatchProcessing //pour activer spring batch
@AllArgsConstructor
public class SpringBatchConfig {
    //pour pouvoir configurer un job j'ai besoin des objets suivants
    private JobRepository jobRepository;
    private PlatformTransactionManager platformTransactionManager;

    private ApiWriter apiWriter;
    private ApiReader apiReader;

    //on définit une méthode de configuration qui permet de retourner un job
    @Bean
    public Job myJob(){
        //une fois je crée je step je retourne je job
        return new JobBuilder("elt-load",jobRepository).start(stepApi()).build();

    }
    //upload data from rest api
    //on configure un step
    @Bean
    public Step stepApi(){
        return new StepBuilder("step_API", jobRepository)
                .<JsonNode, JsonNode>chunk(1,platformTransactionManager)
                .reader(apiReader)
                // .processor(apiProcessor)
                .writer(apiWriter)
                .build();
    }
}
