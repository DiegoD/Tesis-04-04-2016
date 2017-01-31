package com.Reportes.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Esta clase lee las propiedades del archivo con la coneccion de la base de datos
 */
public class PropertiesUtil {
    public static Properties getProperties(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("/database.properties");
        Properties prop = new Properties();
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
