public class NodoABP {
    private Pedido pedido;
    private NodoABP esquerda, direita;

    public NodoABP(Pedido pedido) {
        this.pedido = pedido;
        this.esquerda = null;
        this.direita = null;
    }

    // Getters
    public Pedido getPedido() {
        return pedido;
    }

    public NodoABP getEsquerda() {
        return esquerda;
    }

    public NodoABP getDireita() {
        return direita;
    }

    // Setters
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setEsquerda(NodoABP esquerda) {
        this.esquerda = esquerda;
    }

    public void setDireita(NodoABP direita) {
        this.direita = direita;
    }

    // Verifica se o nodo é uma folha
    public boolean eFolha() {
        return esquerda == null && direita == null;
    }
}