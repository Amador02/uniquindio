package co.edu.uniquindio.p2.empresaenergia.model;

import java.io.Serializable;

public abstract class Cliente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String id;
	protected String nombre;

	public Cliente(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public abstract String getTipoCliente();

	/**
	 * @return el id del cliente
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id el id del cliente a cambiar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre el nombre del cliente a cambiar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Determina si el cliente tiene un id determinado o no
	 * 
	 * @param id
	 * @return
	 */
	public boolean tieneId(String id) {
		return id.equals(this.id);
	}

	protected abstract boolean tieneTodoLleno();

	public boolean existeId() {
		return this.id != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Cliente [id=%s, nombre=%s]", id, nombre);
	}

}
