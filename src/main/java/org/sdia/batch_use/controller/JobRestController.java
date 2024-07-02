package org.sdia.batch_use.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobRestController {
    private final JobLauncher jobLauncher;
    private final Job job;
    //@Autowired
    // private BankTransactionRepository bankTransactionRepository;


    @RequestMapping("/batch")
    public void importCsvToDBJob() throws Exception {

        //on doit parametrer l'execution de job
        JobParameters jobParameters=new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);


        //JobExecution jobExecution =
        // Ajoutez les paramètres de latitude et de longitude à JobExecutionContext
     /*   ExecutionContext jobContext = jobExecution.getExecutionContext();
        jobContext.put("latitude", latitude);
        jobContext.put("longitude", longitude);*/
    }
}
