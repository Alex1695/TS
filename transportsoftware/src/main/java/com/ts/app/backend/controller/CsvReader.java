package com.ts.app.backend.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {
	
	static String csvFile = "C:/Users/sergi/Downloads/configuracion_muelles_gr7.csv";
    static BufferedReader br = null;
    static String line = "";
    static String cvsSplitBy = ",";
    static List<List<String>> listDocks = new ArrayList<>();
    
    
    public static void readCsv(InputStream inputStream) {
    	try {
    		
    		br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            //br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                listDocks.add(Arrays.asList(values));

                System.out.println("ListDocks [docks= " + listDocks.get(0));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}