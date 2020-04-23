package com.mikedogg.jsoupcb;

public class OwnerOutput {
	
	private String playerName;
	private String team;
	private int Ab;
	private int inning1;
	private int inning2;
	private int inning3;
	private int inning4;
	private int inning5;
	private int inning6;
	private int inning7;
	private float inning8;
	private int inning9;
	private int inning10;
	private float inning11;
	private int inning12;
	private int inning13;
	private int inning14;
	private int inning15;
	private int inning16;
	private int inning17;
	private int inning18;
	
	public OwnerOutput() {
		super();
		this.playerName = "";
		this.team = "";
		this.Ab = 0;
		this.inning1 = 0;
		this.inning2 = 0;
		this.inning3 = 0;
		this.inning4 = 0;
		this.inning5 = 0;
		this.inning6 = 0;
		this.inning7 = 0;
		this.inning8 = 0;
		this.inning9 = 0;
		this.inning10 = 0;
		this.inning11 = 0;
		this.inning12 = 0;
		this.inning13 = 0;
		this.inning14 = 0;
		this.inning15 = 0;
		this.inning16 = 0;
		this.inning17 = 0;
		this.inning18 = 0;
	}
	
	OwnerOutput (Hitter hitter ) {
		
		this.playerName = hitter.getPlayer();
		this.team = hitter.getTeam();
		this.Ab = hitter.getAb();
		this.inning1 = hitter.getRuns()-hitter.getHr();
		this.inning2 = hitter.getHits();
		this.inning3 = hitter.getHr();
		this.inning4 = hitter.getRbi();
		this.inning5 = hitter.getSb();
		this.inning6 = hitter.getErr();
		this.inning7 = 0;
		this.inning8 = 0;
		this.inning9 = 0;
		this.inning10 = 0;
		this.inning11 = 0;
		this.inning12 = hitter.getBb() - hitter.getSo();
		this.inning13 = hitter.getDoubles();
		this.inning14 = hitter.getTriples();
		this.inning15 = hitter.getSacFly()+hitter.getSacBunt();
		this.inning16 = 0;
		this.inning17 = 0;
		this.inning18 = 0;
	
	return;
	
	}

	OwnerOutput (Pitcher pitcher) {
		
		this.playerName = pitcher.getPlayer();		
		this.team = pitcher.getTeam();
		this.Ab = 0;
		this.inning1 = 0;
		this.inning2 = 0;
		this.inning3 = 0;
		this.inning4 = 0;
		this.inning5 = 0;
		this.inning6 = 0;		
		this.inning7 = pitcher.getWin() + pitcher.getSave() + pitcher.getHold();
		this.inning8 = pitcher.getIp() - pitcher.getEr();
		this.inning9 = pitcher.getK() - pitcher.getBb();
		this.inning10 = 0;
		this.inning11 = pitcher.getIp() - pitcher.getHits();
		this.inning12 = 0;
		this.inning13 = 0;
		this.inning14 = 0;
		this.inning15 = 0;
		this.inning16 = pitcher.getWp();
		this.inning17 = pitcher.getHbp();
		this.inning18 = pitcher.getBk();
		
		return;
	}

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getAb() {
		return Ab;
	}
	public void setAb(int ab) {
		Ab = ab;
	}
	public int getInning1() {
		return inning1;
	}
	public void setInning1(int inning1) {
		this.inning1 = inning1;
	}
	public int getInning2() {
		return inning2;
	}
	public void setInning2(int inning2) {
		this.inning2 = inning2;
	}
	public int getInning3() {
		return inning3;
	}
	public void setInning3(int inning3) {
		this.inning3 = inning3;
	}
	public int getInning4() {
		return inning4;
	}
	public void setInning4(int inning4) {
		this.inning4 = inning4;
	}
	public int getInning5() {
		return inning5;
	}
	public void setInning5(int inning5) {
		this.inning5 = inning5;
	}
	public int getInning6() {
		return inning6;
	}
	public void setInning6(int inning6) {
		this.inning6 = inning6;
	}
	public int getInning7() {
		return inning7;
	}
	public void setInning7(int inning7) {
		this.inning7 = inning7;
	}
	public float getInning8() {
		return inning8;
	}
	public void setInning8(float inning8) {
		this.inning8 = inning8;
	}
	public int getInning9() {
		return inning9;
	}
	public void setInning9(int inning9) {
		this.inning9 = inning9;
	}
	public int getInning10() {
		return inning10;
	}
	public void setInning10(int inning10) {
		this.inning10 = inning10;
	}
	public float getInning11() {
		return inning11;
	}
	public void setInning11(float inning11) {
		this.inning11 = inning11;
	}
	public int getInning12() {
		return inning12;
	}
	public void setInning12(int inning12) {
		this.inning12 = inning12;
	}
	public int getInning13() {
		return inning13;
	}
	public void setInning13(int inning13) {
		this.inning13 = inning13;
	}
	public int getInning14() {
		return inning14;
	}
	public void setInning14(int inning14) {
		this.inning14 = inning14;
	}
	public int getInning15() {
		return inning15;
	}
	public void setInning15(int inning15) {
		this.inning15 = inning15;
	}
	public int getInning16() {
		return inning16;
	}
	public void setInning16(int inning16) {
		this.inning16 = inning16;
	}
	public int getInning17() {
		return inning17;
	}
	public void setInning17(int inning17) {
		this.inning17 = inning17;
	}
	public int getInning18() {
		return inning18;
	}
	public void setInning18(int inning18) {
		this.inning18 = inning18;
	}

	public OwnerOutput addToExistingPlayer (Hitter hitter ) {
		
		this.Ab += hitter.getAb();
		this.inning1 += hitter.getRuns()-hitter.getHr();
		this.inning2 += hitter.getHits();
		this.inning3 += hitter.getHr();
		this.inning4 += hitter.getRbi();
		this.inning5 += hitter.getSb();
		this.inning6 += hitter.getErr();
		this.inning12 += hitter.getBb() - hitter.getSo();
		this.inning13 += hitter.getDoubles();
		this.inning14 += hitter.getTriples();
		this.inning15 += hitter.getSacFly()+hitter.getSacBunt();
		
		return this;
	}
	
	public OwnerOutput addToExistingPlayer (Pitcher pitcher) {

		this.inning7 += pitcher.getWin() + pitcher.getSave() + pitcher.getHold();
		this.inning8 += pitcher.getIp() - pitcher.getEr();
		this.inning9 += pitcher.getK() - pitcher.getBb();
		this.inning11 += pitcher.getIp() - pitcher.getHits();
		this.inning16 += pitcher.getWp();
		this.inning17 += pitcher.getHbp();
		this.inning18 += pitcher.getBk();

		return this;
	}
	
	public OwnerOutput addToTotals (OwnerOutput addToSum) {
		
		this.Ab += addToSum.getAb();
		this.inning1 += addToSum.getInning1();
		this.inning2 += addToSum.getInning2();
		this.inning3 += addToSum.getInning3();
		this.inning4 += addToSum.getInning4();
		this.inning5 += addToSum.getInning5();
		this.inning6 += addToSum.getInning6();
		this.inning7 += addToSum.getInning7();
		this.inning8 += addToSum.getInning8();
		this.inning9 += addToSum.getInning9();
		this.inning11 += addToSum.getInning11();
		this.inning12 += addToSum.getInning12();
		this.inning13 += addToSum.getInning13();
		this.inning14 += addToSum.getInning14();
		this.inning15 += addToSum.getInning15();
		this.inning16 += addToSum.getInning16();
		this.inning17 += addToSum.getInning17();
		this.inning18 += addToSum.getInning18();

		return this;
	}

}
