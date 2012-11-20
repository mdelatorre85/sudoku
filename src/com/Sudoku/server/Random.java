package com.Sudoku.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Stack;

public class Random {

	private static Stack<Double> randoms = new Stack<Double>();
	private static boolean isWebOK = true;

	public static double getRandom() {
		if (isWebOK) {
			if (randoms.isEmpty()) {
				try {
					String newUrl = "http://www.random.org/decimal-fractions/?num=100&dec=20&col=1&format=plain&rnd=new";
					URL u = new URL(newUrl);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(u.openStream(), "UTF-8"));
					String line;

					while ((line = reader.readLine()) != null) {
						randoms.push(Double.parseDouble(line));
					}
				} catch (IOException ex) {
					isWebOK = false;
					return Math.random();
				} catch (Exception e) {
					isWebOK = false;
					return Math.random();
				}
			}
			return randoms.pop();
		} else {
			return Math.random();
		}
	}

}
