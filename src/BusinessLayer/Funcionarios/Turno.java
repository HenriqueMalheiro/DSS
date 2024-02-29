package src.Negocio.Funcionarios;

import java.time.LocalTime;

public class Turno {
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private int codTurno;


    //Construtor vazio
    public Turno(){
        this.horaInicio = null;
        this.horaFim = null;
        this.codTurno=-1;
    }

    //Construtor parametrizado
    public Turno(LocalTime horaInicio, LocalTime horaFim,int codTurno){
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.codTurno=codTurno;
    }

    //Constutor cópia
    public Turno(Turno umTurno){
        this.horaInicio = umTurno.getHoraInicio();
        this.horaFim = umTurno.getHoraFim();
        this.codTurno=umTurno.getCodTurno();
    }

    //Gets
    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    public LocalTime getHoraFim(){
        return this.horaFim;
    }

    public int getCodTurno() {
        return codTurno;
    }


    //Sets
    public void setHoraInicio(LocalTime novaHora){
        this.horaInicio = novaHora;
    }

    public void setHoraFim(LocalTime novaHora){
        this.horaFim = novaHora;
    }

    public void setCodTurno(int codTurno) {
        this.codTurno = codTurno;
    }

    // Método que verifica se dois Turnos são iguais
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Turno e = (Turno) o;

        return (this.horaInicio.equals(e.getHoraInicio()) && this.horaFim.equals(e.getHoraFim()));
    }

    // Método que transforma o objeto Turno numa String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Turno:: {");
        if(this.horaInicio == null)
            sb.append(" Hora de inicio: N/D");
        else
            sb.append(" Hora de inicio: ").append(this.horaInicio.toString());

        if(this.horaFim == null)
            sb.append(" Hora de fim: N/D").append("}");
        else
            sb.append(" Hora de fim: ").append(this.horaFim.toString()).append("}");

        return sb.toString();
    }

    // Método que cria uma cópia de um Turno
    @Override
    public Turno clone() {

        return new Turno(this);
    }
}
