/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.List;
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
    private List<Cliente> clientes;

    public Caixa(int id, Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem) {
        this.id = id;
        this.clientesSem = clientesSem;
        this.caixasSem   = caixasSem;
        this.mutexSem    = mutexSem;
    }

    public void setClientes(List clientes) {
        this.clientes = clientes;
    }

    public void setSemaphore(Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem){
        this.clientesSem = clientesSem;
        this.caixasSem = caixasSem;
        this.mutexSem = mutexSem;
    }
    
    @Override
    public void run() {
        System.out.println("caixa criado" + this.id);
        while(true){
            try {
                clientesSem.acquire();
                System.out.println("caixa" + this.id + " iniciou atendimento");
                mutexSem.acquire();
                //libera o primeiro cliente da fila.
                Cliente cliente = clientes.get(0);
                clientes.remove(0);
                mutexSem.release();
                long tempoInicio = System.currentTimeMillis();
                while(System.currentTimeMillis() - tempoInicio < cliente.getTempoAtendimento()){
                    System.out.println("Caixa " + this.id + "atendendo " + cliente.getId());
                }
                caixasSem.release();
                //
            } catch (InterruptedException ex) {
                Logger.getLogger(Caixa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
