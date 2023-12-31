package co.edu.uniquindio.p2.empresaenergia.controllers;

import co.edu.uniquindio.p2.empresaenergia.exceptions.PersonaException;
import co.edu.uniquindio.p2.empresaenergia.exceptions.NullException;
import co.edu.uniquindio.p2.empresaenergia.model.Persona;
import co.edu.uniquindio.p2.empresaenergia.model.Cliente;
import co.edu.uniquindio.p2.empresaenergia.model.ClienteNatural;
import co.edu.uniquindio.p2.empresaenergia.utility.FxUtility;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GestionClienteNaturalController {
	@FXML
	private TableView<Cliente> tableClientes;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtApellidos;

	@FXML
	private TextField txtEstrato;

	@FXML
	private TableColumn<Cliente, String> columnId;

	@FXML
	private TableColumn<Cliente, String> columnNombre;

	@FXML
	private SplitPane mainPane;

	@FXML
	private TextField txtCedula;

	@FXML
	private TableColumn<Cliente, String> columnTipoCliente;

	@FXML
	void initialize() {
		columnId.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getId()));
		columnNombre.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getNombre()));
		columnTipoCliente
				.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getTipoCliente().getNombre()));
		FxUtility.setAsIntegerTextfield(txtEstrato, 1, 6);
		actualizarTabla();
	}

	/**
	 * Obtiene un cliente natural a partir de los campos de texto
	 * 
	 * @return
	 */
	private ClienteNatural obtenerClienteNaturalCampos() {
		int estrato = 0;
		try {
			estrato = Integer.parseInt(txtEstrato.getText());
		} catch (NumberFormatException e) {
		}
		return new ClienteNatural(txtCedula.getText(), txtNombre.getText(), txtApellidos.getText(), estrato);
	}

	@FXML
	void agregarEvent(ActionEvent event) {
		agregarAction();
	}

	/**
	 * Agrega un cliente a la empresa de energía
	 */
	private void agregarAction() {
		try {
			ClienteNatural clienteNaturalCampos = obtenerClienteNaturalCampos();
			ModelFactoryController.getInstance().agregarCliente(clienteNaturalCampos);
			actualizarTabla();
			FxUtility.mostrarMensaje("Informacion", "El cliente ha sido agregado con éxito", "",
					AlertType.CONFIRMATION);
		} catch (NullException | PersonaException e) {
			FxUtility.mostrarMensaje("Advertencia", "No se pudo agregar el cliente", e.getMessage(), AlertType.WARNING);
		}
	}

	@FXML
	void actualizarEvent(ActionEvent event) {
		actualizarAction();
	}

	private void actualizarAction() {
		ClienteNatural clienteNaturalCampos = obtenerClienteNaturalCampos();
		try {
			ModelFactoryController.getInstance().actualizarCliente(clienteNaturalCampos);
			actualizarTabla();
			FxUtility.mostrarMensaje("Informacion", "Informacion", "El cliente fue actualizado con exito",
					AlertType.CONFIRMATION);
		} catch (NullException | PersonaException e) {
			FxUtility.mostrarMensaje("Advertencia", "No se pudo actualizar el cliente", e.getMessage(),
					AlertType.WARNING);
		}
	}

	@FXML
	void vaciarCamposEvent(ActionEvent event) {
		vaciarCamposAction();
	}

	private void vaciarCamposAction() {
		FxUtility.generarAdvertenciaSobreescritura(() -> {
			txtApellidos.setText("");
			txtCedula.setText("");
			txtEstrato.setText("");
			txtNombre.setText("");
		});
	}

	@FXML
	void cargarDatosEvent(ActionEvent event) {
		cargarDatosAction();
	}

	private void cargarDatosAction() {
		Persona cliente = tableClientes.getSelectionModel().getSelectedItem();
		if (cliente == null) {
			FxUtility.mostrarMensaje("Advertencia", "Selecciona un elemento en la tabla", "", AlertType.ERROR);
			return;
		}
		if (!(cliente instanceof ClienteNatural)) {
			FxUtility.mostrarMensaje("Advertencia", "El cliente no es natural", "", AlertType.ERROR);
			return;
		}
		FxUtility.generarAdvertenciaSobreescritura(() -> {
			ClienteNatural clienteNatural = (ClienteNatural) cliente;
			txtApellidos.setText(clienteNatural.getApellidos());
			txtCedula.setText(clienteNatural.getCedula());
			txtEstrato.setText(clienteNatural.getEstrato() + "");
			txtNombre.setText(clienteNatural.getNombre());
		});
	}

	@FXML
	void eliminarSelecionEvent(ActionEvent event) {
		eliminarSeleccionAction();
	}

	private void eliminarSeleccionAction() {
		Cliente cliente = tableClientes.getSelectionModel().getSelectedItem();
		if (cliente == null) {
			FxUtility.mostrarMensaje("Advertencia", "Selecciona un elemento en la tabla", "", AlertType.ERROR);
			return;
		}
		try {
			ModelFactoryController.getInstance().eliminarCliente(cliente);
			actualizarTabla();
			FxUtility.mostrarMensaje("Informacion", "El cliente fue eliminado con éxito", "", AlertType.CONFIRMATION);
		} catch (NullException | PersonaException e) {
			FxUtility.mostrarMensaje("Informacion", "El cliente no pudo ser eliminado", e.getMessage(),
					AlertType.CONFIRMATION);
		}
	}

	private void actualizarTabla() {
		tableClientes
				.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().getListaClientes()));
		tableClientes.refresh();
	}
}
