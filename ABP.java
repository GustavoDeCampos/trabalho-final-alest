public class ABP{
    NodoABP raiz;

    public ABP() {
        this.raiz = null;
    }

    public void inserir(Pedido pedido) {
        raiz = inserirRecursivo(raiz, pedido);
    }

    public NodoABP inserirRecursivo(NodoABP raiz, Pedido pedido) {
        if (raiz == null) {
            raiz = new NodoABP(pedido);
            return raiz;
        }
        // Usando compareTo para comparação de Strings
        if (pedido.getCodigo().compareTo(raiz.getPedido().getCodigo()) < 0) {
            raiz.setEsquerda(inserirRecursivo(raiz.getEsquerda(), pedido));
        } else if (pedido.getCodigo().compareTo(raiz.getPedido().getCodigo()) > 0) {
            raiz.setDireita(inserirRecursivo(raiz.getDireita(), pedido));
        }
        return raiz;
    }

    public void caminhamentoCentral() {
        caminhamentoCentralRecursivo(raiz);
    }

    private void caminhamentoCentralRecursivo(NodoABP raiz) {
        if (raiz != null) {
            caminhamentoCentralRecursivo(raiz.getEsquerda());
            System.out.println(raiz.getPedido().getCodigo());
            caminhamentoCentralRecursivo(raiz.getDireita());
        }
    }
}
