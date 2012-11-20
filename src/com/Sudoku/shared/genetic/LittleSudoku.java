package com.Sudoku.shared.genetic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Un miembro de la población de alguna de las generaciones.
 * 
 * El puede cruzarse, mutar y otras mañas.
 * 
 * @author miguelangeldelatorre
 * 
 */
public class LittleSudoku implements Serializable {

	private static final double MUTATIONPROVABILITY = .005;
	private static final double CROSSPROVABILITY = .7;

	static int genotypeLenght = -666;

	/**
	 * La estructura del sudoku como fue ingresada originalmente por el usuario.
	 * esta se requiere para saber que pocisiones se pueden modificar por
	 * cruzamineto o mutación
	 */
	private static int[][] originalS = null;
	/**
	 * La estructura actual de
	 */
	private int[][] s = null;
	private int[] genotype = null;
	private int fitness = -666;

	public LittleSudoku(int[][] s, int[][] originalSudoku) {
		if (originalS == null) {
			originalS = originalSudoku;
		}
		this.s = s;

		if (genotypeLenght == -666) {
			int k = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (originalS[i][j] == 0) {
						k++;
					}
				}
			}
			genotypeLenght = k;
		}

		this.genotype = new int[genotypeLenght];
		int k = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (originalS[i][j] == 0) {
					genotype[k] = s[i][j];
					k++;
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private LittleSudoku() {
	}

	private LittleSudoku(int[][] originalSudoku, int[] genotype) {
		if (originalS == null) {
			originalS = originalSudoku;
		}
		this.genotype = genotype;
		if (genotypeLenght == -666) {
			int k = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (originalS[i][j] == 0) {
						k++;
					}
				}
			}
		}
		s = new int[9][9];
		int k = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (originalS[i][j] == 0) {
					s[i][j] = genotype[k];
					k++;
				} else {
					s[i][j] = originalS[i][j];
				}
			}
		}

	}

	private static final long serialVersionUID = -4844066473974303503L;

	/**
	 * 
	 * @return La estructura del sudoku en su estado actual
	 */
	public int[][] getS() {
		if (s == null) {
			s = new int[9][9];
		}
		return s;
	}

	/**
	 * 
	 * @return la estructura del sudoku como fue ingresada originalmente por el
	 *         usuario
	 */
	public int[][] getOriginalS() {
		if (originalS == null) {
			originalS = new int[9][9];
		}
		return originalS;
	}

	public int[] getGenotype() {
		return genotype;
	}

	public int getFitness() {

		if (fitness == -666) {

			TreeSet<LittleCell> badCells = new TreeSet<LittleCell>();

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					// evaluo que la celda no sea de las originales
					if (originalS[i][j] == 0) {

						for (int k = i + 1; k < 9; k++) {
							if (s[i][j] == s[k][j]) {
								badCells.add(new LittleCell(i, j));
								if (originalS[k][j] == 0) {
									badCells.add(new LittleCell(k, j));
								}
							}
						}

						for (int k = j + 1; k < 9; k++) {
							if (s[i][j] == s[i][k]) {
								badCells.add(new LittleCell(i, j));
								if (originalS[i][k] == 0) {
									badCells.add(new LittleCell(i, k));
								}
							}
						}
					}
				}
			}

			for (int a = 0; a < 9; a++) {
				for (int b = 0; b < 9; b++) {
					if (originalS[a][b] == 0) {
						int k = 0, l = 0, m = 0, n = 0;
						if (a < 3) {
							k = 0;
							l = 3;
						} else if (a < 6) {
							k = 3;
							l = 6;
						} else if (a < 9) {
							k = 6;
							l = 9;
						}

						if (b < 3) {
							m = 0;
							n = 3;
						} else if (b < 6) {
							m = 3;
							n = 6;
						} else if (b < 9) {
							m = 6;
							n = 9;
						}

						for (int i = k; i < l; i++) {
							for (int j = m; j < n; j++) {
								if (a != i && b != j)
									if (s[i][j] == s[a][b]) {
										if (originalS[i][j] == 0) {
											badCells.add(new LittleCell(i, j));
										}
										badCells.add(new LittleCell(a, b));
									}
							}
						}

					}

				}
			}
			fitness = 81 - badCells.size();
		}
		return fitness;
	}

	private class LittleCell implements Comparable<LittleCell> {

		private int x, y;

		public LittleCell(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(LittleCell o) {
			return getIndex() - o.getIndex();
		}

		private int getIndex() {
			return x * 10 + y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LittleCell) {
				return getIndex() == ((LittleCell) obj).getIndex();
			}
			return false;
		}

		@Override
		public int hashCode() {
			StringBuilder sb = new StringBuilder();
			sb.append(x);
			sb.append(",");
			sb.append(y);
			return sb.toString().hashCode();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("(");
			sb.append(x);
			sb.append(",");
			sb.append(y);
			sb.append(")");
			return sb.toString();
		}

	}

	public void mutate() {
		if (Math.random() <= MUTATIONPROVABILITY) {
			int x = (int) Math.floor(Math.random() * genotypeLenght);
			int y = (int) Math.floor(Math.random() * genotypeLenght);
			int valueUno = genotype[x];
			int valueDos = genotype[y];
			genotype[y] = valueUno;
			genotype[x] = valueDos;

			// s = new int[9][9];
			int k = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (originalS[i][j] == 0) {
						s[i][j] = genotype[k];
						k++;
					} else {
						s[i][j] = originalS[i][j];
					}
				}
			}
		}
	}

	public List<LittleSudoku> simpleCrossing(LittleSudoku mother) {
		ArrayList<LittleSudoku> retorno = new ArrayList<LittleSudoku>();
		if (Math.random() <= CROSSPROVABILITY) {
			int crossPoint = (int) (Math.floor(Math.random() * genotypeLenght
					- 1));
			if (crossPoint == 0) {
				crossPoint++;
			}
			int[] hijoA = new int[genotypeLenght];
			int[] hijoB = new int[genotypeLenght];

			for (int i = 0; i < genotypeLenght; i++) {
				if (i < crossPoint) {
					hijoA[i] = genotype[i];
					hijoB[i] = mother.genotype[i];
				} else {
					hijoB[i] = genotype[i];
					hijoA[i] = mother.genotype[i];
				}
			}

			retorno.add(new LittleSudoku(originalS, hijoA));
			retorno.add(new LittleSudoku(originalS, hijoB));

		} else {
			retorno.add(this);
			retorno.add(mother);
		}
		return retorno;
	}

}
