package src.Negocio.Veiculos;

import src.Dados.FichaVeiculoDAO;
import src.Exceptions.FichaVeiculoNaoEncontradoException;

import java.util.HashSet;
import java.util.Set;

public class Cliente {
    private int nif;
    private String nome;
    private String morada;
    private int telefone;
    private String email;
    private Set<String> matriculas;
    private FichaVeiculoDAO fichasVeiculos;

    //Construtor vazio
    public Cliente(){
        this.nif = -1;
        this.nome = "N/D";
        this.morada = "N/D";
        this.telefone = -1;
        this.email = "N/D";
        this.matriculas = new HashSet<>();
        this.fichasVeiculos = FichaVeiculoDAO.getInstance();
    }

    //Construtor parametrizado
    public Cliente(int nif, String nome, String morada, int telefone, String email, Set<String> matriculas){
        this.nif = nif;
        this.nome = nome;
        this.morada = morada;
        this.telefone = telefone;
        this.email = email;
        this.matriculas = new HashSet<>(matriculas);
        this.fichasVeiculos = FichaVeiculoDAO.getInstance();

    }

    //Construtor cópia
    public Cliente(Cliente umCliente){
        this.nif = umCliente.getNif();
        this.nome = umCliente.getNome();
        this.morada = umCliente.getMorada();
        this.telefone = umCliente.getTelefone();
        this.email = umCliente.getEmail();
        this.matriculas = umCliente.getMatriculas();
        this.fichasVeiculos = FichaVeiculoDAO.getInstance();

    }

    //Métodos gets
    public int getNif() {
        return this.nif;
    }

    public String getNome(){
        return this.nome;
    }

    public String getMorada(){
        return this.morada;
    }

    public int getTelefone() {
        return this.telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Set<String> getMatriculas() {
        return new HashSet<>(this.matriculas);
    }


    //Métodos Sets
    public void setNif(int nif) {
        this.nif = nif;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatriculas(Set<String> matriculas) {
        this.matriculas = new HashSet<>(matriculas);
    }


    // Método que verfica se dois Clientes são iguais
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Cliente e = (Cliente) o;

        return ( (this.nif == e.getNif()) && (this.nome.equals(e.getNome())) && this.morada.equals(e.getMorada())
                && (this.telefone == e.getTelefone()) && (this.email.equals(e.getEmail())) && (this.matriculas.equals(e.getMatriculas())));
    }

    // Método que transforma o objeto Cliente numa String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente:: {");
        sb.append(" NIF: ").append(this.nif);
        sb.append(" Nome: ").append(this.nome);
        sb.append(" Morada: ").append(this.morada);
        sb.append(" Telefone: ").append(this.telefone);
        sb.append(" Email: ").append(this.email);
        sb.append(" Ids matriculas: ").append(this.matriculas.toString()).append("}");

        return sb.toString();
    }

    // Método que cria uma cópia de um funcionário
    @Override
    public Cliente clone() {
        return new Cliente(this);
    }


    //Metodos adicionais
    public String listarFichas() throws FichaVeiculoNaoEncontradoException {
        StringBuilder sb = new StringBuilder();
        for (String matricula:this.matriculas){
            sb.append(this.fichasVeiculos.get(matricula).toString());
        }
        return sb.toString();
    }
}
