package br.com.italo.service;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.com.italo.model.InputCSV;;

@Component

public class FileReaderCSV {

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

}
