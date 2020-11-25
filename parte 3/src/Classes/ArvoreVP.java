package Classes;

/**
 * Classe que implementa uma Árvore Vermelho-Preto.
 * @author Fabrício Guidine, Débora Duarte, Walkíria Garcia
 * @version 1.0
 */
public class ArvoreVP {
    
    // 0 = preto ; 1 = vermelho
    NoVP raiz;
    NoVP folha; // criado para facilitar comparações
    private int nTrocas;
    private int nComparacoes;
    
    /**
     * Construtor da classe.
     */
    public ArvoreVP() {
        
		this.folha = new NoVP();
		this.folha.setCor(0);
                this.folha.setEsq(null);
                this.folha.setDir(null);
                this.raiz = this.folha;
                this.nTrocas = 0;
                this.nComparacoes = 0;
    }
    
    /**
     * Verifica se o registro a ser adicionado tem algum atributo que não satisfaz as condições 
     * de entrada na árvore Vermelho-Preto.
     * @param reg Registro a ser inserido na árvore.
     * @return um booleano de valor true se há algum impedimento ou false se não existe impedimento.
     * @see Registro
     */
    boolean verificaRegistro(Registro reg){   //volta true se deu problema
        if (reg.getId() == null || reg.getId().compareTo("-1")==0){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Troca dois nós de lugar na árvore. 
     * @param trocar NoVP a ser trocado.
     * @param substituto NoVP a ser trocado.
     * @see NoVP
     */
    void troca (NoVP trocar, NoVP substituto){
        nTrocas++;
        //System.out.println(nTrocas);
        if (trocar.getPai() == null) {
			this.raiz = substituto;
		} else if (trocar == trocar.getPai().getEsq()){
			trocar.getPai().setEsq(substituto);
		} else {
			trocar.getPai().setDir(substituto);
		}
		substituto.setPai(trocar.getPai());
    }
    
    /**
     * Retorna o NoVp de menor valor a esquerda do nó passado como parâmetro.
     * @param no NoVP no qual a busca é iniciada.
     * @return NoVP tem a menor chave a esquerda do NoVP passado como parâmetro.
     * @see NoVP
     */
    NoVP minimo(NoVP no) {
        while (no.getEsq() != folha) {
                no = no.getEsq();
        }
        return no;
    }
    
    /**
     * Retorna o NoVp de maio valor a direita do nó passado como parâmetro.
     * @param no NoVP no qual a busca é iniciada.
     * @return NoVP tem a maior chave a direita do NoVP passado como parâmetro.
     * @see NoVP
     */
    public NoVP maximo(NoVP no) {
        while (no.getDir() != folha) {
                no = no.getDir();
        }
        return no;
    }
    
    /*~~~~~~~~~~~~~~~~ INSERIR, REMOVER E BUSCAR ~~~~~~~~~~~~~~~~*/
    
    /**
     * Dado um objeto do tipo Registro passado como parâmetro a função insere o objeto na
     * árvore Vermelho-Preto.
     * @param reg objeto do tipo Registro a ser inserido na árvore.
     * @see Registro
     */
    public void inserir(Registro reg){
        
        if (reg == null){
            System.out.println("O registro inserido é null");
        }
        
        if (verificaRegistro(reg) == false){
            
            NoVP no = new NoVP();
            no.setPai(null);
            no.setDir(folha);
            no.setEsq(folha);
            no.setInfo(reg);

            NoVP n = this.raiz;
            NoVP n1 = null;

            while (n != folha) {
                n1 = n;
                if (no.getInfo().intGetId() < n.getInfo().intGetId()) { nComparacoes++;
                        n = n.getEsq();
                } else {
                        n = n.getDir();
                }
            }
            
            //n1 e pai de n
            no.setPai(n1);
            if (n1 == null) {
                    this.raiz = no;
            } else if (no.getInfo().intGetId() < n1.getInfo().intGetId()) { nComparacoes++;
                    n1.setEsq(no);
            } else {
                    n1.setDir(no);
            }
            
            // se no é raiz
            if (no.getPai() == null){
                //System.out.println("Saiu");
                    no.setCor(0);
                    return;
            }
            
            // se avô de no é null
            if (no.getPai().getPai() == null) {
                //System.out.println("Saiu");
                    return;
            }
            
            // Conserta quebras de propriedade
		fixInserir(no);
        }
    }
    
    /**
     * Função auxiliar que remove um registro passado como parâmetro.
     * @param no NoVP por onde a busca pelo no que contém o registro a ser removido começa.
     * @param reg Registro a ser removido da árvore.
     * @see NoVP
     * @see Registro
     * @see Remove
     */
    void auxRemove(NoVP no, Registro reg){
        
        if (verificaRegistro(reg) == true){
            return;
        }
        int chave = reg.intGetId();
        
        NoVP z = folha;
        NoVP x, y;

        while (no != folha){
            if (no.getInfo().intGetId() == chave) { nComparacoes++;
                    z = no;
            }

            if (no.getInfo().intGetId() <= chave) {
                    no = no.getDir();
            } else {
                    no = no.getEsq();
            }
        }
        
        if (z == folha) {
            //não encontrou a chave
            return;
        }
        
        y = z;
        int yOriginalColor = y.getCor();
        if (z.getEsq() == folha) {
                x = z.getDir();
                troca(z, z.getDir());
        } else if (z.getDir() == folha) {
                x = z.getEsq();
                troca(z, z.getEsq());
        } else {
                y = minimo(z.getDir());
                yOriginalColor = y.getCor();
                x = y.getDir();
                if (y.getPai() == z) {
                        x.setPai(y);
                } else {
                        troca(y, y.getDir());
                        y.setDir(z.getDir());
                        y.getDir().setPai(y);
                }

                troca(z, y);
                y.setEsq(z.getEsq());
                y.getEsq().setPai(y);
                y.setCor(z.getCor());
        }
        if (yOriginalColor == 0){
                fixRemover(x);
        }
    }
    
    /**
     * Dado um objeto do tipo Registro passado como parâmetro a função remove o objeto 
     * da árvore Vermelho-Preto chamando a função auxRemove passando a raiz da árvore e o
     * Registro como parâmetros.
     * @param reg Registro a ser removido.
     * @see Registro
     * @see auxRemove
     */
    public void Remove (Registro reg){
        auxRemove(this.raiz, reg);
    }
    
    NoVP auxBusca(NoVP no, int chave) {       
        
        try{
        if (no == folha || chave == no.getInfo().intGetId()) { nComparacoes++;
                return no;
        }

        if (chave < no.getInfo().intGetId()) { nComparacoes++;
                return auxBusca(no.getEsq(), chave);
        } 
        return auxBusca(no.getDir(), chave);
        } catch (NullPointerException ex){
            System.out.println("Null na aux.");
        }
        return null;
    }
    
    public NoVP Busca(Registro reg) {
        if (verificaRegistro(reg) == true){
            System.out.println("Achou um deu ruim");
            return null;            
        }
        int chave = reg.intGetId();
        return auxBusca(this.raiz, chave);
    }
    
    /*~~~~~~~~~~~~~~~~ RESOLVENDO QUEBRAS DE PROPRIEDADE ~~~~~~~~~~~~~~~~*/
    
    /**
     * Verifica se houve a quebra de alguma propriedade após a inserção de um nó e já faz o 
     * rebalanceamento da árvore se necessário.
     * @param no NoVP a ser verificado.
     * @see inserir
     * @see NoVP
     */
    private void fixInserir(NoVP no){
        //System.out.println(no.getPai().getCor());
        NoVP u;
        while (no.getPai().getCor() == 1) {
            if (no.getPai() == no.getPai().getPai().getDir()) {
                //System.out.println("Entrou rot-1");
                u = no.getPai().getPai().getEsq(); // tio
                if (u.getCor() == 1) {
                    // pai e tio são vermelhos
                   // System.out.println("Entrou rot0");
                    u.setCor(0);
                    no.getPai().setCor(0);
                    no.getPai().getPai().setCor(1);
                    no = no.getPai().getPai();
                } else {
                    if (no == no.getPai().getEsq()) {
                        // pai e vermelho e tio e preto ou null
                        // pai é filho direito do avô e o no e filho esquerdo de pai
                        no = no.getPai();
                       // System.out.println("Entrou rot");
                        rotacaoDireita(no);
                    }
                        // pai e vermelho e tio e preto ou null
                        // pai e  filho direito de avô e o no é filho direito de pai
                    no.getPai().setCor(0);
                    no.getPai().getPai().setCor(1);
                    rotacaoEsquerda(no.getPai().getPai());
                    }
            } else {
                u = no.getPai().getPai().getDir(); //tio

                if (u.getCor() == 1) {
                    // pai e tio são vermelhos
                    u.setCor(0);
                    no.getPai().setCor(0);
                    no.getPai().getPai().setCor(1);
                    no = no.getPai().getPai();	
                } else {
                    if (no == no.getPai().getDir()) {
                        // pai e vermelho e tio e preto ou null
                        // pai é filho direito do avô e o no e filho esquerdo de pai
                        no = no.getPai();
                        rotacaoEsquerda(no);
                    }
                        // pai e vermelho e tio e preto ou null
                        // pai e  filho direito de avô e o no é filho direito de pai
                    no.getPai().setCor(0);
                    no.getPai().getPai().setCor(1);
                    rotacaoDireita(no.getPai().getPai());
                }
            }
            if (no == raiz) {
                break;
            }
        }
        raiz.setCor(0);
    }
    
    /**
     * Verifica se houve a quebra de alguma propriedade após a remoção de um nó e já faz o 
     * rebalanceamento da árvore se necessário.
     * @param no NoVP a ser verificado.
     * @see Remover
     * @see NoVP
     */
    private void fixRemover(NoVP no )  {
        try{
        NoVP s;
        while (no != raiz && no.getCor() == 0) {
            if (no == no.getPai().getEsq()) {
                s = no.getPai().getDir();
                if (s.getCor() == 1) {
                        //pai e tio são vermelhos
                        s.setCor(0);
                        no.getPai().setCor(1);
                        rotacaoEsquerda(no.getPai());
                        s = no.getPai().getDir();
                }

            if (s.getEsq().getCor() == 0 && s.getDir().getCor() == 0) {
                //pai é vermelho e tio e preto ou null
                s.setCor(1);
                no = no.getPai();
                } else {
                    if (s.getDir().getCor() == 0) {
                        //o no é preto, imão do nó e preto
                        //filho esquerdo do irmão é vermelho e filho direito do irmao é preto
                        s.getEsq().setCor(0);
                        s.setCor(1);
                        rotacaoDireita(s);
                        s = no.getPai().getDir();
                    } 

                    // o no é preto, irmão do nó é preto
                    //filho direito do irmçao e vermelho
                    s.setCor(no.getPai().getCor());
                    no.getPai().setCor(0);
                    s.getDir().setCor(0);
                    rotacaoEsquerda(no.getPai());
                    no = raiz;
                }
                } else {
                    s = no.getPai().getEsq();
                    if (s.getCor() == 1) {
                        // o no é preto e o irmão e vermelho
                        s.setCor(0);
                        no.getPai().setCor(1);
                        rotacaoDireita(no.getPai());
                        s = no.getPai().getEsq();
                    }

                    if (s.getDir().getCor() == 0 && s.getDir().getCor() == 0) {
                        // o no é preto, o irmão do nó é preto e ambos os filhos do irmão são pretos
                        s.setCor(1);
                        no = no.getPai();
                    } else {
                        if (s.getEsq().getCor() == 0) {
                            //o no é preto, imão do nó e preto
                            //filho esquerdo do irmão é vermelho e filho direito do irmao é preto
                            s.getDir().setCor(0);
                            s.setCor(1);
                            rotacaoEsquerda(s);
                            s = no.getPai().getEsq();
                        } 

                        // o no é preto, irmão do nó é preto
                        //filho direito do irmão e vermelho
                        s.setCor(no.getPai().getCor());
                        no.getPai().setCor(0);
                        s.getEsq().setCor(0);
                        rotacaoDireita(no.getPai());
                        no = raiz;
                        }
                } 
        }
        no.setCor(0);
        } catch (NullPointerException ex){
            
        }
    }
    
    /**
     * A função faz uma rotação a esquerda sobre o NoVP passado como parâmetro.
     * @param no NoVP que é o "pivô" da rotação.
     */
    public void rotacaoEsquerda(NoVP no) {
        nTrocas++;
        //System.out.println(nTrocas);
        NoVP y = no.getDir();
        no.setDir(y.getEsq());
        if (y.getEsq() != folha) {
                y.getEsq().setPai(no);
        }
        y.setPai(no.getPai());
        if (no.getPai() == null) {
                this.raiz = y;
        } else if (no == no.getPai().getEsq()) {
                no.getPai().setEsq(y);
        } else {
                no.getPai().setDir(y);
        }
        y.setEsq(no);
        no.setPai(y);
    }
    
    /**
     * A função faz uma rotação a direita sobre o NoVP passado como parâmetro.
     * @param no NoVP que é o "pivô" da rotação.
     */
    public void rotacaoDireita(NoVP no) {
        nTrocas++;
       // System.out.println(nTrocas);
        NoVP y = no.getEsq();
        no.setEsq(y.getDir());
        if (y.getDir() != folha) {
                y.getDir().setPai(no);
        }
        y.setPai(no.getPai());
        if (no.getPai() == null) {
                this.raiz = y;
        } else if (no == no.getPai().getDir()) {
                no.getPai().setDir(y);
        } else {
                no.getPai().setEsq(y);
        }
        y.setDir(no);
        no.setPai(y);
    }

   /**
     * @return Inteiro que é o número de comparações feitas.
     */
    public int getnComparacoes() {
        return nComparacoes;
    }

    /**
     * @param nComparacoes Inteiro que determina o valor do atributo nComparacoes.
     */
    public void setnComparacoes(int nComparacoes) {
        this.nComparacoes = nComparacoes;
    }

    /**
     * @return Inteiro que é o número de trocas feitas.
     */
    public int getnTrocas() {
        return nTrocas;
    }

    /**
     * @param nTrocas Inteiro que determina o valor do atributo nTrocas.
     */
    public void setnTrocas(int nTrocas) {
        this.nTrocas = nTrocas;
    }
    
    
}
