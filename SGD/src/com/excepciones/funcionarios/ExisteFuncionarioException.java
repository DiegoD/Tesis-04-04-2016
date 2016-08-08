package com.excepciones.funcionarios;

public class ExisteFuncionarioException extends Exception{

	public ExisteFuncionarioException(){
		super("El funcionario ya existe");
	}
}
