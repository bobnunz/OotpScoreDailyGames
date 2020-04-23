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
			for (int idx=0; idx<e.childrenSize(); idx++) {
				switch (idx) {

				case 1:  //playerId and player (name)
					hitter.setPlayerId(e.child(idx).child(0).attr("href"));
					if (ownedPlayers.get(hitter.getPlayerId()) == null) continue;
					hitter.setPlayer(e.child(idx).child(0).text());
					break;
					
				case 2: // playerTeam
					hitter.setTeam(e.child(idx).child(0).text());
					break;
				case 8: // playerAB
					hitter.setAb(Integer.parseInt(e.child(idx).text()));
					break;
				case 9: // playerRuns
					hitter.setRuns(Integer.parseInt(e.child(idx).text()));
					break;
				case 10: // playerHits
					hitter.setHits(Integer.parseInt(e.child(idx).text()));
					break;
				case 11: // player2B
					hitter.setDoubles(Integer.parseInt(e.child(idx).text()));
					break;
				case 12: // player3B
					hitter.setTriples(Integer.parseInt(e.child(idx).text()));
					break;
				case 13: // playeHR
					hitter.setHr(Integer.parseInt(e.child(idx).text()));
					break;
				case 14: // playerRBI
					hitter.setRbi(Integer.parseInt(e.child(idx).text()));
					break;
				case 15: // playerSB
					hitter.setSb(Integer.parseInt(e.child(idx).text()));
					break;
				case 17: // playerBB
					hitter.setBb(Integer.parseInt(e.child(idx).text()));
					break;
				case 18: // playerSO
					hitter.setSo(Integer.parseInt(e.child(idx).text()));
					break;
				case 26: // playerSacFly
					hitter.setSacFly(Integer.parseInt(e.child(idx).text()));
					break;
				case 27: // playerSacBunt
					hitter.setSacBunt(Integer.parseInt(e.child(idx).text()));
					break;
				}
			}
			
			if ( ownedPlayers.get(hitter.getPlayerId()) != null) retHitMap.put(hitter.getPlayerId(), hitter);
		}
		
		return retHitMap;
	}

}
