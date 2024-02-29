package src.Negocio.Veiculos;

public abstract class MotorCombustao extends Motor {
    public static int codMotor = 2;
    public int cilindrada;

    //Construtor vazio
    public MotorCombustao(){
        super();
        this.cilindrada = -1;
    }

    //Construtor parametrizado
    public MotorCombustao(String marca, int idMotor, int cilindrada) {
        super(marca,idMotor);
        this.cilindrada = cilindrada;
    }

    //Construtor copia
    public MotorCombustao(MotorCombustao mc){
        super(mc);
        this.cilindrada = mc.getCilindrada();
    }

    //Gets
    public int getCilindrada() {
        return cilindrada;
    }


    //Sets
    public void setCilindrada(int cilindrada){
        this.cilindrada = cilindrada;
    }


    //Equals,toString,clone
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        MotorCombustao t = (MotorCombustao) o;

        return (super.equals(t) && this.cilindrada == t.getCilindrada()) ;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Motor Combustão:: {");
        sb.append(" Codigo Motor: ").append(MotorCombustao.codMotor);
        sb.append(" Cilindrada: ").append(this.cilindrada);
        sb.append(super.toString()).append("}");
        return sb.toString();
    }

    @Override
    public abstract MotorCombustao clone();
}