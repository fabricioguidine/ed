package Classes;

 /**
  * Classe para objetos do tipo HeapSort, onde serão contidos valores e métodos para o mesmo.
  * @author Débora Duarte e Walkíria Garcia
  * @version 1.0
  */
public class HeapSort 
{ 
    private int nComparacoes;
    private int nTrocas;
    
    /**
    * Construtor para a classe HeapSort
    */
    public HeapSort (){
        nComparacoes = 0;
        nTrocas = 0;
    }
    
    /**
      * Constrói a Heap. 
      * Troca o item na posição 1 do Array com o item da última posição .
      * Use o procedimento MaxHeapify para reconstruir a Heap para os itens A[1], A[2],..., A[n − 1], sendo n = tamanho do vetor.
      * @param vet Array de objetos do tipo Registro a ser ordenado.
      */
    public void HeapSorting(Registro vet[]) 
    { 
        int n = vet.length; 
  
         
        for (int i = n / 2 - 1; i >= 0; i--) 
            MaxHeapify(vet, n, i); 
  
        nTrocas++;
        for (int i=n-1; i>0; i--) 
        { 
            // Move a raiz atual para o final 
            Registro temp = vet[0]; 
            vet[0] = vet[i]; 
            vet[i] = temp; 
  
            // Chama heapfy para a heap reduzida
            MaxHeapify(vet, i, 0); 
        } 
    } 
  
    /**
     * Verifica se a propriedade de heap máximo não é violada em um determinado nó da árvore. 
     * @param vet Array de objetos do tipo Registro a ser ordenado 
     * @param n Inteiro que representa o tamanho do vetor
     * @param i Inteiro que representa a posição do nó pai
     * @see Registro
     */
    void MaxHeapify(Registro vet[], int n, int i) 
    { 
        int pai = i; // Initialize pai as root 
        int esq = 2*i + 1; // esquerda = 2*i + 1 
        int dir = 2*i + 2; // direita = 2*i + 2
        
        
        //System.out.println(n + " " + i + " " + l + " "+ r);
        // Se filho a esquerda é maior que pai
        nComparacoes++;
        if (esq < n && (vet[esq].getTitle().compareTo(vet[pai].getTitle())>0 )) 
            pai = esq; 
  
        // Se filho da direita é maior que pai
        nComparacoes++;
        if (dir < n && (vet[dir].getTitle().compareTo(vet[pai].getTitle())>0 )) 
            pai = dir; 
  
        // Se pai não é a raiz 
        if (pai != i) 
        { 
            nTrocas++;
            Registro swap = vet[i]; 
            vet[i] = vet[pai]; 
            vet[pai] = swap; 
  
            // MaxHeapfy a subárvore afetada 
            MaxHeapify(vet, n, pai); 
        } 
    }
    
    /**
     * Retorna o número de comparações realizados pelo HeapSorting 
     * @return nComparacoes Inteiro
     */
    public int getnComparacoes() {
        return nComparacoes;
    }

    /**
    * Retorna o número de trocas realizados pelo HeapSorting
    * @return nTrocas Inteiro
    */
    public int getnTrocas() {
        return nTrocas;
    }
    
    
}
