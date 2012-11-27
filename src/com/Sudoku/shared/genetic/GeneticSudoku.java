package com.Sudoku.shared.genetic;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

import com.Sudoku.shared.Sudoku;

/**
 * Para causar desconcierto esta clase no representa un sudoku, a pesar de que
 * tiene Sudoku en el nombre.
 * 
 * Esta clase es el manager para la ejecución del algoritmo genético en el
 * servidor.
 * 
 * @author miguelangeldelatorre
 * 
 */
public class GeneticSudoku extends Sudoku implements Serializable {
	private static final long serialVersionUID = -8014190867918637851L;
	public static final int MAXGENERATIONS = 500;

	private Stack<Generation> generations = new Stack<Generation>();
	private int[][] originalSudoku;
	private LittleSudoku bestExample = null;

	public GeneticSudoku() {
		super();
	}

	public GeneticSudoku(int[][] s) {
		super(s);
		originalSudoku = s;

		generations.push(new Generation(s));
		generations.peek().calculateFitness();
		bestExample = generations.peek().getBestSudoku();

		int currentGeneration = 0;
		while (currentGeneration < MAXGENERATIONS) {
			currentGeneration++;
			System.out.println("Generación: " + currentGeneration);
			Generation nextGeneration = new Generation(null);

			// Elitismo
			if (bestExample.getFitness() < generations.peek().getBestSudoku()
					.getFitness()) {
				bestExample = generations.peek().getBestSudoku();
			}
			nextGeneration.getSudokus()
					.push(generations.peek().getBestSudoku());

			while (nextGeneration.getSudokus().size() < Generation.POPULATIONSIZE) {
				LittleSudoku father = generations.peek().getTournamentWinner();
				LittleSudoku mother = generations.peek().getTournamentWinner();

				List<LittleSudoku> children = father.simpleCrossing(mother);

				for (LittleSudoku child : children) {
					child.mutate();
					child = child.localSearch();
					nextGeneration.getSudokus().push(child);
				}
			}
			generations.peek().nullPopulation();
			nextGeneration.calculateFitness();
			System.out.println("\tBest Fitness: "
					+ nextGeneration.getBestFitness());
			System.out.println("\tAverage Fitness: "
					+ nextGeneration.getAverageFitness());
			System.out.println("\tWorst Fitness: "
					+ nextGeneration.getWorstFitness());
			generations.push(nextGeneration);

			if (nextGeneration.getBestFitness() == 81) {
				break;
			}
		}

		int[][] bestS = bestExample.getS();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				setCelda(i, j, bestS[i][j]);
			}
		}

		System.out.println("Ta·Dah");
	}

	public Stack<Generation> getGenerations() {
		return generations;
	}

	public LittleSudoku getBestSpecimen() {
		return bestExample;
	}

	public int[][] getOriginalSudoku() {
		return originalSudoku;
	}
}
