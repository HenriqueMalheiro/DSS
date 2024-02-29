package src.OficinaUI;

import src.Negocio.OficinaLN;

public class Main {
    public static void main(String[] args){
        View view = new View();
        OficinaLN oficinaLN = new OficinaLN();
        Controlador controlador = new Controlador(view,oficinaLN);
        controlador.menuInicio();
    }
}