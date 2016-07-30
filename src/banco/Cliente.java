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
    private Semaphore localSem;

    public Cliente(int id, long tempoAtendimento, int senha) {
        this.id = id;
        this.tempoAtendimento = tempoAtendimento;
        this.senha = senha;
        localSem = new Semaphore(0);
    }

    public Semaphore getLocalSem() {
        return localSem;
    }
    
    public void setSemaphore(Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem){
        this.clientesSem = clientesSem;
        this.caixasSem = caixasSem;
        this.mutexSem = mutexSem;
    }
   
    @Override
    public void run() {
        try {
            //localSem.acquire();
            caixasSem.acquire();
            
            //
            long tempoInicio = System.currentTimeMillis();
            while(System.currentTimeMillis() - tempoInicio < tempoAtendimento){
                System.out.println("atendendo " + this.id);
            }            
            clientesSem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
