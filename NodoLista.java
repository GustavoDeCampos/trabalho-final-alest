public class NodoLista {
    Pedido pedido;
    NodoLista proximo;

    public NodoLista(Pedido pedido) {
        this.pedido = pedido;
        this.proximo = null;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public NodoLista getProximo() {
        return proximo;
    }

    public void setProximo(NodoLista proximo) {
        this.proximo = proximo;
    }
}
