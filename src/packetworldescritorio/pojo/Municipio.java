package packetworldescritorio.pojo;

public class Municipio {

    private String claveEstado;
    private String nombre;
    private String claveMunicipio;

    public Municipio() {
    }

    public Municipio(String claveEstado, String nombre, String claveMunicipio) {
        this.claveEstado = claveEstado;
        this.nombre = nombre;
        this.claveMunicipio = claveMunicipio;
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

    public String getClaveMunicipio() {
        return claveMunicipio;
    }

    public void setClaveMunicipio(String claveMunicipio) {
        this.claveMunicipio = claveMunicipio;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    

}
