package br.com.italo.service;

import org.springframework.batch.item.ItemProcessor;

import br.com.italo.config.CSVtoCSVConfig;
import br.com.italo.enums.EvenOrOddEnum;
import br.com.italo.model.InputCSV;
import br.com.italo.model.OutPutCore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSVInputProcessor implements ItemProcessor<InputCSV, OutPutCore> {
	
	@Override
	public OutPutCore process(final InputCSV item) throws Exception {

		int numero = item.getNumero();
		
		OutPutCore outPutCore = new OutPutCore();
		
		outPutCore.setParOuImpar(checkEven(numero));
		outPutCore.setResto17(restOf17(numero).toString());
		outPutCore.setMultiplo17(multipletOf17(numero).toString());
		outPutCore.setNumero(String.valueOf(numero));
		
		totalizador(outPutCore);
		
		log.info("Converting (" + numero + ") into (" + outPutCore + ")");
		return outPutCore;
	}

	private EvenOrOddEnum checkEven(int numero) {
		int is = numero % 2;
		return is == 0 ? EvenOrOddEnum.PAR : EvenOrOddEnum.IMPAR;
	}
	
	private Integer restOf17(int numero) {
		Integer rest = numero % 17;
		return rest;
	}
	
	private Integer multipletOf17(int numero) {
		Integer rest = numero / 17;
		return rest;
	}
	
	private void totalizador(OutPutCore outPutCore) {
		
		if(outPutCore.getParOuImpar().equals(EvenOrOddEnum.PAR)){
			CSVtoCSVConfig.SOMAPAR = CSVtoCSVConfig.SOMAPAR + Integer.valueOf(outPutCore.getNumero());
			CSVtoCSVConfig.QUANTIDADEPAR++;
		} else {
			CSVtoCSVConfig.SOMAIMPAR = CSVtoCSVConfig.SOMAIMPAR + Integer.valueOf(outPutCore.getNumero());
			CSVtoCSVConfig.QUANTIDADEIMPAR++;
		}
	}
}
