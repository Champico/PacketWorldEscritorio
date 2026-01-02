/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.pojo;

/**
 * Clase de modelado para las sucursales.
 */
public class Sucursal {

    private int idSucursal;
    private String codigo;
    private String nombre;
    private String estatus;
    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;
    private String ciudad;
    private String estado;
    private String claveEstado;
    private String claveCiudad;

    public Sucursal() {
    }

    public Sucursal(int idSucursal, String codigo, String nombre, String status, String calle, String numero, String colonia, String codigoPostal, String ciudad, String estado, String claveEstado, String claveCiudad) {
        this.idSucursal = idSucursal;
        this.codigo = codigo;
        this.nombre = nombre;
        this.estatus = status;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.claveEstado = claveEstado;
        this.claveCiudad = claveCiudad;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String status) {
        this.estatus = status;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }

    
    public String getClaveEstado() {
        return claveEstado;
    }

    public void setClaveEstado(String claveEstado) {
        this.claveEstado = claveEstado;
    }

    public String getClaveCiudad() {
        return claveCiudad;
    }

    public void setClaveCiudad(String claveCiudad) {
        this.claveCiudad = claveCiudad;
    }

}
