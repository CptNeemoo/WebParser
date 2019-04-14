package com.cptneemoo;

import com.cptneemoo.service.WebParserService;
import com.cptneemoo.service.XMLCreatorService;

import java.util.HashMap;
import java.util.logging.Logger;

public class Main {

    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            String URL = "https://prom.ua/p895411456-igrovoj-noutbu-omen.html";
            HashMap<String, String> information = new WebParserService().parseUrl(URL);
            XMLCreatorService xmlCreatorService = new XMLCreatorService();
            xmlCreatorService.create(information);
        } catch (Exception e){
            log.severe("Exception of type " + e.getClass().getName() + " with message " + e.getMessage());
        }

    }
}
