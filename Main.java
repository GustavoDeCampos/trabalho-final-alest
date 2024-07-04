public class Main {
    public static void main(String[] args) {
        Pizzaria pizzaria = new Pizzaria();

        pizzaria.lerPedidosCSV();

        pizzaria.simular();

        pizzaria.exportarSituacaoFilaCSV("situacao_fila.csv");
        pizzaria.exportarABPCSV("ordem_processamento.csv");
    }
}