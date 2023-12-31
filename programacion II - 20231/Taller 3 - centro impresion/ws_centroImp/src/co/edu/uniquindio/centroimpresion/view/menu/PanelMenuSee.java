package co.edu.uniquindio.centroimpresion.view.menu;

import java.util.ArrayList;

import co.edu.uniquindio.centroimpresion.controllers.CtrlSeeImps;
import co.edu.uniquindio.centroimpresion.model.TipoEmpleado;
import co.edu.uniquindio.centroimpresion.view.custom.Boton;
import co.edu.uniquindio.centroimpresion.view.see.PanelSeeDocsVolver;
import co.edu.uniquindio.centroimpresion.view.see.PanelSeeImpCartucho;
import co.edu.uniquindio.centroimpresion.view.see.PanelSeeImpLaser;
import co.edu.uniquindio.centroimpresion.view.see.PanelSeeImps;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PanelMenuSee extends BorderPane {

	private TipoEmpleado tipoEmpleado;
	private Stage stage;

	public PanelMenuSee(TipoEmpleado tipoEmpleado, Stage stage) {
		this.tipoEmpleado = tipoEmpleado;
		this.stage = stage;
		initComponents();
	}

	public void initComponents() {
		VBox vbox = new VBox();
		Boton botonCola = new Boton("Ver Documentos en Cola", e -> setCenter(new PanelSeeDocsVolver(this)),
				"boton-opcion");
		VBox.setMargin(botonCola, new Insets(20));
		vbox.getChildren().add(botonCola);
		if (tipoEmpleado.puedeVerDocs()) {
			Boton boton = new Boton("Ver Documentos Impresos", e -> setCenter(new PanelSeeDocsVolver(this, true)),
					"boton-opcion");
			VBox.setMargin(boton, new Insets(20));
			vbox.getChildren().add(boton);
		}
		if (tipoEmpleado.puedeVerImpresoras()) {
			Boton botonImpresora = new Boton("Ver Impresora Seleccionada",
					e -> CtrlSeeImps.irAVerImpresoraSeleccionada(this, eventoVolver -> initComponents()),
					"boton-opcion");
			VBox.setMargin(botonImpresora, new Insets(20));
			vbox.getChildren().add(botonImpresora);
			Boton boton = new Boton("Ver Impresoras", e -> setCenter(new PanelSeeImps(this, stage)), "boton-opcion");
			VBox.setMargin(boton, new Insets(20));
			vbox.getChildren().add(boton);
			Boton botonCartucho = new Boton("Ver Impresoras Cartucho",
					e -> setCenter(new PanelSeeImpCartucho(this, stage)), "boton-opcion");
			VBox.setMargin(botonCartucho, new Insets(20));
			vbox.getChildren().add(botonCartucho);
		}
		if (tipoEmpleado.puedeVerImpresoras()) {
			Boton boton = new Boton("Ver Impresoras laser", e -> setCenter(new PanelSeeImpLaser(this, stage)),
					"boton-opcion");
			VBox.setMargin(boton, new Insets(20));
			vbox.getChildren().add(boton);
		}
		vbox.setId("centered-box");
		setCenter(vbox);
	}

	public static String[] generarOpciones(TipoEmpleado tipoEmpleado) {
		ArrayList<String> opcionesTxt = new ArrayList<String>();
		if (tipoEmpleado.puedeVerDocs()) {
			opcionesTxt.add("Cola");
		}
		if (tipoEmpleado.puedeVerImpresoras()) {
			opcionesTxt.add("impresoras laser");
			opcionesTxt.add("impresoras cartucho");
		}
		return opcionesTxt.toArray(new String[opcionesTxt.size()]);
	}

}
