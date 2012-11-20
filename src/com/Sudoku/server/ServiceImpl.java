package com.Sudoku.server;

import com.Sudoku.client.Service;
import com.Sudoku.shared.Sudoku;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServiceImpl extends RemoteServiceServlet implements Service {

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
	public Sudoku genetico(int[][] s) {

		
		
		
		
		
		
		
		
		
		return null;
	}

}
