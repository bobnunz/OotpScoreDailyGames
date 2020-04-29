package com.mikedogg.jsoupcb;

import java.io.IOException;
import java.util.TreeMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class ParseDailyHitters {
	
	static TreeMap<String, Hitter> getDailyHitStats(Document document, TreeMap<String,OwnedPlayers> ownedPlayers) throws IOException {
		
		TreeMap<String,Hitter> retHitMap = new TreeMap<String,Hitter> ();
						
		// get table body and all enclosed elements (there should only be one table/tbody)
		
		Element elementsTbody = document.getElementsByTag("tbody").first();
		
		// loop through all rows to get stats for each player
		for (Element e:elementsTbody.children()) {
		
			// get stats for players in each data row <tr>
			
			Hitter hitter = new Hitter();
			// load batting players into TreeMap as a means of keeping only unique occurrances
			hitter.setPlayerId(e.getElementsByAttributeValue("data-stat", "player").select("a").first().attr("href"));
			if (ownedPlayers.get(hitter.getPlayerId()) == null) continue;
			hitter.setPlayer(e.getElementsByAttributeValue("data-stat", "player").select("a").first().text());
			hitter.setTeam(e.getElementsByAttributeValue("data-stat", "team_ID").select("a").first().text());
			hitter.setAb(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "AB").first().text()));
			hitter.setRuns(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "R").first().text()));
			hitter.setHits(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "H").first().text()));
			hitter.setHr(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "HR").first().text()));
			hitter.setRbi(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "RBI").first().text()));
			hitter.setSb(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SB").first().text()));
			hitter.setDoubles(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "2B").first().text()));
			hitter.setTriples(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "3B").first().text()));
			hitter.setSacFly(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SF").first().text()));
			hitter.setSacBunt(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SH").first().text()));
			hitter.setSo(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SO").first().text()));
			hitter.setBb(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "BB").first().text()));
			
			if ( ownedPlayers.get(hitter.getPlayerId()) != null) retHitMap.put(hitter.getPlayerId(), hitter);
		}
		
		return retHitMap;
	}

}
