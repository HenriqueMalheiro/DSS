package src.Negocio.Funcionarios;

import src.Dados.DiaDeTrabalhoDAO;

import java.time.LocalDate;
import java.time.LocalTime;

public class DiaDeTrabalho {
    private int codDiaTrabalho;
    private LocalDate dia;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;

    private static int contadorDias = DiaDeTrabalhoDAO.size();

    //Construtor vazio
    public DiaDeTrabalho(){
        this.codDiaTrabalho = contadorDias++;
        this.dia = LocalDate.now();
        this.horaEntrada = LocalTime.now();
        this.horaSaida = null;
    }

    //Construtor parametrizado
    public DiaDeTrabalho(int codDiaTrabalho, LocalDate dia, LocalTime horaEntrada, LocalTime horaSaida){
        this.codDiaTrabalho = codDiaTrabalho;
        this.dia = dia;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    //Constutor cópia
    public DiaDeTrabalho(DiaDeTrabalho umDiaDeTrabalho){
        this.codDiaTrabalho = umDiaDeTrabalho.getCodDiaTrabalho();
        this.dia = umDiaDeTrabalho.getDia();
        this.horaEntrada = umDiaDeTrabalho.getHoraEntrada();
        this.horaSaida = umDiaDeTrabalho.getHoraSaida();
    }

    //Gets
    public int getCodDiaTrabalho() {
        return codDiaTrabalho;
    }

    public LocalDate getDia() {
        return dia;
    }

    public LocalTime getHoraEntrada() {
        return this.horaEntrada;
    }

    public LocalTime getHoraSaida(){
        return this.horaSaida;
    }


    //Sets
    public void setCodDiaTrabalho(int codDiaTrabalho) {
        this.codDiaTrabalho = codDiaTrabalho;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public void setHoraEntrada(LocalTime novaHora){
        this.horaEntrada = novaHora;
    }

    public void setHoraSaida(LocalTime novaHora){
        this.horaSaida = novaHora;
    }


    // Método que verifica se dois Dias de trabalho são iguais
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        DiaDeTrabalho e = (DiaDeTrabalho) o;

        return (this.codDiaTrabalho == e.getCodDiaTrabalho() &&
                this.horaEntrada.equals(e.getHoraEntrada()) && this.horaSaida.equals(e.getHoraSaida())
                && this.dia.equals(e.getDia()));
    }

    // Método que transforma o objeto DiaDeTrabalho numa String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dia de Trabalho:: {");
        sb.append(" Codigo: ").append(this.codDiaTrabalho);
        sb.append(" Dia: ").append(this.dia.toString());
        sb.append(" Hora de entrada: ").append(this.horaEntrada.toString());

        if(this.horaSaida == null)
            sb.append(" Hora de saida: N/D").append("}");
        else
            sb.append(" Hora de saida: ").append(this.horaSaida).append("}");

        return sb.toString();
    }

    // Método que cria uma cópia de um DiaDeTrabalho
    @Override
    public DiaDeTrabalho clone() {
        return new DiaDeTrabalho(this);
    }
}
