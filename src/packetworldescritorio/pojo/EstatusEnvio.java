package packetworldescritorio.pojo;

public class EstatusEnvio {

    private Integer idEstatus;
    private String estadoEnvio;

    public EstatusEnvio() {
    }

    public EstatusEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public EstatusEnvio(Integer idEstatus, String estadoEnvio) {
        this.idEstatus = idEstatus;
        this.estadoEnvio = estadoEnvio;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    @Override
    public String toString() {
        return estadoEnvio;
    }

}
