package src.Negocio;

import src.Exceptions.*;
import src.Negocio.Funcionarios.ISubFuncionarios;
import src.Negocio.Funcionarios.SubFuncionarios;
import src.Negocio.Servicos.ISubServicos;
import src.Negocio.Servicos.Pedido;
import src.Negocio.Servicos.SubServicos;
import src.Negocio.Veiculos.ISubVeiculos;
import src.Negocio.Veiculos.SubVeiculos;

import java.time.LocalTime;

public class OficinaLN implements IOficinaLN{

    private ISubServicos subServicos;
    private ISubVeiculos subVeiculos;
    private ISubFuncionarios subFuncionarios;

    public OficinaLN(){
        this.subServicos = new SubServicos();
        this.subVeiculos = new SubVeiculos();
        this.subFuncionarios = new SubFuncionarios();
    }


    public boolean verificaCredenciaisCliente(int nif){
        return this.subVeiculos.verificarCredenciaisCliente(nif);
    }

    public String listarFichasCliente(int nif) throws ClienteNaoEncontradoException, FichaVeiculoNaoEncontradoException {
        return this.subVeiculos.listarFichasCliente(nif);
    }

    public String listarServicosMotor(String matricula) throws FichaVeiculoNaoEncontradoException {
        int codMotor = this.subVeiculos.getCodMotor(matricula);
        return this.subServicos.listarServicosMotor(codMotor);
    }

    public Integer disponibilidadePostosTrabalho(int codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException {
        return this.subServicos.disponibilidadePostosTrabalho(codServico);
    }
    public LocalTime tempoEstimadoPosto(int codPosto,int codServico) throws ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException {
        return this.subServicos.tempoEstimadoPosto(codPosto,codServico);
    }

    public float precoServico(int codServico) throws ServicoNaoEncontradoException {
        return this.subServicos.precoServico(codServico);
    }
    public Pedido agendarServico(int codServico,int codPosto,String matricula) throws FichaVeiculoNaoEncontradoException, ServicoNaoEncontradoException, PostoTrabalhoNaoEncontradoException {
        Pedido pedido = this.subServicos.agendarServico(codServico,codPosto,matricula);
        this.subVeiculos.adicionarPedidoServico_FichaVeiculo(matricula,pedido.getCodPedido());
        return pedido;
    }

    public boolean verificarCompetencias(int codPosto, int codFunc) throws FuncionarioNaoEncontradoException {
        return this.subFuncionarios.verificarCompetencias(codPosto, codFunc);
    }

    public String informaPostoTrabalho(int codFunc) throws FuncionarioNaoEncontradoException {
        return this.subFuncionarios.informaPostoTrabalho(codFunc);

    }
    public void inicioTurno(int codFunc,int codPosto) throws FuncionarioNaoEncontradoException {
        this.subFuncionarios.inicioTurno(codFunc,codPosto);
    }

    public void fimTurno(int codFunc) throws FuncionarioNaoEncontradoException, DiaDeTrabalhoNaoEncontradoException {
        this.subFuncionarios.fimTurno(codFunc);
    }

    public void registarPedidoSMS(int codPedido) throws PedidoNaoEncontradoException{
        this.subServicos.registarPedidoSMS(codPedido);
    }

    public String listarPedidosPosto(Integer codPosto) throws PedidoNaoEncontradoException, PostoTrabalhoNaoEncontradoException{
       return  this.subServicos.listarPedidosPosto(codPosto);
    }

    public Pedido atribuirServico(int codFunc) throws PedidosQueueVazia, FuncionarioNaoEncontradoException, RececionistaNaoTemPedidos{
        return this.subFuncionarios.atribuirServico(codFunc);
    }

    public void conclusaoServico_comSucesso(int codPedido) throws PedidoNaoEncontradoException{
        this.subServicos.conclusaoServico_comSucesso(codPedido);
    }

    public void conclusaoServico_semSucesso(int codPedido, String motivo) throws PedidoNaoEncontradoException{
        this.subServicos.conclusaoServico_semSucesso(codPedido,motivo);
    }

    public void adicionarServicoSugerido_FichaVeiculo(String matricula, int codServico) throws FichaVeiculoNaoEncontradoException{
        this.subVeiculos.adicionarServicoSugerido_FichaVeiculo(matricula, codServico);
    }

    public String enviarSMS(String matricula, int codPedido) throws ClienteNaoEncontradoException, PedidoNaoEncontradoException, FichaVeiculoNaoEncontradoException{
        return this.subVeiculos.enviarSMS(matricula, codPedido);
    }
}
