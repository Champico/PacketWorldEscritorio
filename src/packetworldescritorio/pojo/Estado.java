package packetworldescritorio.pojo;

import java.util.Objects;

public class Estado {

    private String claveEstado;
    private String nombre;
    private String nombreExtendido;
    private String abreviatura;
    private Integer minCp;
    private Integer maxCp;
    private Double latitudDec;
    private Double longitudDec;

    public Estado() {
    }

    public Estado(String claveEstado, String nombre, String nombreExtendido, String abreviatura, Integer minCp, Integer maxCp, Double latitudDec, Double longitudDec) {
        this.claveEstado = claveEstado;
        this.nombre = nombre;
        this.nombreExtendido = nombreExtendido;
        this.abreviatura = abreviatura;
        this.minCp = minCp;
        this.maxCp = maxCp;
        this.latitudDec = latitudDec;
        this.longitudDec = longitudDec;
    }

    public String getClaveEstado() {
        return claveEstado;
    }

    public void setClaveEstado(String claveEstado) {
        this.claveEstado = claveEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreExtendido() {
        return nombreExtendido;
    }

    public void setNombreExtendido(String nombreExtendido) {
        this.nombreExtendido = nombreExtendido;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Integer getMinCp() {
        return minCp;
    }

    public void setMinCp(Integer minCp) {
        this.minCp = minCp;
    }

    public Integer getMaxCp() {
        return maxCp;
    }

    public void setMaxCp(Integer maxCp) {
        this.maxCp = maxCp;
    }

    public Double getLatitudDec() {
        return latitudDec;
    }

    public void setLatitudDec(Double latitudDec) {
        this.latitudDec = latitudDec;
    }

    public Double getLongitudDec() {
        return longitudDec;
    }

    public void setLongitudDec(Double longitudDec) {
        this.longitudDec = longitudDec;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Estado that = (Estado) o;
    return claveEstado.equals(that.claveEstado);
}

@Override
public int hashCode() {
    return Objects.hash(claveEstado);
}

}
