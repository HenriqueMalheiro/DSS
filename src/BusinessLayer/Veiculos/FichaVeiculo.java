package src.Negocio.Veiculos;

import src.Dados.PedidoDAO;
import src.Dados.ServicoDAO;
import src.Exceptions.PedidoNaoEncontradoException;
import src.Negocio.Servicos.Pedido;

import java.util.HashSet;
import java.util.Set;

public class FichaVeiculo {
    private String matricula;
    private Set<Integer> codigosPedidos;
    private PedidoDAO pedidos;
    private Set<Integer> codigosServicos;
    private ServicoDAO servicosSugeridos;
    private Motor motor;

    //Construtor vazio
    public FichaVeiculo(){
        this.matricula = "N/D";
        this.codigosPedidos = new HashSet<>();
        this.pedidos = PedidoDAO.getInstance();
        this.codigosServicos = new HashSet<>();
        this.servicosSugeridos = ServicoDAO.getInstance();
        this.motor = null;
    }

    //Construtor parametrizado
    public FichaVeiculo(String matricula, Set<Integer> codigosPedidos, Set<Integer> codigosServicos, Motor m){
        this.matricula = matricula;
        this.codigosPedidos = new HashSet<>(codigosPedidos);
        this.pedidos = PedidoDAO.getInstance();
        this.codigosServicos = new HashSet<>(codigosServicos);
        this.servicosSugeridos = ServicoDAO.getInstance();
        this.motor = m.clone(); //Porque é por composição
    }

    //Construtor copia
    public FichaVeiculo(FichaVeiculo umaFicha){
        this.matricula = umaFicha.getMatricula();
        this.codigosPedidos = umaFicha.getCodigosPedidos();
        this.codigosServicos = umaFicha.getCodigosServicos();
        this.motor = umaFicha.getMotor();
    }

    //Métodos gets
    public String getMatricula() {
        return this.matricula;
    }

    public Set<Integer> getCodigosPedidos() {
        return new HashSet<>(this.codigosPedidos);
    }

    public Set<Integer> getCodigosServicos() {
        return new HashSet<>(this.codigosServicos);
    }

    public Motor getMotor() {
        return motor.clone(); // porque é por composição
    }



    //Métodos sets
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setCodigosPedidos(Set<Integer> codigosPedidos) {
        this.codigosPedidos = new HashSet<>(codigosPedidos);
    }

    public void setCodigosServicos(Set<Integer> codigosServicos) {
        this.codigosServicos = new HashSet<>(codigosServicos);
    }

    public void setMotor(Motor motor) {
        this.motor = motor.clone(); //porque é por composição
    }



    // Método que verfica se duas Fichas são iguais
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        FichaVeiculo e = (FichaVeiculo) o;

        return ( (this.matricula.equals(e.getMatricula())) && (this.codigosPedidos.equals(e.getCodigosPedidos()))
                && (this.codigosServicos.equals(e.getCodigosServicos())) && (this.motor.equals(e.getMotor())));
    }

    // Método que transforma o objeto Ficha numa String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ficha Veiculo:: {");
        sb.append(" Matricula: ").append(this.matricula);
        sb.append(" Codigos pedidos: ").append(this.codigosPedidos.toString());
        sb.append(" Codigos servicos: ").append(this.codigosServicos.toString());
        sb.append(" Motor: ").append(this.motor.toString()).append("}");


        return sb.toString();
    }

    // Método que cria uma cópia de um funcionário
    @Override
    public FichaVeiculo clone() {
        return new FichaVeiculo(this);
    }


    //Metodos adicionais
    public void adicionarPedido(int codPedido) {
        this.codigosPedidos.add(codPedido);
    }

    public void adicionarServico(int codServico) {
        this.codigosServicos.add(codServico);
    }

    public String enviarSMS(int codPedido) throws PedidoNaoEncontradoException {
        String mensagem = null;
        if(this.codigosPedidos.contains(codPedido)){
            Pedido p = this.pedidos.get(codPedido);
            mensagem = p.preencherSMS();
        }
        return mensagem;

    }
}

