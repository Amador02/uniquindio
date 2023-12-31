package co.edu.uniquindio.clinicaVeterinaria.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.clinicaVeterinaria.exceptions.ClienteNoExistenteException;
import co.edu.uniquindio.clinicaVeterinaria.model.Cliente;
import co.edu.uniquindio.clinicaVeterinaria.model.Mascota;
import co.edu.uniquindio.clinicaVeterinaria.model.Sexo;
import co.edu.uniquindio.clinicaVeterinaria.model.Tipo;
import co.edu.uniquindio.clinicaVeterinaria.services.FxUtility;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import one.jpro.routing.LinkUtil;

public class RegistroMascotaController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<Cliente> tblCliente;

	@FXML
	private TableColumn<Cliente, String> colNombreCliente;

	@FXML
	private TextField txtNombre;

	@FXML
	private ComboBox<Sexo> cbSexo;

	@FXML
	private TextField txtRaza;

	@FXML
	private TextField txtEdad;

	@FXML
	private GridPane gridMascota;

	@FXML
	private TableColumn<Cliente, String> colCedulaCliente;

	@FXML
	private TextField txtCedula;

	@FXML
	private Button btnRegistrar;

	@FXML
	private ComboBox<Tipo> cbTipo;

	private static RegistroMascotaController instance;

	public static RegistroMascotaController getInstance() {
		return instance;
	}

	public RegistroMascotaController() {
		instance = this;
	}

	@FXML
	void initialize() {
		ModelFactoryController.getInstance().loadData();
		cbSexo.getItems().addAll(Sexo.values());
		cbTipo.getItems().addAll(Tipo.values());
		FxUtility.setAsIntegerTextfield(txtEdad);
		FxUtility.setAsIntegerTextfield(txtCedula);
		tblCliente.setItems(
				FXCollections.observableArrayList(ModelFactoryController.getInstance().filtrarClienteCedu("")));

		txtCedula.textProperty().addListener((observable, oldValue, newValue) -> {
			actualizarTabla(newValue);
		});
		colCedulaCliente.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getCedula()));
		colNombreCliente.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getNombre()));

		tblCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			gridMascota.setDisable(newValue == null);

		});
	}

	@FXML
	void registrarEvent(ActionEvent event) {
		registrarAction();
	}

	private void registrarAction() {
		if (!verificarCampos()) {
			Menucontroller.getInstance().crearAlerta("Llene todos los campos");
			return;
		}
		try {
			Cliente cliente = tblCliente.getSelectionModel().getSelectedItem();
			ModelFactoryController.getInstance().getClinica().agregarMascota(cliente,
					new Mascota(cliente, txtNombre.getText().trim(), Integer.valueOf(txtEdad.getText()),
							txtRaza.getText().trim(), cbTipo.getValue(), cbSexo.getValue()));
			Menucontroller.getInstance().crearAlerta("Mascota agregada con éxito");
			ModelFactoryController.getInstance().saveData();
			vaciarCampos();
			LinkUtil.gotoPage(btnRegistrar, "/inicio");
		} catch (ClienteNoExistenteException e) {
			Menucontroller.getInstance().crearAlerta("No existe ningun cliente con esta cedula");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	private boolean verificarCampos() {
		if (txtNombre.getText().trim().isEmpty() || txtRaza.getText().trim().isEmpty()
				|| txtEdad.getText().trim().isEmpty() || tblCliente.getSelectionModel().getSelectedItem() == null
				|| cbSexo.getValue() == null || cbTipo.getValue() == null) {
			return false;
		}
		return true;
	}

	private void vaciarCampos() {
		txtNombre.clear();
		cbSexo.setValue(null);
		txtRaza.clear();
		txtEdad.clear();
		txtCedula.clear();
		tblCliente.getSelectionModel().clearSelection();
		cbTipo.setValue(null);
	}

	private void actualizarTabla(String newValue) {
		tblCliente.setItems(
				FXCollections.observableArrayList(ModelFactoryController.getInstance().filtrarClienteCedu(newValue)));
		tblCliente.refresh();
	}

	public void actualizarTabla() {
		actualizarTabla("1");
		actualizarTabla("");
	}
}
