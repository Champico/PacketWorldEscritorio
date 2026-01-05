/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.pojo;

import java.util.Objects;

/**
 * Clase de modelado para los paquetes.
 */
public class Paquete {

    private Integer idPaquete;
    private String descripcion;
    private Float peso;
    private Float alto;
    private Float ancho;
    private Float profundidad;
    private Integer idEnvio;
    private String guiaEnvio;

    public Paquete() {
    }

    public Paquete(Integer idPaquete, String descripcion, Float peso, Float alto, Float ancho, Float profundidad, Integer idEnvio, String guiaEnvio) {
        this.idPaquete = idPaquete;
        this.descripcion = descripcion;
        this.peso = peso;
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.idEnvio = idEnvio;
        this.guiaEnvio = guiaEnvio;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAlto() {
        return alto;
    }

    public void setAlto(Float alto) {
        this.alto = alto;
    }

    public Float getAncho() {
        return ancho;
    }

    public void setAncho(Float ancho) {
        this.ancho = ancho;
    }

    public Float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Float profundidad) {
        this.profundidad = profundidad;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.descripcion);
        hash = 97 * hash + Objects.hashCode(this.peso);
        hash = 97 * hash + Objects.hashCode(this.alto);
        hash = 97 * hash + Objects.hashCode(this.ancho);
        hash = 97 * hash + Objects.hashCode(this.profundidad);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paquete other = (Paquete) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.alto, other.alto)) {
            return false;
        }
        if (!Objects.equals(this.ancho, other.ancho)) {
            return false;
        }
        if (!Objects.equals(this.profundidad, other.profundidad)) {
            return false;
        }
        return true;
    }

    public void copy(Paquete paquete) {
        this.idPaquete = paquete.getIdPaquete();
        this.descripcion = paquete.getDescripcion();
        this.peso = paquete.getPeso();
        this.alto = paquete.getAlto();
        this.ancho = paquete.getAncho();
        this.profundidad = paquete.getProfundidad();
        this.idEnvio = paquete.getIdEnvio();
    }

    @Override
    public String toString() {
        return "Paquete{" + "idPaquete=" + idPaquete + ", descripcion=" + descripcion + ", peso=" + peso + ", alto=" + alto + ", ancho=" + ancho + ", profundidad=" + profundidad + ", idEnvio=" + idEnvio + '}';
    }

    public String getGuiaEnvio() {
        return guiaEnvio;
    }

    public void setGuiaEnvio(String guiaEnvio) {
        this.guiaEnvio = guiaEnvio;
    }
}
