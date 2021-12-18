package practica23;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.JFrame;

public class TresEnLineaFrame extends JFrame {
	private final int ANCHO = 400;
	private final int ALTO = 400;
	
	private JButton c1,c2,c3,c4,c5,c6,c7,c8,c9;
	private HashMap<JButton,Integer> botones = new HashMap<JButton,Integer>();
	
	public TresEnLineaFrame() {
		setTitle("Tres en Linea");
		setSize(ANCHO,ALTO);
		setLayout(new GridLayout(3,3));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContents();
		setVisible(true);
	
	}

	private void createContents() {
		Listener listener  = new Listener();
		
		c1 = new JButton();
		c2 = new JButton();
		c3 = new JButton();
		c4 = new JButton();
		c5 = new JButton();
		c6 = new JButton();
		c7 = new JButton();
		c8 = new JButton();
		c9 = new JButton();
		inicalizarBotones();
		
		add(c1);
		add(c2);
		add(c3);
		add(c4);
		add(c5);
		add(c6);
		add(c7);
		add(c8);
		add(c9);
		
		c1.addActionListener(listener);
		c2.addActionListener(listener);
		c3.addActionListener(listener);
		c4.addActionListener(listener);
		c5.addActionListener(listener);
		c6.addActionListener(listener);
		c7.addActionListener(listener);
		c8.addActionListener(listener);
		c9.addActionListener(listener);
		
	}
	public void inicalizarBotones() {
		botones.put(c1,0);
		botones.put(c2,1);
		botones.put(c3,2);
		botones.put(c4,10);
		botones.put(c5,11);
		botones.put(c6,12);
		botones.put(c7,20);
		botones.put(c8,21);
		botones.put(c9,22);
	}
	private class Listener implements ActionListener {
		private String [][] casillas = new String [3][3];
		private String [] valores = {"X","O"} ;
		private int valor = 0;
		private ArrayList<Integer> posiciones;
		private String ganador=null;
		
		public void actionPerformed(ActionEvent e) {
			JButton c = (JButton)e.getSource();
			llenarCasilla(c);
			
			if(casilleroLlenos()>=5) {
				posiciones = verificarTresEnlinea(c);
				if(posiciones.size()==3) {
					ganador = c.getText();
					if(ganador.equals("X")) {
						pintarTresEnLinea(posiciones,Color.YELLOW);
					}
					else {
						pintarTresEnLinea(posiciones,Color.GREEN);
					}
					JOptionPane.showMessageDialog(null, "Juego Terminado GANA "+ganador);
					//salir de la ventana 
					System.exit(0);
				}
			}
			if(casilleroLlenos()==9 && ganador==null) {
				JOptionPane.showMessageDialog(null, "Juego Terminado EMPATE ");
				// salir de la ventana 
				System.exit(0);
			}

		}
		public void llenarCasilla(JButton button) {
			int fila,col;
			fila = botones.get(button)/10;
			col = botones.get(button)%10;
			if(casillas[fila][col]==null) {
				casillas[fila][col]=valores[valor];
				button.setText(valores[valor]);
				valor= (valor+1)%2;
			}
			else {
				JOptionPane.showMessageDialog(null, "el casillero esta ocupado");
			}
		}
		public ArrayList<Integer> verificarTresEnlinea(JButton button) {
			
			int fila = botones.get(button)/10;
			int col = botones.get(button)%10;
			ArrayList<Integer> posiciones;
			
			posiciones = verificarFilaColumna(fila,col);
			if(posiciones.size()<2){
				if((fila ==0 & col==0) || (fila==2 &col==2)) {
					posiciones = verificarDiagonal(button,1);
				}
				else if((fila ==0 & col==2) || (fila==2 &col==0)) {
					posiciones = verificarDiagonal(button,2);
				}
				else if(fila ==1 & col==1) {
					posiciones = verificarCentro(button);
				}
			}
			posiciones.add(botones.get(button));
			
			return posiciones;
		}
		public ArrayList<Integer> verificarFilaColumna(int fila,int col) {
	
			ArrayList<Integer> posiciones = new ArrayList<Integer>();
			for(int i=1;i<=2;i++) {
				int c=(col+i)%3;
				if((casillas[fila][c]!=null) && (casillas[fila][c].equals(casillas[fila][col]))) {
					posiciones.add(fila*10+c);
				}
			}
			if(posiciones.size()<2) {
				posiciones.clear();//
				for(int i=1;i<=2;i++) {
					int f=(fila+i)%3;
					if((casillas[f][col]!=null) && (casillas[f][col].equals(casillas[fila][col]))) {
						posiciones.add(f*10+col);
					}
				}
			}
			return posiciones;	
		}
		public ArrayList<Integer> verificarDiagonal(JButton button,int op) {
			int fila = botones.get(button)/10;
			int col = botones.get(button)%10;
			ArrayList<Integer> posiciones = new ArrayList<Integer>();
			
			//op 1= izq 2 =derecha
			int c=col;
			for(int i=1;i<=2;i++) {
				int f=(fila+i)%3;
				c=(c+op)%3;
				if((casillas[f][c]!=null) && (casillas[f][c].equals(casillas[fila][col]))) {
					posiciones.add(f*10+c);
				}
			}
			return posiciones;	
			
		}
		public ArrayList<Integer> verificarCentro(JButton button) {
			ArrayList<Integer> posiciones = new ArrayList<Integer>();
			posiciones = verificarDiagonal(button,1);
			if(posiciones.size()<3) {
				posiciones.clear();
				posiciones = verificarDiagonal(button,2);
			}
			return posiciones;	
		}
		public int casilleroLlenos() {
			int llenos = 0;
			for(int i=0;i<casillas.length;i++) {
				for(int j=0;j<casillas[i].length;j++) {
					if(casillas[i][j]!=null) {
						llenos++;
					}
				}
			}
			return llenos;
		}
		public void pintarTresEnLinea(ArrayList<Integer>posiciones,Color c) {
			for(int pos:posiciones ) {
				for(JButton b: botones.keySet()) {
					if(botones.get(b)==pos) {
						b.setBackground(c);
					}
				}
			}
			
		}	
	}
	
	public static void main(String[] args) {
		TresEnLineaFrame michi = new TresEnLineaFrame ();
		
	}

}
