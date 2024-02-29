package src.Negocio.Servicos;
import src.Dados.PedidoDAO;
import src.Exceptions.*;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Queue;

public class PostoTrabalho {
    private int codPosto;
    private LocalTime disponibilidade;
    private String nome;
    private boolean ativo;

    private Queue<Integer> codigosPedidos;
    private PedidoDAO pedidos;

    public PostoTrabalho(int codPosto, LocalTime disponibilidade, String nome, boolean ativo, Queue<Integer> codigosPedidos) {
        this.codPosto = codPosto;
        this.disponibilidade = disponibilidade;
        this.nome = nome;
        this.ativo = ativo;
        this.codigosPedidos = new ArrayDeque<>(codigosPedidos);
        this.pedidos = PedidoDAO.getInstance();
    }

    public PostoTrabalho(){
        this.codPosto = -1;
        this.disponibilidade = null;
        this.nome = "N/D";
        this.pedidos = PedidoDAO.getInstance();
        this.ativo = false;
    }

    //Getters e Setters
    public int getCodPosto() {
        return codPosto;
    }
    public LocalTime getDisponibilidade() {
        return disponibilidade;
    }
    public String getNome() {
        return nome;
    }
    public boolean getAtivo(){return ativo;}

    public Queue<Integer> getCodigosPedidos() {
        return codigosPedidos;
    }

    public void setCodPosto(int codPosto) {
        this.codPosto = codPosto;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    public void setDisponibilidade(LocalTime disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    //Metodos
    public Pedido adicionarPedidoPosto(int codEstado, int codServico, String matricula) throws ServicoNaoEncontradoException {
        Pedido pedido =new Pedido(codServico,matricula);
        pedido.setCodEstado(codEstado);
        Servico servico = pedido.getServico();
        int duracao = servico.getDuracaoMedia();
        this.disponibilidade=this.disponibilidade.plusMinutes(duracao);
        int codPedido= pedido.getCodPedido();
        this.codigosPedidos.add(codPedido);
        this.pedidos.put(pedido.getCodPedido(),pedido);
        return pedido;
    }

    public Pedido proximoServico() throws PedidosQueueVazia {
        try {
            return this.pedidos.get(this.codigosPedidos.poll());
        }catch (Exception e) {
            throw new PedidosQueueVazia("Não existem pedidos na queue para atribuir ao mecânico.");
        }
    }

    public LocalTime tempoEstimado(Integer duracaoMedia){
        return this.disponibilidade.plusMinutes(duracaoMedia);
    }

    public String listarPedidos() throws PedidoNaoEncontradoException {
        StringBuilder sb = new StringBuilder();
        for (Integer codigoPedido:this.codigosPedidos){
            sb.append(this.pedidos.get(codigoPedido).toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "PostoTrabalho{" +
                "codPosto=" + codPosto +
                ", disponibilidade=" + disponibilidade +
                ", nome='" + nome + '\'' +
                ", ativo=" + ativo +
                ", codigosPedidos=" + codigosPedidos +
                ", pedidos=" + pedidos +
                '}';
    }
}
