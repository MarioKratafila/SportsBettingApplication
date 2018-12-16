package com.epam.training.toto.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Hit {
	
	// Key is the number of hits, Value is the number of wagers for that hit count
	private Map<Integer,Integer> wagers = new HashMap<>();
	
	// Key is the number of hits, Value is the prize for that hit count
	private Map<Integer,Integer> prizes  = new HashMap<>();
	
	public Hit(int[] wagersFromCsv, String[] prizesFromCsv) {
		
		for (int i=0; i<wagersFromCsv.length; i++) {
			wagers.put(14-i, wagersFromCsv[i]);
		}

		for (int i=0; i<prizesFromCsv.length; i++) {
			int prize = Integer.parseInt(prizesFromCsv[i].substring(0, prizesFromCsv[i].length()-3).replaceAll(" ", ""));
			prizes.put(14-i,prize);
		}
	}

	public int getWagers(int hits) {
		return wagers.get(hits);
	}

	public int getPrize(int hits) {
		return prizes.get(hits);
	}
	
	public int getMaxPrize() {
		Collection<Integer> p = prizes.values();
		int max = 0;
		for (int prize: p) {
			if (prize > max) {
				max = prize;
			}
		}
		return max;
	}


	

}
