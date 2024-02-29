package src.Negocio.Veiculos;

public abstract class Motor {
    private String marca;
    private int idMotor;

    public static int codMotor=1;
    private static int contadorPedidos = 1;


    //Construtor vazio
    public Motor(){
        this.marca = "N/D";
        this.idMotor=contadorPedidos++;
    }

    //Construtor parametrizado
    public Motor(String marca,int idMotor) {
        this.marca = marca;
        this.idMotor = idMotor;
        contadorPedidos++;
    }

    //Constrotor cópia
    public Motor(Motor umMotor){
        this.marca = umMotor.getMarca();
        this.idMotor=umMotor.getIdMotor();
    }


    //Gets
    public String getMarca() {
        return marca;
    }
    public int getIdMotor() {
        return idMotor;
    }
    public int getCodMotor(){
        return codMotor;
    }


    //Sets
    public void setMarca(String marca){
        this.marca = marca;
    }
    public void setCodMotor(int codMotor) {
        Motor.codMotor = codMotor;
    }
    public void setIdMotor(int idMotor) {
        this.idMotor = idMotor;
    }

    //equals,toString,clone
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Motor e = (Motor) o;

        return (this.marca.equals(e.getMarca()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Motor:: {");
        sb.append(" Marca: ").append(this.marca).append("}");

        return sb.toString();
    }

   public abstract Motor clone();
}
