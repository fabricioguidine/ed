package trabalho03;

import Classes.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe principal do projeto Trabalho03, criado como parte das exigências da disciplina DCC012 - Estruturas de Dados II.
 * Essa classe tem como objetivo comparar o desempenho das estruturas Árvore Vermelho-Preto,
 * Árvore B+ de ordem 2  e Árvore B+ de ordem 20 ao inserir e buscar diferentes quantidades de objetos do tipo Registros nas mesmas. 
 * @author Débora Duarte, Fabrício Guidine e Walkíria Garcia
 * @version 1.0
*/
public class Trabalho03 {
    
    private static final int NUM_ALGORITMOS = 3;
    
    static int[ ] entradas;
    
    static ArquivoSaida arqSaidaI = new ArquivoSaida();
    static ArquivoSaida arqSaidaB = new ArquivoSaida();
    
    

    /**
     * LerEntradas é responsavel por ler e retornar o conteúdo do arquivo 
     * 'entrada.txt'.
     * @return entradas do tipo Array de Inteiros.
     * @exception IOException se o arquivo "entrada.txt" não é encontrado na pasta do projeto.
     */
    public static int[] lerEntradas() throws IOException{
        int tam;
        int cont = 1;
        int[] entradas;
       
        FileInputStream entrada = new FileInputStream("entrada.txt");
        InputStreamReader entradaFormatada = new InputStreamReader(entrada);
        BufferedReader entradaString = new BufferedReader(entradaFormatada);

        String linha = entradaString.readLine();

        tam = Integer.parseInt(linha);

        entradas = new int[tam+1];
        
        entradas[0] = tam;

        while(linha != null && cont <= tam){
            linha = entradaString.readLine();
            entradas[cont] = Integer.parseInt(linha);
            //System.out.println(entradas[cont]);
            cont++;

        }
        entrada.close();
        return entradas;
    }
    
    /**
     * Método responsavel por carregar de forma aleatória um número tam de registros de forma desordenada.
     * @param registros array de objetos do tipo Registro onde os dados vão ser armazenados.
     * @param tam do tipo Inteiro que representa o tamanho do vetor de Registros.
     * @see Registro
    */
    public static void LeRegistros (Registro registros[], int tam){
        
        try {
        
            RandomAccessFile raf = new RandomAccessFile("dataset_simp_sem_descricao.csv", "r");
            
            long posicao = ThreadLocalRandom.current().nextLong(raf.length());
            
            raf.seek(posicao);
            String line = "";
            
            String[] linhas = new String[tam+tam/2];
            raf.readLine();
            
            for (int i=0; i<tam+tam/2;i++){
                if (raf.getFilePointer()==raf.length()){
                    raf.seek(0);
                }
                
                linhas[i] = raf.readLine();
            }
            int i=0,j=0;
            while (i<tam && j<tam+tam/2){
                line = linhas[j];
                int last = line.length()-1;
                if (line.charAt(last)!='"'){
                    String line2 = linhas[j+1];
                    j++;
                    String info = line+line2;
                    if (info!=null){
                    Registro reg = new Registro(info);
                    registros[i] = reg;
                    i++;
                    j++;
                    }
                }else{                
                Registro reg = new Registro(line);
                registros[i] = reg;
                i++;
                j++;
                }
            }
         raf.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    /**
     * Insere os registros na Árvore Vermelho-Preto e já adiciona as informações ao arquivo de saída.
     * @param registros os registros a serem inseridos.
     * @param bst a Árvore Vermelho-Preto onde os registros vão ser inseridos.
     * @see Registro
     * @see ArvoreVP
     */
    public static void InsereVp(Registro[] registros, ArvoreVP bst){
        long tempo;
        long inicio = System.currentTimeMillis();
        for (int i=0; i< registros.length; i++){
               bst.inserir(registros[i]);
        }
        long fim  = System.currentTimeMillis();
        tempo = fim - inicio;
        
        Saida nsaida1 = new Saida("Árvore Vermelho-Preto","Inserção",Integer.toString(registros.length), bst.getnComparacoes(), bst.getnTrocas(), tempo);
        arqSaidaI.addSaida(nsaida1);
    }
    
    /**
     * Busca Registros na Árvore Vermelho-Preto e já adiciona as informações ao arquivo de saída.
     * @param registros os Registros a serem buscados.
     * @param bst a Árvore onde os Registros vão ser buscados.
     * @see Registro
     * @see ArvoreVP
     */
    public static void BuscaVP(Registro[] registros, ArvoreVP bst){
        
        bst.setnComparacoes(0);
        bst.setnTrocas(0);
        long tempo;
        long inicio = System.currentTimeMillis();
        for (int i=0; i< registros.length/2; i++){
             bst.Busca(registros[i]);
        }
        long fim  = System.currentTimeMillis();
        tempo = fim - inicio;
        
        Saida nsaida1 = new Saida("Árvore Vermelho-Preto","Busca",Integer.toString(registros.length), bst.getnComparacoes(), bst.getnTrocas(), tempo);
        arqSaidaB.addSaida(nsaida1);
    }
    
    /**
     * Insere os Registros na Árvore B+ e e já adiciona as informações ao arquivo de saída.
     * @param registros os Registros a serem inseridos.
     * @param bst a Árvore B+ onde os Registros vão ser inseridos.
     * @see Registro
     * @see BPlusTree
     */
    public static void InsereBPlus(Registro[] registros, BPlusTree bst){
        long tempo;
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < registros.length; i++) {
            if (registros[i] != null && registros[i].getId().compareTo("-1")!=0){ 
                bst.insere(registros[i].intGetId(), registros[i]);
            }
               
        }
        long fim  = System.currentTimeMillis();
        tempo = fim - inicio;
        
        Saida nsaida1 = new Saida("Árvore B+","Inserção",Integer.toString(registros.length), bst.getnComparacoes(), bst.getnTrocas(), tempo);
        arqSaidaI.addSaida(nsaida1);
    }
    
    /**
     * Busca os Registros na Árvore B+ e já adiciona as informações ao arquivo de saída.
     * @param registros os Registros a serem buscados.
     * @param bst a Árvore B+ onde os registros vão ser buscados.
     * @see Registro
     * @see BPlusTree
     */
    public static void BuscaBPlus(Registro[] registros, BPlusTree bst){
        bst.setnComparacoes(0);
        bst.setnTrocas(0);
        
        long tempo;
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < registros.length/2; i++) {
            if (registros[i] != null && registros[i].getId().compareTo("-1")!=0){ 
                bst.busca(registros[i].intGetId());
            }
               
        }
        long fim  = System.currentTimeMillis();
        tempo = fim - inicio;
        
        Saida nsaida1 = new Saida("Árvore B+","Busca",Integer.toString(registros.length), bst.getnComparacoes(), bst.getnTrocas(), tempo);
        arqSaidaB.addSaida(nsaida1);
    }
    
    /**
     * Cria a Árvore Vermelho-Preto e chama as funções de inserção e busca.
     * @param registros os Registros a serem buscados e inseridos.
     * @see Registro
     * @see ArvoreVP
     * @see InsereVp
     * @see BuscaVP
     */
    public static void ArvVP(Registro[] registros){
        
        ArvoreVP bst = new ArvoreVP();
        
        InsereVp(registros,bst);
        BuscaVP(registros,bst);
    }
    
    /**
     * Cria a Árvore B+ e chama as funções de inserção e busca.
     * @param registros os Registros a serem buscados e inseridos.
     * @param ordem Inteiro que é a ordem da Àrvore B+.
     * @see Registro
     * @see ArvoreVP
     * @see InsereVp
     * @see BuscaVP
     */
    public static void ArvBPlus(Registro[] registros, int ordem){
        
        BPlusTree bst = new BPlusTree(ordem);
        
        InsereBPlus(registros,bst);
        BuscaBPlus(registros,bst);
    }
    
    /**
     * Função main da clase Trabalho02.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {

        try {
            
            
            entradas = lerEntradas();
            //System.out.println(entradas[0]);
            int tamEntradas = entradas[0] + 1;
            
           
            
            for (int i=1; i< tamEntradas; i++){
                Registro[] registros = new Registro[entradas[i]];
                LeRegistros (registros, entradas[i]);
                
                ArvVP(registros);
                ArvBPlus(registros,2);
                ArvBPlus(registros,20);
                
                System.out.println(entradas[i]);
                
            }
            
            arqSaidaI.gravaSaidas("saidaInsercao.txt");
            arqSaidaB.gravaSaidas("saidaBusca.txt");
            
            
        } catch (IOException ex) {
           System.out.println("ARQUIVO NÃO ENCONTRADO!");
        }
            
    }
    
}
