package com.Sudoku.shared.genetic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Un miembro de la población de alguna de las generaciones.
 * 
 * El puede cruzarse, mutar, realizar búsqueda local y otras mañas.
 * 
 * @author miguelangeldelatorre
 * 
 */
public class LittleSudoku implements Serializable, Cloneable {

	private static final double MUTATIONPROVABILITY = .0005;
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
	TreeSet<LittleCell> wrongCell = null;

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
			genotypeLenght = k;
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
		getFitness();
	}

	private static final long serialVersionUID = -4844066473974303503L;

	/**
	 * 
	 * @return La estructura del sudoku en su estado actual
	 */
	public int[][] getS() {
		fitness = -666;
		wrongCell = null;
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

	// public int[] getGenotype() {
	// fitness = -666;
	// wrongCell = null;
	// return genotype;
	// }

	public int getFitness() {

		if (fitness == -666) {
			TreeSet<LittleCell> badCells = new TreeSet<LittleCell>();

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					// evaluo que la celda no sea de las originales
					// if (originalS[i][j] == 0) {
					for (int k = i + 1; k < 9; k++) {
						if (s[i][j] == s[k][j]) {
							badCells.add(new LittleCell(i, j));
							badCells.add(new LittleCell(k, j));
						}
					}

					for (int k = j + 1; k < 9; k++) {
						if (s[i][j] == s[i][k]) {
							badCells.add(new LittleCell(i, j));
							badCells.add(new LittleCell(i, k));
						}
					}

					int k = 0, l = 0, m = 0, n = 0;
					if (i < 3) {
						k = 0;
						l = 3;
					} else if (i < 6) {
						k = 3;
						l = 6;
					} else if (i < 9) {
						k = 6;
						l = 9;
					}

					if (j < 3) {
						m = 0;
						n = 3;
					} else if (j < 6) {
						m = 3;
						n = 6;
					} else if (j < 9) {
						m = 6;
						n = 9;
					}

					for (int a = k; a < l; a++) {
						for (int b = m; b < n; b++) {
							if (a != i && b != j) {
								if (s[a][b] == s[i][j]) {
									badCells.add(new LittleCell(i, j));
									badCells.add(new LittleCell(a, b));
								}
							}
						}
					}
					// }
				}
			}

			fitness = 81 - badCells.size();
		}
		return fitness;
	}

	public void mutate() {
		for (int w = 0; w < genotype.length; w++) {
			if (Math.random() <= MUTATIONPROVABILITY) {
				int y = (int) Math.floor(Math.random() * genotypeLenght);
				int valueUno = genotype[w];
				int valueDos = genotype[y];
				genotype[y] = valueUno;
				genotype[w] = valueDos;
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
				fitness = -666;
				wrongCell = null;
			}
		}
	}

	public List<LittleSudoku> simpleCrossing(LittleSudoku mother) {
		ArrayList<LittleSudoku> retorno = new ArrayList<LittleSudoku>();
		if (Math.random() <= CROSSPROVABILITY) {
			int crossPoint = (int) (Math.floor(Math.random() * genotypeLenght
					- 2));
			crossPoint++;

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

	public LittleSudoku localSearch() {
		if (!isValid()) {
			getWrongCells();
			/**
			 * Ya estan las wrongCells en la colección Se busca acceder de forma
			 * aleatoria a las wrongcells para mejorar la exploración
			 */
			// Stack<Integer> wrongCellIndexValues = new Stack<Integer>();
			// List<LittleCell> wronCellList = new ArrayList<LittleCell>();
			// Iterator<LittleCell> wrongCellIterator = wrongCell.iterator();
			// for (int i = 0; wrongCellIterator.hasNext(); i++) {
			// wrongCellIndexValues.push(i);
			// wronCellList.add(wrongCellIterator.next());
			// }

			// while (wrongCellIndexValues.size() > 0) {
			// int index = (int) Math.floor(Math.random()
			// * wrongCellIndexValues.size());
			// LittleCell cell = wronCellList.get(index);
			// wrongCellIndexValues.remove(index);
			// if (originalS[cell.getX()][cell.getY()] == 0) {
			//
			// boolean[] availableValues = new boolean[9];
			// for (int i = 0; i < 9; i++) {
			// availableValues[s[cell.getX()][i] - 1] = true;
			// availableValues[s[i][cell.getY()] - 1] = true;
			// }
			// int a = cell.getX(), b = cell.getY();
			// int k = 0, l = 0, m = 0, n = 0;
			// if (a < 3) {
			// k = 0;
			// l = 3;
			// } else if (a < 6) {
			// k = 3;
			// l = 6;
			// } else if (a < 9) {
			// k = 6;
			// l = 9;
			// }
			//
			// if (b < 3) {
			// m = 0;
			// n = 3;
			// } else if (b < 6) {
			// m = 3;
			// n = 6;
			// } else if (b < 9) {
			// m = 6;
			// n = 9;
			// }
			//
			// for (int i = k; i < l; i++) {
			// for (int j = m; j < n; j++) {
			// availableValues[s[i][j] - 1] = true;
			// }
			// }
			// // los probables valores ya estan en availableValues
			// Stack<Integer> availableValuesStack = new Stack<Integer>();
			// for (int i = 0; i < availableValues.length; i++) {
			// if (availableValues[i] == false) {
			// availableValuesStack.push(new Integer(i + 1));
			// }
			// }
			// if (availableValuesStack.size() > 0) {
			// if (availableValuesStack.size() == 1) {
			// s[cell.getX()][cell.getY()] = availableValuesStack
			// .peek();
			// } else {
			// int indice = (int) Math.floor(Math.random()
			// * availableValuesStack.size());
			// // i+1 is a available value;
			// s[cell.getX()][cell.getY()] = availableValuesStack
			// .get(indice);
			// }
			// }
			// }
			//
			// }

			for (LittleCell cell : wrongCell) {
				boolean[] availableValues = new boolean[9];
				for (int i = 0; i < 9; i++) {
					availableValues[s[cell.getX()][i] - 1] = true;
					availableValues[s[i][cell.getY()] - 1] = true;
				}

				int a = cell.getX(), b = cell.getY();
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
						availableValues[s[i][j] - 1] = true;
					}
				}
				// los probables valores ya estan en availableValues
				Stack<Integer> availableValuesStack = new Stack<Integer>();
				for (int i = 0; i < availableValues.length; i++) {
					if (availableValues[i] == false) {
						availableValuesStack.push(new Integer(i + 1));
					}
				}
				if (availableValuesStack.size() > 0) {
					if (availableValuesStack.size() == 0) {
						s[cell.getX()][cell.getY()] = availableValuesStack
								.peek();
					} else {
						int i = (int) Math.floor(Math.random()
								* availableValuesStack.size());
						// i+1 is a available value;
						s[cell.getX()][cell.getY()] = availableValuesStack
								.get(i);
					}
				}
			}

		}
		return this;

	}

	public TreeSet<LittleCell> getWrongCells() {

		if (wrongCell == null) {
			wrongCell = new TreeSet<LittleCell>();

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					for (int k = i + 1; k < 9; k++) {
						if (s[i][j] == s[k][j]) {
							if (originalS[i][j] == 0) {
								wrongCell.add(new LittleCell(i, j));
							}
							if (originalS[k][j] == 0) {
								wrongCell.add(new LittleCell(k, j));
							}
						}
					}

					for (int k = j + 1; k < 9; k++) {
						if (s[i][j] == s[i][k]) {
							if (originalS[i][j] == 0) {
								wrongCell.add(new LittleCell(i, j));
							}
							if (originalS[i][k] == 0) {
								wrongCell.add(new LittleCell(i, k));
							}
						}
					}

					int k = 0, l = 0, m = 0, n = 0;
					if (i < 3) {
						k = 0;
						l = 3;
					} else if (i < 6) {
						k = 3;
						l = 6;
					} else if (i < 9) {
						k = 6;
						l = 9;
					}

					if (j < 3) {
						m = 0;
						n = 3;
					} else if (j < 6) {
						m = 3;
						n = 6;
					} else if (j < 9) {
						m = 6;
						n = 9;
					}

					for (int a = k; a < l; a++) {
						for (int b = m; b < n; b++) {
							if (a != i && b != j) {
								if (s[a][b] == s[i][j]) {
									if (originalS[i][j] == 0) {
										wrongCell.add(new LittleCell(i, j));
									}
									if (originalS[a][b] == 0) {
										wrongCell.add(new LittleCell(a, b));
									}
								}
							}
						}
					}
				}
			}
		}
		return wrongCell;
	}

	public boolean isValid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				for (int k = j + 1; k < 9; k++) {
					if (k != j) {
						if (s[i][j] == s[i][k]) {
							return false;
						}
					}
				}

				for (int k = i + 1; k < 9; k++) {
					if (k != i) {
						if (s[i][j] == s[k][j]) {
							return false;
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
									return false;
								}
						}
					}

				}

			}
		}

		return true;
	}

	public LittleSudoku clone() {
		LittleSudoku clon = new LittleSudoku();
		clon.s = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				clon.s[i][j] = s[i][j];
			}
		}
		clon.fitness = -666;
		clon.wrongCell = null;
		clon.genotype = new int[genotypeLenght];
		int k = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (originalS[i][j] == 0) {
					clon.genotype[k] = s[i][j];
					k++;
				}
			}
		}
		clon.getFitness();

		return clon;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		if (s != null) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					sb.append(s[i][j]);
					sb.append(" ");
				}
				sb.append("\n");
			}
			return sb.toString();
		}
		return super.toString();

	}

	public void setOriginalSudoku(int[][] originalSudoku) {
		LittleSudoku.originalS = originalSudoku;
	}

}
