package com.gpmc.modelClass;

import java.util.Date;

public class Topic {
	private String tile;
	private Team teamA;
	private Team teamB;
	private Date startTime;
	private int maxTurn;
	private long turnCycleFrequency;  //miliseconds
	private Team presentTurnOwner;
	private long changeTurnTimeLeft; //miliseconds
	private boolean status; //three value: pending, progressing, end
	private Team winner;
	
	public String getTile() {
		return tile;
	}
	public void setTile(String tile) {
		this.tile = tile;
	}
	public Team getTeamA() {
		return teamA;
	}
	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}
	public Team getTeamB() {
		return teamB;
	}
	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getMaxTurn() {
		return maxTurn;
	}
	public void setMaxTurn(int maxTurn) {
		this.maxTurn = maxTurn;
	}
	public long getTurnCycleFrequency() {
		return turnCycleFrequency;
	}
	public void setTurnCycleFrequency(long turnCycleFrequency) {
		this.turnCycleFrequency = turnCycleFrequency;
	}
	public Team getPresentTurnOwner() {
		return presentTurnOwner;
	}
	public void setPresentTurnOwner(Team presentTurnOwner) {
		this.presentTurnOwner = presentTurnOwner;
	}
	public long getChangeTurnTimeLeft() {
		return changeTurnTimeLeft;
	}
	public void setChangeTurnTimeLeft(long changeTurnTimeLeft) {
		this.changeTurnTimeLeft = changeTurnTimeLeft;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Team getWinner() {
		return winner;
	}
	public void setWinner(Team winner) {
		this.winner = winner;
	}
} 
