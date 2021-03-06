package com.mikedogg.jsoupcb;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVReader;

public class AssembleAndOutput {
	
	static void buildDailyScoringFile (Properties prop, String month, String day) throws IOException {
		
		String dailyURL = prop.getProperty("dailyBattingURL").replaceAll("MM", month).replaceAll("DD", day);
		Document document = Jsoup.connect(dailyURL).get();
		
		// instantiate treemap that will be used to output daily stats 	
		TreeMap<OwnedPlayers,OwnerOutput> dailyScoreOutput = new TreeMap<OwnedPlayers, OwnerOutput>(new OwnerComp());

		// get owned players
		TreeMap<String, OwnedPlayers> ownedPlayers = getOwnedPlayers (prop);
		
		// get hitter stats
		TreeMap<String,Hitter> hitters = ParseDailyHitters.getDailyHitStats(document, ownedPlayers);
		
		//add hitters to output daily stats treemap
		for (String oPH:hitters.keySet()) {
			OwnedPlayers key = ownedPlayers.get(hitters.get(oPH).getPlayerId());
			OwnerOutput value = new OwnerOutput (hitters.get(oPH));
			dailyScoreOutput.put(key, value);
		}
		
		// get pitcher stats
		dailyURL = prop.getProperty("dailyPitchingURL").replaceAll("MM", month).replaceAll("DD", day);
		document = Jsoup.connect(dailyURL).get();
		TreeMap<String, Pitcher> pitchers = ParseDailyPitchers.getDailyPitStats(document, ownedPlayers);
		for (String oPH:pitchers.keySet()) {
			OwnedPlayers key = ownedPlayers.get(pitchers.get(oPH).getPlayerId());
			OwnerOutput existingPitcher = dailyScoreOutput.get(key);
			if ( existingPitcher == null )
				dailyScoreOutput.put(key, new OwnerOutput (pitchers.get(oPH)));
			else
				dailyScoreOutput.replace(key, existingPitcher.addToExistingPlayer(pitchers.get(oPH)));
		}
		
		// parse through all boxscore games to get errors (6th)
		
		dailyURL = prop.getProperty("dailyBoxScoresURL").replaceAll("MM", month).replaceAll("DD", day);
		document = Jsoup.connect(dailyURL).get();
		
		// URL to each box score has the text "Final" in the <a> tag 
		Elements elements = document.select("a:contains(final)");
		
		// loop through every game, get errors for owned players only, update dailyScoreOutput for any player with error(s)
		for (Element i:elements) {
			Document doc= Jsoup.connect(i.absUrl("href")).get();
			TreeMap <String, Integer> playerErrors = getErrorsOwnedPlayers(doc, ownedPlayers);
			for (String j:playerErrors.keySet()) {
				OwnedPlayers key = ownedPlayers.get(j);
				OwnerOutput value = dailyScoreOutput.get(key);
				value.setInning6(playerErrors.get(j));
				dailyScoreOutput.replace(key, value);
			}
		}
		
		// get results for owned managers (10th)

		TreeMap <String, String> mgrResults=getMgrResults(ownedPlayers, document);
		
		// write out daily file
		createDailyFile (prop, dailyScoreOutput,mgrResults, month, day);
	}
	
	private static TreeMap<String, OwnedPlayers> getOwnedPlayers (Properties prop) throws IOException {

		String ownedPlayerFile = prop.getProperty("masterPlayerFile");
	    Reader reader = Files.newBufferedReader(Paths.get(ownedPlayerFile), Charset.forName("windows-1252"));
	    CSVReader csvReader = new CSVReader(reader);
	    List<String[]> list = new ArrayList<>();
	    list = csvReader.readAll();
	    reader.close();
	    csvReader.close();
	    
	    TreeMap<String, OwnedPlayers> ownedPlayers = new TreeMap<String, OwnedPlayers>();
	    int idx = 0;
	    for (String[] i:list) {
	    	if (idx == 1) {
	    		idx = 2;
	    		continue;
	    	}
	    	//input = owner, playerid, playername, playerteam
	    	// TreeMap key=ootpPlayerId value=OwnedPlayers instance
	    	if (i[0].length() == 4 && i[0].charAt(0) >='A' && i[0].charAt(0)<='Z') {
	    		ownedPlayers.put(i[1], new OwnedPlayers(i[1],i[0],i[3],i[2]));
	    	}
	    }
	    
	    return ownedPlayers;
	}
	
	private static TreeMap <String, Integer> getErrorsOwnedPlayers (Document doc, TreeMap<String, OwnedPlayers> ownedPlayers) throws IOException {
		
		// The HTML section immediately after a teams batting stats tables contains the
		// ERROR stats among others such as Doubles, Triples, etc. These stats are enclosed in
		// a pair (hierchical) of <div> tags. the approach is to read each element
		// of the inner <div> tag and then look at each child (grandchild) element. the ERRORS stats is
		// BOLDED. 

		int loopVal=1;
		String batCategory = "";
		String[] arrayOfTokens;
		
		// return from method
		TreeMap <String, Integer> playerErrors = new TreeMap <String, Integer> ();

		// loop through the first 2 html tables, find the first (assumed) <div> tag 
		// that is the next tag after the table, and loop for Errors: with a <b> tag
		do {
			// first <div> tag after table
			//TODO - verify tag if <div>
			Element startPattern = doc.selectFirst("#all_stat-table-"+String.valueOf(loopVal)).nextElementSibling();
			
			String playerId;
			
			// loop through all elements (tags) within <div> element
			for (int i=0; i<startPattern.child(0).childrenSize(); i++)  {
				Element tagName;
				tagName = startPattern.child(0).child(i);
				
				// found <b> tag
				if (tagName.tagName().equalsIgnoreCase("b") && tagName.ownText().equalsIgnoreCase("Errors:")) {
					batCategory = "ERRORS";
				}
				
				// get players listed after Errors:  
				else if (tagName.tagName().equalsIgnoreCase("a") && ! batCategory.isEmpty()) {
					playerId = tagName.attr("href");

	
					// read the next node (children of <b> Errors tag) to get # of errors (blank then = 1)
					// The line reads =>  Errors: Playername ?(nth ...), nextPlayer ...
					arrayOfTokens = startPattern.child(0).child(i).nextSibling().toString().trim().split("\\(");
					int valStat = 0;
					if ( arrayOfTokens[0].matches(" ?") ) {
						valStat = 1;
					}
					else {
						valStat = Integer.parseInt(arrayOfTokens[0].trim());
					}
					if (ownedPlayers.get(playerId) != null) 
						playerErrors.put(playerId, valStat);
				}
				
				// clear the bold text flag 
				// this will occur if the tag is not <b> error 
				// when reading multiple players within a <b> tag for Errors, the "else if" above will be true
				else
					batCategory = "";
				
			}
			
			loopVal++;

		} while (loopVal < 3);
		
		return playerErrors;

	}
	
	public static TreeMap<String,String> getMgrResults (TreeMap<String, OwnedPlayers> ownedPlayers, Document document) throws IOException {
			    
	    //insert mgrs into TreeMap for later lookup
    	// TreeMap key=owner value=teamFullName:WL 
	    // WL=> 0=loss, 1=win
	    TreeMap<String, String> keyTeamValOwner = new TreeMap<String, String>();
	    TreeMap<String, String> keyOwnerValTeam = new TreeMap<String, String>();
	    for (String i: ownedPlayers.keySet()) {
	    	if (ownedPlayers.get(i).getOotpName().contains(Character.toString(':')))  {
	    		keyTeamValOwner.put(ownedPlayers.get(i).getOotpPlayerId(), ownedPlayers.get(i).getOwner());
    			keyOwnerValTeam.put(ownedPlayers.get(i).getOwner(), ownedPlayers.get(i).getOotpPlayerId()+":0");
	    	}
	    }

	    // every matchup is enclosed in a <table> that has a class called winner - select to get winning team
	    Elements winnerElements=document.getElementsByAttributeValue("class", "winner");
	    
	    // loop through all winners, set keyOwnerValTeam with status (1=win, default=0)	
	    // the class=winner in on a <tr>, get the text from the first <a> tag that is enclosed within the first <td> tag
	    for (Element e: winnerElements) {
	    	String winningTeam = e.child(0).child(0).ownText();
	    	String winningOwner = keyTeamValOwner.get(winningTeam);
	    	
	    	// assign the winner to any teams that are owned
	    	if ( winningOwner != null) {
	            String[] ownerValue=keyOwnerValTeam.get(winningOwner).split(":");
	    		int ownerWins=Integer.parseInt(ownerValue[1]);
	    		keyOwnerValTeam.replace(winningOwner,winningTeam+":"+(++ownerWins) );	
	    	}
	    }
	    	    
	    return keyOwnerValTeam;
	}
	
	private static void createDailyFile (Properties prop, TreeMap<OwnedPlayers,OwnerOutput> dailyScoreOutput, TreeMap <String, String> mgrResults,
			String month, String day) throws IOException {

        String prevOwner = " ";
        OwnerOutput ownerTotals = new OwnerOutput();
        
        // daily output file -> dailyScores_M_D_2020.csv
        FileWriter myWriter = new FileWriter(prop.getProperty("dailyScoresFile").replaceAll("MM", month).replaceAll("DD", day));  
        
        // trickery to round float to 1 decimal place
        DecimalFormat df = new DecimalFormat("##.#");
        
        // loop through dailyScoreOutput players by owner 
        // when next owner found, print totals line for prev owner's players
        for (OwnedPlayers player: dailyScoreOutput.keySet() ) {
        	
        	// 1st player of next owner found, print totals for prev (except if this is the first time through
        	if (!player.getOwner().equalsIgnoreCase(prevOwner)) {
        		
        		// if 1st time through, prevowner will be blank
        		if (!prevOwner.equalsIgnoreCase(" ")) {
        			
        			// the format of prevOwner is owner:playerId
        			String[] tokens = mgrResults.get(prevOwner).split(":");
          			
          			// write out totals line for prevOwner
        			myWriter.write("Totals ("+tokens[0]+"),,,"
        					+ownerTotals.getAb()+","
        					+ownerTotals.getInning1()+","
        					+ownerTotals.getInning2()+","
        					+ownerTotals.getInning3()+","
        					+ownerTotals.getInning4()+","
        					+ownerTotals.getInning5()+","
        					+ownerTotals.getInning6()+","
        					+ownerTotals.getInning7()+","
        					+df.format(ownerTotals.getInning8())+","
        					+ownerTotals.getInning9()+","
        					+tokens[1]+","
        					+df.format(ownerTotals.getInning11())+","
        					+ownerTotals.getInning12()+","
        					+ownerTotals.getInning13()+","
        					+ownerTotals.getInning14()+","
        					+ownerTotals.getInning15()+","
        					+ownerTotals.getInning16()+","
        					+ownerTotals.getInning17()+","
        					+ownerTotals.getInning18()+","
        					+"\n\n");       			
        		}
        		
        		// initialize totals for owner's players 
        		ownerTotals = new OwnerOutput();
        		
        		// write out column heading before the owner's first player is printed
        		myWriter.write("Owner,Player,Team,AB,NR,Hits,HRs,RBIs,SBs,ERRs,WHS,nIP,nKB,(MGR),IP-Hits,BB-SO,2Bs,3Bs,SF+SH,WP,HBP,BK\n");
        		prevOwner=player.getOwner();       		
        	}
        	
        	// get the owner's next player, print it
        	OwnerOutput eachPlayer = dailyScoreOutput.get(player);
			myWriter.write(player.getOwner()+","
					+eachPlayer.getPlayerName()+","
					+eachPlayer.getTeam()+","
					+eachPlayer.getAb()+","
					+eachPlayer.getInning1()+","
					+eachPlayer.getInning2()+","
					+eachPlayer.getInning3()+","
					+eachPlayer.getInning4()+","
					+eachPlayer.getInning5()+","
					+eachPlayer.getInning6()+","
					+eachPlayer.getInning7()+","
					+df.format(eachPlayer.getInning8())+","
					+eachPlayer.getInning9()+","
					+eachPlayer.getInning10()+","
					+df.format(eachPlayer.getInning11())+","
					+eachPlayer.getInning12()+","
					+eachPlayer.getInning13()+","
					+eachPlayer.getInning14()+","
					+eachPlayer.getInning15()+","
					+eachPlayer.getInning16()+","
					+eachPlayer.getInning17()+","
					+eachPlayer.getInning18()+","
					+"\n"); 
			
			// add players stats to totsl for current owner being processed (prevOwner)
			ownerTotals.addToTotals(eachPlayer);
			
			// write out the totals line for the last player / last owner
			if(player.getCompositeKey().equalsIgnoreCase(dailyScoreOutput.lastKey().getCompositeKey())) {
     			String[] tokens = mgrResults.get(prevOwner).split(":");
    			myWriter.write("Totals ("+tokens[0]+"),,,"
    					+ownerTotals.getAb()+","
    					+ownerTotals.getInning1()+","
    					+ownerTotals.getInning2()+","
    					+ownerTotals.getInning3()+","
    					+ownerTotals.getInning4()+","
    					+ownerTotals.getInning5()+","
    					+ownerTotals.getInning6()+","
    					+ownerTotals.getInning7()+","
    					+df.format(ownerTotals.getInning8())+","
    					+ownerTotals.getInning9()+","
    					+tokens[1]+","
    					+df.format(ownerTotals.getInning11())+","
    					+ownerTotals.getInning12()+","
    					+ownerTotals.getInning13()+","
    					+ownerTotals.getInning14()+","
    					+ownerTotals.getInning15()+","
    					+ownerTotals.getInning16()+","
    					+ownerTotals.getInning17()+","
    					+ownerTotals.getInning18()+","
    					+"\n");       			
				
			}
        	
        }
        
        myWriter.close();
		
	}

}
	
