package src.Negocio.Funcionarios;

import src.Exceptions.RececionistaNaoTemPedidos;
import src.Negocio.Servicos.Pedido;
import src.Negocio.Servicos.PostoTrabalho;

public class Rececionista extends Funcionario {

    //Construtor vazio
    public Rececionista(){
        super();
    }

    //Construtor parametrizado
    public Rececionista(Integer codFunc, String nome, PostoTrabalho postoTrabalhoAtual, Turno turno){
        super(codFunc, nome, postoTrabalhoAtual, turno);
    }

    //Construtor cópia
    public Rececionista(Rececionista umRececionista){
        super(umRececionista);
    }


    @Override
    public Rececionista clone() {
        return new Rececionista(this);
    }

    public boolean equals(Object o){return super.equals(o);}

    public String toString(){return super.toString();}

    public Pedido atribuirServico() throws RececionistaNaoTemPedidos {
      throw new RececionistaNaoTemPedidos("O rececionista não realiza pedidos");
    }
}
