public class ABP{
    NodoABP raiz;

    public ABP() {
        this.raiz = null;
    }

    public void inserir(Pedido pedido) {
        NodoABP novoNodo = new NodoABP(pedido);
        if (raiz == null) {
            raiz = novoNodo;
        } else {
            NodoABP atual = raiz;
            while (true) {
                if (pedido.getCodigo().compareTo(atual.getPedido().getCodigo()) < 0) {
                    if (atual.getEsquerda() != null) {
                        atual = atual.getEsquerda();
                    } else {
                        atual.setEsquerda(novoNodo);
                        break;
                    }
                } else if (pedido.getCodigo().compareTo(atual.getPedido().getCodigo()) > 0) {
                    if (atual.getDireita() != null) {
                        atual = atual.getDireita();
                    } else {
                        atual.setDireita(novoNodo);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }
}
