package com.Sudoku.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("s")
public interface Service extends RemoteService {

	com.Sudoku.shared.Sudoku isSudoku(int[][] s);

	com.Sudoku.shared.Sudoku fuerzaBruta(int[][] s);

	com.Sudoku.shared.Sudoku aproximacion(int[][] s);

	com.Sudoku.shared.Sudoku inteligente(int[][] s);
	
	com.Sudoku.shared.Sudoku genetico(int[][] s);

}
