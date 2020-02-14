package com.ryan.springbatchexample.config;

import com.ryan.springbatchexample.telco.Telco;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory setBuilderFactory,
            ItemReader<Telco> telcoItemReader, ItemProcessor<Telco, Telco> telcoItemProcessor,
            ItemWriter<Telco> telcoItemWriter) {

        Step step = setBuilderFactory.get("TELCO-File-Load").<Telco, Telco>chunk(100).reader(telcoItemReader)
                .processor(telcoItemProcessor).writer(telcoItemWriter).build();

        return jobBuilderFactory.get("TELCO-Load").incrementer(new RunIdIncrementer()).start(step).build();
    }

    @Bean
    public FlatFileItemReader<Telco> telcoItemReader(@Value("${input}") Resource resource) {

        FlatFileItemReader<Telco> flatFileItemReader = new FlatFileItemReader<>();
        log.info("Setting resources...");
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        // log.info("CONTENT: ");
        flatFileItemReader.setStrict(false);

        return flatFileItemReader;
    }

    private LineMapper<Telco> lineMapper() {

        DefaultLineMapper<Telco> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Telco> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] { "id", "mobile_prefix", "telco", "plan_type" });

        fieldSetMapper.setTargetType(Telco.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}