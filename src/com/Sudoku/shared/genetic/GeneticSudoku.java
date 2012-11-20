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
	public static final int MAXGENERATIONS = 3000;

	private Stack<Generation> generations = new Stack<Generation>();

	public GeneticSudoku() {
		super();
	}

	public GeneticSudoku(int[][] s) {
		super(s);
		generations.push(new Generation(s));
		generations.peek().calculateFitness();

		int currentGeneration = 0;
		while (currentGeneration < MAXGENERATIONS) {
			currentGeneration++;
			System.out.println("Generación: " + currentGeneration);
			Generation nextGeneration = new Generation(null);
			while (nextGeneration.getSudokus().size() < Generation.POPULATIONSIZE) {
				LittleSudoku father = generations.peek().getTournamentWinner();
				LittleSudoku mother = generations.peek().getTournamentWinner();

				List<LittleSudoku> children = father.simpleCrossing(mother);

				for (LittleSudoku child : children) {
					child.mutate();
					nextGeneration.getSudokus().push(child);
				}
			}
			nextGeneration.calculateFitness();

			System.out.println("\tBest Fitness: "
					+ nextGeneration.getBestFitness());
			System.out.println("\tAverage Fitness: "
					+ nextGeneration.getAverageFitness());
			System.out.println("\tWorst Fitness: "
					+ nextGeneration.getWorstFitness());

			generations.push(nextGeneration);
		}

		int[][] bestS = generations.peek().getBestSudoku().getS();
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

}
