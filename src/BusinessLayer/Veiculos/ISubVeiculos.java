package src.Negocio.Veiculos;

import src.Exceptions.ClienteNaoEncontradoException;
import src.Exceptions.FichaVeiculoNaoEncontradoException;
import src.Exceptions.PedidoNaoEncontradoException;
import src.Negocio.Servicos.Pedido;

 public interface ISubVeiculos {

     String listarFichasCliente(Integer NIF) throws ClienteNaoEncontradoException, FichaVeiculoNaoEncontradoException;
     boolean verificarCredenciaisCliente(Integer NIF);
     void adicionarPedidoServico_FichaVeiculo(String matricula, int codPedido) throws FichaVeiculoNaoEncontradoException;
     void adicionarServicoSugerido_FichaVeiculo(String matricula, int codServico) throws FichaVeiculoNaoEncontradoException;
     String enviarSMS(String matricula, int codPedido) throws ClienteNaoEncontradoException, PedidoNaoEncontradoException, FichaVeiculoNaoEncontradoException;
     int getCodMotor(String matricula) throws FichaVeiculoNaoEncontradoException;
}