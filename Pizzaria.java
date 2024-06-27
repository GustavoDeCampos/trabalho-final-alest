import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pizzaria {
    ListaPedidos pedidos = new ListaPedidos();
    ListaPedidos fila = new ListaPedidos();
    ABP abp = new ABP();
    int tempoAtual = 0;
    Pedido emProducao = null;
    ListaPedidos pedidosProcessados = new ListaPedidos();
    ListaPedidos prontos = new ListaPedidos();
    private Pedido pedidoMaisDemorado = null;
    private int totalPedidosProcessados = 0;
    private int tempoTotalExecutado = 0;
    private int tempoPreparoMaisDemorado = 0;

    public void lerPedidosCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("pedidos_pizza_15.csv"))) {
            br.readLine(); 
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(",");
                String codigo = valores[0];
                String sabor = valores[1];
                int instante = Integer.parseInt(valores[2]);
                int tempoPreparo = Integer.parseInt(valores[3]);
                Pedido pedido = new Pedido(codigo, sabor, instante, tempoPreparo); 
                pedidos.adicionar(pedido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processarPedido() {
        if (!fila.estaVazia() && fila.cabeca.pedido.getInstante() <= tempoAtual) {
            Pedido pedido = fila.remover();
            abp.inserir(pedido);
            try {
                Thread.sleep(pedido.getTempoPreparo() * 1000);
                tempoTotalExecutado += pedido.getTempoPreparo();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    
            totalPedidosProcessados++;
    
            if (pedidoMaisDemorado == null || pedido.getTempoPreparo() > tempoPreparoMaisDemorado) {
                pedidoMaisDemorado = pedido;
                tempoPreparoMaisDemorado = pedido.getTempoPreparo();
                pedidoMaisDemorado.setProximoComMesmoTempoPreparo(null);
            } else if (pedido.getTempoPreparo() == tempoPreparoMaisDemorado) {
                Pedido atual = pedidoMaisDemorado;
                while (atual.getProximoComMesmoTempoPreparo() != null) {
                    atual = atual.getProximoComMesmoTempoPreparo();
                }
                atual.setProximoComMesmoTempoPreparo(pedido);
            }
        }
    }

    public void simular(int unidadesTempo) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String entrada = "";
        System.out.println("Pressione <ENTER> para avançar 1 ciclo ou digite 'C' para continuação automática.");
        try {
            while (!entrada.equalsIgnoreCase("C")) {
                entrada = reader.readLine();
                if (entrada.isEmpty()) {
                    tempoAtual++;
                    processarPedido();
                } else if (entrada.equalsIgnoreCase("C")) {
                    while (true) {
                        tempoAtual++;
                        processarPedido();
                        Thread.sleep(1000); 
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            exibirResultadosFinais(); 
        }
    }

    private void exibirResultadosFinais() {
        System.out.println("Total de pedidos processados: " + totalPedidosProcessados);
        System.out.println("Total de tempo executado: " + tempoTotalExecutado + " segundos");
        System.out.println("Pedidos mais demorados:");
        Pedido atual = pedidoMaisDemorado;
        while (atual != null) {
            System.out.println(atual);
            atual = atual.getProximoComMesmoTempoPreparo();
        }
    }

    public void exportarSituacaoFilaCSV(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("instante de tempo t,fila de pedidos,em produção,prontos\n");
            while (!fila.estaVazia() || emProducao != null || !pedidos.estaVazia()) {
                while (!pedidos.estaVazia() && pedidos.verPrimeiro().getInstante() <= tempoAtual) {
                    fila.adicionar(pedidos.removerPrimeiro());
                }

                if (emProducao != null && emProducao.getTempoPreparo() + emProducao.getInstante() == tempoAtual) {
                    prontos.adicionar(emProducao);
                    emProducao = null;
                }

                if (emProducao == null && !fila.estaVazia()) {
                    emProducao = fila.removerPrimeiro();
                }

                bw.write(tempoAtual + "," + fila.toString() + "," + (emProducao != null ? emProducao.toString() : "") + "," + prontos.toString() + "\n");
                tempoAtual++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarABPCSV(String caminhoArquivo) {
        StringBuilder sb = new StringBuilder();
    exportarABPInOrder(abp.raiz, sb);
    if (sb.length() > 0) { 
        sb.setLength(sb.length() - 1);
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
        bw.write(sb.toString());
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void exportarABPInOrder(NodoABP nodoABP, StringBuilder sb) {
        if (nodoABP != null) {
            exportarABPInOrder(nodoABP.getEsquerda(), sb);
            sb.append(nodoABP.getPedido().toString());
            exportarABPInOrder(nodoABP.getDireita(), sb);
        }
    }
}
