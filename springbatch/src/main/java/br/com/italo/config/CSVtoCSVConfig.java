package br.com.italo.config;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import br.com.italo.model.InputCSV;
import br.com.italo.model.OutPutCore;
import br.com.italo.service.CSVInputProcessor;

@EnableBatchProcessing
@Configuration
public class CSVtoCSVConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

	

	@Bean
	public FlatFileItemReader<InputCSV> csvAnimeReader() {
		FlatFileItemReader<InputCSV> reader = new FlatFileItemReader<InputCSV>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource("input.csv"));
		reader.setLineMapper(new DefaultLineMapper<InputCSV>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "numero"});
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<InputCSV>() {
					{
						setTargetType(InputCSV.class);
					}
				});
			}
		});
		return reader;
	}
	
	
	@Bean
	ItemProcessor<InputCSV, OutPutCore> csvProcessor() {
		return new CSVInputProcessor();
	}
	
	
	@Bean
	public FlatFileItemWriter<OutPutCore> writer() {
		FlatFileItemWriter<OutPutCore> writer = new FlatFileItemWriter<OutPutCore>();
		writer.setResource(new FileSystemResource("src\\main\\resources\\output.csv"));
		writer.setHeaderCallback(new StringHeaderWriter("Número,Par/Impar,Multiplo17,Resto17"));
		writer.setLineAggregator(new DelimitedLineAggregator<OutPutCore>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<OutPutCore>() {
					{
						setNames(new String[] { "numero", "parOuImpar", "multiplo17", "resto17" });
					}
				});
			}
		});

		return writer;
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<InputCSV, OutPutCore>chunk(0).reader(csvAnimeReader()).processor(csvProcessor())
				.writer(writer()).build();
	}

	@Bean
	public Job exportToCSV() {
		return jobBuilderFactory.get("exportToCSV").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
	
}
