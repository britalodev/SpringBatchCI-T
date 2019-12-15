package br.com.italo.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
	ItemProcessor<InputCSV, OutPutCore> csvAnimeProcessor() {
		return new CSVInputProcessor();
	}
	
	
	@Bean
	public FlatFileItemWriter<OutPutCore> writer() {
		FlatFileItemWriter<OutPutCore> writer = new FlatFileItemWriter<OutPutCore>();
		writer.setResource(new FileSystemResource("/home/italo/person.csv"));
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
	
}
