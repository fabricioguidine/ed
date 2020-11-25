/**
 * Universidade Federal de Juiz de Fora – Departamento de Ciência da Computação
 * @author deboraizabel
 * @author fabricioguidine
 * @author walkiriagarcia
*/

package trabalho01;

import Classes.ArquivoSaida;
import Classes.HeapSort;
import Classes.QuickSort;
import Classes.Registro;
import Classes.Saida;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class Trabalho01 {
    private static final int NUM_ALGORITMOS = 2;

    /**
     * LerEntradas é responsavel por ler e retornar o conteúdo do arquivo
     * 'entrada.txt', que define 5 tamanhos diferentes para o vetor ser executado
     * @return entradas tipo int[]
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
            System.out.println(entradas[cont]);
            cont++;
        }

        entrada.close();
        return entradas;
    }

     /**
    * Método responsavel por carregar de forma aleatória um número tam de registros de forma desordenada.
    * @param registros array de objetos do tipo Registro onde os dados vão ser armazenados
    * @param tam do tipo inteiro que representa o tamanho do vetor de Registros
    */
    public static void ArmazenaNoVetor (Registro registros[], int tam){

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
      * Chama a função da classe Heapsort,que ordena o vetor.
      * Com o vetor ordenada, a função também é responsável por gerar um objeto do tio Saída
      * e armazenar no ArrayList de classe ArquivoSaída
      * @param registro array de objetos do tipo Registro onde os dados vão ser armazenados
      * @param tam do tipo inteiro que representa o tamanho do vetor de Registros
      * @param arqSaida do tipo ArquivoSaida onde é armazenado os dados ordenados prontos para saída
      */
    public static void fazOrdenacaoHeapSort(Registro[] registro,int tam, ArquivoSaida arqSaida){
        long tempo;
        String nRegistros = Integer.toString(tam);
        HeapSort hpSort = new HeapSort();
        long inicio = System.currentTimeMillis();
        hpSort.HeapSorting(registro);
        long fim  = System.currentTimeMillis();
        tempo = fim - inicio;
        Saida nsaida1 = new Saida("Heap Sort",nRegistros, hpSort.getnComparacoes(), hpSort.getnTrocas(), tempo);
        arqSaida.addSaida(nsaida1);
    }

    /**
      * Chama a função da classe Quicksort,que ordena o vetor.
      * Com o vetor ordenada, a função também é responsável por gerar um objeto do tio Saída
      * e armazenar no ArrayList de classe ArquivoSaída
      * @param registro array de objetos do tipo Registro onde os dados vão ser armazenados
      * @param tam do tipo inteiro que representa o tamanho do vetor de Registros
      * @param arqSaida do tipo ArquivoSaida onde é armazenado os dados ordenados prontos para saída
      */
    public static void fazOrdenacaoQuickSort(Registro[] registro, int tam, ArquivoSaida arqSaida){
        long tempo;
        String nRegistro = Integer.toString(tam);
        QuickSort qSort = new QuickSort();
        long inicio = System.currentTimeMillis();
        qSort.quickSorting(registro, 0, tam);
        long fim = System.currentTimeMillis();
        tempo = fim - inicio;
        Saida nsaida2 = new Saida("Quick Sort",nRegistro,qSort.getnComparacoes(),qSort.getnTrocas(),tempo);
        arqSaida.addSaida(nsaida2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Registro[] registros;
            Registro[] registrosaux;
            int[] entradas = lerEntradas();
            System.out.println(entradas[0]);
            int tamEntradas = entradas[0] + 1;
            int tamSaidas = entradas[0] * NUM_ALGORITMOS;
            ArquivoSaida arqSaida = new ArquivoSaida(tamSaidas);

            for (int i=1; i< tamEntradas; i++){
                registros = new Registro[entradas[i]];
                ArmazenaNoVetor(registros,entradas[i]);
                registrosaux = registros.clone();
                fazOrdenacaoHeapSort(registros,entradas[i],arqSaida);
                fazOrdenacaoQuickSort(registrosaux,entradas[i],arqSaida);
            }

            System.out.println("Saída Gravada!");
            arqSaida.gravaSaidas();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
