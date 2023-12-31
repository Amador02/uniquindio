package co.edu.uniquindio.taller2.tests;

import org.junit.Assert;
import org.junit.Test;

import co.edu.uniquindio.taller2.model.Banco;
import co.edu.uniquindio.taller2.model.Cuenta;
import co.edu.uniquindio.taller2.model.CuentaAhorros;
import co.edu.uniquindio.taller2.model.CuentaCorriente;

public class TestEqualsFalse {

	@Test
	public void testCuentaAhorrosEquals2() {
		// Falla porque tienen diferente c�digo
		CuentaAhorros cuentaAhorros = new CuentaAhorros("0001", 0f, 0f);
		CuentaAhorros cuentaAhorros2 = new CuentaAhorros("0002", 100, 100);
		Assert.assertEquals(cuentaAhorros, cuentaAhorros2);
	}

	@Test
	public void testCuentaAhorrosEqualsCuentaCorriente() {
		// Falla porque tienen el mismo c�digo pero no son de la misma clase
		Cuenta cuentaAhorros = new CuentaAhorros("0001", 0f, 0f);
		Cuenta cuentaCorriente = new CuentaCorriente("0001", 0f, 0f);
		Assert.assertEquals(cuentaAhorros, cuentaCorriente);
	}

	@Test
	public void testBancoEquals2() {
		// Falla porque no tienen el mismo c�digo
		Banco banco = new Banco("Bancolombia", "Cra 10N # 5-30", "AB-001");
		Banco banco2 = new Banco("NuBank", "Cra 5N # 10-30", "AB-002");
		Assert.assertEquals(banco, banco2);
	}
}
