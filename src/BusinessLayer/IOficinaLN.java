package src.Negocio;

import src.Exceptions.*;
import src.Negocio.Servicos.Pedido;

import java.time.LocalTime;

 public interface IOficinaLN {

     boolean verificaCredenciaisCliente(int nif);
     String listarFichasCliente(int nif) throws ClienteNaoEncontradoException, FichaVeiculoNaoEncontradoException;
     String listarServicosMotor(String matricula) throws FichaVeiculoNaoEncontradoException;
     Integer disponibilidadePostosTrabalho(int codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;
     LocalTime tempoEstimadoPosto(int codPosto, int codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;
     float precoServico(int codServico) throws ServicoNaoEncontradoException;
     Pedido agendarServico(int codServico, int codPosto, String matricula) throws FichaVeiculoNaoEncontradoException, ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;
     boolean verificarCompetencias(int codPosto, int codFunc) throws FuncionarioNaoEncontradoException;
     String informaPostoTrabalho(int codFunc) throws FuncionarioNaoEncontradoException;
     void inicioTurno(int codFunc,int codPosto) throws FuncionarioNaoEncontradoException;
     void fimTurno(int codFunc) throws FuncionarioNaoEncontradoException, DiaDeTrabalhoNaoEncontradoException;
     void registarPedidoSMS(int codPedido) throws PedidoNaoEncontradoException;
     String listarPedidosPosto(Integer codPosto) throws PedidoNaoEncontradoException, PostoTrabalhoNaoEncontradoException;
     Pedido atribuirServico(int codFunc) throws PedidosQueueVazia, FuncionarioNaoEncontradoException, RececionistaNaoTemPedidos;
     void conclusaoServico_comSucesso(int codPedido) throws PedidoNaoEncontradoException;
     void conclusaoServico_semSucesso(int codPedido, String motivo) throws PedidoNaoEncontradoException;
     void adicionarServicoSugerido_FichaVeiculo(String matricula, int codServico) throws FichaVeiculoNaoEncontradoException;
     String enviarSMS(String matricula, int codPedido) throws ClienteNaoEncontradoException, PedidoNaoEncontradoException, FichaVeiculoNaoEncontradoException;
}
