package com.Sudoku.client;

import com.Sudoku.shared.Sudoku;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ServiceAsync {

	void isSudoku(int[][] s, AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void fuerzaBruta(int[][] s, AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void aproximacion(int[][] s,
			AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void inteligente(int[][] s, AsyncCallback<com.Sudoku.shared.Sudoku> callback);

	void genetico(int[][] s, AsyncCallback<Sudoku> callback);

}
