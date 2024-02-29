package src.Negocio.Funcionarios;

import src.Exceptions.DiaDeTrabalhoNaoEncontradoException;
import src.Exceptions.FuncionarioNaoEncontradoException;
import src.Exceptions.PedidosQueueVazia;
import src.Exceptions.RececionistaNaoTemPedidos;
import src.Negocio.Servicos.Pedido;

public interface ISubFuncionarios {

     boolean verificarCompetencias(int codFunc, int codPosto) throws FuncionarioNaoEncontradoException;
     String informaPostoTrabalho(int codFunc) throws FuncionarioNaoEncontradoException;
     Pedido atribuirServico(int codFunc) throws PedidosQueueVazia, FuncionarioNaoEncontradoException, RececionistaNaoTemPedidos;
     void inicioTurno(int codFunc,int codPosto) throws FuncionarioNaoEncontradoException;
     void fimTurno(int codFunc) throws FuncionarioNaoEncontradoException, DiaDeTrabalhoNaoEncontradoException;
}