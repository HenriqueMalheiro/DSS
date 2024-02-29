package src.Negocio.Veiculos;

import src.Dados.FichaVeiculoDAO;
import src.Exceptions.ClienteNaoEncontradoException;
import src.Exceptions.FichaVeiculoNaoEncontradoException;
import src.Exceptions.PedidoNaoEncontradoException;
import src.Dados.ClienteDAO;
import src.Negocio.Servicos.Pedido;

public class SubVeiculos implements ISubVeiculos {
    private ClienteDAO clientes;
    private FichaVeiculoDAO fichasVeiculos;

    public SubVeiculos() {
        this.clientes = ClienteDAO.getInstance();
        this.fichasVeiculos = FichaVeiculoDAO.getInstance();
    }

    public String listarFichasCliente(Integer NIF) throws ClienteNaoEncontradoException, FichaVeiculoNaoEncontradoException {
        return this.clientes.get(NIF).listarFichas();
    }

    public int getCodMotor(String matricula) throws FichaVeiculoNaoEncontradoException {
        return fichasVeiculos.get(matricula).getMotor().getCodMotor();
    }


    public boolean verificarCredenciaisCliente(Integer NIF) {
        return this.clientes.containsKey(NIF);
    }

    public void adicionarPedidoServico_FichaVeiculo(String matricula, int codPedido) throws FichaVeiculoNaoEncontradoException {
        FichaVeiculo fv = this.fichasVeiculos.get(matricula);
        fv.adicionarPedido(codPedido);
    }

    public void adicionarServicoSugerido_FichaVeiculo(String matricula, int codServico) throws FichaVeiculoNaoEncontradoException {
        FichaVeiculo fv = this.fichasVeiculos.get(matricula);
        fv.adicionarServico(codServico);
    }

    public String enviarSMS(String matricula, int codPedido) throws PedidoNaoEncontradoException, FichaVeiculoNaoEncontradoException {
        FichaVeiculo fv = this.fichasVeiculos.get(matricula);
        return fv.enviarSMS(codPedido);
    }
}