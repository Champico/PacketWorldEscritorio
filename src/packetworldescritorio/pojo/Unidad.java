/** @authores  Pipe, Kevin, champ */

package packetworldescritorio.pojo;

/**
 * Clase de modelado para las unidades.
 */

public class Unidad {
    
    private int idUnidad;
    private String marca;
    private String modelo;
    private String anio;
    private String vin;
    private String numIdInterno;
    private String motivoBaja;
    private String estatus;
    private int idTipo;
    private String tipo;
    private Integer idColaborador;

    public Unidad() {
    }

    public Unidad(int idUnidad, String marca, String modelo, String anio, String vin, String numIdInterno, String motivoBaja, String estatus, int idTipo, String tipo, Integer idColaborador) {
        this.idUnidad = idUnidad;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.vin = vin;
        this.numIdInterno = numIdInterno;
        this.motivoBaja = motivoBaja;
        this.estatus = estatus;
        this.idTipo = idTipo;
        this.tipo = tipo;
        this.idColaborador = idColaborador;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getNumIdInterno() {
        return numIdInterno;
    }

    public void setNumIdInterno(String numIdInterno) {
        this.numIdInterno = numIdInterno;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }
    
    
}
