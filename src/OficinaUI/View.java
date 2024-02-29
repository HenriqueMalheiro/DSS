package src.OficinaUI;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class View {
    private Scanner scanner;

    public View (){
        this.scanner = new Scanner(System.in);
    }

    // Método para escrever no ficheiro
    public void printInterface(String mensagem) {
        System.out.println(mensagem);
    }

    // Inicio
    public int iniciarmenu() {
        int escolha;
        System.out.println("\n---------- MENU ----------");
        System.out.println("1. Interface do Rececionista");
        System.out.println("2. Interface do Mecânico");
        System.out.println("3. Sair");
        System.out.println("--------------------------");
        System.out.print("Escolha uma opção: ");

        try {
            escolha = scanner.nextInt();
            if (escolha >= 1 && escolha <= 3) {
                return escolha;
            } else {
                System.out.println("Opção não disponível. Tente novamente.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite o número correspondente à opção desejada.\n");
            scanner.nextLine(); // Limpar o buffer do scanner
        }

        return iniciarmenu();
    }

    public void sair() {
        System.out.println("Sair...");
    }


    // -------------------------- Parte de fazer um pedido/ MENU DO RECECIONISTA  ------------------------------------------------------------------

    public  int menuRececionista() {
        int escolha;

        System.out.println("\n---------- MENU DO RECECIONISTA ----------");
        System.out.println("1. Fazer um pedido");
        System.out.println("2. Sair do turno");
        System.out.println("------------------------------------------");
        System.out.print("Escolha uma opção: ");

        try {
            escolha = scanner.nextInt();
            if (escolha >= 1 && escolha <= 2) {
                return escolha;
            } else {
                System.out.println("Opção não disponível. Tente novamente.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite o número correspondente à opção desejada.\n");
            scanner.nextLine();
        }

        return menuRececionista();
    }

    public int pedirNIF() {
        System.out.print("Digite o NIF do cliente: ");
        int nif;

        try {
            nif = scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Entrada Inválida. Por favor, digite um número inteiro.\n");
            scanner.nextLine();
            nif = pedirNIF();
        }

        return nif;
    }

    public int pedirPostoTrabalho(){
        System.out.print("Introduza o código do posto onde está a efetuar o registo do turno: ");
        int posto;

        try {
            posto = scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Entrada Inválida. Por favor, digite um número inteiro.\n");
            scanner.nextLine();
            posto = pedirPostoTrabalho();
        }

        return posto;
    }

    //Pedir quando sair do turno
    public int pedirCrendeciasFunc(){
        System.out.print("Introduza o seu código de funcionário: ");
        int codFunc;

        try {
            codFunc = scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Entrada Inválida. Por favor, digite um número inteiro.\n");
            scanner.nextLine();
            codFunc = pedirCrendeciasFunc();
        }

        return codFunc;

    }


    public void listarVeiculosCliente(String v) {
        System.out.println("Lista de veículos do cliente:");
        System.out.println(v);
    }


    public String pedirMatricula() {
        System.out.print("\nDigite a matrícula do veículo: ");
        scanner.nextLine();
        String matricula;

        try {
            matricula = scanner.nextLine();
            return matricula;
        } catch (NoSuchElementException e){
            System.out.println("Não foi possível ler a matricula.\n ");
            matricula = pedirMatricula();
        }

        return matricula;
    }


    public void listarServicos(String servicos) {
        System.out.println("\nLista de serviços disponíveis para o veículo:\n" + servicos);
    }

    public int escolhaServicoParaRealizar() {
        int escolha;
        System.out.println("1. Código do Serviço");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");


        try {
            escolha = scanner.nextInt();
            if (escolha >= 1 && escolha <= 2) {
                return escolha;
            } else {
                System.out.println("Opção não disponível. Tente novamente.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite o número correspondente à opção desejada.\n");
            scanner.nextLine(); // Limpar o buffer do scanner
        }

        return escolhaServicoParaRealizar();
    }

    public int pedirCodigoDoServico() {
        System.out.print("Digite o código do serviço: ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Por favor, digite um número inteiro.\n");
            scanner.nextLine();
            return pedirCodigoDoServico();
        }
    }


    public void precoServico(float preco) {
        System.out.println("\nO preço do serviço é: " + preco);
    }

    public void tempoestimadotermino(LocalTime hora) {
        // Formate a hora para exibição
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormatada = hora.format(formatter);

        System.out.println("Tempo estimado de término: " + horaFormatada);
    }

    public int escolhaAgendarServico() {
        int escolha;
        System.out.println("\n1. Agendar Serviço");
        System.out.println("2. Não Agendar Serviço");
        System.out.print("Escolha uma opção: ");

        try {
            escolha = scanner.nextInt();
            if (escolha >= 1 && escolha <= 2) {
                return escolha;
            } else {
                System.out.println("Opção não disponível. Tente novamente.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite o número correspondente à opção desejada.\n");
            scanner.nextLine(); // Limpar o bagendarServicouffer do scanner
        }

        return escolhaAgendarServico();
    }

    public int escolhaQuerMensagem() {
        int escolha;
        System.out.println("\nReceber mensagem após a realização do serviço:");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.print("Escolha uma opção: ");

        try {
            escolha = scanner.nextInt();
            if (escolha >= 1 && escolha <= 2) {
                return escolha;
            } else {
                System.out.println("Opção não disponível. Tente novamente.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite o número correspondente à opção desejada.\n");
            scanner.nextLine(); // Limpar o buffer do scanner
        }

        return escolhaQuerMensagem();
    }

    public void agendarServicoSucesso(String pedido){
        System.out.println("\nO pedido foi registado com sucesso:\n" + pedido);
    }


    public boolean sucessoInsucesso() {
        System.out.print("Sucesso, (true/false): ");
        boolean sucesso;

        try {
            sucesso = scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite true ou false.\n");
            scanner.nextLine(); // Limpa o buffer do scanner
            sucesso = sucessoInsucesso();
        }
        return sucesso;
    }

    public String motivo() {
        scanner.nextLine();
        System.out.print("Qual o motivo para a não realização do pedido:");
        return scanner.nextLine();
    }

//-----------------------------------------------------Mecanico----------------------------------------------------------

    public int menuMecanico() {
        int escolha;
        System.out.println("\n---------- MENU DO MECÂNICO ----------");
        System.out.println("1. Consultar lista de pedidos");
        System.out.println("2. Realizar pedido");
        System.out.println("3. Sair do turno");
        System.out.println("---------------------------------------");
        System.out.print("Escolha uma opção: ");

        try {
            escolha = scanner.nextInt();
            if (escolha >= 1 && escolha <= 3) {
                return escolha;
            } else {
                System.out.println("Opção não disponível. Tente novamente.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite o número correspondente à opção desejada.\n");
            scanner.nextLine(); // Limpar o buffer do scanner
        }

        return menuMecanico();
    }

    public void listarpedidos(String pedido){
        System.out.println("Consultar lista de pedido: " + pedido);
    }
    public void pedidoatribuido(String pedido) {
        System.out.println("\nPedido atribuido: " + pedido);
    }


    public void indicarConclusaoServico() {
        System.out.println("\nIndicar conclusão serviço:");
        pressEnterToContinue();
    }

    private void pressEnterToContinue() {
        System.out.println("Press 'Enter' to continue...");

        try {
            System.in.read(); // This will wait for the user to press Enter
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int servidoadicional() {
        System.out.println("\nAdicionar um serviço adicional:");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.print("Escolha uma opção: ");

        int escolha;
        try {
            escolha = scanner.nextInt();
            if (escolha >= 1 && escolha <= 2) {
                return escolha;
            } else {
                System.out.println("Opção não disponível. Tente novamente.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite o número correspondente à opção desejada.\n");
            scanner.nextLine(); // Limpar o buffer do scanner
        }

        return servidoadicional();
    }
    public int pedircodServico() {
        System.out.print("Qual é o serviço: ");

        int cd;
        try {
            cd = scanner.nextInt();
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler o código do serviço.\n");
            scanner.nextLine();
            cd = pedircodServico(); // Chama recursivamente para tentar novamente
        }
        return cd;
    }
}