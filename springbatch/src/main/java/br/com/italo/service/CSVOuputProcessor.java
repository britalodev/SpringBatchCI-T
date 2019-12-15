package br.com.italo.service;

import org.springframework.batch.item.ItemProcessor;

import br.com.italo.enums.EvenOrOddEnum;
import br.com.italo.model.InputCSV;
import br.com.italo.model.OutPutCore;
import lombok.extern.slf4j.Slf4j;

public class CSVOuputProcessor implements ItemProcessor<OutPutCore, OutPutCore> {

	@Override
	public OutPutCore process(final OutPutCore item) throws Exception {

		return item;
	}

}
