package com.Sudoku.server;

import com.Sudoku.client.Service;
import com.Sudoku.shared.Sudoku;
import com.Sudoku.shared.genetic.GeneticSudoku;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
public class ServiceImpl extends RemoteServiceServlet implements Service {

	private static final long serialVersionUID = -1185878854310405805L;

	@Override
	public com.Sudoku.shared.Sudoku isSudoku(int[][] s) {
		com.Sudoku.shared.Sudoku w = new com.Sudoku.shared.Sudoku(s);

		if (w.isValid()) {
			return w;
		}
		return null;
	}

	@Override
	public Sudoku fuerzaBruta(int[][] s) {
		Sudoku q = new Sudoku(s);
		if (q.isValid()) {
			q.solveFuerzaBruta();
			return q;
		} else {
			return null;
		}

	}

	@Override
	public Sudoku aproximacion(int[][] s) {
		Sudoku q = new Sudoku(s);
		if (q.isValid()) {
			q.solveAproximacion(400);
			return q;
		} else {
			return null;
		}
	}

	@Override
	public Sudoku inteligente(int[][] s) {
		// Sudoku q = new Sudoku(Sudokus.s1);
		// q.solveInteligente(100);
		// return q;
		// System.out.println(q.toStringPosibles());
		// return q;
		Sudoku q = new Sudoku(s);
		if (q.isValid()) {
			q.solveInteligente(400);
			return q;
		} else {
			return null;
		}
	}

	@Override
	public GeneticSudoku genetic(int[][] s) {
		return new GeneticSudoku(s);
	}


}
