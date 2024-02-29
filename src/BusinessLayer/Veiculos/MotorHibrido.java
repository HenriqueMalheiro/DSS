package src.Negocio.Veiculos;

public class MotorHibrido extends Motor {
    private static int codMotor;

    private MotorCombustao combustao;
    private MotorEletrico eletrico;

    //Construtor vazio
    public MotorHibrido(){
        MotorHibrido.codMotor = -1;
        this.combustao = null;
        this.eletrico = null;
    }

    //Construtor Parametrizado
    public MotorHibrido(String marca,int idMotor, MotorCombustao combustao, MotorEletrico eletrico) {
        super(marca,idMotor);
        if(combustao instanceof MotorGasolina)
            MotorHibrido.codMotor = 4;
        else
            MotorHibrido.codMotor = 5;

        this.combustao = combustao.clone(); //pq é composição
        this.eletrico = eletrico.clone(); //pq é composição
    }

    public int getCodMotor() {
        return codMotor;
    }

    //Construtor copia
    public MotorHibrido(MotorHibrido mh){
        super(mh);
        this.combustao = mh.getCombustao();
        this.eletrico = mh.getEletrico();
    }

    //gets

    public MotorCombustao getCombustao() {
        return combustao.clone();
    }

    public MotorEletrico getEletrico() {
        return eletrico.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        MotorHibrido t = (MotorHibrido) o;

        return (super.equals(t) && this.combustao.equals(t.getCombustao()) && this.eletrico.equals(t.getEletrico())) ;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Motor Hibrido:: {");
        sb.append(" Codigo Motor: ").append(MotorHibrido.codMotor);
        sb.append(" Motor combustão: ").append(this.combustao.toString());
        sb.append(" Motor eletrico: ").append(this.eletrico.toString());
        sb.append(super.toString()).append("}");
        return sb.toString();
    }

    @Override
    public MotorHibrido clone() {
        return new MotorHibrido(this);
    }
}
