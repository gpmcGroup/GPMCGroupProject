package com.gpmc.modelClass;

import java.io.File;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gpmc.util.xmlUtil;

public class Topic {
	private String title;
	private Team teamA;
	private Team teamB;
	private Date startTime;
	private int maxTurn;
	private long turnCycleFrequency; // miliseconds
	private Team presentTurnOwner;
	private long changeTurnTimeLeft; // miliseconds
	private boolean status; // three value: pending, progressing, end
	private String content;

	public String getContent() {
		if (content == null) {
			String xpath = "//topic[title='" + title + "']";
			try {
				System.out.println(xmlUtil.getTopicFilePath(title,"Topic"));
				Document doc = new SAXReader()
						.read(new File(xmlUtil.getTopicFilePath(title,"Topic")));

				// 1.
				Element ele = (Element) doc.selectSingleNode(xpath);

				return ele.getStringValue();
				// 2.
				// String x = doc.valueOf(xpath);
				// return x;
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private Team winner;

	public String getTitle() {
		return title;
	}

	public void setTitle(String tile) {
		this.title = tile;
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

	// public String getDetail(String detailTag) {
	// String xpath = "//topic[title='dasdas'/" + detailTag + "]";
	//
	// }
	//
	public String getAll() {
		String xpath = "//topic[title='" + title + "']";
		try {
			System.out.println(xmlUtil.getTopicFilePath(title,"Topic"));
			Document doc = new SAXReader()
					.read(new File(xmlUtil.getTopicFilePath(title, "Topic")));

			// 1.
			Element ele = (Element) doc.selectSingleNode(xpath);
			

			return ele.asXML();
			// 2.
			// String x = doc.valueOf(xpath);
			// return x;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}