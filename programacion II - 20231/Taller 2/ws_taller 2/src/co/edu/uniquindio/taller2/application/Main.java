package co.edu.uniquindio.taller2.application;

import co.edu.uniquindio.taller2.exceptions.CuentaException;
import co.edu.uniquindio.taller2.model.CuentaAhorros;

public class Main {
	public static void main(String[] args) {
		System.out.println("Crea una cuenta con código AB001, saldo $1000 y tasa anual de $10000");
		CuentaAhorros cuentaAhorros = new CuentaAhorros("AB001", 1000f, 1.2f);
		System.out.println("La cuenta no está activa (su saldo es menor a $10000), "
				+ "entonces no se va a poder ni agregar ni retirar saldo");
		System.out.println("Intenta consignar $10000:");
		System.out.println("Cuenta (impresion): " + cuentaAhorros.imprimir());
		try {
			cuentaAhorros.consignarDinero(10000f);
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Cuenta (impresion): " + cuentaAhorros.imprimir());
		System.out.println("Intenta retirar $10000:");
		try {
			cuentaAhorros.retirarDinero(10000f);
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Cuenta (impresion): " + cuentaAhorros.imprimir());
		System.out.println("Verifica si la cuenta está activa: " + (cuentaAhorros.estaActiva() ? "si" : "no"));
		System.out.println("Activa una cuenta con el método activarCuenta agregandole $10000 a esta misma");
		try {
			cuentaAhorros.activarCuenta(10000f);
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Cuenta (impresion): " + cuentaAhorros.imprimir());
		System.out.println("Verifica si la cuenta está activa: " + (cuentaAhorros.estaActiva() ? "si" : "no"));
		System.out.println("Intenta consignar $5000:");
		try {
			cuentaAhorros.consignarDinero(5000f);
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Calcula los intereses de la cuenta y los agrega al saldo actual:");
		cuentaAhorros.calcularIntereses();

		System.out.println("Cuenta: " + cuentaAhorros);
		System.out.println("Extrae mensualmente una comisión y luego agrega los intereses a la cuenta");
		try {
			cuentaAhorros.extractoMensual();
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Cuenta: " + cuentaAhorros);
		System.out.println("Intenta activar la cuenta agregandole dinero");
		try {
			cuentaAhorros.activarCuenta(1000f);
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Intenta retirar de a $100 5 veces");
		for (int i = 0; i < 5; i++)
			try {
				cuentaAhorros.retirarDinero(100f);
				System.out.println("Cuenta: " + cuentaAhorros);
			} catch (CuentaException e) {
				System.err.println(e.getMessage());
			}
		System.out.println("Extrae mensualmente una comisión y luego agrega los intereses a la cuenta");
		try {
			cuentaAhorros.extractoMensual();
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Cuenta: " + cuentaAhorros);
		System.out.println("Cuenta (imprimir):" + cuentaAhorros.imprimir());
	}
}
