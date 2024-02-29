package src.Negocio.Veiculos;

public class MotorGasolina extends MotorCombustao {
    public static int codMotor = 4;

    //Construtor vazio
    public MotorGasolina(){
        super();
    }

    //Construtor parametrizado
    public MotorGasolina(String marca,int idMotor, int cilindrada) {
        super(marca,idMotor,cilindrada);
    }

    //Construtor copia
    public MotorGasolina(MotorGasolina mg){
        super(mg);
    }

    public  int getCodMotor() {
        return codMotor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        MotorGasolina t = (MotorGasolina) o;

        return (super.equals(t)) ;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Motor Gasolina:: {");
        sb.append(" Codigo Motor: ").append(MotorGasolina.codMotor);
        sb.append(super.toString()).append("}");
        return sb.toString();
    }

    @Override
    public MotorGasolina clone() {
        return new MotorGasolina(this);
    }
}
