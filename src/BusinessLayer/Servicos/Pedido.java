package src.Negocio.Servicos;

import src.Dados.PedidoDAO;
import src.Dados.ServicoDAO;
import src.Exceptions.ServicoNaoEncontradoException;

public class Pedido {
    private String motivo;
    private boolean atribuido;
    private boolean mensagem;
    private int codEstado;
    private int codPedido;
    private int codServico;
    private String matricula;
    private ServicoDAO servico;

    private static int contadorPedidos = PedidoDAO.size();

    // Construtor
    public Pedido(int codPedido,String motivo, boolean atribuido, boolean mensagem, int codEstado,String matricula ,int codServico) {
        this.codPedido = codPedido;
        this.motivo = motivo;
        this.atribuido = atribuido;
        this.mensagem = mensagem;
        this.codEstado = codEstado;
        this.codServico = codServico;
        this.matricula=matricula;
        this.servico = ServicoDAO.getInstance();
    }


    public Pedido(int codServico){
        this.codServico = codServico;
        this.codPedido = ++contadorPedidos;
        this.servico = ServicoDAO.getInstance();
    }

    public Pedido(int codServico, String matricula) {
        this.codServico = codServico;
        this.codPedido = ++contadorPedidos;
        this.matricula = matricula;
        this.servico = ServicoDAO.getInstance();
    }

    // Métodos de acesso (getters e setters) para cada atributo
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean getAtribuido() {
        return atribuido;
    }

    public void setAtribuido(boolean atribuido) {
        this.atribuido = atribuido;
    }

    public boolean getMensagem() {
        return mensagem;
    }

    public void setMensagem(boolean mensagem) {
        this.mensagem = mensagem;
    }

    public int getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(int codEstado) {
        this.codEstado = codEstado;
    }

    public int getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(int codPedido) {
        this.codPedido = codPedido;
    }

    public int getCodServico() {
        return codServico;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Servico getServico() throws ServicoNaoEncontradoException {
        return this.servico.get(this.codServico);
    }

    public void setCodServico(int codServico) {
        this.codServico = codServico;
    }

    public String preencherSMS() {
        String mensagem = "N/D";
        if(this.mensagem) {
            if (this.motivo.equals("N/D")) {
                mensagem = "Mensagem do Pedido " + this.codPedido + " concluído com sucesso";
            } else {
                mensagem = "Mensagem do Pedido " + this.codPedido + " concluido sem sucesso porque " + this.motivo;
            }
        }
        return mensagem;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "motivo='" + motivo + '\'' +
                ", matricula=" + matricula +
                ", atribuido=" + atribuido +
                ", mensagem=" + mensagem +
                ", codEstado=" + codEstado +
                ", codPedido=" + codPedido +
                ", codServico=" + codServico +
                '}';
    }
}
