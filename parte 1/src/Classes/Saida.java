/**
 * Universidade Federal de Juiz de Fora – Departamento de Ciência da Computação
 * @author deboraizabel
 * @author fabricioguidine
 * @author walkiriagarcia
*/

package Classes;

/**
 *Classe para objetos do tipo Saída, onde serão contidos valores e métodos para o mesmo
*/
public class Saida {

    private String algoritmo;
    private String nRegistros;
    private String nComparacoes;
    private String nTrocas;
    private String tempo;

/**
  * Construtor vazio da classe Saida
*/
    public Saida(){

    }

/**
    * Construtor da classe Saida
    * @param algoritmo
    * @param nRegistros
    * @param nComparacoes
    * @param nTrocas
    * @param tempo
*/
    public Saida (String algoritmo, String nRegistros, int nComparacoes, int nTrocas, long tempo){

        this.algoritmo = algoritmo;
        this.nRegistros = nRegistros;
        this.nComparacoes = Integer.toString(nComparacoes);
        this.nTrocas = Integer.toString(nTrocas);
        this.tempo = Long.toString(tempo);
    }

/**
    * Método que retorna o nome do algoritmo de ordenação usado ( quicksort ou heapsort )
    * @return algoritmo tipo String
*/
    public String getAlgoritmo() {
        return algoritmo;
    }

/**
    * Método que retorna o tamanho dos registros
    * @return nRegistros tipo String
*/
    public String getnRegistros() {
        return nRegistros;
    }

/**
    * Método que retorna a quantidade de comparações realizadas
    * @return nComparacoes tipo String
*/
    public String getnComparacoes() {
        return nComparacoes;
    }

/**
   * Método que retorna a quantidade de trocas
   * @return nTrocas tipo String
*/
    public String getnTrocas() {
        return nTrocas;
    }

/**
    * Método que retorna o tempo que demorou para fazer a ordenação
    * @return tempo tipo String
*/
    public String getTempo() {
        return tempo;
    }
}
