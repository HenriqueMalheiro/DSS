package src.Negocio.Servicos;

import src.Exceptions.PedidoNaoEncontradoException;
import src.Exceptions.PostoTrabalhoNaoEncontradoException;
import src.Exceptions.ServicoNaoEncontradoException;

import java.time.LocalTime;

public interface ISubServicos {

    void registarPedidoSMS(int codPedido) throws PedidoNaoEncontradoException;
    String listarServicosMotor(int codMotor);
    Pedido agendarServico(int codServico, int codPosto,String matricula) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;
    void conclusaoServico_comSucesso(int codPedido) throws PedidoNaoEncontradoException;
    void conclusaoServico_semSucesso(int codPedido, String motivo) throws PedidoNaoEncontradoException;
    String listarPedidosPosto(Integer codPosto) throws PedidoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;
    Integer disponibilidadePostosTrabalho(Integer codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;
    Float precoServico(Integer codServico) throws ServicoNaoEncontradoException;
    LocalTime tempoEstimadoPosto(Integer codPosto,Integer codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;

}