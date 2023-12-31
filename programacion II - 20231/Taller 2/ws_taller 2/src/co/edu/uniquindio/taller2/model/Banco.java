package co.edu.uniquindio.taller2.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.taller2.exceptions.CuentaException;

public class Banco {

	protected String nombre;
	protected String direccion;
	protected String codigo;
	protected List<Cuenta> listaCuentas = new ArrayList<Cuenta>();

	/**
	 * Es el constructor de la clase Banco
	 *
	 * @param nombre
	 * @param direccion
	 * @param codigo
	 */
	public Banco(String nombre, String direccion, String codigo) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.codigo = codigo;
	}

	/**
	 * Es el constructor de la clase Banco sin parámetros
	 */
	public Banco() {
	}

	/**
	 * Busca una cuenta a partir de su c�digo {@code code}, muestra un null en caso
	 * de que no se encuentre
	 *
	 * @param codigo es el c�digo
	 * @return
	 */
	public Cuenta buscarCuenta(String codigo) {
		return getListaCuentas().stream().filter(cadaCuenta -> cadaCuenta.getCodigo().equals(codigo)).findFirst()
				.orElse(null);
	}

	/**
	 * Busca una cuenta a partir de su c�digo {@code codigo} y si no se encuentra
	 * muestra un error
	 *
	 * @param codigo es el código
	 * @return la cuenta
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 */
	private Cuenta buscarCuentaOError(String codigo) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuenta(codigo);
		if (cuentaEncontrada == null) {
			throw new CuentaException("La cuenta no existe (" + codigo + ")");
		}
		return cuentaEncontrada;
	}

	/**
	 * Agrega una cuenta de ahorros a la lista de cuentas, muestra un error en caso
	 * de que ya exista
	 *
	 * @param codigo    es el código
	 * @param saldo     es el saldo de la cuenta
	 * @param tasaAnual es la tasa anual de la cuenta
	 * @throws CuentaException en caso de que si se encuentre la cuenta
	 */
	public void agregarCuentaAhorros(String codigo, float saldo, float tasaAnual) throws CuentaException {
		if (validarCuenta(codigo)) {
			throw new CuentaException("La cuenta ya existe (" + codigo + ")");
		}
		getListaCuentas().add(new CuentaAhorros(codigo, saldo, tasaAnual));
	}

	/**
	 * Agrega una cuenta corriente a la lista de cuentas, muestra un error en caso
	 * de que ya exista
	 *
	 * @param codigo    es el código
	 * @param saldo     es el saldo de la cuenta
	 * @param tasaAnual es la tasa anual de la cuenta
	 * @throws CuentaException en caso de que si se encuentre la cuenta
	 */
	public void agregarCuentaCorriente(String codigo, float saldo, float tasaAnual) throws CuentaException {
		if (validarCuenta(codigo)) {
			throw new CuentaException("La cuenta ya existe (" + codigo + ")");
		}
		getListaCuentas().add(new CuentaCorriente(codigo, saldo, tasaAnual));
	}

	/**
	 * Elimina una cuenta a partir de su c�digo {@code codigo}, muestra un error en
	 * caso de que no se encuentre la cuenta
	 *
	 * @param codigo es el c�digo
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 */
	public void eliminarCuenta(String codigo) throws CuentaException {
		Cuenta cuenta = buscarCuenta(codigo);
		if (cuenta == null) {
			throw new CuentaException("La cuenta no existe (" + codigo + ")");
		}
		getListaCuentas().remove(cuenta);

	}

	/**
	 * Actualiza una cuenta a una nueva version de ella {@code cuenta}
	 *
	 * @param cuenta es la cuenta a actualizar
	 */
	public void actualizarCuenta(Cuenta cuenta) {
		Cuenta cuentaEncontrada = buscarCuenta(cuenta.getCodigo());

		int index = listaCuentas.indexOf(cuentaEncontrada);
		listaCuentas.set(index, cuenta);
	}

	/**
	 * Consigna un dinero a una cuenta que se encuentra a partir del c�digo
	 * {@code codigo}
	 *
	 * @param codigo es el c�digo
	 * @param dinero es el dinero a consignar
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 */
	public void consignarDineroCuenta(String codigo, float dinero) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		cuentaEncontrada.consignarDinero(dinero);
		actualizarCuenta(cuentaEncontrada);
	}

	/**
	 * Retira un dinero a una cuenta que se encuentra a partir del c�digo
	 * {@code codigo}
	 *
	 * @param codigo es el código
	 * @param dinero es el dinero a retirar
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 */
	public void retirarDineroCuenta(String codigo, float dinero) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		cuentaEncontrada.retirarDinero(dinero);
		actualizarCuenta(cuentaEncontrada);
	}

	/**
	 * Activa una cuenta de ahorros a partir de su codigo {@code codigo} y su saldo
	 * a agregar {@code dineroAAgregar}
	 * 
	 * @param codigo         es el código
	 * @param dineroAAgregar es el dinero a agregar
	 * @throws CuentaException en caso de que no se encuentre la cuenta o en caso de
	 *                         que no sea de ahorros la cuenta encontrada
	 */
	public void activarCuentaAhorrros(String codigo, float dineroAAgregar) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		if (!cuentaEncontrada.getClass().equals(CuentaAhorros.class)) {
			throw new CuentaException("La cuenta tiene que ser de ahorros");
		}

		CuentaAhorros cuentaEncontradaAhorros = (CuentaAhorros) cuentaEncontrada;
		cuentaEncontradaAhorros.activarCuenta(dineroAAgregar);
		actualizarCuenta(cuentaEncontradaAhorros);
	}

	/**
	 * Calcula los intereses de una cuenta a partir de su codigo
	 *
	 * @param codigo es el código
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 */
	public void calcularInteresesCuenta(String codigo) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		cuentaEncontrada.calcularIntereses();
		actualizarCuenta(cuentaEncontrada);
	}

	/**
	 * Extrae el interes mensual de una cuenta a partir de su codigo
	 *
	 * @param codigo es el código
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 */
	public void extraerMensualCuenta(String codigo) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		cuentaEncontrada.extractoMensual();
		actualizarCuenta(cuentaEncontrada);
	}

	/**
	 * Imprime una cuenta a partir de su codigo
	 *
	 * @param codigo es el código
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 * @return la cuenta en String
	 */
	public String imprimirCuenta(String codigo) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		return cuentaEncontrada.imprimir();
	}

	/**
	 * Determina si una cuenta de ahorros esta activa a partir de su codigo
	 *
	 * @param codigo es el código
	 * @return true si esta activa
	 * @throws CuentaException en caso de que no se encuentre la cuenta
	 */
	public boolean estaActivaCuenta(String codigo) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		if (!cuentaEncontrada.getClass().equals(CuentaAhorros.class)) {
			throw new CuentaException("La cuenta tiene que ser de ahorros");
		}
		CuentaAhorros cuentaEncontradaAhorros = (CuentaAhorros) cuentaEncontrada;
		return cuentaEncontradaAhorros.estaActiva();
	}

	/**
	 * Obtiene el sobregiro de una cuenta corriente a partir de su c�digo
	 *
	 * @param codigo es el código
	 * @return el sobregiro
	 * @throws CuentaException en caso de que no se encuentre la cuenta o si la
	 *                         cuenta no es corriente
	 */
	public float obtenerSobregiroCuenta(String codigo) throws CuentaException {
		Cuenta cuentaEncontrada = buscarCuentaOError(codigo);
		if (!cuentaEncontrada.getClass().equals(CuentaCorriente.class)) {
			throw new CuentaException("La cuenta tiene que ser corriente");
		}
		CuentaCorriente cuentaEncontradaCorriente = (CuentaCorriente) cuentaEncontrada;
		return cuentaEncontradaCorriente.getSobregiro();
	}

	/**
	 * Obtiene la lista de cuentas del banco
	 *
	 * @return la lista
	 */
	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}

	/**
	 * Determina si una cuenta existe o no a partir de su c�digo {@code codigo}
	 *
	 * @param codigo es el código
	 * @return true si se encuentra la cuentas
	 */
	public boolean validarCuenta(String codigo) {
		return buscarCuenta(codigo) != null;
	}

	/**
	 * Obtiene el nombre del banco
	 *
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre del banco
	 *
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la dirección del banco
	 *
	 * @return
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Cambia la dirección del banco
	 *
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene el código del banco
	 *
	 * @return
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Cambia el código del banco
	 *
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Banco other = (Banco) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Banco [nombre=%s, direccion=%s, codigo=%s, listaCuentas=%s]", nombre, direccion, codigo,
				listaCuentas);
	}

}
