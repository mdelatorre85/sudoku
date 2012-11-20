package com.Sudoku.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Sudoku implements EntryPoint {

	private ClientController controller;

	private Sudoku() {
		controller = new ClientController();
	}

	public void onModuleLoad() {
		RootPanel.get().add(controller.getEscritorio());
		RootPanel.get().setStyleName("Escritorio");		
	}
}
