public class Pedido {
    private String codigo;
    private String sabor;
    private int tempoPreparo;
    private int instante; 
    Pedido proximoComMesmoTempoPreparo = null;

    public Pedido(String codigo, String sabor, int tempoPreparo, int instante) {
        this.codigo = codigo;
        this.sabor = sabor;
        this.tempoPreparo = tempoPreparo;
        this.instante = instante; 
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public void setInstante(int instante) {
        this.instante = instante;
    }

    public void setProximoComMesmoTempoPreparo(Pedido proximoComMesmoTempoPreparo) {
        this.proximoComMesmoTempoPreparo = proximoComMesmoTempoPreparo;
    }

    public String getSabor() {
        return sabor;
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public int getInstante() {
        return instante; 
    }

    public Pedido getProximoComMesmoTempoPreparo() {
        return proximoComMesmoTempoPreparo;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigo + '\'' +
                ", sabor='" + sabor + '\'' +
                ", tempoPreparo=" + tempoPreparo +
                ", instante=" + instante +
                '}';
    }
}