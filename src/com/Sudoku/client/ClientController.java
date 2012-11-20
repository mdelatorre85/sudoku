package com.Sudoku.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class ClientController {
	private PanelEscritorio escritorio;
	private ServiceAsync server;
	private Tabla tabla;

	public ClientController() {
		server = GWT.create(Service.class);

		escritorio = new PanelEscritorio(this);

		tabla = new Tabla(this);
		escritorio.getPanelEscritorio().add(tabla, 50, 50);
	}

	public ServiceAsync getServer() {
		return server;
	}

	public AbsolutePanel getEscritorio() {
		return escritorio.getPanelEscritorio();
	}

}
