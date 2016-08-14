package com.excepciones.funcionarios;

public class ExisteFuncionarioDocumetnoException extends Exception{


	public ExisteFuncionarioDocumetnoException(){
		super("Ya existe un funcionario con este documento");
	}
	
}
