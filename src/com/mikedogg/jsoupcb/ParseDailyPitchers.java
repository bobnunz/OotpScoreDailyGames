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
			for (int idx=0; idx<e.childrenSize(); idx++) {
				switch (idx) {

				case 1:  //playerId and player (name)
					pitcher.setPlayerId(e.child(idx).child(0).attr("href"));
					if (ownedPlayers.get(pitcher.getPlayerId()) == null) continue;
					pitcher.setPlayer(e.child(idx).child(0).text());
					break;
					
				case 2: // playerTeam
					pitcher.setTeam(e.child(idx).child(0).text());
					break;
				case 5: // playerWins
					pitcher.setWin(Integer.parseInt(e.child(idx).text()));
					break;
				case 15: // playerSaves
					pitcher.setSave(Integer.parseInt(e.child(idx).text()));
					break;
				case 16: // playerHolds
					pitcher.setHold(Integer.parseInt(e.child(idx).text()));
					break;
				case 17: // playerIP
					String newIpVal;
	        		if (! e.child(idx).text().endsWith("0")) {
	        			char[] charArray = e.child(idx).text().toCharArray();
	        			if (e.child(idx).text().endsWith("1"))
	        				charArray[charArray.length-1] = '3';
	        			else
	        				charArray[charArray.length-1] = '7';
	        			newIpVal = String.valueOf(charArray);
	         		}
	        		else
	        			newIpVal=e.child(idx).text();
					pitcher.setIp(Float.parseFloat(newIpVal));
					break;
				case 18: // playeHits
					pitcher.setHits(Integer.parseInt(e.child(idx).text()));
					break;
				case 20: // playerER
					pitcher.setEr(Integer.parseInt(e.child(idx).text()));
					break;
				case 22: // playerBB
					pitcher.setBb(Integer.parseInt(e.child(idx).text()));
					break;
				case 24: // playerSO
					pitcher.setK(Integer.parseInt(e.child(idx).text()));
					break;
				case 25: // playerHBP
					pitcher.setHbp(Integer.parseInt(e.child(idx).text()));
					break;
				case 26: // playerBk
					pitcher.setBk(Integer.parseInt(e.child(idx).text()));
					break;
				case 27: // playerWP
					pitcher.setWp(Integer.parseInt(e.child(idx).text()));
					break;
				}
			}
			
			if (ownedPlayers.get(pitcher.getPlayerId()) != null) retHitMap.put(pitcher.getPlayerId(), pitcher);
		}
		
		return retHitMap;
	}

}
