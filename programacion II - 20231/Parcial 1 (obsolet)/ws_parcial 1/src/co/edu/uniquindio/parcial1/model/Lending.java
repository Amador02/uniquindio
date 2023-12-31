package co.edu.uniquindio.parcial1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lending {
    private LocalDate date;
    private LocalDate deliveryDate;
    private String code;
    private Employer employer;
    private Student student;

    private final List<LendingDetail> lendingDetailList = new ArrayList<LendingDetail>();
    private boolean isEnded = false;

    /**
     * Es el constructor de la clase prestamo
     * 
     * @param date
     * @param deliveryDate
     * @param code
     * @param employer
     * @param student
     */
    public Lending(final LocalDate date, final LocalDate deliveryDate, final String code, final Employer employer,
            final Student student) {
        this.date = date;
        this.deliveryDate = deliveryDate;
        this.code = code;
        this.employer = employer;
        this.student = student;
    }

    /**
     * Es el constructor de la clase prestamo sin parámetros
     */
    public Lending() {
    }

    /**
     * Obtiene la fecha de la clase prestamo
     *
     * @return la fecha
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Cambia la fecha de la clase prestamo
     *
     * @param date la fecha
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * Obtiene el total del prestamo por médio de cada subtotal de detalles del
     * préstamo
     *
     * @return el total
     */
    public Double getTotal() {
        Double total = 0d;
        for (final LendingDetail eachlendingDetail : getLendingDetailList()) {
            total += eachlendingDetail.getSubTotal();
        }
        return total;
    }

    /**
     * Obtiene la fecha de entrega de la clase prestamo
     *
     * @return la fecha de entrega
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Cambia la fecha de entrega de la clase prestamo
     *
     * @param deliveryDate la fecha de entrega
     */
    public void setDeliveryDate(final LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * Obtiene el codigo de prestamo de la clase prestamo
     *
     * @return el codigo
     */
    public String getCode() {
        return code;
    }

    /**
     * Cambia el codigo de prestamo de la clase prestamo
     *
     * @param code el código
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Obtiene la lista de detalles de préstamo del préstamo
     * 
     * @return la lista de detalles de préstamo
     */
    public List<LendingDetail> getLendingDetailList() {
        return lendingDetailList;
    }

    /**
     * Obtiene el empleado del préstamo
     * 
     * @return el empleado
     */
    public Employer getEmployer() {
        return employer;
    }

    /**
     * Cambia el empleado del préstamo
     * 
     * @param employer es el empleado
     */
    public void setEmployer(final Employer employer) {
        this.employer = employer;
    }

    /**
     * Obtiene el estudiante del préstamo
     * 
     * @return el estudiante
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Cambia el estudiante del préstamo
     * 
     * @param student el estudiante
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Obtiene el empleado que ha realizado un préstamo de un Libro a partir del
     * isbn {@code isbn} del libro.
     * 
     * @param isbn es el isbn del libro
     * @return el empleado, si no se encuentra se retorna un empleado sin atributos
     *         (inexistente)
     */
    public Employer getEmployerByIsbn(final String isbn) {
        Employer employer = new Employer();
        for (final LendingDetail eachLendingDetail : getLendingDetailList()) {
            if (eachLendingDetail.hasIsbn(isbn)) {
                employer = getEmployer();
            }
        }
        return employer;
    }

    /**
     * 
     * @param code         es el codigo del detalle del préstamo
     * @param unitaryValue es el valor unitario del detalle del préstamo
     * @param quantity     es la cantidad del detalle del préstamo
     * @param book         es el libro del detalle del préstamo
     * @return 2 mensajes dependiendo de que si el detalle existe o no
     * @throws LibraryException En caso de que ya haya terminado el préstamo
     * @see {@link #isEnded()}
     *      <li>{@link #throwIfEnded()}
     */
    public String addLendingDetail(final String code, final Double unitaryValue, final Integer quantity,
            final Book book)
            throws LibraryException {
        throwIfEnded();
        final LendingDetail lendingDetail = searchLendingDetail(book);
        String msg = "El detalle del préstamo ha sido añadido (" + code + ")";
        if (lendingDetail.getExists()) {
            final int index = getLendingDetailList().indexOf(lendingDetail);
            lendingDetail.addQuantity(quantity);
            getLendingDetailList().set(index, lendingDetail);
            msg = "El detalle del préstamo ya existe (" + code + "), por lo que se agregó una cantidad";
        }
        getLendingDetailList().add(new LendingDetail(code, unitaryValue, quantity, book));
        return msg;
    }

    /**
     * Elimina un detalle del préstamo a partir del código, muestra un error si el
     * préstamo está bloqueado o no lo encuentra
     * 
     * @param code es el codigo del detalle del préstamo
     * @return El detalle del préstamo ha sido eliminado ({@code code})
     * @throws LibraryException En caso de que no exista
     *                          <li>En caso de que ya haya terminado el préstamo
     * @see {@link #isEnded()}
     *      <li>{@link #throwIfEnded()}
     */
    public String removeLendingDetail(final Book book) throws LibraryException {
        throwIfEnded();
        if (validateLendingDetail(book)) {
            throw new LibraryException("El detalle del préstamo no existe (" + code + ")");
        }
        getLendingDetailList().remove(searchLendingDetail(book));
        return "El detalle del préstamo ha sido eliminado (" + code + ")";
    }

    private void throwIfEnded() throws LibraryException {
        if (isEnded())
            throw new LibraryException("El detalle de préstamo no puede ser cambiado");
    }

    public boolean validateLendingDetail(final Book book) {
        return searchLendingDetail(book).getExists();
    }

    public LendingDetail searchLendingDetail(final Book book) {
        return getLendingDetailList().stream().filter(lendingDetail -> lendingDetail.hasIsbn(book.getIsbn()))
                .findFirst()
                .orElse(new LendingDetail());
    }

    /**
     * Determina si un préstamo ya ha sido terminado, en caso de que sí, no se
     * pueden ni agregar ni eliminar detalles
     * 
     * @return true si ya terminó
     */
    public boolean isEnded() {
        return isEnded;
    }

    /**
     * Termina el préstamo y hace que no se puedan agregar o eliminar detalles
     * 
     * @see {@link #isEnded()}
     */
    public void endLending() {
        this.isEnded = true;
    }

    /**
     * Determina si un préstamo existe o no dependiendo de que su código, dia, dia
     * de entrega y empleado son diferentes de null
     * 
     * @return true si ninguna es null
     */
    public boolean getExists() {
        return getCode() != null && getDate() != null && getDeliveryDate() != null && getEmployer() != null
                && getEmployer().getExists();
    }

    /**
     * Determina en agún detalle del préstamo se tiene la cantidad entre 2 valores
     * 
     * @param min es el valor mínimo
     * @param max es el valor máximo
     * @return true si está entre esos 2 valores
     */
    public boolean lendingHasQuantityBetween(Integer min, Integer max) {
        for (LendingDetail lendingDetail : lendingDetailList) {
            if (lendingDetail.hasQuantityBetween(min, max)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
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
        Lending other = (Lending) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    /**
     * Determina si el préstamo tiene un código {@code code} especificado
     * 
     * @param code es el código a comparar
     * @return true si lo tiene
     */
    public boolean hasCode(String code) {
        return getCode().equals(code);
    }

    @Override
    public String toString() {
        return getExists()
                ? "Lending [date=" + date + ", deliveryDate=" + deliveryDate + ", code=" + code + ", employer="
                        + employer
                        + ", student=" + student + ", isEnded=" + isEnded + ", lendingDetailList=" + lendingDetailList
                        + "]"
                : "Lending [Doesn't exists]";
    }

}