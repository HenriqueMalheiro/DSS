package src.Negocio.Funcionarios;

import src.Dados.PedidoDAO;
import src.Exceptions.PedidosQueueVazia;
import src.Negocio.Servicos.Pedido;
import src.Negocio.Servicos.PostoTrabalho;

import java.util.HashSet;
import java.util.Set;

public class Mecanico extends Funcionario {
    public Set<Integer> codigosPedidosRealizados;
    public PedidoDAO pedidosRealizados;
    public int codPedidoExecutar;


    //Construtor vazio
    public Mecanico(){
        super();
        this.codigosPedidosRealizados = new HashSet<>();
        this.pedidosRealizados = PedidoDAO.getInstance();
        this.codPedidoExecutar = -1;

    }

    //Construtor parametrizado
    public Mecanico(Integer codFunc, String nome, PostoTrabalho postoTrabalhoAtual, Turno turno, Set<Integer> codigosPedidosRealizados){
        super(codFunc,nome, postoTrabalhoAtual, turno);
        this.codigosPedidosRealizados = new HashSet<>(codigosPedidosRealizados);
        this.pedidosRealizados = PedidoDAO.getInstance();
        this.codPedidoExecutar = -1;
    }

    //Construtor copia
    public Mecanico(Mecanico umMecanico){
        super(umMecanico);
        this.codigosPedidosRealizados = umMecanico.getCodigosPedidosRealizados();
        this.pedidosRealizados=umMecanico.getPedidosRealizados();
        this.codPedidoExecutar = umMecanico.getCodFunc();
    }

    //Gets
    public Set<Integer> getCodigosPedidosRealizados() {
        return new HashSet<>(codigosPedidosRealizados);
    }

    public PedidoDAO getPedidosRealizados() {
        return pedidosRealizados;
    }

    public int getCodPedidoExecutar() {
        return codPedidoExecutar;
    }

    @Override
    public int getCodFunc() {
        return super.getCodFunc();
    }

    //Sets
    public void setCodigosPedidosRealizados(Set<Integer> codigosPedidosRealizados) {
        this.codigosPedidosRealizados = new HashSet<>(codigosPedidosRealizados);
    }

    public void setPedidosRealizados(PedidoDAO pedidosRealizados) {
        this.pedidosRealizados = pedidosRealizados;
    }

    @Override
    public void setCodFunc(int codFunc) {
        super.setCodFunc(codFunc);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mecanico:: {");
        sb.append(super.toString());
        sb.append(" Codigos pedidos realizados: ").append(this.codigosPedidosRealizados.toString()).append("}");
        return sb.toString();
    }

    @Override
    public Pedido atribuirServico() throws PedidosQueueVazia {
            Pedido proximoPedido = this.getPostoTrabalhoAtual().proximoServico();
            proximoPedido.setAtribuido(true);
            pedidosRealizados.put(proximoPedido.getCodPedido(),proximoPedido);
            if(this.codPedidoExecutar != -1){
                this.codigosPedidosRealizados.add(this.codPedidoExecutar);
            }
            this.codPedidoExecutar = proximoPedido.getCodPedido();
            return proximoPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Mecanico t = (Mecanico) o;

        return (super.equals(t) && this.codigosPedidosRealizados.equals(t.getCodigosPedidosRealizados()));
    }

    @Override
    public Mecanico clone() {
        return new Mecanico(this);
    }


    @Override
    public boolean verificaPosto(int codPosto) {
        return super.verificaPosto(codPosto);
    }
}
