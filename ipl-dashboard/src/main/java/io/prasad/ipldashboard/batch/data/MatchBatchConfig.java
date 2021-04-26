package io.prasad.ipldashboard.batch.data;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.prasad.ipldashboard.batch.data.model.Match;

@Configuration
@EnableBatchProcessing
public class MatchBatchConfig {

    private static final String FIELDS_NAMES[] = new String[] {
        "id","city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner","toss_decision",
        "winner","result","result_margin","eliminator","method","umpire1","umpire2"
    };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public EntityManagerFactory emf;
 
    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>().name("matchItemReader")
                .resource(new ClassPathResource("IPL_DATA.csv")).delimited().names(FIELDS_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
                    {
                        setTargetType(MatchInput.class);
                    }
                }).build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    /*
     * @Bean public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
     * return new JdbcBatchItemWriterBuilder<Match>()
     * .itemSqlParameterSourceProvider(new
     * BeanPropertyItemSqlParameterSourceProvider<>())
     * .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)"
     * ) .dataSource(dataSource) .build(); }
     */

    @Bean
    public JpaItemWriter<Match> writer() {
        JpaItemWriter<Match> writer = new JpaItemWriter<Match>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }


    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }
 
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<MatchInput, Match>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
 


}
