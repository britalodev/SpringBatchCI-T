package br.com.italo.model;

import br.com.italo.enums.EvenOrOddEnum;
import lombok.Data;

@Data
public class OutPutCore {

	private String numero;
	private EvenOrOddEnum ParOuImpar;
	private String multiplo17;
	private String resto17;
	
}
