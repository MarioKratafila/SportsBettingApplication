package com.epam.training.toto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;

import com.epam.training.toto.service.TotoService;

public class App {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###");
	private static final DecimalFormat PERC_FORMAT = new DecimalFormat("##.##");

	public static void main(String[] args) throws FileNotFoundException, IOException {

		try (Scanner in = new Scanner(System.in)) {
			TotoService myTotoService = startup();
			maxPrize(myTotoService);
			statistics(myTotoService);
			query(in, myTotoService);
		}

	}

	private static void query(Scanner in, TotoService myTotoService) {
		LocalDate myDate = queryDate(in);
		char[] myResults = queryResults(in);
		int[] displayRecord = myTotoService.queryPastGames(myDate, myResults);
		System.out.println("Result: hits: "+displayRecord[0]+", amount: "+DECIMAL_FORMAT.format(displayRecord[1]).replaceAll(",", " ")+" Ft");
	}

	private static void statistics(TotoService myTotoService) {
		System.out.println("Statistics about the distribution of the outcomes:");
		System.out.println("team #1 won: " + percentageFormatter(myTotoService.distOutcomes("1")) + " %, team #2 won: "
				+ percentageFormatter(myTotoService.distOutcomes("2")) + " %, draw: "
				+ percentageFormatter(myTotoService.distOutcomes("X")) + " %");
		System.out.println();
	}

	private static void maxPrize(TotoService myTotoService) {
		System.out.println("Largest Prize Won So Far: " + prizeFormatter(myTotoService.topPrize()));
		System.out.println();
	}

	private static TotoService startup() throws FileNotFoundException, IOException {
		System.out.println("======================================");
		System.out.println(" TOTO GAME STATISTICS SERVICE CONSOLE ");
		System.out.println("======================================");
		System.out.println();
		TotoService myTotoService = new TotoService();
		System.out.println();
		return myTotoService;
	}

	private static LocalDate queryDate(Scanner in) {
		System.out.println("Query results from the database:");
		LocalDate date;

		System.out.print("Enter date: ");
		boolean correctDateEntered = false;
		String reqDate;
		int year = 0;
		int month = 0;
		int day = 0;
		while (!correctDateEntered) {
			reqDate = in.next();
			if (reqDate.matches("\\d{4}.\\d{2}.\\d{2}.")) {
				year = Integer.parseInt(reqDate.substring(0, 4));
				month = Integer.parseInt(reqDate.substring(5, 7));
				day = Integer.parseInt(reqDate.substring(8, 10));
				if (month <= 12 && day <= 31) {
					correctDateEntered = true;
				} else {
					System.out.println("Try to enter a valid date!");
					;
				}
			} else {
				System.out.println("Please enter date in a \"yyyy.MM.dd.\" format!");
			}

		}
		date = LocalDate.of(year, month, day);

		return date;

	}

	private static char[] queryResults(Scanner in) {
		char[] results = new char[14];

		System.out.print("Enter outcomes (1/2/X): ");
		boolean correctOutcomesEntered = false;
		String entry = "";

		while (!correctOutcomesEntered) {

			entry = in.next();

		

		if (entry.length() == 14) {

			for (int i = 0; i < 14; i++) {
				if (entry.charAt(i) == '1' || entry.charAt(i) == '2' || entry.charAt(i) == 'X') {
					results[i] = entry.charAt(i);
					correctOutcomesEntered = true;
				} else {
					System.out.println("You can only use the 1/2/X characters!");
					correctOutcomesEntered = false;
					break;
				}

			}
		} else {
			System.out.println("Please use the \"21112XX1211112\" format!");
		}
		}
		return results;
	}

	private static String prizeFormatter(int prizeWon) {
		return ("" + DECIMAL_FORMAT.format(prizeWon).replaceAll(",", " ") + " Ft");
	}

	private static String percentageFormatter(double percentage) {
		return ("" + PERC_FORMAT.format(percentage));
	}

}
