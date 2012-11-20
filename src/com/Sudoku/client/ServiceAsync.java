package com.Sudoku.client;

import com.Sudoku.shared.genetic.GeneticSudoku;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync {

	void isSudoku(int[][] s, AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void fuerzaBruta(int[][] s, AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void aproximacion(int[][] s,
			AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void inteligente(int[][] s, AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void genetic(int[][] s, AsyncCallback<GeneticSudoku> callback);



}
