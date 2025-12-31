package packetworldescritorio.pojo;

public class Asentamiento {

    private Integer idAsentamiento;
    private String codigoPostal;
    private String nombre;
    private String idAsentaCpcons;
    private String claveMunicipio;
    private String claveEstado;
    private String claveCiudad;
    private String ciudad;
    private String claveOficina;
    private String cpAdmin;
    private String cpCodigo;
    private String claveTipoAsenta;
    private Integer idZona;

    public Asentamiento() {
    }

    public Asentamiento(Integer idAsentamiento, String codigoPostal, String nombre, String idAsentaCpcons, String claveMunicipio, String claveEstado, String claveCiudad, String ciudad, String claveOficina, String cpAdmin, String cpCodigo, String claveTipoAsenta, Integer idZona) {
        this.idAsentamiento = idAsentamiento;
        this.codigoPostal = codigoPostal;
        this.nombre = nombre;
        this.idAsentaCpcons = idAsentaCpcons;
        this.claveMunicipio = claveMunicipio;
        this.claveEstado = claveEstado;
        this.claveCiudad = claveCiudad;
        this.ciudad = ciudad;
        this.claveOficina = claveOficina;
        this.cpAdmin = cpAdmin;
        this.cpCodigo = cpCodigo;
        this.claveTipoAsenta = claveTipoAsenta;
        this.idZona = idZona;
    }

    public Integer getIdAsentamiento() {
        return idAsentamiento;
    }

    public void setIdAsentamiento(Integer idAsentamiento) {
        this.idAsentamiento = idAsentamiento;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdAsentaCpcons() {
        return idAsentaCpcons;
    }

    public void setIdAsentaCpcons(String idAsentaCpcons) {
        this.idAsentaCpcons = idAsentaCpcons;
    }

    public String getClaveMunicipio() {
        return claveMunicipio;
    }

    public void setClaveMunicipio(String claveMunicipio) {
        this.claveMunicipio = claveMunicipio;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getClaveOficina() {
        return claveOficina;
    }

    public void setClaveOficina(String claveOficina) {
        this.claveOficina = claveOficina;
    }

    public String getCpAdmin() {
        return cpAdmin;
    }

    public void setCpAdmin(String cpAdmin) {
        this.cpAdmin = cpAdmin;
    }

    public String getCpCodigo() {
        return cpCodigo;
    }

    public void setCpCodigo(String cpCodigo) {
        this.cpCodigo = cpCodigo;
    }

    public String getClaveTipoAsenta() {
        return claveTipoAsenta;
    }

    public void setClaveTipoAsenta(String claveTipoAsenta) {
        this.claveTipoAsenta = claveTipoAsenta;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    

}
