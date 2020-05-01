package com.mikedogg.jsoupcb;

public class OwnedPlayers {
	


	private String ootpPlayerId;
	private String owner;
	private String team;
	private String ootpName;
	

	public OwnedPlayers(String ootpPlayerId, String owner, String team, String ootpName) {
		super();
		this.ootpPlayerId = ootpPlayerId;
		this.owner = owner;
		this.team = team;
		this.ootpName = ootpName;
	}
	
	public OwnedPlayers() {
		super();
		this.ootpPlayerId = "";
		this.owner = "";
		this.team = "";
		this.ootpName = "";
	}

	
	 public String getOotpPlayerId() {
		return ootpPlayerId;
	}


	public void setOotpPlayerId(String ootpPlayerId) {
		this.ootpPlayerId = ootpPlayerId;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}

	public String getOotpName() {
		return ootpName;
	}


	public void setOotpName(String ootpName) {
		this.ootpName = ootpName;
	}

/* Called by entry.getKey() 
    Overriding toString() method from super class Object
    Since key is Object we are return our own key value
*/

	public String toString(){
	  //return super.toString();
	  return "("+this.ootpPlayerId+")";
	 }
	
	public String getCompositeKey () {
		return this.owner+":"+this.ootpPlayerId;
	}


}
