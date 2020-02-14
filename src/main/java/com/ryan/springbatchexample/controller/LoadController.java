package com.ryan.springbatchexample.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author Ryan R.
 * 
 */

@Controller
@RequestMapping("/load")
@Slf4j
public class LoadController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    /**
     * 
     * @return the batch status of the launched job
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    @GetMapping
    public ResponseEntity<BatchStatus> load() throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(maps);

        log.info("JobLauncher: " + jobLauncher.toString());
        log.info("Job: " + job.toString());

        JobExecution jobExecution;
        try {
            jobExecution = jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Loading batch...");

        while (jobExecution.isRunning()) {
            log.info("...");
        }
        log.info("JobExecution: " + jobExecution.getStatus());

        return ResponseEntity.ok(jobExecution.getStatus());
    }

    /**
     * Once all the steps are completed, status is now back into the JobRepository
     */
}