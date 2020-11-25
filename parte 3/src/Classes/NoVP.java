package Classes;

/**
 * Classe que implementa o No da Árvore Vermelho-Preto.
 * @author Fabrício Guidine, Débora Duarte, Walkíria Garcia
 * @version 1.0
 * @see Registro
 * @see NoVP
 */
public class NoVP {
    
    private Registro info;
    private NoVP esq;
    private NoVP dir;
    private NoVP pai;
    private int cor;

    /**
     * Retorna o registro que é a chave contida no nó.
     * @return um objeto do tipo Registro que é a chave contida no nó.
     * @see Registro
     */
    public Registro getInfo() {
        return info;
    }

    /**
     * Altera o valor da chave contida no nó.
     * @param info registro a ser colocado no nó.
     * @see Registro
     */
    public void setInfo(Registro info) {
        this.info = info;
    }

    /**
     * Retorna o filho a esquerda do no.
     * @return NoVP que é o filho a esquerda do nó.
     */
    public NoVP getEsq() {
        return esq;
    }

    /**
     * Coloca um NoVP passado como parâmetro como sendo novo filho a esquerda do nó.
     * @param esq o NoVP a ser colocado.
     * @see NoVP
     */
    public void setEsq(NoVP esq) {
        this.esq = esq;
    }

    /**
     * Retorna o filho a esquerda do no.
     * @return NoVP que é o filho a esquerda do nó.
     */
    public NoVP getDir() {
        return dir;
    }

    /**
     * Coloca um NoVP passado como parâmetro como sendo novo filho a direita do nó.
     * @param dir a ser colocado como filho direito do nó.
     * @see NoVP
     */
    public void setDir(NoVP dir) {
        this.dir = dir;
    }

    /**
     * @return o NoVP que é o pai do nó.
     */
    public NoVP getPai() {
        return pai;
    }

    /**
     * @param pai Recebe um NoVP como parâmetro e o coloca como pai do nó.
     */
    public void setPai(NoVP pai) {
        this.pai = pai;
    }

    /**
     * @return o Inteiro que é a cor do nó.
     */
    public int getCor() {
        return cor;
    }

    /**
     * @param cor Recebe como parâmetro um inteiro e o coloca como cor do nó. 0 para oreto e 1 para vermelho.
     */
    public void setCor(int cor) {
        this.cor = cor;
    }
    
    
}
