package banco;

import java.awt.BorderLayout; 
import java.awt.TextArea;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFrame;

public class ConsoleWindow {
	private TextArea txtArea;
	private JFrame jFrame;
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy  hh:mm:ss");
	private boolean isDebugEnabled;
	
	public ConsoleWindow(){
	jFrame = new JFrame();
    	jFrame.setSize(450, 100);
    	jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	jFrame.addWindowListener(new WindowAdapter() {    		 
            public void windowClosing(WindowEvent e) {
            	System.exit(0);
            }
        });

    	txtArea = new TextArea();
    	txtArea.setEditable(false);
    	
    	jFrame.getContentPane().add(txtArea,BorderLayout.CENTER);    	
    	jFrame.setLocationRelativeTo(null);
    	jFrame.pack();
    	jFrame.setVisible(false);
	}
	
	public void hide() {
		jFrame.setVisible(false);
	}
	
	public void setDebugEnabled(boolean value){
		isDebugEnabled = value;
	}
	
	public boolean getDebugEnabled(){
		return isDebugEnabled;
	}
	
	/**
	 * showDebugBox
	 *     creates a debug box
	 * @author nta-ifce
	 */
	public void showConsoleWindow() {		
    	jFrame.setVisible(true);		
	}
	
	public void showConsoleWindow(boolean value){
		jFrame.setVisible(value);
	}
	
	public void write(String text){
		if (getDebugEnabled() && txtArea != null){
			txtArea.append("[" + dateFormatter.format(Calendar.getInstance().getTime()) + "]  " + text);
		}
	}
		
	public void writeln(String text){
		if (getDebugEnabled() && txtArea != null){
			txtArea.append("\n[" + dateFormatter.format(Calendar.getInstance().getTime()) + "]  " + text);
		}
	}
		
	public void writeln(){
		txtArea.append("\n");		
	}
	
	public void append(String text){
		txtArea.append(text);
	}
	
	public void writeError(String text){
		writeln(text);
		writeln("Aplica\u00E7\u00E3o ser\u00e1 encerrada em 5 segundos...");
		Thread shutdownThread = new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		shutdownThread.start();
	}
}