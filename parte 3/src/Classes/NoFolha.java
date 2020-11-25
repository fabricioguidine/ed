package Classes;

import java.util.Arrays;

        /**
	 * Essa classe representa os Nos Folha da árvore B+. O No Folha não tem filhos.
         * @author Fabrício Guidine, Débora Duarte, Walkíria Garcia
         * @version 1.0
         */
	public class NoFolha extends No {
		int maxNumPairs;
		int minNumPairs;
		int numPairs;
		NoFolha irmaoEsquerdo;
		NoFolha irmaoDireito;
		DictionaryPair[] dictionary;

		/**
                 * Dado um inteiro como parâmetro, essa função muda o valor da chave na posição indicada para null.
		 * @param index: Inteiro que representa a posição da chave a ser alterada para null.
		 */
		public void delete(int index) {

			// Delete dictionary pair from leaf
			this.dictionary[index] = null;

			// Decrement numPairs
			numPairs--;
		}

		/**
                 * A função tenta inserir um dictionarypair no NoFolha. Se consegue, o número de pares aumenta, o 
                 * Array do tipo DictionaryPair é ordenado e a função retorna um booleano de valor true.
                 * Se a função falha, ela retorna um booleano de vaor false.
		 * @param dp: O DictionaryPair a ser inserido.
		 * @return Um booleano que indica se a função obteve sucesso ou não.
		 */
		public boolean insere(DictionaryPair dp) {
			if (this.estaCheio()) {

				/* Flow of execution goes here when numPairs == maxNumPairs */

				return false;
			} else {

				// Insert dictionary pair, increment numPairs, sort dictionary
				this.dictionary[numPairs] = dp;
				numPairs++;
				Arrays.sort(this.dictionary, 0, numPairs);

				return true;
			}
		}

		/**
                 * A função determina se o nó folha tem o número de chaves abaixo do mínimo permitido.
		 * @return um booleano de valor true se o número de chaves está abaixo do mínimo.
		 */
		public boolean isAbaixoMin() { return numPairs < minNumPairs; }

		/**
                 * A função determina se o número de chaves é igual ao número máximo de chaves permitidos.
		 * @return um booleano que indica se o nó está cheio.
		 */
		public boolean estaCheio() { return numPairs == maxNumPairs; }

		/**
                 * Esse método verifica se o NoFolha pode "dar" um de seus dictionaryspairs, ou uma de seus valores, para um nó
                 * sem o número suficiente de nós. Ele só está apto a isso se o número de valores está acima do mínimo.
		 * @return um booleano indicando se o nó pode dar um de deus dictionaryspairs.
		 */
		public boolean podeCeder() { return numPairs > minNumPairs; }

		/**
                 * A função determina se o NoFolha pode ser unido com outro nó, isso só é
                 * possível se o nó possui o número mínimo de filhos .
		 * @return um booleano que indica se a união com o NoInterno é possível.
		 */
		public boolean podeUnir() {
			return numPairs == minNumPairs;
		}

		/**
		 * Construtor da classe.
		 * @param m: Inteiro que é a ordem da árvore B+.
		 * @param dp: primeiro DictionaryPair a ser inserido em um novo nó.
		 */
		public NoFolha(int m, DictionaryPair dp) {
			this.maxNumPairs = m - 1;
			this.minNumPairs = (int)(Math.ceil(m/2) - 1);
			this.dictionary = new DictionaryPair[m];
			this.numPairs = 0;
			this.insere(dp);
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
		 * Construtor da classe.
		 * @param dps: Array de objetos do tipo DictionaryPair para serem inseridos em um novo NoFolha.
		 * @param m: Inteiro que é ordem da árvore B+.
		 * @param pai: pai do novo NoFolha criado.
		 */
		public NoFolha(int m, DictionaryPair[] dps, NoInterno pai) {
			this.maxNumPairs = m - 1;
			this.minNumPairs = (int)(Math.ceil(m/2) - 1);
			this.dictionary = dps;
			this.numPairs = busca(dps);
			this.pai = pai;
		}
	}