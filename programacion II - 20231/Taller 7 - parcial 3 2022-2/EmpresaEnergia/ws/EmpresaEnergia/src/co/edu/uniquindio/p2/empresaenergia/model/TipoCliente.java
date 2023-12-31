package co.edu.uniquindio.p2.empresaenergia.model;

public enum TipoCliente {
	JURIDICO("Jurídico"), NATURAL("Natural");

	private String nombre;

	/**
	 * Es el constructor del enum de tipo de cliente
	 * 
	 * @param nombre
	 */
	private TipoCliente(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el nombre del tipo de cliente
	 */
	public String getNombre() {
		return nombre;
	}
}
