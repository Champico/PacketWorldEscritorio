
package packetworldescritorio.dto;

public class CaracteristicasPaquete {

    private float pesoMinimo;
    private float pesoMaximo;
    private float alturaMinima;
    private float alturaMaxima;
    private float anchoMinimo;
    private float anchoMaximo;
    private float profundadMinima;
    private float profundidadMaxima;
    private String unidadDeMedidaPeso;
    private String unidadDeMedidaDistancia;

    public CaracteristicasPaquete() {
        this.pesoMinimo = 0.0f;
        this.pesoMaximo = 10.0f;
        this.alturaMinima = 0.0f;
        this.alturaMaxima = 10.0f;
        this.anchoMinimo = 0.0f;
        this.anchoMaximo = 10.0f;
        this.profundidadMaxima = 10.0f;
        this.profundadMinima = 0.0f;
        this.unidadDeMedidaPeso = "kilogramos";
        this.unidadDeMedidaDistancia = "centimetros";
    }

    public CaracteristicasPaquete(
            float pesoMinimo,
            float pesoMaximo,
            float alturaMinima,
            float alturaMaxima,
            float anchoMinimo,
            float anchoMaximo,
            float profundidadMinima,
            float profundidadMaxima,
            String unidadDeMedidaPeso,
            String unidadDeMedidaDistancia) {

        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
        this.alturaMinima = alturaMinima;
        this.alturaMaxima = alturaMaxima;
        this.anchoMinimo = anchoMinimo;
        this.anchoMaximo = anchoMaximo;
        this.profundidadMaxima = profundidadMaxima;
        this.profundadMinima = profundidadMinima;
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

    public float getAnchoMinimo() {
        return anchoMinimo;
    }

    public void setAnchoMinimo(float anchoMinimo) {
        this.anchoMinimo = anchoMinimo;
    }

    public float getAnchoMaximo() {
        return anchoMaximo;
    }

    public void setAnchoMaximo(float anchoMaximo) {
        this.anchoMaximo = anchoMaximo;
    }

    public float getProfundadMinima() {
        return profundadMinima;
    }

    public void setProfundadMinima(float profundadMinima) {
        this.profundadMinima = profundadMinima;
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