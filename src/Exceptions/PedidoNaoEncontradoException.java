package src.Exceptions;

public class PedidoNaoEncontradoException extends Exception{
    public PedidoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}