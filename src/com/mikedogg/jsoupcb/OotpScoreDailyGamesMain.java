package com.mikedogg.jsoupcb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		
		AssembleAndOutput.buildDailyScoringFile(month, day);
		
		System.out.println("New daily stats done for "+month+"/"+day);
	}

}
