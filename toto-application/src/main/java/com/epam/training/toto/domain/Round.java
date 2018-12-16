package com.epam.training.toto.domain;

import java.time.LocalDate;
import java.util.Arrays;

public class Round {
	
	private int year;
	private int week;
	private int roundOfWeek;
	private LocalDate date;
	private Hit hits;
	private Outcome[] outcomes = new Outcome[14];

	
	public Round(int year, int week, int roundOfWeek, LocalDate date, Hit hits, Outcome[] outcomes) {
		this.year = year;
		this.week = week;
		this.roundOfWeek = roundOfWeek;
		this.date = date;
		this.hits = hits;
		this.outcomes = outcomes;
	}


	public int getYear() {
		return year;
	}


	public int getWeek() {
		return week;
	}


	public int getRoundOfWeek() {
		return roundOfWeek;
	}


	public LocalDate getDate() {
		return date;
	}


	public Hit getHits() {
		return hits;
	}


	public Outcome[] getOutcomes() {
		return outcomes;
	}
	
	public int getTotalOfTipType(String o) {
		int counter = 0;
		for (Outcome outcome: outcomes) {
			if (outcome.getResult().equals(o)) {
				counter++;
			}
		}
		return counter;
	}
	

	@Override
	public String toString() {
		return "Round [year=" + year + ", week=" + week + ", roundOfWeek=" + roundOfWeek + ", date=" + date + ", hits="
				+ hits + ", outcomes=" + Arrays.toString(outcomes) + "]";
	}
	
	

	
	
	
	

}
