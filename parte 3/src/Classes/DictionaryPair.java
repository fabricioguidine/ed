package Classes;

        /**
	 * Essa classe representa um dictionary pair para ser usado na BPlusTree a fim de simplificar 
         * algumas partes da implementação. 
         * Implementa a comparação entre os registros a fim de facilitar uma posterior ordenação.
         * @author Fabrício Guidine, Débora Duarte, Walkíria Garcia
         * @version 1.0
	 */
	public class DictionaryPair implements Comparable<DictionaryPair> {
		int chave;
		Registro valor;

		/**
		 * Construtor da classe
		 * @param chave: Inetiro que é a chave do par chave-valor
		 * @param valor: Registro que é o valor do par chave-valor
                 * @see Registro
		 */
		public DictionaryPair(int chave, Registro valor) {
			this.chave = chave;
			this.valor = valor;
		}

		/**
                 * Implementa a comparação entre as chaves.
		 * @param o DictionaryPair para comparação.
		 * @return Retorna 0 se as chaves são iguais. Retorna 1 se a chave comparada é maior. 
		 */
		@Override
		public int compareTo(DictionaryPair o) {
			if (chave == o.chave) { return 0; }
			else if (chave > o.chave) { return 1; }
			else { return -1; }
		}
	}
