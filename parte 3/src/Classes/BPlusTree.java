package Classes;
import java.util.*;

/**
 * Classe que implementa uma Árvore B+.
 * @author Fabrício Guidine, Débora Duarte, Walkíria Garcia
 * @version 1.0
 */
public class BPlusTree {
	int m;
	NoInterno raiz;
	NoFolha primeiraFolha;
        private int nComparacoes;
        private int nTrocas;

	/*~~~~~~~~~~~~~~~~ FUNÇÕES AUXILIARES ~~~~~~~~~~~~~~~~*/

	/**
         * Essa função faz uma busca binária em um Array do tipo DictionaryPair ordenado e retorna
         * um inteiro que representa a posição do objeto encontrado.
         * Se o valor não é encontrado a função retorna um valor negativo.
	 * @param dps: Array do tipo Dictionarypair onde a busca será feita.
	 * @param t: Inteiro que é a chave que vai ser buscada.
	 * @return Retorna um Inteiro que é a posição da chave encontrada, se não encontra a chave retorna um valor negativo.
	 */
	private int buscaBin(DictionaryPair[] dps, int numPairs, int t) {
		Comparator<DictionaryPair> c = new Comparator<DictionaryPair>() {
			@Override
			public int compare(DictionaryPair o1, DictionaryPair o2) {
				Integer a = Integer.valueOf(o1.chave);
				Integer b = Integer.valueOf(o2.chave);
                                setnComparacoes(getnComparacoes() + 1);
				return a.compareTo(b);
			}
		};
                Registro r = new Registro();
		return Arrays.binarySearch(dps, 0, numPairs, new DictionaryPair(t, r), c);
	}

	/**
         * Esse método busca o NoFolha onde está contida a chave passada como parâmetro.
	 * @param chave: a chave a ser buscada.
	 * @return o NoFolha que contém a chave buscada.
	 */
	private NoFolha buscaNoFolha(int chave) {

		Integer[] chaves = this.raiz.chaves;
		int i;

		for (i = 0; i < this.raiz.num - 1; i++) {
                        setnComparacoes(getnComparacoes() + 1);
			if (chave < chaves[i]) { break; }
		}

		No filho = this.raiz.filhos[i];
		if (filho instanceof NoFolha) {
			return (NoFolha)filho;
		} else {
			return buscaNoFolha((NoInterno)filho, chave);
		}
	}
        /**
         * Dado um NoInterno e uma chave inteira como parâmentro a função busca a chave informada entre os filhos
         * do nó informado. 
         * @param no um NoInterno em cujos filhos a busca vai ser executada.
         * @param chave um Inteiro que é a chave a ser procurada.
         * @return o NoFolha onde a chave procurada se encontra.
         */    
	private NoFolha buscaNoFolha(NoInterno no, int chave) {

		Integer[] chaves = no.chaves;
		int i;

		for (i = 0; i < no.num - 1; i++) {
                        setnComparacoes(getnComparacoes() + 1);
			if (chave < chaves[i]) { break; }
		}

		No filho = no.filhos[i];
		if (filho instanceof NoFolha) {
			return (NoFolha)filho;
		} else {
			return buscaNoFolha((NoInterno)no.filhos[i], chave);
		}
	}

	/**
         * Dada um lista de ponteiros do tipo No, a funçõa vai retornar a posição onde o 
         * NoFolha passada como parâmetro se encontra.
	 * @param ponteiros: um array de boletos do tipo No.
	 * @param no: o No folha procurado.
	 * @return Um Inteiro que é a posição do NoFolha encontrado.
	 */
	private int getIndexNo(No[] ponteiros, NoFolha no) {
		int i;
		for (i = 0; i < ponteiros.length; i++) {
			if (ponteiros[i] == no) { break; }
		}
		return i;
	}

	/**
         * Retorna a metade do valor que é a ordem da árvore B+.
	 * @return um Inteiro que é a metade do valor da ordem da árvore B+.
	 */
	private int getMeio() {
		return (int)Math.ceil((this.m + 1) / 2.0) - 1;
	}

	/**
         * Dado um NoInterno como parâmetro a função resolve quebras de propriedades encontradas no No.
	 * @param in: Um NoInterno que quebra uma ou mais propriedades.
	 */
	private void resolve(NoInterno in) {

		NoInterno irmao;
		NoInterno pai = in.pai;

		// Conserta a raiz deficiente
		if (this.raiz == in) {
			for (int i = 0; i < in.filhos.length; i++) {
				if (in.filhos[i] != null) {
					if (in.filhos[i] instanceof NoInterno) {
						this.raiz = (NoInterno)in.filhos[i]; setnTrocas(getnTrocas() + 1);
						this.raiz.pai = null;
					} else if (in.filhos[i] instanceof NoFolha) {
						this.raiz = null;
					}
				}
			}
		}

		else if (in.irmaoEsquerdo != null && in.irmaoEsquerdo.podeCeder()) {
			irmao = in.irmaoEsquerdo; setnTrocas(getnTrocas() + 1);
		} else if (in.irmaoDireito != null && in.irmaoDireito.podeCeder()) {
			irmao = in.irmaoDireito; setnTrocas(getnTrocas() + 1);

			// Copia chave e ponteiro do irmão
			int chaveemp = irmao.chaves[0];
			No pointer = irmao.filhos[0];

			// Copia raiz e chave para pai
			in.chaves[in.num - 1] = pai.chaves[0];
			in.filhos[in.num] = pointer;

			// Copia chave para raiz
			pai.chaves[0] = chaveemp;

			irmao.remove(0);
                        
			Arrays.sort(irmao.chaves); setnTrocas(getnTrocas() + 1);
			irmao.remove(0);
			desce(in.filhos, 1);
		}

		else if (in.irmaoEsquerdo != null && in.irmaoEsquerdo.podeUnir()) {

		} else if (in.irmaoDireito != null && in.irmaoDireito.podeUnir()) {
			irmao = in.irmaoDireito; setnTrocas(getnTrocas() + 1);


			irmao.chaves[irmao.num - 1] = pai.chaves[pai.num - 2];
			Arrays.sort(irmao.chaves, 0, irmao.num); setnTrocas(getnTrocas() + 1);
			pai.chaves[pai.num - 2] = null;

			for (int i = 0; i < in.filhos.length; i++) {
				if (in.filhos[i] != null) {
                                        setnTrocas(getnTrocas() + 1);
					irmao.insereFilhoInicio(in.filhos[i]);
					in.filhos[i].pai = irmao;
					in.remove(i);
				}
			}

			pai.remove(in);

			irmao.irmaoEsquerdo = in.irmaoEsquerdo;
		}

		if (pai != null && pai.isAbaixoMin()) {
			resolve(pai);
		}
	}

	/**
	 * A função verifica se a árvore B+ está vazia.
	 * @return um boleano que indica se árvore está vazia ou não.
	 */
	private boolean estaVazia() {
		return primeiraFolha == null;
	}

	/**
         * Dado um Array do tipo dictionarypair (uma chave inteira e um valor do tipo Registro) essa função faz uma busca
         * nesse array ordenado e retorna a posição do primeiro elemento null encontrado. Se nenhum elemento null é encontrado a 
         * função vai retornar o valor -1;
         * @param dps: Array do tipo dictionarypair ordenado
         * @return Inteiro que representa a posição do primeiro null encontrado,  retorna -1 se nnehum valor null é encontrado.
         */
	public int busca(DictionaryPair[] dps) {
		for (int i = 0; i <  dps.length; i++) {
			if (dps[i] == null) { return i; }
		}
		return -1;
	}

	/**
         * Dado um Array do tipo No essa função faz uma busca
         * nesse array ordenado e retorna a posição do primeiro elemento null encontrado. Se nenhum elemento null é encontrado a 
         * função vai retornar o valor -1;
         * @param nos Array do tipo No onde é feita a busca.
         * @return Inteiro que representa a posição do primeiro null encontrado,  retorna -1 se nnehum valor null é encontrado.
        */
	public int busca(No[] nos) {
		for (int i = 0; i <  nos.length; i++) {
			if (nos[i] == null) { return i; }
		}
		return -1;
	}

	/**
         * Essa função desloca para baixo nos precedidos por null.
	 * @param nos: Array de Nos que serão deslocados para baixo.
	 * @param quantidade: Inteiro que determina o quão para baixo os nos serão deslocados.
	 */
	private void desce(No[] nos, int quantidade) {
		No[] novosNos = new No[this.m + 1];
		for (int i = quantidade; i < nos.length; i++) {
			novosNos[i - quantidade] = nos[i];
		}
		nos = novosNos;
	}

	/**
         * Método que ordena um Array de objetos do tipo DictionaryPair.
	 * @param dictionary: Array de DictionaryPair a ser ordenado.
	 */
	private void sortDictionary(DictionaryPair[] dictionary) {
                setnTrocas(getnTrocas() + 1);
		Arrays.sort(dictionary, new Comparator<DictionaryPair>() {
			@Override
			public int compare(DictionaryPair o1, DictionaryPair o2) {
				if (o1 == null && o2 == null) { return 0; }
				if (o1 == null) { return 1; }
				if (o2 == null) { return -1; }
				return o1.compareTo(o2);
			}
		});
	}

	/**
         * Este método modifica o NoInterno 'in' removendo todos os ponteiros dentro dos filhos na posição split especificada.
         * O método retorna os ponteiros removidos em um Array de Nos para ser usado ao construir um novo NoInterno irmao.
	 * @param in: NoInterno cujos filhos serão divididos.
	 * @param split: posição onde será feita a cisão.
	 * @return um Array com os Nos removidos.
	 */
	private No[] divideFilhos(NoInterno in, int split) {

		No[] n1 = in.filhos;
		No[] n2 = new No[this.m + 1];

		for (int i = split + 1; i < n1.length; i++) {
			n2[i - split - 1] = n1[i];
			in.remove(i);
		}

		return n2;
	}

	/**
         * Essa função divide um nó dentro da árvore B +. O Nó e dividido e suas chaves (e valores)
         * são armazenados em dois Arrays do tipo DictionaryPair. O método retorna os pares que não estão mais no 
         * NoFolha dividido.
	 * @param no: Array de objetos do tipo DictionaryPair a ser didividido.
	 * @param p: A posição onde a cisão ocorre.
	 * @return DictionaryPair[] Array com os pares que não estão mais no NoFolha.
	 */
	private DictionaryPair[] splitDictionary(NoFolha no, int p) {

		DictionaryPair[] dictionary = no.dictionary;

		DictionaryPair[] h = new DictionaryPair[this.m];

		for (int i = p; i < dictionary.length; i++) {
			h[i - p] = dictionary[i];
			no.delete(i);
		}

		return h;
	}

	/**
         * Função que divide eum nó interno.
	 * @param no: NoInterno a ser dividido.
	 */
	private void divideNoInterno(NoInterno no) {

		NoInterno pai = no.pai;


		int meio = getMeio();
		int npaichave = no.chaves[meio];
		Integer[] hk = divideChaves(no.chaves, meio);
		No[] hp = divideFilhos(no, meio);

	
		no.num = busca(no.filhos);


		NoInterno irmao = new NoInterno(this.m, hk, hp);
		for (No pointer : hp) {
			if (pointer != null) { pointer.pai = irmao; }
		}


		irmao.irmaoDireito = no.irmaoDireito;
		if (irmao.irmaoDireito != null) {
			irmao.irmaoDireito.irmaoEsquerdo = irmao;
		}
		no.irmaoDireito = irmao;
		irmao.irmaoEsquerdo = no;

		if (pai == null) {

			Integer[] chaves = new Integer[this.m];
			chaves[0] = npaichave;
			NoInterno novaRaiz = new NoInterno(this.m, chaves);
			novaRaiz.addFilho(no);
			novaRaiz.addFilho(irmao);
			this.raiz = novaRaiz;

			no.pai = novaRaiz;
			irmao.pai = novaRaiz;

		} else {

			// Add chave to pai
			pai.chaves[pai.num - 1] = npaichave;
			Arrays.sort(pai.chaves, 0, pai.num);

			// Set up pointer to new irmao
			int pointerIndex = pai.getIndexNo(no) + 1;
			pai.insereFilho(irmao, pointerIndex);
			irmao.pai = pai;
		}
	}

	/**
         * Modifica um Array de chaves Inteiras removendo metade das chaves e as retornando em um outro Array.
	 * Usado na cisão de um NoInterno.
	 * @param chaves: Um Array de chaves Inteiras.
	 * @param p: Inteiro que indica a posição onde a divisão deve ocorrer.
	 * @return Integer[]  que contém as chaves removidas.
	 */
	private Integer[] divideChaves(Integer[] chaves, int p) {

		Integer[] hk = new Integer[this.m];

		chaves[p] = null;

		for (int i = p + 1; i < chaves.length; i++) {
			hk[i - p - 1] = chaves[i];
			chaves[i] = null;
		}

		return hk;
	}

	/*~~~~~~~~~~~~~~~~ DELETAR INSERIR BUSCAR ~~~~~~~~~~~~~~~~*/

	/**
	 * Remove uma chave da árvore B+
	 * @param chave: Inteiro que é a chave a ser removida.
	 */
	public void delete(int chave) {
            if (estaVazia()) {
                 System.err.println("Impossível Deletar: A árvore está vazia.");

            } else {
                 NoFolha ln = (this.raiz == null) ? this.primeiraFolha : buscaNoFolha(chave);
                 int dpIndex = buscaBin(ln.dictionary, ln.numPairs, chave);


                if (dpIndex < 0) {
                    System.err.println("Impossível Deletar: A chave não pôde ser encontrada.");

                } else {
                     ln.delete(dpIndex);

                    if (ln.isAbaixoMin()) {
                        
                        setnTrocas(getnTrocas() + 1);
                        NoFolha irmao;
                        NoInterno pai = ln.pai;

                        if (ln.irmaoEsquerdo != null &&
                             ln.irmaoEsquerdo.pai == ln.pai &&
                             ln.irmaoEsquerdo.podeCeder()) {

                             irmao = ln.irmaoEsquerdo;
                             DictionaryPair edp = irmao.dictionary[irmao.numPairs - 1];

                             ln.insere(edp);
                             sortDictionary(ln.dictionary);
                             irmao.delete(irmao.numPairs - 1);

                             int pointerIndex = getIndexNo(pai.filhos, ln);
                            if (!(edp.chave >= pai.chaves[pointerIndex - 1])) { setnComparacoes(getnComparacoes() + 1);
                                 pai.chaves[pointerIndex - 1] = ln.dictionary[0].chave; setnTrocas(getnTrocas() + 1);
                            }

                            } else if (ln.irmaoDireito != null &&
                                 ln.irmaoDireito.pai == ln.pai &&
                                 ln.irmaoDireito.podeCeder()) {

                                 irmao = ln.irmaoDireito;
                                 DictionaryPair edp = irmao.dictionary[0];

                                 ln.insere(edp);
                                 irmao.delete(0);
                                 sortDictionary(irmao.dictionary);

                                 int pointerIndex = getIndexNo(pai.filhos, ln);
                                if (!(edp.chave < pai.chaves[pointerIndex])) { setnComparacoes(getnComparacoes() + 1);
                                     pai.chaves[pointerIndex] = irmao.dictionary[0].chave; setnTrocas(getnTrocas() + 1);
                                }

                            }
                            else if (ln.irmaoEsquerdo != null && ln.irmaoEsquerdo.pai == ln.pai && ln.irmaoEsquerdo.podeUnir()){

                                 irmao = ln.irmaoEsquerdo;
                                 int pointerIndex = getIndexNo(pai.filhos, ln);

                                 pai.removeChave(pointerIndex - 1);
                                 pai.remove(ln);

                                 irmao.irmaoDireito = ln.irmaoDireito;

                                if (pai.isAbaixoMin()) {
                                     resolve(pai);
                                }

                            } else if (ln.irmaoDireito != null && ln.irmaoDireito.pai == ln.pai && ln.irmaoDireito.podeUnir()){

                                 irmao = ln.irmaoDireito;
                                 int pointerIndex = getIndexNo(pai.filhos, ln);

                                 pai.removeChave(pointerIndex);
                                 pai.remove(pointerIndex);

                                 irmao.irmaoEsquerdo = ln.irmaoEsquerdo;
                                if (irmao.irmaoEsquerdo == null) {
                                     primeiraFolha = irmao;
                                }

                                if (pai.isAbaixoMin()) {
                                     resolve(pai);
                                }
                            }

                    } else if (this.raiz == null && this.primeiraFolha.numPairs == 0) {
                         this.primeiraFolha = null;

                    } else {
                         sortDictionary(ln.dictionary);
                    }
                }
            }
	}

	/**
	 * Dados como parâmetro um inteiro e um objeto do tipo Registro, esse método cria um objeto do tipo 
         * DictionaryPair e o insere na árvore B+.
         * A chave é o atributo ID do objeto Registro.
	 * @param chave: Inteiro que é a chave a ser iserida.
	 * @param value: Registro a ser inserido.
	 */
	public void insere(int chave, Registro value){
            if (estaVazia()) {
                 NoFolha ln = new NoFolha(this.m, new DictionaryPair(chave, value));
                 this.primeiraFolha = ln;
            } else {

                 NoFolha ln = (this.raiz == null) ? this.primeiraFolha : buscaNoFolha(chave);

                if (!ln.insere(new DictionaryPair(chave, value))) {

                     ln.dictionary[ln.numPairs] = new DictionaryPair(chave, value);
                     ln.numPairs++;
                     sortDictionary(ln.dictionary);

                     int meio = getMeio();
                     DictionaryPair[] halfDict = splitDictionary(ln, meio);

                    if (ln.pai == null) {

                         Integer[] pai_chaves = new Integer[this.m];
                         pai_chaves[0] = halfDict[0].chave;
                         NoInterno pai = new NoInterno(this.m, pai_chaves);
                         ln.pai = pai;
                         pai.addFilho(ln);

                    } else {

                         int npaichave = halfDict[0].chave;
                         ln.pai.chaves[ln.pai.num - 1] = npaichave;
                         Arrays.sort(ln.pai.chaves, 0, ln.pai.num);
                    }

                     NoFolha newNoFolha = new NoFolha(this.m, halfDict, ln.pai);

                     int pointerIndex = ln.pai.getIndexNo(ln) + 1;
                     ln.pai.insereFilho(newNoFolha, pointerIndex);

                     newNoFolha.irmaoDireito = ln.irmaoDireito;
                     
                    if (newNoFolha.irmaoDireito != null) {
                         newNoFolha.irmaoDireito.irmaoEsquerdo = newNoFolha;
                    }
                     ln.irmaoDireito = newNoFolha;
                     newNoFolha.irmaoEsquerdo = ln;

                    if (this.raiz == null) {

                         this.raiz = ln.pai;

                    } else {

                         NoInterno in = ln.pai;
                        while (in != null) {
                            if (in.estaCheio()) {
                                 setnTrocas(getnTrocas() + 1);
                                 divideNoInterno(in);
                            } else {
                                 break;
                            }
                            in = in.pai;
                        }
                    }
                }
            }
	}

	/**
         * Dada um chava inteira como parâmetro, a função encontra o Registro que contém a chave informada na árvore B+.
	 * @param chave: uma chave Inteira a ser encontrada na árvore B+.
	 * @return o Registro associado a chave informada.
	 */
	public Registro busca(int chave) {

            if (estaVazia()) { return null; }
                 NoFolha ln = (this.raiz == null) ? this.primeiraFolha : buscaNoFolha(chave);

            DictionaryPair[] dps = ln.dictionary;
            int index = buscaBin(dps, ln.numPairs, chave);

            if (index < 0) {
                 return null;
            } else {
                 return dps[index].valor;
            }
	}

	/**
         * Retorna um ArrayList com registros cujos ID's se encontram dentro de um intervalo
         * determinado pelos valores inicial e término.
	 * @param inicial: Inteiro que é o valor inicial do intervalo.
	 * @param termino: Inteiro que é o valor final do intervalo.
	 * @return um ArrayList do tipo Registro com todos os Registros cujos Ids estão dentro do intervalor determinado.
         * @see Registro
	 */
	public ArrayList<Registro> busca(int inicial, int termino) {

             ArrayList<Registro> valores = new ArrayList<Registro>();

             NoFolha no = this.primeiraFolha;
            while (no != null) {

                 DictionaryPair dps[] = no.dictionary;
                for (DictionaryPair dp : dps) {

                    if (dp == null) { break; }

                    if (inicial <= dp.chave && dp.chave <= termino) {
                         valores.add(dp.valor);
                     }
                }

                no = no.irmaoDireito;

            }

            return valores;
	}

	/**
	 * Construtor da classe.
	 * @param m: a ordem da árvore B+.
	 */
	public BPlusTree(int m) {
		this.m = m;
		this.raiz = null;
                this.nComparacoes = 0;
                this.nTrocas = 0;
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
