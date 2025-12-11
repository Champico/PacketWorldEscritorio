package packetworldescritorio.pojo;

public class Session {

    private Colaborador colaboradorActual;

    private Session() {}

    private static class Holder {
        private static final Session INSTANCE = new Session();
    }

    public static Session getInstance() {
        return Holder.INSTANCE;
    }

    public Colaborador getUsuarioActual() {
        return colaboradorActual;
    }

    public void setUsuarioActual(Colaborador colaborador) {
        this.colaboradorActual = colaborador;
    }

    public void limpiarSesion() {
        this.colaboradorActual = null;
    }
}
