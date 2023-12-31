package co.edu.uniquindio.taller.ejercicio2a3.model;

import java.util.Comparator;

public class BankAccount implements Comparator<BankAccount>, Comparable<BankAccount> {

	private final String accountNumber;
	private AccountType accountType;
	private Double balance;
	private Client owner;

	/**
	 * Es el constructor de la cuenta bancaria sin parámetros
	 */
	public BankAccount() {
		accountNumber = null;
	}

	/**
	 * Es el constructor de la cuenta bancaria sin un dueño
	 * 
	 * @param accountNumber
	 * @param accountType
	 * @see AccountType
	 */
	public BankAccount(final String accountNumber, final AccountType accountType) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = 0d;
		this.owner = new Client();
	}

	/**
	 * Es el constructor de la cuenta bancaria con dueño
	 * 
	 * @param accountNumber
	 * @param accountType
	 * @param owner
	 * @see AccountType
	 */
	public BankAccount(final String accountNumber, final AccountType accountType, final Client owner) {
		this(accountNumber, accountType);
		this.owner = owner;
	}

	/**
	 * Obtiene el número de cuenta del propietario de la cuenta
	 * 
	 * @return
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Obtiene el tipo de cuenta del propietario de la cuenta
	 * 
	 * @return
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * Cambia el tipo de cuenta del propietario de la cuenta
	 * 
	 * @param accountType
	 */
	public void setAccountType(final AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * Obtiene el saldo actual del propietario de la cuenta
	 * 
	 * @return
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * Cambia el saldo actual del propietario de la cuenta
	 * 
	 * @param balance
	 */
	public void setBalance(final Double balance) {
		this.balance = balance;
	}

	/**
	 * Determina si el numbero de cuenta existe (no es null ni está vacía la cadena)
	 * 
	 * @return
	 */
	public boolean getAccountNumberExists() {
		return !(getAccountNumber() == null || getAccountNumber().isEmpty());
	}

	/**
	 * Determina si el tipo de cuenta existe (no es null)
	 * 
	 * @return
	 */
	public boolean getAccountTypeExists() {
		return getAccountType() != null;
	}

	/**
	 * Determina si la cuenta bancaria existe preguntando si existe el nombre,
	 * apellido, numero de cuenta y tipo de cuenta
	 * 
	 * @see {@link #getAccountNumberExists()}
	 *      <li>{@link #getAccountTypeExists()}
	 * @return si la cuenta bancaria existe (true) o no (false)
	 */
	public boolean getExists() {
		return getAccountNumberExists() && getAccountTypeExists();
	}

	/**
	 * Obtiene el saldo con formato $dinero con 2 decimales, ejemplo: Saldo = 2000
	 * Saldo con formato: $2000.00
	 * 
	 * @return
	 */
	public String getFormattedBalance() {
		return "$" + String.format("%.2f", getBalance()).replace(",", ".");
	}

	/**
	 * Consigna un sald {@code balance} a la cuenta, muestra un error en caso de que
	 * la cuenta no exista
	 * 
	 * @param balance
	 * @throws BankException
	 * @see {@link #getExists()}
	 */
	public String consignBalance(final Double balance) throws BankException {
		if (!getExists())
			throw new BankException("La cuenta no existe");
		setBalance(getBalance() + balance);
		return "Han sido agregados " + balance + " a la cuenta (" + getAccountNumber() + ")";
	}

	/**
	 * Retira un saldo {@code balance} de la cuenta, muestra un error si el saldo no
	 * es suficiente o la cuenta no existe
	 * 
	 * @param balance
	 * @throws BankException
	 * @see {@link #getExists()}
	 */
	public void withDrawBalance(final Double balance) throws BankException {
		if (!getExists())
			throw new BankException("La cuenta no existe");
		if (getBalance() < balance)
			throw new BankException("El saldo es insuficiente");
		setBalance(getBalance() - balance);
	}

	/**
	 * Envía dinero de una cuenta a otra, suelta un error en caso de que alguna
	 * cuenta no exista o si el dinero a enviar no es suficiente
	 * 
	 * @param cb
	 * @param amount
	 * @throws BankException
	 * @see {@link #consignBalance()}
	 *      <li>{@link #withDrawBalance()}
	 *      <li>{@link #getExists()}
	 */
	public void sendBalance(final BankAccount cb, final Double amount) throws BankException {
		if (!getExists() || !cb.getExists())
			throw new BankException("Alguna de las cuentas no existe");
		withDrawBalance(amount);
		cb.consignBalance(amount);
	}

	/**
	 * Compara los saldos de 2 cuentas bancarias, la actual y una mandada por
	 * parámetro {@code cb}
	 * 
	 * @param cb
	 * @return true si el saldo actual de la cuenta es mayor o igual que el saldo de
	 *         la cuenta enviada
	 */
	public boolean compareBalanceTo(final BankAccount cb) {
		return getBalance() >= cb.getBalance();
	}

	/**
	 * Obtiene el dueño de la cuenta bancaria
	 * 
	 * @return
	 */
	public Client getOwner() {
		return owner;
	}

	/**
	 * Cambia el dueño de la cuenta bancaria
	 * 
	 * @param owner
	 */
	public void setOwner(final Client owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BankAccount other = (BankAccount) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getExists()
				? "CuentaBancaria [OwnerName=" + getOwner().getName() + ", OwnerLastName=" + getOwner().getLastName()
						+ ", accountNumber=" + accountNumber + ", accountType=" + accountType + ", balance=" + balance
						+ "]"
				: "CuentaBancaria [?]";
	}

	@Override
	public int compareTo(final BankAccount o) {
		return getAccountNumber().compareTo(o.getAccountNumber());
	}

	@Override
	public int compare(final BankAccount o1, final BankAccount o2) {
		return o1.compareTo(o2);
	}
}
