package src.Negocio.Servicos;

import src.Dados.ServicoDAO;

import java.util.HashSet;
import java.util.Set;

public class Pack extends Servico {
    private ServicoDAO servicos;
    private Set<Integer> codServicos;

    public Pack(){
        super();
        this.servicos=ServicoDAO.getInstance();
        this.codServicos=new HashSet<>();
    }

    public Pack(boolean universal, int codServico, int duracaoMedia, String nome, float preco, int codMotor,
                Set<Integer> codigosPosto, Set<Integer> codServicos){
        super(universal,codServico,duracaoMedia,nome,preco,codMotor,codigosPosto);
        this.servicos=ServicoDAO.getInstance();
        this.codServicos= new HashSet<>(codServicos);
    }

    public Pack(Pack pack){
        super(pack);
        this.servicos=pack.getServicos();
        this.codServicos=pack.getCodServicos();
    }


    //Gets
    public ServicoDAO getServicos() {
        return servicos;
    }

    public Set<Integer> getCodServicos() {
        return new HashSet<>(codServicos);
    }

    //Sets
    public void setServicos(ServicoDAO servicos) {
        this.servicos = servicos;
    }

    public void setCodServicos(Set<Integer> codServicos) {
        this.codServicos = new HashSet<>(codServicos);
    }

    //toString, equals, clone
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Pack:: {");
        sb.append(super.toString());
        sb.append("Lista de codigos de Servicos: ").append(this.codServicos.toString()).append("}\n");

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Pack p = (Pack) o;

        return (super.equals(p) && this.codServicos.equals(p.getCodServicos()));
    }

    @Override
    public Pack clone() {
        return new Pack(this);
    }
}
