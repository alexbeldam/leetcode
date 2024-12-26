import java.util.Scanner;

enum ErroLista {
    INDICE_INVALIDO("PosicaoInvalidaException"),
    VAZIA("NenhumItemException"),
    SEM_ERRO("NenhumErro");
    
    String msgErro;
    
    ErroLista(String msg) {
        msgErro = msg;
    }
    
    public String getMessage() {
        return msgErro;
    }
}

class ErroListaWrapper {
    private ErroLista erro;
    
    public ErroListaWrapper() {
        this(ErroLista.SEM_ERRO);
    }
    
    public ErroListaWrapper(ErroLista erro) {
        this.erro = erro;
    }

    public ErroLista getErro() {
        return erro;
    }

    public void setErro(ErroLista erro) {
        this.erro = erro;
    }
}

/**
 * Classe que respresenta uma {@code Lista} de inteiros.
 */
class Lista {

    /**
     * Classe que representa um {@code Nó} da {@code Lista}.
     */
    private static class Node {

        /**O {@code item} que esse {@code Nó} mantém*/
        private final Integer item;

        /**Referencia ao {@code Nó} precedente a esse*/
        private Node previous;

        /**Referencia ao {@code Nó} procedente a esse*/
        private Node next;

        /**
         * Construtor para criar um {@code Nó}, com o procedente indicado
         * e que guarda o {@code item} designado  
         * 
         * @param item o item que este {@code Nó} deve manter
         * @param next o {@code Nó} procedente a este
         */
        public Node(Node next, Integer item) {
            this(item, null, next);
        }

        /**
         * Construtor para criar um {@code Nó}, com o precedente indicado
         * e que guarda o {@code item} designado  
         * 
         * @param item o item que este {@code Nó} deve manter
         * @param previous o {@code Nó} precedente a este
         */
        public Node(Integer item, Node previous) {
            this(item, previous, null);
        }

        /**
         * Construtor para criar um {@code Nó}, com o precedente e procedente
         * indicados e que guarda o {@code item} designado  
         * 
         * @param item o item que este {@code Nó} deve manter
         * @param previous o {@code Nó} precedente a este
         */
        public Node(Integer item, Node previous, Node next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    
        /**
         * @return o {@code item} que este {@code Nó} mantém
         */
        public Integer getItem() {
            return item;
        }

        /**
         * @return a referencia ao {@code Nó} próximo ao atual ou {@code null} se não houver próximo.
         */
        public Node getNext() {
            return next;
        }

        /**
         * @return a referencia ao {@code Nó} anterior ao atual ou {@code null} se não houver anterior.
         */
        public Node getPrevious() {
            return previous;
        }
    
        /**
         * Altera a referencia do próximo {@code Nó} para o indicado
         * 
         * @param next o próximo {@code Nó}
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * Altera a referencia do {@code Nó} aterior para o indicado
         * 
         * @param previous o próximo {@code Nó}
         */
        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }

    /**O primeiro {@code Nó} da {@code Lista}*/
    private Node first;

    /**O último {@code Nó} da {@code Lista}*/
    private Node last;

    /**O número de elementos na {@code Lista}*/
    private int size;

    /** 
     * Construtor para inicializar uma {@code Lista} vazia
    */
    public Lista() {
        first = last = null;
        size = 0;
    }

    /**
     * Adiciona o {@code item} ao fim da {@code Lista}
     * 
     * @param item o item
     */
    public void adicionarFim(Integer item) {
        Node no = new Node(item, last);

        if (vazia()) 
            first = no;
        else
            last.setNext(no);

        last = no;

        size++;
    }

    /**
     * Adiciona o {@code item} ao início da {@code Lista}
     * 
     * @param item o item
     */
    public void adicionarInicio(Integer item) {
        Node no = new Node(first, item);

        if (vazia())
            last = no;
        else 
            first.setPrevious(no);

        first = no;

        size++;
    }

    /**
     * Adiciona o {@code item} à posição indicada da {@code Lista}
     * 
     * @param item o item
     * @param index a posição
     * @param e objeto responsável por transmitir se 
     * houve erro, {@code ErroLista.INDICE_INVALIDO},
     * ou não, {@code ErroLista.SEM_ERRO}
     */
    public void adicionar(Integer item, int index, ErroListaWrapper e) {
        if (index < 0 || index > size) {
            e.setErro(ErroLista.INDICE_INVALIDO);

            return;
        }
        
        e.setErro(ErroLista.SEM_ERRO);

        if (index == 0) {
            adicionarInicio(item);

            return;
        }
        if (index == size) {
            adicionarFim(item);

            return;
        }

        Node current = getNode(index);
        Node previous = current.getPrevious();
        Node no = new Node(item, previous, current);

        previous.setNext(no);
        current.setPrevious(no);

        size++;
    }
    
    /**
     * Remove o {@code item} da posição 0
     * 
     * @param e objeto responsável por transmitir se 
     * houve erro, {@code ErroLista.VAZIA},
     * ou não, {@code ErroLista.SEM_ERRO}
     * @return o {@code item} que foi removido, ou {@code null}
     * se a {@code Lista} estiver vazia
     */
    public Integer removerInicio(ErroListaWrapper e) {
        if (vazia()) {
            e.setErro(ErroLista.VAZIA);

            return null;
        }

        e.setErro(ErroLista.SEM_ERRO);

        Node aux = first;
        Node next = aux.getNext();

        if (next == null)
            last = null;
        else 
            next.setPrevious(null);

        first = next;

        size--;

        return aux.getItem();
    }

    /**
     * Remove o {@code item} da posição final
     * 
     * @param e objeto responsável por transmitir se 
     * houve erro, {@code ErroLista.VAZIA},
     * ou não, {@code ErroLista.SEM_ERRO}
     * @return o {@code item} que foi removido, ou {@code null}
     * se a {@code Lista} estiver vazia
     */
    public Integer removerFim(ErroListaWrapper e) {
        if (vazia()) {
            e.setErro(ErroLista.VAZIA);

            return null;
        }

        e.setErro(ErroLista.SEM_ERRO);

        Node aux = last;
        Node previous = aux.getPrevious();

        if (previous == null)
            first = null;
        else 
            previous.setNext(null);

        last = previous;

        size--;

        return aux.getItem();
    }

    /**
     * Remove o {@code item} da posição dada
     * 
     * @param index a posição
     * @param e objeto responsável por transmitir se 
     * houve erro, {@code ErroLista.VAZIA} ou {@code ErroLista.INDICE_INVALIDO},
     * ou não, {@code ErroLista.SEM_ERRO}
     * @return o {@code item} que foi removido, ou {@code null}
     * se a {@code Lista} estiver vazia ou a posição foi inválida
     */
    public Integer remover(int index, ErroListaWrapper e) {
        if (vazia()) {
            e.setErro(ErroLista.VAZIA);

            return null;
        }
        else if (index < 0 || index >= size) {
            e.setErro(ErroLista.INDICE_INVALIDO);

            return null;
        }

        e.setErro(ErroLista.SEM_ERRO);

        if (index == 0)
            return removerInicio(e);
        if (index == size - 1)
            return removerFim(e);

        Node aux = getNode(index);
        Node previous = aux.getPrevious();
        Node next = aux.getNext();

        previous.setNext(next);
        next.setPrevious(previous);

        size--;

        return aux.getItem();
    }

    /**
     * @param index a posição
     * @return o {@code Nó} na posição index
     */
    private Node getNode(int index) {
        Node aux;

        if (index < size / 2) {
            aux = first;

            for (int i = 0; i < index; i++)
                aux = aux.getNext();
        }
        else {
            aux = last;

            for (int i = size - 1; i > index; i--)
                aux = aux.getPrevious();
        }

        return aux;
    }

    /**
     * Retorna o {@code item} na posição 0 sem remover
     * 
     * @param e objeto responsável por transmitir se 
     * houve erro, {@code ErroLista.VAZIA},
     * ou não, {@code ErroLista.SEM_ERRO}
     * @return o {@code item} na posição 0 ou {@code null}
     * se a {@code Lista} estiver vazia
     */
    public Integer getItem(ErroListaWrapper e) {
        if (vazia()) {
            e.setErro(ErroLista.VAZIA);

            return null;
        }

        e.setErro(ErroLista.SEM_ERRO);

        return first.getItem();
    }

    /**
     * Retorna o {@code item} na posição dada sem remover
     * 
     * @param index a posição
     * @param e objeto responsável por transmitir se 
     * houve erro, {@code ErroLista.VAZIA} ou {@code ErroLista.INDICE_INVALIDO},
     * ou não, {@code ErroLista.SEM_ERRO}
     * @return o {@code item} que na posição dada, ou {@code null}
     * se a {@code Lista} estiver vazia ou a posição foi inválida
     */
    public Integer getItem(int index, ErroListaWrapper e) {
        if (vazia()) {
            e.setErro(ErroLista.VAZIA);

            return null;
        }
        else if (index < 0 || index >= size) {
            e.setErro(ErroLista.INDICE_INVALIDO);

            return null;
        }

        if (index == 0)
            return getItem(e);

        e.setErro(ErroLista.SEM_ERRO);

        return getNode(index).getItem();
    }

    /**
     * @return o tamanho da {@code Lista}
     */
    public int tamanho() {
        return size;
    }

    /**
     * Retorna se o tamanho {@code Lista} é igual a 0
     * 
     * @return {@code true} se o tamanho for igual a 0
     * e {@code false} caso contrário
     */
    public boolean vazia() {
        return size == 0;
    }

    /**
     * @return a {@code Lista} em forma de vetor ou {@code null}
     * se a {@code Lista} estiver vazia
     */
    public Integer[] toArray() {
        if (vazia())
            return null;

        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++)
            array[i] = getNode(i).getItem();

        return array;
    }
}


class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Lista lista = new Lista();
        String token;
        Integer valor;
        int posicao;
        ErroListaWrapper erro = new ErroListaWrapper();
        
        token = in.next();
        while (!token.equals("Q")) {
            erro.setErro(ErroLista.SEM_ERRO);
            switch(token) {
                case "AF":
                    valor = in.nextInt();
                    lista.adicionarFim(valor);
                    break;
                case "AI":
                    valor = in.nextInt();
                    lista.adicionarInicio(valor);
                    break;                    
                case "AP":
                    posicao = in.nextInt();
                    valor = in.nextInt();
                    lista.adicionar(valor, posicao, erro);
                    if (erro.getErro() != ErroLista.SEM_ERRO)
                        System.out.println(erro.getErro().getMessage());
                    break;          
                case "RI":
                    lista.removerInicio(erro);
                    if (erro.getErro() != ErroLista.SEM_ERRO)
                        System.out.println(erro.getErro().getMessage());
                    break;
                case "RF":
                    lista.removerFim(erro);
                    if (erro.getErro() != ErroLista.SEM_ERRO)
                        System.out.println(erro.getErro().getMessage());
                    break;
                case "RP":                    
                    posicao = in.nextInt();
                    lista.remover(posicao, erro);
                    if (erro.getErro() != ErroLista.SEM_ERRO)
                        System.out.println(erro.getErro().getMessage());
                    break;
                case "G":
                    valor = lista.getItem(erro);
                    if (erro.getErro() != ErroLista.SEM_ERRO)
                        System.out.println(erro.getErro().getMessage());
                    else
                        System.out.println(valor);
                    break;
                case "GP":
                    posicao = in.nextInt();
                    valor = lista.getItem(posicao, erro);
                    if (erro.getErro() != ErroLista.SEM_ERRO)
                        System.out.println(erro.getErro().getMessage());
                    else
                        System.out.println(valor);
                    break;
                case "T":
                    System.out.println(lista.tamanho());
                    break;
                case "V":
                    System.out.println(lista.vazia());
                    break;
                case "P":
                    Integer valores[] = lista.toArray();
                    if (valores != null) {
                        for(Integer item: valores)
                            System.out.println(item);
                    }
                    break;
            }
            token = in.next();
        }   
    }
}
