package com.mikedogg.jsoupcb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Properties;

public class OotpScoreDailyGamesMain {

	public static void main(String[] args) throws IOException, URISyntaxException {

		String month = "";
		String day = "";
		
		if (args.length == 2 ) {
			month=args[0];
			day = args[1];
		}
		else {
			LocalDateTime now = LocalDateTime.now(); 
			month = Integer.toString(now.getMonthValue());
			day = Integer.toString(now.getDayOfMonth());
		}
		
        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("ootpconfig.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find ootpconfig.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            input.close();

            // print all properties and values in ootpconfig.properties
            Enumeration < ? > enumeration = prop.propertyNames();
            
            while (enumeration.hasMoreElements()) {
            	String name = (String) enumeration.nextElement();
                System.out.println(name+": "+prop.getProperty(name));
            }

            AssembleAndOutput.buildDailyScoringFile(prop, month, day);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

		
		
		System.out.println("Daily stats done for "+month+"/"+day);
	}

}
