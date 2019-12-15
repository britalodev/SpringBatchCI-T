package br.com.italo.enums;

import lombok.Getter;

@Getter
public enum EvenOrOddEnum {

	PAR("PAR"),
	IMPAR("IMPAR");
	
	   public String evenOrOdd;
	   
	   EvenOrOddEnum(String valor) {
	        evenOrOdd = valor;
	    }
	
}
