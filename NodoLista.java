public class NodoLista {
    Pedido pedido;
    NodoLista proximo;

    public NodoLista(Pedido pedido) {
        this.pedido = pedido;
        this.proximo = null;
    }
}
