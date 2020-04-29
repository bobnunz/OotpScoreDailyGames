package com.mikedogg.jsoupcb;

import java.io.IOException;
import java.util.TreeMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ParseDailyPitchers {
	
	static TreeMap<String, Pitcher> getDailyPitStats(Document document, TreeMap<String,OwnedPlayers> ownedPlayers) throws IOException {
		
		TreeMap<String,Pitcher> retHitMap = new TreeMap<String,Pitcher> ();
				
		// get table body and all enclosed elements (there should only be one table/tbody)
		
		Element elementsTbody = document.getElementsByTag("tbody").first();
		
		// loop through all rows to get stats for each player
		for (Element e:elementsTbody.children()) {
		
			// get stats for players in each data row <tr>
			
			Pitcher pitcher = new Pitcher();
			pitcher.setPlayerId(e.getElementsByAttributeValue("data-stat", "player").select("a").first().attr("href"));
			if (ownedPlayers.get(pitcher.getPlayerId()) == null) continue;
			pitcher.setPlayer(e.getElementsByAttributeValue("data-stat", "player").select("a").first().text());
			pitcher.setTeam(e.getElementsByAttributeValue("data-stat", "team_ID").select("a").first().text());
			pitcher.setWin(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "W").first().text()));
			pitcher.setSave(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SV").first().text()));
			pitcher.setHold(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "HOLD").first().text()));
			pitcher.setIp(Float.parseFloat(e.getElementsByAttributeValue("data-stat", "IP").first().text()));
			String newIpVal = "";
    		if (! e.getElementsByAttributeValue("data-stat", "IP").first().text().endsWith("0")) {
    			char[] charArray = e.getElementsByAttributeValue("data-stat", "IP").first().text().toCharArray();
    			if (e.getElementsByAttributeValue("data-stat", "IP").first().text().endsWith("1"))
    				charArray[charArray.length-1] = '3';
    			else
    				charArray[charArray.length-1] = '7';
    			newIpVal = String.valueOf(charArray);
     		}
    		else
    			newIpVal=e.getElementsByAttributeValue("data-stat", "IP").first().text();
			pitcher.setIp(Float.parseFloat(newIpVal));
			pitcher.setHits(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "H").first().text()));
			pitcher.setEr(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "ER").first().text()));
			pitcher.setK(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SO").first().text()));
			pitcher.setBb(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "BB").first().text()));
			pitcher.setHbp(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "HBP").first().text()));
			pitcher.setBk(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "BK").first().text()));
			pitcher.setWp(Integer.parseInt(e.getElementsByAttributeValue("data-stat", "WP").first().text()));			
			if (ownedPlayers.get(pitcher.getPlayerId()) != null) retHitMap.put(pitcher.getPlayerId(), pitcher);
		}
		
		return retHitMap;
	}

}
