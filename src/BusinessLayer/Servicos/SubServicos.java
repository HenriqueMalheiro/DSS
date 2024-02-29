package src.Negocio.Servicos;

import src.Dados.PedidoDAO;
import src.Dados.PostoTrabalhoDAO;
import src.Dados.ServicoDAO;
import src.Exceptions.PedidoNaoEncontradoException;
import src.Exceptions.PostoTrabalhoNaoEncontradoException;
import src.Exceptions.ServicoNaoEncontradoException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.Map;

public class SubServicos implements ISubServicos {
    private PedidoDAO pedidos;
    private ServicoDAO servicos;
    private PostoTrabalhoDAO postos;
    private Map<String, Integer> estados;


    public SubServicos() {
        this.pedidos = PedidoDAO.getInstance();
        this.servicos = ServicoDAO.getInstance();
        this.postos = PostoTrabalhoDAO.getInstance();
        this.estados = new HashMap<>();
        this.preencheEstados();
    }

    private void preencheEstados(){
        this.estados.put("sucesso",1);
        this.estados.put("insucesso",2);
        this.estados.put("pendente",3);
    }

    public void registarPedidoSMS(int codPedido) throws PedidoNaoEncontradoException {
        Pedido pedido = this.pedidos.get(codPedido);
        pedido.setMensagem(true);
        this.pedidos.put(codPedido, pedido);
    }


    // Isto tem que ser aqui, visto que tehno de ter acesso ao ServicosDAO, não posso tirar desta classe
    public String listarServicosMotor(int codMotor) {

        List<Servico> listaServicos = new ArrayList<>();
        for (Servico servico : this.servicos.values()) {
            int cod = servico.getCodMotor();

            if (cod == 1) {
                listaServicos.add(servico);
            }
            if (codMotor == 4 | codMotor == 5) {
                if (cod == 2) {
                    listaServicos.add(servico);
                }
                if (codMotor == 4) {
                    if (cod == 4) {
                        listaServicos.add(servico);
                    }

                } else {
                    if (cod == 5) {
                        listaServicos.add(servico);
                    }

                }

            } else if (codMotor == 3) {
                if (cod == 3) {
                    listaServicos.add(servico);
                }

            } else if (codMotor == 6 | codMotor == 7) {
                if (cod == 2) {
                    listaServicos.add(servico);
                }
                if (cod == 3) {
                    listaServicos.add(servico);
                }
                if(codMotor == 6){
                    if(cod == 4){
                        listaServicos.add(servico);
                    }
                }else {
                    if(cod == 5){
                        listaServicos.add(servico);
                    }
                }
            }

        }
        StringBuilder str = new StringBuilder();

        for (Servico servico : listaServicos) {
            str.append(servico.toString());
        }
        return str.toString();
    }

    public Pedido agendarServico(int codServico,int codPosto,String matricula) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException {
        PostoTrabalho postoPedido =this.postos.get(codPosto);
        Integer codEstado=this.estados.get("pendente");
        Pedido pedido= postoPedido.adicionarPedidoPosto(codEstado,codServico,matricula);
        postos.put(postoPedido.getCodPosto(),postoPedido);

        return pedido;
    }

    //Método que marca o pedido como concluido com sucesso
    public void conclusaoServico_comSucesso(int codPedido) throws PedidoNaoEncontradoException {
        Pedido p = this.pedidos.get(codPedido);
        int cod = this.estados.get("sucesso");
        p.setCodEstado(cod);
        pedidos.put(codPedido, p);
    }

    //Método que marca o pedido como concluído sem sucesso
    public void conclusaoServico_semSucesso(int codPedido, String motivo) throws PedidoNaoEncontradoException {
        Pedido p = this.pedidos.get(codPedido);
        int cod = this.estados.get("insucesso");
        p.setCodEstado(cod);
        p.setMotivo(motivo);
        pedidos.put(codPedido,p);
    }

    public Integer disponibilidadePostosTrabalho(Integer codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException {
        Servico s = servicos.get(codServico);
        return s.disponibilidadePostosTrabalho();
    }

    public Float precoServico(Integer codServico) throws ServicoNaoEncontradoException {
        Servico s = this.servicos.get(codServico);
        return s.getPreco();
    }

    public LocalTime tempoEstimadoPosto(Integer codPosto, Integer codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException {
        Servico s = this.servicos.get(codServico);
        return this.postos.get(codPosto).tempoEstimado(s.getDuracaoMedia());
    }

    public String listarPedidosPosto(Integer codPosto) throws PedidoNaoEncontradoException, PostoTrabalhoNaoEncontradoException {
        return this.postos.get(codPosto).listarPedidos();
    }
}