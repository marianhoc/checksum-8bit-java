/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes2;


import java.util.Random;

/**
 *
 * @author copes
 */
public class Mensagem {

    private static long seed = 123456L;

    public static String checkSum(String mensagem){ // mensagem de 0´s e 1´s de tamanho multiplo de 8
        
        String result = new String("00000000");              
        int indice = 0;
        
        while(indice < mensagem.length()){  // enquanto o indice for menor ao tamanho da mensagem
                                            // vou percorrer a mensagem pegando um byte 
            result = addingBytes(result, mensagem.substring(indice, indice + 8 ));
            indice = indice + 8;
        }

        return invertBits(result);
        //System.out.println("checksum = " + result);
    }
    
    
    /**
     * 
     * @param a 
     * byte(sequencia de 8 caracteres 0´s ou 1´s)
     * @param b 
     * byte 
     * @return 
     * A soma dos bytes 'a' e 'b'
     */
    private static String addingBytes(String a, String b){
            StringBuilder partialResult = new StringBuilder("00000000");
            int position = 7;
            int carry = 0;
            
            while(position >-1){
                switch( carry +     // soma dos bits na mesma posicao em A e B e do carry
                        Character.getNumericValue(a.charAt(position)) + 
                        Character.getNumericValue(b.charAt(position))){
                    //case 0:   se a soma dos 3 dados for 0 nada deve ser feito
                        //      a variavel carry ja vai ter valor 0     
                        //break;
                    case 1:
                        partialResult.setCharAt(position, '1');
                        carry = 0;                        
                        break;
                    case 2:
                        partialResult.setCharAt(position, '0');
                        carry = 1;
                        break;
                    case 3:
                        partialResult.setCharAt(position, '1');
                        carry = 1;
                        break;                        
                }
                position--;
         
            }
            if (carry == 1){        //se no final do soma tem overflow
                position = 7;       //volto pra posicao inicial pra adicionar o valor
                while(carry == 1){  
                    if (position == -1){    
                        position = 7;   
                    }                    
                    if(partialResult.charAt(position) == '1'){
                        partialResult.setCharAt(position, '0');
                        position--;
                    }else{
                        partialResult.setCharAt(position, '1');
                        carry = 0;
                    }                                                                       
                }
            }
            
        return partialResult.toString();
    }  
    
    /**
     * 
     * @param input
     * recebe uma String de 0´s e 1´s
     * @return 
     * retorna uma String de mesmo tamanho que o input tendo como valor o complemento a 1 dos bits no input
     */
    private static String invertBits(String input){
        StringBuffer output = new StringBuffer();
        
        for (char c: input.toCharArray()){
            if(c == '1')output.append('0');
            if(c == '0')output.append('1');            
        }              
        
        return output.toString();                    
    }
    
    /**
     * A função de geração de mensagens aleatórias recebe como argumento um tamanho, em bytes,
     *      da mensagem a ser gerada. A implementação utiliza uma função escolhida para 
     *      geração de números pseudo-aleatórios para a obtenção dos valores dos bytes da mensagem resultante
     * @param bytes
     * tamanho, em bytes, da mensagem a ser gerada.
     * @return 
     * String, sequencia de 0´se 1´s
     * 
     */ 
    public static String gerarMensagemDeTamanho(int bytes){
        StringBuffer mensagem = new StringBuffer();
        
        for (int i=0; i<bytes*8 ; i++){
            if(Math.random()>0.5){
            mensagem.append('0');    
            }
            else{
                mensagem.append('1');               
            }

        }
        
        System.out.println("mensagem aleatoria = \t" + mensagem);
        System.out.println("mensagem com error  = \t" + inserirErroAleatorio(mensagem.toString(), mensagem.length()/8, 0.2));
        return mensagem.toString();
    }

    /**
     * A função vai inserir erros no bits aleatoriamente
     * para garantir que a mensagem final seja diferente da mensagem original. 
     * 
     * @param mensagemInput sequência de bytes
     * @param tamanhoMensagem tamanho da mensagem em bytes 
     * @param probabilidade probabilidade p de corrupção de bits 
     * @return a mensagem original com bits potencialmente alterados
     */
    public static String inserirErroAleatorio(String mensagemInput, int tamanhoMensagem, double probabilidade) {

        StringBuffer output = new StringBuffer(mensagemInput);
        Boolean changed = false;
        Random random = new Random(seed);
       
        while(!changed){
            for (int i = 0; i < tamanhoMensagem; i++){
                if (random.nextDouble() <= probabilidade) {
                    changed = true;
                    if (output.charAt(i) == '1'){
                        output.setCharAt(i, '0');
                    }else{
                        output.setCharAt(i, '1');
                    }
                }
            }
        }

        return output.toString();
    }
}
    
            


