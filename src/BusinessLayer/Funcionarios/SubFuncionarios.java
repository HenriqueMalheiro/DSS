package src.Negocio.Funcionarios;

import src.Dados.FuncionarioDAO;
import src.Exceptions.DiaDeTrabalhoNaoEncontradoException;
import src.Exceptions.FuncionarioNaoEncontradoException;
import src.Exceptions.PedidosQueueVazia;
import src.Exceptions.RececionistaNaoTemPedidos;
import src.Negocio.Servicos.Pedido;

public class SubFuncionarios implements ISubFuncionarios {
    private FuncionarioDAO funcionarios;

    public SubFuncionarios(){
        this.funcionarios = FuncionarioDAO.getInstance();
    }


    public boolean verificarCompetencias(int codFunc, int codPosto) throws FuncionarioNaoEncontradoException {
        Funcionario f = this.funcionarios.get(codFunc);
        return f.verificaPosto(codPosto);
    }

    public String informaPostoTrabalho(int codFunc) throws FuncionarioNaoEncontradoException {
        Funcionario f = this.funcionarios.get(codFunc);
        return f.meuPosto();
    }

    public Pedido atribuirServico(int codFunc) throws PedidosQueueVazia, FuncionarioNaoEncontradoException, RececionistaNaoTemPedidos {
        Funcionario funcionario = this.funcionarios.get(codFunc);
        Pedido pedido = funcionario.atribuirServico();
        funcionarios.put(funcionario.getCodFunc(),funcionario);

        return pedido;
    }

    public void inicioTurno(int codFunc,int codPosto) throws FuncionarioNaoEncontradoException {
        Funcionario funcionario = funcionarios.get(codFunc);
        funcionario.inicioTurno();
        funcionarios.put(codFunc,funcionario);
    }

    public void fimTurno(int codFunc) throws FuncionarioNaoEncontradoException, DiaDeTrabalhoNaoEncontradoException {
        Funcionario funcionario = funcionarios.get(codFunc);
        funcionario.fimTurno();
        funcionarios.put(codFunc,funcionario);
    }
}
