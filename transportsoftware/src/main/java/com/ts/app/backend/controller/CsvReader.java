package com.ts.app.backend.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ts.app.backend.service.DockService;
import com.ts.app.backend.service.OrderService;

public class CsvReader {
	
	//static String csvFile = "C:/Users/sergi/Downloads/configuracion_muelles_gr7.csv";
    static BufferedReader br = null;
    static String line = "";
    static String cvsSplitBy = ";";
    static ArrayList<String[]> listDocks = new ArrayList<>();

    
    public static ArrayList<String[]> readCsv(InputStream inputStream) {
    	try {
    		listDocks.clear();
    		br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            //br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                listDocks.add(values);
            }
            //Remove CSV Header
            listDocks.remove(0);
            //System.out.println(listDocks.size());
        return listDocks;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
    
    public static void loadDocks(InputStream inputStream) {
    	
    	ArrayList<String[]> listDocks = readCsv(inputStream);
    	DockService.createDocks(listDocks);
    }
    
    public static void loadOrders(InputStream inputStream) {
    	
    	ArrayList<String[]> listOrders = readCsv(inputStream);
    	OrderService.createOrders(listOrders);
    }
}