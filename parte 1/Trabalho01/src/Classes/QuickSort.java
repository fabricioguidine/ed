package Classes;

  /**
   * Classe para objetos do tipo QuickSort, onde serão contidos valores e métodos para o mesmo.
   * @author Débora Duarte e Fabrício Guidine
   * @version 1.0
  */
public class QuickSort {
    
    private int nComparacoes;
    private int nTrocas;
    
    /**
    * Construtor para a classe QuickSort.
    */
    public QuickSort(){
        this.nComparacoes = 0;
        this.nTrocas = 0;        
    }
    
    /**
      * Função responsavel por ordenação o vetor pelo algoritimo QuickSort.
      * @param vet Vetor de objetos do tipo registro a ser ordenado.
      * @param inicio Posição inicial do vetor.
      * @param fim Posição final do vetor.
      * @see Registro
      */
    public void quickSorting(Registro[] vet, int inicio, int fim){
    
    int i, j, mediana;
    i = inicio;
    j = fim-1;
    mediana = (inicio + fim + ((inicio + fim)/2)) / 3;  //mediana é pivo, que foi escolhido pela mediana de 3
    while(i <= j)
    {
        //percorre-se o vetor da esquerda para a direita enquanto vet[i] < pivô,
        nComparacoes++;    
        while ( (((vet[i].getTitle()).compareTo(vet[mediana].getTitle()))<0)  && i<fim){
            i++;
        }
         // depois, percorre-se o vetor da direita para a esquerda enquanto vet[j] > * pivo.
         nComparacoes++;
        while ((((vet[i].getTitle()).compareTo(vet[mediana].getTitle()))<0) && j>inicio){
            j--;
        }
        
        //Por fim, Troca-se vet[i] com vet[j]. Esse processo continua até os apontadores i e j se cruzarem.
        if(i <= j)
        {
            nTrocas++;
            Registro aux = vet[i];
            vet[i] = vet[j];
            vet[j] = aux;
            i++;
            j--;
        }
    }
    if(j > inicio)
        quickSorting(vet, inicio, j+1);
    if(i < fim)
        quickSorting(vet, i, fim);
    }

     /**
     * Método que retorna o número de comparações realizados pelo QuickSort 
     * @return nComparacoes do tipo Inteiro.
     */
    public int getnComparacoes() {
        return nComparacoes;
    }

     /**
     * Método que retorna o número de trocas realizados pelo QuickSort.
     * @return nTrocas do tipo Inteiro.
     */
    public int getnTrocas() {
        return nTrocas;
    }    
}
