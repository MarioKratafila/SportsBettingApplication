package com.epam.training.toto.domain;

public enum Outcome {
	TEAM1("1"), TEAM2("2"), DRAW("X");
	
	private String result;
	
	private Outcome(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}
	
}
