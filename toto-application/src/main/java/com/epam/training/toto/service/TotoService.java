package com.epam.training.toto.service;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.opencsv.CSVReader;

import java.util.List;
import java.util.OptionalInt;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TotoService {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
	private List<Round> rounds;

	public TotoService() throws FileNotFoundException, IOException {

		System.out.println("Initializing database...");

		rounds = readDataTable();

		System.out.println("Initialization done.");

	}

	private static List<Round> readDataTable() throws FileNotFoundException, IOException {
		List<Round> roundsReaded = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new FileReader("resources/toto.csv"), ';')) {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				Round round = createRound(nextLine);
				roundsReaded.add(round);
			}
		}
		return roundsReaded;
	}

	private static Round createRound(String[] nextLine) {

		int year = Integer.parseInt(nextLine[0]);
		int week = Integer.parseInt(nextLine[1]);

		// Checking for "-" entries
		int roundOfWeek;
		if (nextLine[2].equals("-")) {
			roundOfWeek = 0;
		} else {
			roundOfWeek = Integer.parseInt(nextLine[2]);
		}

		// Calculating LocalDate Object, checking for empty Strings
		LocalDate date = null;
		if (!nextLine[3].isEmpty()) {
			date = LocalDate.parse(nextLine[3], FORMATTER);
		}

		// Calculating Hit Object
		int[] wagers = new int[5];
		for (int i = 0; i < wagers.length; i++) {
			wagers[i] = Integer.parseInt(nextLine[4 + i * 2]);
		}
		String[] prizes = new String[5];
		for (int i = 0; i < prizes.length; i++) {
			prizes[i] = nextLine[5 + i * 2];
		}
		Hit hits = new Hit(wagers, prizes);

		// Calculating Outcome array
		Outcome[] outcomes = new Outcome[14];
		for (int i = 0; i < outcomes.length; i++) {
			String o;
			if (nextLine[14 + i].contains("+")) {
				o = nextLine[14 + i].substring(1, 2);
			} else {
				o = nextLine[14 + i];
			}
			switch (o) {
			case "1":
				outcomes[i] = Outcome.TEAM1;
				break;
			case "2":
				outcomes[i] = Outcome.TEAM2;
				break;
			default:
				outcomes[i] = Outcome.DRAW;
			}
		}

		// Constructing Round Object with the data gathered above
		Round round = new Round(year, week, roundOfWeek, date, hits, outcomes);

		return round;
	}

	public int topPrize() {
		
		OptionalInt max = rounds.stream()
				.mapToInt(a -> a.getHits().getMaxPrize())
				.max();
		
		return max.getAsInt();
		
	}
	
	public double distOutcomes(String team) {
		

		int[] results = new int[3];
		
		results[0] = rounds.stream()
				.mapToInt(a -> a.getTotalOfTipType("1"))
				.sum();
		
		results[1] = rounds.stream()
				.mapToInt(a -> a.getTotalOfTipType("2"))
				.sum();
		
		results[2] = rounds.stream()
				.mapToInt(a -> a.getTotalOfTipType("X"))
				.sum();
		
		int total = results[0]+results[1]+results[2];
		
		switch (team) {
		case "1" : return ((double)results[0]/total)*100;
		case "2" : return ((double)results[1]/total)*100;
		default : return ((double)results[2]/total)*100;
		}
		
	}
	
	public int[] queryPastGames(LocalDate date, char[] results) {
		
		int matches = 0;
		int prize = 0;
		for (Round r: rounds) {
			if(r.getDate()!=null && r.getDate().equals(date)) {
				Outcome[] outcomes = r.getOutcomes();
				for (int i=0; i<results.length; i++) {
					if (outcomes[i].getResult().equals(""+results[i])) {
						matches++;
					}
				}
				
				prize = r.getHits().getPrize(matches);
				
			}
		}
		
		
		return new int[] {matches, prize};
		
		
	}
		

}
