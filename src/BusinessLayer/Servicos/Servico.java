package src.Negocio.Servicos;

import src.Dados.PostoTrabalhoDAO;
import src.Exceptions.PostoTrabalhoNaoEncontradoException;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Servico {
    private boolean universal;
    private int codServico;
    private int duracaoMedia;
    private String nome;
    private float preco;
    private int codMotor;

    private Set<Integer> codigosPosto;
    private PostoTrabalhoDAO postosTrabalho;


    // Construtores
    public Servico(){
        this.universal=false;
        this.codServico=-1;
        this.duracaoMedia=0;
        this.nome="N/D";
        this.preco=0;
        this.codMotor=-1;
        this.codigosPosto=new HashSet<>();
        this.postosTrabalho=PostoTrabalhoDAO.getInstance();
    }

    public Servico(boolean universal, int codServico, int duracaoMedia, String nome
            , float preco, int codMotor, Set<Integer> codigosPosto) {
        this.universal = universal;
        this.codServico = codServico;
        this.duracaoMedia = duracaoMedia;
        this.nome = nome;
        this.preco = preco;
        this.codMotor = codMotor;
        this.codigosPosto = new HashSet<>(codigosPosto);
        this.postosTrabalho=PostoTrabalhoDAO.getInstance();
    }

    public Servico(Servico servico){
        this.universal=servico.getUniversal();
        this.codServico=servico.getCodServico();
        this.duracaoMedia=servico.getDuracaoMedia();
        this.nome=servico.getNome();
        this.preco=servico.getPreco();
        this.codMotor=servico.getCodMotor();
        this.codigosPosto=servico.getCodigosPosto();
        this.postosTrabalho=servico.getPostosTrabalho();
    }

    // gets
    public boolean getUniversal() {
        return universal;
    }
    public int getCodServico() {
        return codServico;
    }
    public int getDuracaoMedia() {
        return duracaoMedia;
    }
    public String getNome() {
        return nome;
    }
    public float getPreco() {
        return preco;
    }
    public int getCodMotor() {
        return codMotor;
    }
    public Set<Integer> getCodigosPosto() {
        return new HashSet<>(this.codigosPosto);
    }
    public PostoTrabalhoDAO getPostosTrabalho() {
        return postosTrabalho;
    }

    //Sets
    public void setUniversal(boolean universal) {
        this.universal = universal;
    }
    public void setCodServico(int codServico) {
        this.codServico = codServico;
    }
    public void setDuracaoMedia(int duracaoMedia) {
        this.duracaoMedia = duracaoMedia;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPreco(float preco) {
        this.preco = preco;
    }
    public void setCodMotor(int codMotor) {
        this.codMotor = codMotor;
    }
    public void setCodigosPosto(Set<Integer> codigosPosto) {
        this.codigosPosto = new HashSet<>(codigosPosto);
    }
    public void setPostosTrabalho(PostoTrabalhoDAO postosTrabalho) {
        this.postosTrabalho = postosTrabalho;
    }

    //toString, equals, clone
    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("Serviço: ").append(this.getNome())
                .append(", Código: ").append(this.getCodServico())
                .append(", Duração Média: ").append(this.getDuracaoMedia())
                .append(", Preço: ").append(this.getPreco())
                .append(System.lineSeparator());
        return resultado.toString();
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Servico e = (Servico) o;

        return (this.universal==e.getUniversal() && this.codServico==e.getCodServico() &&
                this.duracaoMedia==e.getDuracaoMedia() && this.nome.equals(e.getNome()) &&
                this.preco==e.getPreco() && this.codMotor==this.getCodMotor() &&
                this.codigosPosto.equals(e.getCodigosPosto()));
    }

    @Override
    public Servico clone(){
        return new Servico(this);
    }


    //Metodos adicionais
    public Integer disponibilidadePostosTrabalho() throws PostoTrabalhoNaoEncontradoException {
        Integer res = null;
        LocalTime horarioDisponibilidadePostos=LocalTime.MAX;

        for(Integer codPosto: this.codigosPosto){
            PostoTrabalho p = this.postosTrabalho.get(codPosto);
            LocalTime horario = p.getDisponibilidade();
            if (horario.isBefore(horarioDisponibilidadePostos)){
                horarioDisponibilidadePostos=horario;
                res=codPosto;
            }
        }
        return res;
    }
}
