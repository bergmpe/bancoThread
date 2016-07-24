/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author padrao
 */
public class Cliente implements Runnable{

    private int id;
    private long tempoAtendimento;
    private int senha;
    private Semaphore clientesSem;
    private Semaphore caixasSem;
    private Semaphore mutexSem;

    public Cliente(int id, long tempoAtendimento, int senha) {
        this.id = id;
        this.tempoAtendimento = tempoAtendimento;
        this.senha = senha;
    }
    
    
    @Override
    public void run() {
        try {
            long tempoInicio = System.currentTimeMillis();
            caixasSem.acquire();
            clientesSem.release();
            //
            while(System.currentTimeMillis() - tempoInicio == tempoAtendimento){}            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
