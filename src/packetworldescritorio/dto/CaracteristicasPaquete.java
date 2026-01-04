
package packetworldescritorio.dto;

public class CaracteristicasPaquete {

    private float pesoMinimo;
    private float pesoMaximo;
    private float alturaMinima;
    private float alturaMaxima;
    private float anchoMinima;
    private float profundidadMaxima;
    private String unidadDeMedidaPeso;
    private String unidadDeMedidaDistancia;

    public CaracteristicasPaquete() {
        this.pesoMinimo = 0.0f;
        this.pesoMaximo = 10.0f;
        this.alturaMinima = 0.0f;
        this.alturaMaxima = 10.0f;
        this.anchoMinima = 0.0f;
        this.profundidadMaxima = 10.0f;
        this.unidadDeMedidaPeso = "kilogramos";
        this.unidadDeMedidaDistancia = "centimetros";
    }

    public CaracteristicasPaquete(
            float pesoMinimo,
            float pesoMaximo,
            float alturaMinima,
            float alturaMaxima,
            float anchoMinima,
            float profundidadMaxima,
            String unidadDeMedidaPeso,
            String unidadDeMedidaDistancia) {

        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
        this.alturaMinima = alturaMinima;
        this.alturaMaxima = alturaMaxima;
        this.anchoMinima = anchoMinima;
        this.profundidadMaxima = profundidadMaxima;
        this.unidadDeMedidaPeso = unidadDeMedidaPeso;
        this.unidadDeMedidaDistancia = unidadDeMedidaDistancia;
    }

    public float getPesoMinimo() {
        return pesoMinimo;
    }

    public void setPesoMinimo(float pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }

    public float getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(float pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public float getAlturaMinima() {
        return alturaMinima;
    }

    public void setAlturaMinima(float alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    public float getAlturaMaxima() {
        return alturaMaxima;
    }

    public void setAlturaMaxima(float alturaMaxima) {
        this.alturaMaxima = alturaMaxima;
    }

    public float getAnchoMinima() {
        return anchoMinima;
    }

    public void setAnchoMinima(float anchoMinima) {
        this.anchoMinima = anchoMinima;
    }

    public float getProfundidadMaxima() {
        return profundidadMaxima;
    }

    public void setProfundidadMaxima(float profundidadMaxima) {
        this.profundidadMaxima = profundidadMaxima;
    }

    public String getUnidadDeMedidaPeso() {
        return unidadDeMedidaPeso;
    }

    public void setUnidadDeMedidaPeso(String unidadDeMedidaPeso) {
        this.unidadDeMedidaPeso = unidadDeMedidaPeso;
    }

    public String getUnidadDeMedidaDistancia() {
        return unidadDeMedidaDistancia;
    }

    public void setUnidadDeMedidaDistancia(String unidadDeMedidaDistancia) {
        this.unidadDeMedidaDistancia = unidadDeMedidaDistancia;
    }
}
