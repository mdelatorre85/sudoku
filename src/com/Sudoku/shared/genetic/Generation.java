package com.Sudoku.shared.genetic;

import java.io.Serializable;
import java.util.Stack;

/**
 * 
 * @author miguelangeldelatorre
 * 
 */
public class Generation implements Serializable {

	private static final long serialVersionUID = -3189980947538288543L;
	private static final int TOURNAMENTSIZE = 20;
	public static final int POPULATIONSIZE = 350;

	private Stack<LittleSudoku> sudokus;

	private LittleSudoku bestSudoku;
	private int bestFitness;

	private LittleSudoku worstSudoku;
	private int worstFitness;

	private double averageFitness;
	private double totalFitness;

	public Generation(int[][] s) {
		sudokus = new Stack<LittleSudoku>();
		if (s != null) {
			int[][] sClone = null;

			for (int i = 0; i < POPULATIONSIZE; i++) {
				sClone = new int[9][9];
				for (int j = 0; j < 9; j++) {
					for (int k = 0; k < 9; k++) {
						if (s[j][k] == 0) {
							sClone[j][k] = (int) (Math
									.floor(Math.random() * 9.0d));
							if (sClone[j][k] < 9) {
								sClone[j][k]++;
							}
						} else {
							sClone[j][k] = s[j][k];
						}
					}
				}
				sudokus.push(new LittleSudoku(sClone, s));
			}
		}
	}

	@SuppressWarnings("unused")
	private Generation() {

	}

	public Stack<LittleSudoku> getSudokus() {
		if (sudokus == null) {
			sudokus = new Stack<LittleSudoku>();
		}
		return sudokus;
	}

	public void calculateFitness() {
		bestFitness = Integer.MIN_VALUE;
		worstFitness = Integer.MAX_VALUE;

		for (LittleSudoku sudoku : sudokus) {
			int f = sudoku.getFitness();
			if (f > bestFitness) {
				bestFitness = f;
				bestSudoku = sudoku;
			}
			if (f < worstFitness) {
				worstFitness = f;
				worstSudoku = sudoku;
			}
			totalFitness += (double) f;
		}

		averageFitness = totalFitness / (double) POPULATIONSIZE;
	}

	public int getBestFitness() {
		return bestFitness;
	}

	public LittleSudoku getBestSudoku() {
		return bestSudoku;
	}

	public int getWorstFitness() {
		return worstFitness;
	}

	public LittleSudoku getWorstSudoku() {
		return worstSudoku;
	}

	public double getAverageFitness() {
		return averageFitness;
	}

	public double getTotalFitness() {
		return totalFitness;
	}

	public LittleSudoku getTournamentWinner() {
		LittleSudoku tournamentWinner = sudokus.get((int) Math.floor(Math
				.random() * POPULATIONSIZE)), tournamentParticipant;
		for (int i = 0; i < TOURNAMENTSIZE; i++) {
			tournamentParticipant = sudokus.get((int) Math.floor(Math.random()
					* POPULATIONSIZE));
			if (tournamentParticipant.getFitness() > tournamentWinner
					.getFitness()) {
				tournamentWinner = tournamentParticipant;
			}
		}

		return tournamentWinner;
	}
}
