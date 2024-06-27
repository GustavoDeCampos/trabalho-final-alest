public class ListaPedidos {
    NodoLista cabeca;

    public void adicionar(Pedido pedido) {
        if (cabeca == null) {
            cabeca = new NodoLista(pedido);
        } else {
            NodoLista atual = cabeca;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = new NodoLista(pedido);
        }
    }

    public Pedido remover() {
        if (cabeca == null) return null;
        Pedido pedido = cabeca.pedido;
        cabeca = cabeca.proximo;
        return pedido;
    }

    public boolean estaVazia() {
        return cabeca == null;
    }

    public Pedido verPrimeiro() {
        if (cabeca == null) return null;
        return cabeca.pedido;
    }
    
    public Pedido removerPrimeiro() {
        return remover(); 
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NodoLista atual = cabeca;
        while (atual != null) {
            sb.append(atual.pedido.toString());
            if (atual.proximo != null) {
                sb.append(", ");
            }
            atual = atual.proximo;
        }
        return sb.toString();
    }
}
