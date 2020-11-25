package Classes;

        /**
	 * Essa classe representa os nós internos da árvore B+. 
         * Armazena somente as chaves, excluindo os valores e os dictionarypairs.
         * @author Fabrício Guidine, Débora Duarte, Walkíria Garcia
         * @version 1.0
	 */
	class NoInterno extends No {
		int numMaximo;
		int numMinimo;
		int num;
		NoInterno irmaoEsquerdo;
		NoInterno irmaoDireito;
		Integer[] chaves;
		No[] filhos;

		/**
		 * Acrescenta um ponteiro no final do Array filhos, pode ser um NoInterno ou um NoFolha.
		 * @param no: No a ser acrescentado no final do Array filhos.
		 */
		void addFilho(No no) {
			this.filhos[num] = no;
			this.num++;
		}

		/**
                 * Dado um nó como parâmetro, a função retorna a posição desse no dentro do Array filhos. 
                 * Se nó não pode ser encontrado, a função retorna -1.
		 * @param no: No a ser encontrado.
		 * @return a posição do nó no Array filhos.
		 */
		int getIndexNo(No no) {
			for (int i = 0; i < filhos.length; i++) {
				if (filhos[i] == no) { return i; }
			}
			return -1;
		}

		/**
		 * Dados um nó e um inteiro como parâmetros, a função insere o nó na posição indicada pelo inteiro no
                 * Array filhos.
		 * @param no: No que é o nó s ser inserido.
		 * @param index: Inteiro que é a posição onde o nó vai ser inserido.
		 */
		void insereFilho(No no, int index) {
			for (int i = num - 1; i >= index ;i--) {
				filhos[i + 1] = filhos[i];
			}
			this.filhos[index] = no;
			this.num++;
		}

		/**
		 * Verifica o número de nós está abaixo do mínimo permitido.
		 * @return um booleano indicando se o número de nós está abaixo do mínimo.
		 */
		public boolean isAbaixoMin() {
			return this.num < this.numMinimo;
		}

		/**
                 * Esse método verifica se o NoInterno pode "dar" um de seus dictionaryspairs, ou uma de seus valores, para um nó
                 * sem o número suficiente de nós. Ele só está apto a isso se o número de valores está acima do mínimo.
		 * @return um booleano indicando se o nó pode dar um de deus dictionaryspairs.
		 */
		boolean podeCeder() { return this.num > this.numMinimo; }

		/**
                 * A função determina se o NoInterno pode ser unido com outro nó, isso só é
                 * possível se o nó possui o número mínimo de filhos .
		 * @return um booleano que indica se a união com o NoInterno é possível.
		 */
		boolean podeUnir() { return this.num == this.numMinimo; }

		/**
                 * Verifica se ocorreu um overflow, se o número de nós está acima do permintido.
		 * @return um booleano que indica se ocorreu um overflow.
		 */
		boolean estaCheio() {
			return this.num == numMaximo + 1;
		}

		/**
                 * Insere um no no início do Array filhos.
		 * @param no: o No a ser inserido no início do Array filhos.
		 */
		void insereFilhoInicio(No no) {
			for (int i = num - 1; i >= 0 ;i--) {
				filhos[i + 1] = filhos[i];
			}
			this.filhos[0] = no;
			this.num++;
		}

		/**
                 * Dado um inteiro como parâmetro, essa função muda o valor da chave na posição indicada para null.
                 * É usada para o no pai de um nó folha deficiente em processo de união.
		 * @param index: Inteiro que representa a posição da chave a ser alterada para null.
		 */
		public void removeChave(int index) { this.chaves[index] = null; }

		/**
                 * Dado um inteiro como parâmetro, a função altera o no na posição indicada para null e também
                 * reduz em menos um o número de nos do NoInterno. Ou seja, remove o nó.
		 * @param index: the location within filhos to be set to null
		 */
		public void remove(int index) {
			this.filhos[index] = null;
			this.num--;
		}

		/**
                 * Remove um no dos filhos do NoInterno. O nó a ser removido é passado como parâmetro.
		 * @param no: O No a ser removido.
		 */
		void remove(No no) {
			for (int i = 0; i < filhos.length; i++) {
				if (filhos[i] == no) { this.filhos[i] = null; }
			}
			this.num--;
		}

		/**
		 * Construtor da classe
		 * @param m: Inteiro que representa o número máximo permitido de nós no NoInterno.
		 * @param chaves: A lista de chaves Inteiras a serem inicializadas.
		 */
		public NoInterno(int m, Integer[] chaves) {
			this.numMaximo = m;
			this.numMinimo = (int)Math.ceil(m/2.0);
			this.num = 0;
			this.chaves = chaves;
			this.filhos = new No[this.numMaximo+1];
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
                * Dado um Array do tipo No essa função faz uma busca nesse array e retorna a posição do primeiro emento null encontrado.
                * Se nenhum elento null é encontrado a função vai retornar o valor -1.
                * @param nos: Array di tipo  No
                * @return Inteiro que representa a posição do primeiro null encontrado,  retorna -1 se nnehum valor null é encontrado.
                * @see No
                */
               public int busca(No[] nos) {
                       for (int i = 0; i <  nos.length; i++) {
                               if (nos[i] == null) { return i; }
                       }
                       return -1;
               }

		/**
		 * Construtor da classe.
		 * @param m: número máximo de chaves no  NoInterno.
		 * @param chaves: Array de chaves Inteiras com as quais o nó é inicialzado.
		 * @param nos: A lista de Nos com os quais o NoInterno é inicializado.
		 */
		public NoInterno(int m, Integer[] chaves, No[] nos) {
			this.numMaximo = m;
			this.numMinimo = (int)Math.ceil(m/2.0);
			this.num = busca(nos);
			this.chaves = chaves;
			this.filhos = nos;
		}
	}