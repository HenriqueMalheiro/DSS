package src.Negocio.Veiculos;

public class MotorGasoleo extends MotorCombustao {
    public static int codMotor = 5;

    //Construtor vazio
    public MotorGasoleo(){
        super();
    }

    //Construtor parametrizado
    public MotorGasoleo(String marca, int idMotor, int cilindrada) {
        super(marca,idMotor,cilindrada);
    }

    //Construtor copia
    public MotorGasoleo(MotorGasoleo mg){
        super(mg);
    }

    public int getCodMotor() {
        return codMotor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        MotorGasoleo t = (MotorGasoleo) o;

        return (super.equals(t)) ;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Motor Gasoleo:: {");
        sb.append(" Codigo Motor: ").append(MotorGasoleo.codMotor);
        sb.append(super.toString()).append("}");
        return sb.toString();
    }

    @Override
    public MotorGasoleo clone() {
        return new MotorGasoleo(this);
    }
}