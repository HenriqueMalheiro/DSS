package src.Exceptions;

public class ClienteNaoEncontradoException extends Exception{
    public ClienteNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}