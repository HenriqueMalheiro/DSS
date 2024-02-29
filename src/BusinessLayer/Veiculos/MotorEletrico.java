package src.Negocio.Veiculos;

public class MotorEletrico extends Motor {
    private static int codMotor = 3;
    private int potenciaEletrica;

    //Construtor vazio
    public MotorEletrico(){
        super();
        this.potenciaEletrica = -1;
    }

    //Construtor parametrizado
    public MotorEletrico(String marca, int idMotor, int potenciaEletrica) {
        super(marca, idMotor);
        this.potenciaEletrica = potenciaEletrica;
    }

    //Construtor copia
    public MotorEletrico(MotorEletrico me){
        super(me);
        this.potenciaEletrica = me.getPotenciaEletrica();
    }

    //Gets
    public int getPotenciaEletrica() {
        return potenciaEletrica;
    }

    //Sets
    public void setPotenciaEletrica(int potenciaEletrica) {
        this.potenciaEletrica = potenciaEletrica;
    }


    //equals,toString,clone
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        MotorEletrico t = (MotorEletrico) o;

        return (super.equals(t) && this.potenciaEletrica == t.getPotenciaEletrica()) ;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Motor Eletrico:: {");
        sb.append(" Codigo Motor: ").append(MotorGasolina.codMotor);
        sb.append(" Potência Eletrica: ").append(MotorEletrico.codMotor);
        sb.append(super.toString()).append("}");
        return sb.toString();
    }

    @Override
    public MotorEletrico clone() {
        return new MotorEletrico(this);
    }
}
