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
    private Pedido pedidoMaisDemorado = null;
    private int totalPedidosProcessados = 0;
    private int tempoTotalExecutado = 0;
    private int tempoPreparoMaisDemorado = 0;
    private int tempoRestantePreparo = 0;
    private ListaPedidos pedidosMaisDemorados = new ListaPedidos();
    private int contagemPedidosMaisDemorados = 0;
    private final String caminhoArquivoCSV = "resultado_pedidos.csv";

    public void lerPedidosCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("pedidos_pizza_15.csv"))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(",");
                String codigo = valores[0];
                String sabor = valores[1];
                int tempoPreparo = Integer.parseInt(valores[2]);
                int instante = Integer.parseInt(valores[3]);
                Pedido pedido = new Pedido(codigo, sabor, instante, tempoPreparo);
                pedidos.adicionar(pedido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processarPedido() {
        while (!pedidos.estaVazia() && pedidos.verPrimeiro().getInstante() <= tempoAtual) {
            fila.adicionar(pedidos.removerPrimeiro());
        }
        if (emProducao == null && !fila.estaVazia()) {
            emProducao = fila.remover();
            tempoRestantePreparo = emProducao.getTempoPreparo();
        }
        if (emProducao != null) {
            tempoTotalExecutado++;
            tempoRestantePreparo--;
            if (tempoRestantePreparo == 0) {
                totalPedidosProcessados++;
                if (pedidoMaisDemorado == null || emProducao.getTempoPreparo() > tempoPreparoMaisDemorado) {
                    tempoPreparoMaisDemorado = emProducao.getTempoPreparo();
                    pedidoMaisDemorado = emProducao;
                    pedidosMaisDemorados = new ListaPedidos();
                    pedidosMaisDemorados.adicionar(emProducao);
                    contagemPedidosMaisDemorados = 1;
                } else if (emProducao.getTempoPreparo() == tempoPreparoMaisDemorado) {
                    pedidosMaisDemorados.adicionar(emProducao);
                    contagemPedidosMaisDemorados++;
                }
                abp.inserir(emProducao);
                emProducao = null;

                if (!fila.estaVazia()) {
                    emProducao = fila.remover();
                    tempoRestantePreparo = emProducao.getTempoPreparo();
                }
            }
        }
    }

    public void simular() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String entrada;
        System.out.println("Pressione <ENTER> para avançar 1 ciclo ou digite 'C' para continuação automática.");
        try {
            while (true) {
                entrada = reader.readLine();
                if (entrada.isEmpty()) {
                    tempoAtual++;
                    processarPedido();
                    exibirResultadosFinais();
                    if (pedidos.estaVazia() && fila.estaVazia() && emProducao == null) {
                        break;
                    }
                } else if (entrada.equalsIgnoreCase("C")) {
                    while (true) {
                        tempoAtual++;
                        processarPedido();
                        if (pedidos.estaVazia() && fila.estaVazia() && emProducao == null) {
                            exibirResultadosFinais();
                            break;
                        }
                    }
                    break;
                }
            }
            exportarABPCSV(caminhoArquivoCSV);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exibirResultadosFinais() {
        int pedidosEmProducao = emProducao != null ? 1 : 0;
        System.out.println("Instante atual: " + tempoAtual);
        System.out.println("Pedidos em produção: " + pedidosEmProducao);
        System.out.println("Pedidos na fila: " + fila.tamanho());
        System.out.println("Total de pedidos processados: " + totalPedidosProcessados);
        System.out.println("Total de instantes executados: " + tempoTotalExecutado);
        if (pedidoMaisDemorado != null) {
            System.out.println("Tempo de preparo mais demorado: " + tempoPreparoMaisDemorado);
            System.out.println("Quantidade de pedidos com o tempo de preparo mais demorado: " + contagemPedidosMaisDemorados);
            System.out.println("Pedidos com o tempo de preparo mais demorado:");
            StringBuilder pedidosStr = new StringBuilder();
            for (int i = 0; i < pedidosMaisDemorados.tamanho(); i++) {
                pedidosStr.append(pedidosMaisDemorados.get(i).toString()).append("\n");
            }
            System.out.print(pedidosStr.toString());
        } else {
            System.out.println("Nenhum pedido foi processado.");
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
            sb.append(nodoABP.getPedido().getCodigo()).append(",");
            exportarABPInOrder(nodoABP.getDireita(), sb);
        }
    }

    public void exportarSituacaoFilaCSV(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}