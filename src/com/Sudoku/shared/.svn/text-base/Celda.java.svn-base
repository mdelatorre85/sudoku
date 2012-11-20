package com.Sudoku.shared;

import java.io.Serializable;

public class Celda implements Serializable {

	private static final long serialVersionUID = 8379004402745691071L;
	/**
	 * valor que indica si esta celda ha sido resualta y si se le ha fijado
	 * algún valor.
	 */
	private boolean isSet = false;
	/**
	 * Valor que tiene esta celda en caso de que halla sido fijada. Este valor
	 * debe de estar entre 1 y 9.
	 */
	private int valor;
	/**
	 * Arreglo de números booleanos que indica que valores podria tomar esta
	 * celda tomando en cuenta la fila, columna y cuadro en la que se encuentra
	 * en el sudoku. si el valor de posibles[0]= true quiere decir que esta
	 * celda puede tomar el valor 1.
	 */
	private boolean[] posibles;

	/**
	 * número de soluciones posibles a esta celda
	 */
	private int nposibles;

	/**
	 * Constructor por defecto
	 */
	public Celda() {
		posibles = new boolean[9];
		for (int i = 0; i < 9; i++) {
			posibles[i] = true;
		}
		valor = 0;
		nposibles = 9;
	}

	/**
	 * Contructor con valor de entrada
	 * 
	 * @param valor
	 *            el valor a fijar a la celda resuelta.
	 */
	public Celda(int valor) {
		this.valor = valor;
		isSet = true;
		posibles = new boolean[9];
		for (int i = 0; i < 9; i++) {
			posibles[i] = false;
		}
		nposibles = 0;
	}

	/**
	 * 
	 * @return true en caso de que la celda este resulta, false de lo contrario.
	 */
	public boolean isSet() {
		return isSet;
	}

	/**
	 * Fija como posible solución para esta celda al número recibido como
	 * parámetro.
	 * 
	 * @param posible
	 *            numero de 1 al 9
	 */
	public void setPosible(int posible) {
		if (!posibles[posible - 1]) {
			nposibles++;
		}
		posibles[posible - 1] = true;
	}

	/**
	 * Remueve al número recibido como parámetro como posible solución para esta
	 * celda.
	 * 
	 * @param posible
	 *            numero de 1 al 9
	 */
	public void setNotPosible(int posible) {
		if (posibles[posible - 1]) {
			nposibles--;
		}
		posibles[posible - 1] = false;
	}

	/**
	 * 
	 * @return el valor fijado para esta celda en caso que ya halla sido
	 *         resuleta, de lo contrario 0.
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * Fija el valor resultado para esta celda
	 * 
	 * @param valor
	 *            el resultado numerico del 1 al 9
	 */
	public void setValor(int valor) {
		this.valor = valor;
		isSet = true;
		for (int i = 0; i < 9; i++) {
			posibles[i] = false;
		}
		nposibles = 0;
	}

	/**
	 * Obtiene un arreglo de booleanos de 9 elementos en el que si la pocisión 0
	 * del arreglo es true entonces 1 es una solución posible para esta celda.
	 * 
	 * @return el arreglo de booleanos descrito
	 */
	public boolean[] getPosibles() {
		return posibles;
	}

	/**
	 * 
	 * @return el número de soluciones posibles para esta celda, 0 en caso de
	 *         que ya este resuelta.
	 */
	public int getNumberOfPossibleSolutions() {
		return nposibles;
	}

	/**
	 * 
	 * @return en caso de que no este resuelto obtiene la menor de las posibles
	 *         soluciones, de lo contrario 0.
	 */
	public int getSinglePosibleSolution() {
		for (int i = 0; i < 9; i++) {
			if (posibles[i]) {
				return i + 1;
			}
		}
		return 0;
	}

	public boolean equals(Celda c) {
		for (int i = 0; i < 9; i++) {
			if (posibles[i] != c.posibles[i]) {
				return false;
			}
			if (c.valor != valor) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Determina la celda que se recibe como parámetro tiene solo dos soluciones
	 * posibles y son las mismas que esta celda.
	 * 
	 * @param celda
	 *            la celda a la que se va a comparar a esta
	 * @return true en caso de que ambas celdas tengan las mismas posibles
	 *         soluciones y que sean solo dos.
	 */
	boolean isTwin(Celda celda) {
		if (celda.getNumberOfPossibleSolutions() == 2
				&& getNumberOfPossibleSolutions() == 2) {
			for (int i = 0; i < 9; i++) {
				if (posibles[i] != celda.posibles[i]) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Revisa si las celdas son Triplets
	 * Tres celdas son Triplets si cumplen con uno de estos tres casos:
	 * *Las tres celdas tienen 3 valores posibles y son los mismos
	 * *Dos de las tres celdas tienen tres valores y son los mismos y la 
	 * celda restante tiene dos valores que son un subconjunto de los tres
	 * valores de las otras dos celdas.
	 * *Una celda tiene tres valores, las celdas restantes tiene dos valores 
	 * que son un subconjunto de los tres valores de la otra celda.
	 *  
	 * @param c1 la celda a analizar
	 * @param c2 la celda a analizar
	 * @return verdadero en caso de que las celdas esta celda, c1 y c2 son 
	 * triplets, falso de los contrario.
	 */
	boolean isTriplets(Celda c1, Celda c2) {
		if (c1.getNumberOfPossibleSolutions() > 1
				&& c1.getNumberOfPossibleSolutions() < 4
				&& c2.getNumberOfPossibleSolutions() > 1
				&& c2.getNumberOfPossibleSolutions() < 4
				&& getNumberOfPossibleSolutions() > 1
				&& getNumberOfPossibleSolutions() < 4) {
			int n = c1.getNumberOfPossibleSolutions()
					+ c2.getNumberOfPossibleSolutions()
					+ getNumberOfPossibleSolutions();
			if (c1.getNumberOfPossibleSolutions() == 3
					&& c2.getNumberOfPossibleSolutions() == 3
					&& getNumberOfPossibleSolutions() == 3) {
				for (int i = 0; i < 9; i++) {
					if (posibles[i]) {
						if (!(c1.getPosibles()[i] && c2.getPosibles()[i])) {
							return false;
						}
					}
				}
				return true;
			} else if (getNumberOfPossibleSolutions() == 3) {
				int w = 0;
				for (int i = 0; i < 9; i++) {
					if (posibles[i] && c1.getPosibles()[i]) {
						w++;
					}
					if (posibles[i] && c2.getPosibles()[i]) {
						w++;
					}
				}
				w += 3;
				if (w == n) {
					return true;
				} else {
					return false;
				}
			} else if (c1.getNumberOfPossibleSolutions() == 3) {
				int w = 0;
				for (int i = 0; i < 9; i++) {
					if (posibles[i] && c1.getPosibles()[i]) {
						w++;
					}
					if (c1.getPosibles()[i] && c2.getPosibles()[i]) {
						w++;
					}
				}
				w += 3;
				if (w == n) {
					return true;
				} else {
					return false;
				}
			} else if (c2.getNumberOfPossibleSolutions() == 3) {
				int w = 0;
				for (int i = 0; i < 9; i++) {
					if (c2.getPosibles()[i] && c1.getPosibles()[i]) {
						w++;
					}
					if (posibles[i] && c2.getPosibles()[i]) {
						w++;
					}
				}
				w += 3;
				if (w == n) {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}
}