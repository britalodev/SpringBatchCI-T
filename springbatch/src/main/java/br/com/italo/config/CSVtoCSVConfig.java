package br.com.italo.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.italo.model.InputCSV;
import br.com.italo.model.OutPutCore;
import br.com.italo.service.CSVInputProcessor;

@Configuration
public class CSVtoCSVConfig {

	@Bean
	ItemProcessor<InputCSV, OutPutCore> csvAnimeProcessor() {
		return new CSVInputProcessor();
	}

}
