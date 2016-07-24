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
public class Caixa implements Runnable{

    private int id;
    private Semaphore clientesSem;
    private Semaphore caixasSem;
    private Semaphore mutexSem;

    public Caixa(int id, Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem) {
        this.id = id;
        this.clientesSem = clientesSem;
        this.caixasSem = caixasSem;
        this.mutexSem = mutexSem;
    }

    @Override
    public void run() {
        while(true){
            try {
                clientesSem.acquire();
                mutexSem.acquire();
                //
                caixasSem.release();
                mutexSem.release();
                //
            } catch (InterruptedException ex) {
                Logger.getLogger(Caixa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
