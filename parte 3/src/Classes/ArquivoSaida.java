package Classes;

import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.FormatterClosedException;
import java.lang.SecurityException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *Classe para objetos do tipo ArquivoSaida, onde serão contidos valores e métodos para o mesmo.
 * @author DéboraDuarte
 * @version 1.0
*/
public class ArquivoSaida {
    ArrayList<Saida> saidas;
    private Formatter arquivo;
    
    /**
     * Cria um arquivo de nome saída.txt.
    */
    private void abrir(String nomeArquivo) {
        try {
            arquivo = new Formatter(nomeArquivo);
        }
        catch( SecurityException semPermissao) {
            System.err.println(" Sem permissao para escrever no arquivo ");
            System.exit(1); //exit(0) é sucesso, outro número significa que terminou com problemas
        }
        catch( FileNotFoundException arquivoInexistente ) {
            System.err.println(" Arquivo inexistente ou arquivo não pode ser criado");
            System.exit(1);
        }
    } 
    
    /**
     * Construtor para a classe ArquivoSaida.
    */
    public ArquivoSaida(){
        saidas = new ArrayList<>();
        
    }
    
    /**
     * Adiciona uma nova saída para posterior escrita no arquivo.
     * @param nSaida do tipo Saida sendo a sáida a ser adicionada.
     * @see Saida
    */
    public void addSaida(Saida nSaida){
        saidas.add(nSaida);
    }
    
    /**
     * Função que chama a função abrir da mesma classe, formata o arquivo para saída e faz a escrita. 
     * @param nomeArquivo String que é o nome do arquivo a ser criado.
    */
    public void gravaSaidas(String nomeArquivo){
        abrir(nomeArquivo);
        try {
            System.out.println("grava saida");
            arquivo.format("Indice,Algoritmo,Operação, Número de Registros,Comparacoes,Trocas,Tempo\n");
            for (int j=0; j<saidas.size(); j++){
            arquivo.format("%d, %s, %s, %s, %s, %s, %s\n" ,j+1,saidas.get(j).getAlgoritmo(),saidas.get(j).getOperacao(),saidas.get(j).getnRegistros(),saidas.get(j).getnComparacoes(),saidas.get(j).getnTrocas(),saidas.get(j).getTempo());
            }
            arquivo.close();
        }
        catch(FormatterClosedException formatoDesconhecido) {
            System.err.println("Erro ao escrever");
            return;
        }
        catch(NoSuchElementException excecaoElemento){
            System.err.println("Entrada invalida");
        }
    }
    
    public void fechar(){
        arquivo.close();
    }
    
}
