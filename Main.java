import com.sun.jna.Library;
import com.sun.jna.Native;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public interface ImpressoraDLL extends Library {
        ImpressoraDLL INSTANCE = Native.load("C:\\Users\\queiroz_alves\\Downloads\\Java-Aluno EM (2)\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll", ImpressoraDLL.class);

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
        int FechaConexaoImpressora();
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
        int Corte(int avanco);
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
        int AvancaPapel(int linhas);
        int StatusImpressora(int param);
        int AbreGavetaElgin();
        int AbreGaveta(int pino, int ti, int tf);
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo = 1;
    private static String modelo = "";
    private static String conexao = "";
    private static int parametro = 0;
    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    /*
     * CONFIGURAÇÃO DE CONEXÃO
     * Aqui o usuário define:
     * - tipo (USB, serial etc)
     * - modelo da impressora
     * - string de conexão (COM3, IP etc)
     * - parâmetro adicional
     */

    public static void configurarConexao() {
        if (!conexaoAberta) {
            System.out.println("Digite o tipo de conexão (ex: 1 para USB, 2 para serial, etc.): ");
            try {
                tipo = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Valor inválido. Mantendo tipo padrão.");
            }

            System.out.print("Digite o modelo (ex: modelo da impressora): ");
            modelo = scanner.nextLine();

            System.out.print("Digite a string de conexão (ex: porta COM, IP:porta ou caminho): ");
            conexao = scanner.nextLine();

            System.out.print("Digite um parâmetro inteiro (opcional): ");
            try {
                parametro = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                parametro = 0;
            }

            System.out.println("Configuração salva.");
        } else {
            System.out.println("Conexão já aberta. Feche para reconfigurar.");
        }


    }

    public static void abrirConexao() {
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }


    // função de fechar conexão.
    public static void fecharConexao() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
        if (retorno == 0) {
            conexaoAberta = false;
            System.out.println("Conexão fechada com sucesso.");
        } else {
            System.out.println("Erro ao fechar conexão. Código: " + retorno);
        }
    }


    // função de imprimir textos. O úsuario personaliza o que deseja digitar.
    public static void ImpressaoTexto() {
        if (!conexaoAberta) {
            System.out.println("Erro: não há conexao aberta com a impressora.");
            return;
        }

        System.out.print("Digite o texto que deseja imprimir: ");
        String dados = scanner.nextLine();

        System.out.print("Digite a posição (0 = Esquerda, 1 = Centro, 2 = Direita): ");
        int posicao = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o estilo (0 = Fonte A, 1 = Fonte B, 2 = Sublinhado, 4 = Reverso, 8 = Negrito): ");
        int estilo = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o tamanho (ex: 0 normal): ");
        int tamanho = Integer.parseInt(scanner.nextLine());

        int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(dados, posicao, estilo, tamanho);
        if (retorno >= 0) {
            System.out.println("Texto enviado para a impressora com sucesso!");
        } else {
            System.out.println("Erro ao enviar texto. Código: " + retorno);
        }
    }


    // Função para cortar o papel, após ter colocar tudo que deseja
    public static void Corte() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        int retorno = ImpressoraDLL.INSTANCE.Corte(2);
        if (retorno == 0) System.out.println("Corte realizado com sucesso.");
        else System.out.println("Erro ao realizar corte. Código: " + retorno);
    }

    //Função que permite o usuario adicione um qrcode personalizado e o personalizar

    public static void ImpressaoQRCode() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        System.out.println("Digite as informações que irão compor o QRCode:");
        String dados = scanner.nextLine();

        System.out.println("Tamanho do QRCode (1 a 6):");
        int tamanho = Integer.parseInt(scanner.nextLine());

        System.out.println("Nível de correção (1 = 7%, 2 = 15%, 3 = 25%, 4 = 30%):");
        int nivel = Integer.parseInt(scanner.nextLine());

        int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, tamanho, nivel);
        if (retorno >= 0) System.out.println("QRCode enviado com sucesso.");
        else System.out.println("Erro ao enviar QRCode. Código: " + retorno);
    }


    //Função que permite o usuario adicione um codigo de barras e o personalizae
    public static void ImpressaoCodigoBarras() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        System.out.println("Selecione o tipo de código de barras (0..8):");
        int tipo = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite os dados do código de barras: ");
        String dados = scanner.nextLine();

        System.out.print("Altura (1-255): ");
        int altura = Integer.parseInt(scanner.nextLine());

        System.out.print("Largura (1-6): ");
        int largura = Integer.parseInt(scanner.nextLine());

        System.out.println("HRI (1 Acima, 2 Abaixo, 3 Ambos, 4 Não imprimir)");
        int hri = Integer.parseInt(scanner.nextLine());

        int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(tipo, dados, altura, largura, hri);
        if (retorno == 0) System.out.println("Código de barras impresso com sucesso!");
        else System.out.println("Erro ao imprimir código de barras. Código: " + retorno);
    }

    //Função que permite o úsuario digitar o valor para avançar o papel
    public static void AvancaPapel() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        System.out.print("Digite quantas linhas devem pular: ");
        int linhas = Integer.parseInt(scanner.nextLine());

        int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);
        if (retorno >= 0) System.out.println("Avanço do papel realizado.");
        else System.out.println("Erro ao avançar papel. Código: " + retorno);
    }


    // função que abre a gavet aelgin
    public static void AbreGavetaElgin() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        int retorno = ImpressoraDLL.INSTANCE.AbreGavetaElgin();
        if (retorno == 0) System.out.println("Gaveta Elgin aberta com sucesso.");
        else System.out.println("Erro ao abrir gaveta Elgin. Código: " + retorno);
    }


    // função que permite o úsuario abrir a gaveta da máquina com parametros
    public static void AbreGaveta() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        System.out.print("Digite o pino (0/1): ");
        int pino = Integer.parseInt(scanner.nextLine());

        System.out.print("Tempo inicialização (1-255): ");
        int ti = Integer.parseInt(scanner.nextLine());

        System.out.print("Tempo desativação (1-255): ");
        int tf = Integer.parseInt(scanner.nextLine());

        int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(pino, ti, tf);
        if (retorno == 0) System.out.println("Gaveta aberta com sucesso.");
        else System.out.println("Erro ao abrir gaveta. Código: " + retorno);
    }

    //função que envia um sinal sonoro (beep)
    public static void SinalSonoro() {
        if (!conexaoAberta) {
            System.out.println("Não há conexão aberta.");
            return;
        }

        System.out.print("Quantidade de sinais (1-63): ");
        int qtd = Integer.parseInt(scanner.nextLine());

        System.out.print("Tempo início (1-25): ");
        int inicio = Integer.parseInt(scanner.nextLine());

        System.out.print("Tempo fim (1-25): ");
        int fim = Integer.parseInt(scanner.nextLine());

        int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(qtd, inicio, fim);
        if (retorno == 0) System.out.println("Sinal sonoro enviado com sucesso.");
        else System.out.println("Erro ao enviar sinal sonoro. Código: " + retorno);
    }

    private static String lerArquivoComoString(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = fis.readAllBytes();
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }

    private static void imprimeXmlComFileChooser(boolean cancelamento) {
        if (!conexaoAberta) {
            System.out.println("Erro: Conexão não está aberta.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos XML", "xml"));

        if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("Nenhum arquivo selecionado.");
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();
        try {
            String conteudo = lerArquivoComoString(selectedFile.getAbsolutePath());
            int ret;

            if (cancelamento) {
                String ass = capturarEntrada("Coloque a assinatura QRCode (ou deixe em branco): ");
                ret = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(conteudo, ass, 0);
            } else {
                ret = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(conteudo, 0);
            }

            ImpressoraDLL.INSTANCE.Corte(5);
            System.out.println(ret == 0 ? "Impressão de XML realizada" : "Erro ao imprimir XML! Código: " + ret);

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo XML.");
        }
    }

    /*
     * MENU PRINCIPAL
     * Apresenta todas as funções disponíveis ao usuário.
     */

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n**************** MENU IMPRESSORA *******************");
            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4  - Impressao QRCode");
            System.out.println("5  - Impressao Cod Barras");
            System.out.println("6  - Impressao XML SAT");
            System.out.println("7  - Impressao XML Cancelamento SAT");
            System.out.println("8  - Abrir Gaveta Elgin");
            System.out.println("9  - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("11 - Avancar Papel");
            System.out.println("12 - Corte");
            System.out.println("0  - Sair");

            String escolha = capturarEntrada("Digite a opção desejada: ");

            //switch com as escolhas de acordo com o menu

            switch (escolha) {
                case "1": configurarConexao(); break;
                case "2": abrirConexao(); break;
                case "3": ImpressaoTexto(); break;
                case "4": ImpressaoQRCode(); break;
                case "5": ImpressaoCodigoBarras(); break;
                case "6": imprimeXmlComFileChooser(false); break;
                case "7": imprimeXmlComFileChooser(true); break;
                case "8": AbreGavetaElgin(); break;
                case "9": AbreGaveta(); break;
                case "10": SinalSonoro(); break;
                case "11": AvancaPapel(); break;
                case "12": Corte(); break;
                case "0":
                    fecharConexao();
                    System.out.println("Programa encerrado.");
                    scanner.close();
                    return;
                default:
                    System.out.println("OPÇÃO INVÁLIDA");
            }
        }
    }
}
