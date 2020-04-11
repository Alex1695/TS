package com.ts.app.views.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

public class DictionaryManager {

	
	private static Properties lang_dicc;
	
	/**
     * Method to load selected laguage diccionary from src/main/resources
     */
    public static void setLanguage(String lang) throws IOException {
        //Importante agregar el resource directorie en el .pom
        lang_dicc = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream(lang+".properties")) {
            lang_dicc.load(resourceStream);
            System.out.println("set language: "+lang);
        }catch(Exception e){
        	System.out.println("ERROR. No ha sido posible cargar el diccionario");
        }
    }
    
	/**
	 * Method to get a dictionary field on the current language set
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String translateField(String str) throws UnsupportedEncodingException {
    	if(lang_dicc != null) {
    		String translate = lang_dicc.getProperty(str);
    		//convertir a utf8 o algo
    		return translate;
    	}else {
    		return "null";
    	}	
    }
	
}
