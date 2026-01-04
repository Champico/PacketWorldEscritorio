/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.pojo;

/**
 * Clase de modelado para los envios.
 */
import java.util.List;

public class Envio {

    private Integer idEnvio;
    private String numeroGuia;
    private Float costo;
    private String destinatarioNombre;
    private String destinatarioApellidoP;
    private String destinatarioApellidoM;
    private String destinatarioTelefono;
    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;
    private String ciudad;
    private String estado;
    private Integer idCliente;
    private Integer idSucursalOrigen;
    private Integer idEstatus;
    private String nombreEstatus;
    private Integer idConductor;
    private String nombreConductor;
    private List<Paquete> paquetes;
    private String claveCiudad;
    private String claveEstado;
    private String destinatario;

    public Envio() {
    }

    public Envio(Integer idEnvio, String numeroGuia, Float costo, String destinatarioNombre, String destinatarioApellidoP, String destinatarioApellidoM, String calle, String numero, String colonia, String codigoPostal, String ciudad, String estado, Integer idCliente, Integer idSucursalOrigen, Integer idEstatus, String nombreEstatus, Integer idConductor, String nombreConductor, List<Paquete> paquetes, String claveEstado, String claveCiudad, String destinatario, String destinatarioTelefono) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.costo = costo;
        this.destinatarioNombre = destinatarioNombre;
        this.destinatarioApellidoP = destinatarioApellidoP;
        this.destinatarioApellidoM = destinatarioApellidoM;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.idCliente = idCliente;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idEstatus = idEstatus;
        this.nombreEstatus = nombreEstatus;
        this.idConductor = idConductor;
        this.nombreConductor = nombreConductor;
        this.paquetes = paquetes;
        this.claveEstado = claveEstado;
        this.claveCiudad = claveCiudad;
        this.destinatario = destinatario;
        this.destinatarioTelefono = destinatarioTelefono;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public void setDestinatarioNombre(String destinatarioNombre) {
        this.destinatarioNombre = destinatarioNombre;
    }

    public String getDestinatarioApellidoP() {
        return destinatarioApellidoP;
    }

    public void setDestinatarioApellidoP(String destinatarioApellidoP) {
        this.destinatarioApellidoP = destinatarioApellidoP;
    }

    public String getDestinatarioApellidoM() {
        return destinatarioApellidoM;
    }

    public void setDestinatarioApellidoM(String destinatarioApellidoM) {
        this.destinatarioApellidoM = destinatarioApellidoM;
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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public void setIdSucursalOrigen(Integer idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
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

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getDestinatarioTelefono() {
        return destinatarioTelefono;
    }

    public void setDestinatarioTelefono(String destinatarioTelefono) {
        this.destinatarioTelefono = destinatarioTelefono;
    }

    @Override
    public String toString() {
        return "Envio{" + "idEnvio=" + idEnvio + ", numeroGuia=" + numeroGuia + ", costo=" + costo + ", destinatarioNombre=" + destinatarioNombre + ", destinatarioApellidoP=" + destinatarioApellidoP + ", destinatarioApellidoM=" + destinatarioApellidoM + ", calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + ", ciudad=" + ciudad + ", estado=" + estado + ", idCliente=" + idCliente + ", idSucursalOrigen=" + idSucursalOrigen + ", idEstatus=" + idEstatus + ", nombreEstatus=" + nombreEstatus + '}';
    }

}
