package src.Negocio.Funcionarios;

import src.Dados.DiaDeTrabalhoDAO;
import src.Exceptions.DiaDeTrabalhoNaoEncontradoException;
import src.Exceptions.PedidosQueueVazia;
import src.Exceptions.RececionistaNaoTemPedidos;
import src.Negocio.Servicos.Pedido;
import src.Negocio.Servicos.PostoTrabalho;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Funcionario {
    private int codFunc;
    private String nome;
    private PostoTrabalho postoTrabalhoAtual;
    private Turno turno;
    private DiaDeTrabalhoDAO diasTrabalho;


    //Construtor Vazio
    public Funcionario(){
        this.codFunc = -1;
        this.nome = "N/D";
        this.postoTrabalhoAtual = new PostoTrabalho();
        this.turno = new Turno();
        this.diasTrabalho = DiaDeTrabalhoDAO.getInstance();
    }

    //Construtor Parametrizado
    public Funcionario(Integer codFunc, String nome, PostoTrabalho postoTrabalhoAtual, Turno turno){
        this.codFunc = codFunc;
        this.nome = nome;
        this.postoTrabalhoAtual = postoTrabalhoAtual; //pq é agregação
        this.turno = turno.clone(); // pq composição
        this.diasTrabalho= DiaDeTrabalhoDAO.getInstance();
    }

    //Constutor cópia
    public Funcionario(Funcionario umFuncionario){
        this.codFunc = umFuncionario.getCodFunc();
        this.nome = umFuncionario.getNome();
        this.postoTrabalhoAtual = umFuncionario.getPostoTrabalhoAtual();
        this.turno = umFuncionario.getTurno();
        this.diasTrabalho = DiaDeTrabalhoDAO.getInstance();
    }

    //Gets

    public int getCodFunc() {
        return codFunc;
    }

    public String getNome() {
        return nome;
    }

    //Como é agregação não é preciso fazer clone
    public PostoTrabalho getPostoTrabalhoAtual() {
        return postoTrabalhoAtual;
    }

    public Turno getTurno() {
        return (this.turno.clone());
    }

    public DiaDeTrabalhoDAO getDiasTrabalho() {
        return diasTrabalho;
    }

    //Sets
    public void setCodFunc(int codFunc) {
        this.codFunc = codFunc;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPostoTrabalhoAtual(PostoTrabalho postoTrabalhoAtual) {
        this.postoTrabalhoAtual = postoTrabalhoAtual;
    }

    public void setTurno(Turno turno) {
        this.turno = turno.clone();
    }

    public void setDiasTrabalho(DiaDeTrabalhoDAO diasTrabalho) {
        this.diasTrabalho = diasTrabalho;
    }

    // Método que verfica se dois funcionários são iguais
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Funcionario e = (Funcionario) o;

        return (this.codFunc == e.getCodFunc() && this.nome.equals(e.getNome()) &&
                this.postoTrabalhoAtual.equals(e.getPostoTrabalhoAtual()) &&
                this.turno.equals(e.getTurno()));
    }

    // Método que transforma o objeto funcionário numa String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Funcionário:: {");
        sb.append(" Codigo funcionário: ").append(this.codFunc);
        sb.append(" Nome: ").append(this.nome);
        sb.append(" Posto atual: ").append(this.postoTrabalhoAtual.toString());
        sb.append(" Turno: ").append(this.turno.toString()).append("}");
        return sb.toString();
    }

    // Método que cria uma cópia de um funcionário
    public abstract Funcionario clone();



    // Método que verifica se o posto passado como argumento é do funcionário
    public boolean verificaPosto(int codPosto) {
        boolean b = false;

        int idPosto = this.postoTrabalhoAtual.getCodPosto();
        if(idPosto == codPosto)
            b = true;

        return b;
    }

    // Método que devolve o nome do posto a que o funcionário pertence
    public String meuPosto() {
        return this.postoTrabalhoAtual.getNome();
    }


    public void inicioTurno(){
        postoTrabalhoAtual.setAtivo(true);
        LocalTime horasEntrada = LocalTime.now();
        DiaDeTrabalho diaDeTrabalho = new DiaDeTrabalho();
        diaDeTrabalho.setHoraEntrada(horasEntrada);
        this.diasTrabalho.put(this.codFunc,diaDeTrabalho);
    }

    public void fimTurno() throws DiaDeTrabalhoNaoEncontradoException {
        postoTrabalhoAtual.setAtivo(false);

        LocalDate dataSaida = LocalDate.now();
        DiaDeTrabalho diaDeTrabalho = this.diasTrabalho.get(dataSaida,this.codFunc);
        LocalTime horaSaida = LocalTime.now();
        diaDeTrabalho.setHoraSaida(horaSaida);
        this.diasTrabalho.put(this.codFunc,diaDeTrabalho);
    }

    public abstract Pedido atribuirServico() throws PedidosQueueVazia, RececionistaNaoTemPedidos;
}
