
package packetworldescritorio.pojo;

public class HistorialEstatus {

    private Integer idHistorial;
    private Integer idEnvio;
    private String fechaCambio;
    private String comentario;
    private Integer idEstatus;
    private String nombreEstatus;
    private Integer idColaborador;
    private Integer idRol;
    private String noPersonal;
    private String nombreRol;
    private String colaborador; 

    public HistorialEstatus() {
    }
    
    public HistorialEstatus(
            Integer idHistorial,
            Integer idEnvio,
            String fechaCambio,
            String comentario,
            Integer idEstatus,
            String nombreEstatus,
            Integer idColaborador,
            Integer idRol,
            String noPersonal,
            String nombreRol,
            String colaborador
    ) {
        this.idHistorial = idHistorial;
        this.idEnvio = idEnvio;
        this.fechaCambio = fechaCambio;
        this.comentario = comentario;
        this.idEstatus = idEstatus;
        this.nombreEstatus = nombreEstatus;
        this.idColaborador = idColaborador;
        this.idRol = idRol;
        this.noPersonal = noPersonal;
        this.nombreRol = nombreRol;
        this.colaborador = colaborador;
    }

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(String fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    @Override
    public String toString() {
        return "HistorialEnvioDetalle{" +
                "idHistorial=" + idHistorial +
                ", idEnvio=" + idEnvio +
                ", fechaCambio='" + fechaCambio + '\'' +
                ", comentario='" + comentario + '\'' +
                ", nombreEstatus='" + nombreEstatus + '\'' +
                ", colaborador='" + colaborador + '\'' +
                ", nombreRol='" + nombreRol + '\'' +
                '}';
    }
}

