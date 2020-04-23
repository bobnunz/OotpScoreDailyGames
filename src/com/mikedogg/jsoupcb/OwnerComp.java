package com.mikedogg.jsoupcb;

import java.util.Comparator;

public class OwnerComp implements Comparator<OwnedPlayers>{
	
		 @Override
		 public int compare(OwnedPlayers obj1, OwnedPlayers obj2) {
		        
		  // Sort TreeMap based on name
		  //return obj1.getName().compareTo(obj2.getName());
		  
		  // Sort TreeMap based on owner:ootpPlayerId
		  return obj1.getCompositeKey().compareTo(obj2.getCompositeKey());
		  
		}

}
