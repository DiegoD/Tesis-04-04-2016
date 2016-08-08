package com.excepciones.funcionarios;

public class MemberFuncionarioException extends Exception{

	public MemberFuncionarioException(){
		super("Existe un funcionario con este código");
	}
}
