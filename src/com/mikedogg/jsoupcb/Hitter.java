package com.mikedogg.jsoupcb;

public class Hitter {
	
	private String playerId;
	private String player;
	private String team;
	private int ab;
	private int runs;
	private int hits;
	private int rbi;
	private int bb;
	private int so;
	private int doubles;
	private int triples;
	private int hr;
	private int sb;
	private int err;
	private int sacFly;
	private int sacBunt;
	
	public Hitter(String playerId, String player, String team, int ab, int runs, int hits, int rbi, int bb, int so, int doubles,
			int triples, int hr, int sb, int err, int sacFly, int sacBunt) {
		super();
		this.playerId=playerId;
		this.player = player;
		this.team = team;
		this.ab = ab;
		this.runs = runs;
		this.hits = hits;
		this.rbi = rbi;
		this.bb = bb;
		this.so = so;
		this.doubles = doubles;
		this.triples = triples;
		this.hr = hr;
		this.sb = sb;
		this.err = err;
		this.sacFly = sacFly;
		this.sacBunt = sacBunt;
	}
	
	public Hitter() {
		super();
		this.playerId = "";
		this.player = "";
		this.team = "";
		this.ab = 0;
		this.runs = 0;
		this.hits = 0;
		this.rbi = 0;
		this.bb = 0;
		this.so = 0;
		this.doubles = 0;
		this.triples = 0;
		this.hr = 0;
		this.sb = 0;
		this.err = 0;
		this.sacFly = 0;
		this.sacBunt = 0;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getAb() {
		return ab;
	}
	public void setAb(int ab) {
		this.ab = ab;
	}
	public int getRuns() {
		return runs;
	}
	public void setRuns(int runs) {
		this.runs = runs;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getRbi() {
		return rbi;
	}
	public void setRbi(int rbi) {
		this.rbi = rbi;
	}
	public int getBb() {
		return bb;
	}

	public void setBb(int bb) {
		this.bb = bb;
	}

	public int getSo() {
		return so;
	}

	public void setSo(int so) {
		this.so = so;
	}

	public int getDoubles() {
		return doubles;
	}
	public void setDoubles(int doubles) {
		this.doubles = doubles;
	}
	public int getTriples() {
		return triples;
	}
	public void setTriples(int triples) {
		this.triples = triples;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	public int getSb() {
		return sb;
	}
	public void setSb(int sb) {
		this.sb = sb;
	}
	public int getErr() {
		return err;
	}
	public void setErr(int err) {
		this.err = err;
	}

	public int getSacFly() {
		return sacFly;
	}

	public void setSacFly(int sacFly) {
		this.sacFly = sacFly;
	}

	public int getSacBunt() {
		return sacBunt;
	}

	public void setSacBunt(int sacBunt) {
		this.sacBunt = sacBunt;
	}

}
