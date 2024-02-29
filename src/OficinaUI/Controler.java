package src.OficinaUI;

import src.Exceptions.*;
import src.Negocio.IOficinaLN;
import src.Negocio.OficinaLN;
import src.Negocio.Servicos.Pedido;

public class Controlador {
    private View menu;
    private IOficinaLN oficina;

    //Construtor
    public Controlador(View view, OficinaLN oficina){
        this.menu = view;
        this.oficina = oficina;
    }

    public void menuInicio()  {
        int codPosto = this.menu.pedirPostoTrabalho();
        int codFunc = this.menu.pedirCrendeciasFunc();

        try {
            if(!this.oficina.verificarCompetencias(codPosto, codFunc)){
                this.menu.printInterface("Diriga-se para o "+this.oficina.informaPostoTrabalho(codFunc)+"\n");
                this.menuInicio();
            }
        } catch (FuncionarioNaoEncontradoException e) {
            this.menu.printInterface(e.getMessage());
        }

        boolean loop = true;
        while (loop) {
            int e = this.menu.iniciarmenu();
            if (e == 1) {
                try {
                    this.oficina.inicioTurno(codFunc,codPosto);
                } catch (FuncionarioNaoEncontradoException ex) {
                    this.menu.printInterface(ex.getMessage());
                }
                this.menuRececionista(codFunc);
            } else if (e == 2) {
                try {
                    this.oficina.inicioTurno(codFunc,codPosto);
                } catch (FuncionarioNaoEncontradoException ex) {
                    this.menu.printInterface(ex.getMessage());
                }
                this.menuMecanico(codPosto,codFunc);
            } else if (e == 3) {
                this.menu.sair();
                loop = false;
            }else {
                System.out.println("Opção não disponível. Tente novamente.");
                this.menu.iniciarmenu();
            }
        }
    }

    public void menuRececionista(int codFunc) {
        boolean loop = true;
        while (loop) {
            int e = this.menu.menuRececionista();
            if (e == 1){
                this.fazerPedido();
            }else if(e == 2){
                try {
                    this.oficina.fimTurno(codFunc);
                } catch (FuncionarioNaoEncontradoException | DiaDeTrabalhoNaoEncontradoException ex) {
                    this.menu.printInterface(ex.getMessage());
                }
                loop = false;
            }
        }
    }

    public void fazerPedido()  {
        int nifCliente = this.menu.pedirNIF();
        if(this.oficina.verificaCredenciaisCliente(nifCliente)){
            try {
                this.menu.listarVeiculosCliente(this.oficina.listarFichasCliente(nifCliente));
            } catch (ClienteNaoEncontradoException | FichaVeiculoNaoEncontradoException e) {
                this.menu.printInterface(e.getMessage());
            }
            String matricula = this.menu.pedirMatricula();
            try {
                this.menu.listarServicos(this.oficina.listarServicosMotor(matricula));

                int e = this.menu.escolhaServicoParaRealizar();
                if(e  == 1){
                    int codServico = this.menu.pedirCodigoDoServico();
                    int codPosto;
                    try {
                        codPosto = this.oficina.disponibilidadePostosTrabalho(codServico);
                        this.menu.precoServico(this.oficina.precoServico(codServico));
                        this.menu.tempoestimadotermino(this.oficina.tempoEstimadoPosto(codPosto,codServico));

                        int escolha = this.menu.escolhaAgendarServico();
                        if (escolha == 1) {
                            try {
                                Pedido pedido = this.oficina.agendarServico(codServico, codPosto, matricula);
                                this.menu.agendarServicoSucesso(pedido.toString());

                                int msm = this.menu.escolhaQuerMensagem();
                                if(msm == 1){
                                    try {
                                        this.oficina.registarPedidoSMS(pedido.getCodPedido());
                                    } catch (PedidoNaoEncontradoException ex) {
                                        this.menu.printInterface(ex.getMessage());
                                    }
                                }
                            } catch (ServicoNaoEncontradoException | PostoTrabalhoNaoEncontradoException |
                                     FichaVeiculoNaoEncontradoException ex) {
                                this.menu.printInterface(ex.getMessage());
                            }
                        } else {
                            this.menu.printInterface("Não pretendo realizar o serviço pedido.");
                        }
                    } catch (ServicoNaoEncontradoException | PostoTrabalhoNaoEncontradoException ex) {
                        this.menu.printInterface(ex.getMessage());
                    }
                }else {
                    this.menu.printInterface("Não há intenção de realizar qualquer serviço.");
                }
            } catch ( FichaVeiculoNaoEncontradoException e) {
                this.menu.printInterface(e.getMessage());
            }
        }else{
            this.menu.printInterface("Cliente não registado.");
        }
    }

    public void menuMecanico(int codPosto,int codFunc){
        boolean loop = true;
        while (loop){
            int e = this.menu.menuMecanico();
            if(e == 1){
                try {
                    this.menu.listarpedidos(this.oficina.listarPedidosPosto(codPosto));
                } catch (PedidoNaoEncontradoException | PostoTrabalhoNaoEncontradoException ex) {
                    this.menu.printInterface(ex.getMessage());
                }
            } else if(e == 2){
                try {
                    Pedido pedido = this.oficina.atribuirServico(codFunc);
                    this.menu.pedidoatribuido(pedido.toString());
                    this.menu.indicarConclusaoServico();
                    boolean s = this.menu.sucessoInsucesso();
                    if(s){
                        try {
                            this.oficina.conclusaoServico_comSucesso(pedido.getCodPedido());
                        } catch (PedidoNaoEncontradoException ex) {
                            this.menu.printInterface(ex.getMessage());
                        }
                        int b = this.menu.servidoadicional();
                        if(b == 1) {
                            try {
                                this.menu.listarServicos(this.oficina.listarServicosMotor(pedido.getMatricula()));
                            } catch (FichaVeiculoNaoEncontradoException ex) {
                                this.menu.printInterface(ex.getMessage());
                            }
                            int codServico = this.menu.pedircodServico();
                            try {
                                this.oficina.adicionarServicoSugerido_FichaVeiculo(pedido.getMatricula(), codServico);
                            } catch (FichaVeiculoNaoEncontradoException ex) {
                                this.menu.printInterface(ex.getMessage());
                            }
                        }
                    }else{
                        String motivo = this.menu.motivo();
                        try {
                            this.oficina.conclusaoServico_semSucesso(pedido.getCodPedido(),motivo);
                        } catch (PedidoNaoEncontradoException ex) {
                            this.menu.printInterface(ex.getMessage());
                        }
                    }
                    try {
                        this.menu.printInterface(this.oficina.enviarSMS(pedido.getMatricula(), pedido.getCodPedido()));
                    } catch (ClienteNaoEncontradoException | PedidoNaoEncontradoException |
                             FichaVeiculoNaoEncontradoException ex) {
                        this.menu.printInterface(ex.getMessage());
                    }
                } catch (PedidosQueueVazia | FuncionarioNaoEncontradoException| RececionistaNaoTemPedidos ex) {
                    this.menu.printInterface(ex.getMessage());
                }
            } else if(e == 3){
                try {
                    this.oficina.fimTurno(codFunc);
                } catch (FuncionarioNaoEncontradoException | DiaDeTrabalhoNaoEncontradoException ex) {
                    this.menu.printInterface(ex.getMessage());
                }
                loop = false;
            }
        }
    }
}