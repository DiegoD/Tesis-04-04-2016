package com.persistencia;

import java.util.ArrayList;

import com.excepciones.ObteniendoMonedasException;
import com.valueObject.MonedaVO;

public interface IDAOMonedas {

	public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedasException;
}
