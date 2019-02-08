package com.droppages.pedrohenrique.pocketcoin.Testes;

import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Constantes
    private static final String CREDITO = "credito";
    private static final String DEBITO = "debito";

    // Variáveis globais
    private static Scanner input;
    private static List<NaturezaDaAcao> naturezas = new ArrayList<>();
    private static List<Usuario>        usuarios = new ArrayList<>();
    private static boolean              usuarioLogado = false;
    private static int                  idDoUsuario = -1;

    // Ids para controle de cadastro
    private static long ultimoIdMovimentacao = 0;
    private static long ultimoIdCategoria = 0;
    private static long ultimoIdCarteira = 0;
    private static long ultimoIdUsuario = 0;
    private static long ultimoIdTag = 0;


    // Main
    public static void main(String[] args) {
        preencheNatureza();
        int opcao = -1;

        while (opcao != 0) {
            opcao = menu(0);

            if (usuarioLogado) {
                if (opcao == 1) {
                    carteira();
                } else if (opcao == 2){
                    categoria();
                } else if (opcao == 3){
                    tag();
                } else if (opcao == 4){
                    movimentacao();
                } else if (opcao == 5){
                    dadosGerais();
                } else if (opcao == 6){
                    logoff();
                }
            } else {
                if (opcao == 1) {
                    login();
                } else if (opcao == 2) {
                    cadastrarNovoUsuario();
                }
            }
        }
    }


    /*
     * Funções de carteira
     * */
    private static void carteira() {
        int opcao = -1;
        while (opcao != 3) {
            opcao = menu(1);
            if (opcao == 1) {
                listarCarteirasCadastradas();
            } else if (opcao == 2) {
                cadastrarCarteira();
            }
        }
    }

    private static void listarCarteirasCadastradas() {
        List<Carteira> carteiras = selecionarUsuarioLogado().getCarteiras();

        if (carteiras.size() == 0) {
            System.out.println("nenhuma carteira cadastrada");
        } else {
            for (Carteira carteira: carteiras) {
                System.out.println("***");
                System.out.println("ID: " + carteira.id +
                        "\nNome: " + carteira.getNome() +
                        "\nSaldo: " + carteira.getSaldo() +
                        "\n");
            }
        }
    }

    private static void cadastrarCarteira() {
        input = new Scanner(System.in);

        System.out.print("Nome da carteira: ");
        String nome = input.nextLine();

        System.out.print("Valor inicial: ");
        float valor = input.nextFloat();

        if (nome.isEmpty()) {
            System.out.println("Erro no cadastro, nome da carteira não pode ser vazio!");
        } else if (valor < 0) {
            System.out.println("Erro no cadastro,  valor inicial não pode ser inferior a 0 (zero)!");
        } else {
            Carteira carteira = new Carteira(nome, valor);
            carteira.id = ultimoIdCarteira;
            ultimoIdCarteira++;
            selecionarUsuarioLogado().adicionarCarteira(carteira);
        }
    }


    /*
     * Funções de categoria
     * */
    private static void categoria() {
        int opcao = -1;
        while (opcao != 3) {
            opcao = menu(2);
            if (opcao == 1) {
                listarCategoriasCadastradas();
            } else if (opcao == 2) {
                cadastrarCategoria();
            }
        }
    }

    private static void listarCategoriasCadastradas() {
        List<Categoria> categorias = selecionarUsuarioLogado().getCategorias();

        if (categorias.size() == 0) {
            System.out.println("nenhuma categoria cadastrada");
        } else {
            for (Categoria categoria: categorias) {
                System.out.println("***");
                System.out.println("ID: " + categoria.id +
                        "\nNome: " + categoria.getNome() +
                        "\nNatureza: " + categoria.getNatureza().getTarget().getNome() +
                        "\n");
            }
        }
    }

    private static void cadastrarCategoria() {
        input = new Scanner(System.in);

        System.out.print("Nome da categoria: ");
        String nome = input.nextLine();
        int idNatureza = -1;

        while (idNatureza != 0 && idNatureza != 1) {
            System.out.println("opção: " + naturezas.get(0).id + " - " + naturezas.get(0).getNome());
            System.out.println("opção: " + naturezas.get(1).id + " - " + naturezas.get(1).getNome());
            idNatureza = input.nextInt();
        }

        if (nome.isEmpty()) {
            System.out.println("Erro no cadastro, nome da categoria não pode ser vazio!");
        } else {
            Categoria categoria = new Categoria(nome, naturezas.get(idNatureza));
            categoria.id = ultimoIdCategoria;
            ultimoIdCategoria++;
            selecionarUsuarioLogado().adicionarCategoria(categoria);
        }
    }


    /*
     * Funções de Tag
     * */
    private static void tag() {
        int opcao = -1;
        while (opcao != 3) {
            opcao = menu(3);
            if (opcao == 1) {
                listarTagCadastradas();
            } else if (opcao == 2) {
                cadastrarTag();
            }
        }
    }

    private static void listarTagCadastradas() {
        List<Tag> tags = selecionarUsuarioLogado().getTags();

        if (tags.size() == 0) {
            System.out.println("nenhuma tag cadastrada");
        } else {
            for (Tag tag: tags) {
                System.out.println("***");
                System.out.println("ID: " + tag.id +
                        "\nNome: " + tag.getNome() +
                        "\n");
            }
        }
    }

    private static void cadastrarTag() {
        input = new Scanner(System.in);

        System.out.print("Nome da tag: ");
        String nome = input.nextLine();

        if (nome.isEmpty()) {
            System.out.println("Erro no cadastro, nome da tag não pode ser vazio!");
        } else {
            Tag tag = new Tag(nome);
            tag.id = ultimoIdTag;
            ultimoIdTag++;
            selecionarUsuarioLogado().adicionarTag(tag);
        }
    }


    /*
     * Funções das movimentacoes
     */
    private static void movimentacao() {
        System.out.println("Movimentação");
    }


    private static void dadosGerais() {
        System.out.println("dados gerais");
    }


    /*
     * Funções do usuário
     */
    private static void cadastrarNovoUsuario() {
        input = new Scanner(System.in);

        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Login: ");
        String login = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();
        System.out.print("Repetir senha: ");
        String repeteSenha = input.nextLine();

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || repeteSenha.isEmpty()) {
            System.out.println("Erro no cadastro, preencha todos os campos");
        } else {
            if (!senha.equals(repeteSenha)) {
                System.out.println("Senhas diferentes");
            } else {
                Usuario usuario = new Usuario(nome, login, senha);
                usuarios.add(usuario);
            }
        }
    }

    private static void login(){
        input = new Scanner(System.in);
        boolean usuarioLocalizado = false;

        System.out.print("Login: ");
        String login = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();

        for (Usuario usuario: usuarios) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                idDoUsuario = (int) usuario.id;
                usuarioLogado = true;
                usuarioLocalizado = true;
            }
        }

        if (!usuarioLocalizado) {
            System.out.println("Usuário não localizado!");
        }
    }

    private static void logoff() {
        usuarioLogado = false;
        idDoUsuario = -1;
    }


    /*
     * Funções do sistema
     * */
    private static Usuario selecionarUsuarioLogado() {
        return usuarios.get(idDoUsuario);
    }

    private static void preencheNatureza(){
        NaturezaDaAcao natureza = new NaturezaDaAcao(CREDITO);
        natureza.id = 0;
        naturezas.add(natureza);

        natureza = new NaturezaDaAcao(DEBITO);
        natureza.id = 1;
        naturezas.add(natureza);

        // Para debug
        Usuario usuario = new Usuario("p", "p", "p");
        usuario.id = ultimoIdUsuario;
        ultimoIdUsuario++;
        usuarios.add(usuario);
    }

    private static int menu(int menuId){
        input = new Scanner(System.in);
        int minimo, maximo;
        int opcao = -1;
        String menu;

        if (usuarioLogado) {
            if (menuId == 1) {
                // Menu carteira
                minimo = 0;
                maximo = 3;
                menu = "1 - Listar carteiras cadastradas \n" +
                        "2 - Cadastrar carteira \n" +
                        "3 - Voltar ao inicio \n" +
                        "0 - Encerrar sistema \n" +
                        ">>> ";
            } else if (menuId == 2) {
                // Menu categoria
                minimo = 0;
                maximo = 3;
                menu = "1 - Listar categorias cadastradas \n" +
                        "2 - Cadastrar categoria \n" +
                        "3 - Voltar ao inicio \n" +
                        "0 - Encerrar sistema \n" +
                        ">>> ";
            } else if (menuId == 3) {
                // Menu tag
                minimo = 0;
                maximo = 3;
                menu = "1 - Listar tags cadastradas \n" +
                        "2 - Cadastrar tag \n" +
                        "3 - Voltar ao inicio \n" +
                        "0 - Encerrar sistema \n" +
                        ">>> ";
            } else {
                // Menu principal
                minimo = 0;
                maximo = 6;
                menu = "1 - Carteira \n" +
                        "2 - Categoria \n" +
                        "3 - Tag \n" +
                        "4 - Movimentação \n" +
                        "5 - Dados gerais \n" +
                        "6 - Encerrar sessão \n" +
                        "0 - Encerrar sistema \n" +
                        ">>> ";
            }
        } else {
            minimo = 0;
            maximo = 2;
            menu = "1 - Login \n" +
                    "2 - Registrar \n" +
                    "0 - Encerrar sistema\n" +
                    ">>> ";
        }

        while (opcao < minimo || opcao > maximo) {
            System.out.println("-------------------------");
            System.out.print(menu);
            opcao = input.nextInt();
        }

        return opcao;

    }

}
