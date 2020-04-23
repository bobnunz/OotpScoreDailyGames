package com.mikedogg.jsoupcb;

public class Pitcher {
	
	private String playerId;
	private String player;
	private String team;
	private float ip;
	private int hits;
	private int er;
	private int bb;
	private int k;
	private int win;
	private int save;
	private int hold;
	private int wp;
	private int hbp;
	private int bk;

	public Pitcher(String playerId, String player, String team, float ip, int hits, int er, int bb, int k, int win, int save,
			int hold, int wp, int hbp, int bk) {
		super();
		this.playerId = playerId;
		this.player = player;
		this.team = team;
		this.ip = ip;
		this.hits = hits;
		this.er = er;
		this.bb = bb;
		this.k = k;
		this.win = win;
		this.save = save;
		this.hold = hold;
		this.wp = wp;
		this.hbp = hbp;
		this.bk = bk;
	}
	
	public Pitcher() {
		super();
		this.playerId = "";
		this.player = "";
		this.team = "";
		this.ip = 0;
		this.hits =  0;
		this.er = 0;
		this.bb = 0;
		this.k = 0;
		this.win =0;
		this.save = 0;
		this.hold = 0;
		this.wp = 0;
		this.hbp = 0;
		this.bk = 0;
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
	public float getIp() {
		return ip;
	}
	public void setIp(float ip) {
		this.ip = ip;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getEr() {
		return er;
	}
	public void setEr(int er) {
		this.er = er;
	}
	public int getBb() {
		return bb;
	}
	public void setBb(int bb) {
		this.bb = bb;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getSave() {
		return save;
	}
	public void setSave(int save) {
		this.save = save;
	}
	public int getHold() {
		return hold;
	}
	public void setHold(int hold) {
		this.hold = hold;
	}
	public int getWp() {
		return wp;
	}
	public void setWp(int wp) {
		this.wp = wp;
	}
	public int getHbp() {
		return hbp;
	}
	public void setHbp(int hbp) {
		this.hbp = hbp;
	}
	public int getBk() {
		return bk;
	}
	public void setBk(int bk) {
		this.bk = bk;
	}

}
