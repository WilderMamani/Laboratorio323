package com.example.laboratorio2_4enlinea;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class Renderiza extends GLSurfaceView implements Renderer {
	
	private Rectangulo rectangulo;
	private Circulo [][]fichas;
	private int [][]colores;
	private int columnas[];
	private Tablero tablero;
	Context contexto;
	int alto, ancho;
	boolean cambioTurno = true;
	boolean victoria = false;
	public Renderiza(Context context) {
		super(context);
		this.contexto = context;
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		columnas = new int[7];
		tablero = new Tablero();
		colores = new int[7][6];
		fichas = new Circulo[7][6];
		for(int i = 0; i < 7;i++)
			for(int j = 0; j < 6;j++)
				fichas[i][j] = new Circulo(0.5f, 20, true);
		for(int i = 0; i < 7;i++)
			for(int j = 0; j < 6;j++)
				colores[i][j] = 0;
		for(int i = 0; i < 7;i++)
			columnas[i] = 0;
		gl.glClearColor(0, 0, 0, 0);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glColor4f(1, 1, 1, 0);
		tablero.dibuja(gl); 
		for(int i = 0; i < 7;i++){
			for(int j = 0; j < 6;j++){
				gl.glLoadIdentity();
				if(colores[i][j] == 0)
					gl.glColor4f(0, 0, 0, 0);
				if(colores[i][j] == 1)
					gl.glColor4f(1, 0, 0, 0);
				if(colores[i][j] == 2)
					gl.glColor4f(0, 0, 1, 0);
				float indiceX = 1f * (i+1) + 0.5f;
				float indiceY = 1f * (j+1) + 4;
				gl.glTranslatef(indiceX, indiceY, 0);
				fichas[i][j].dibuja(gl);
			}
		}
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		ancho = width;
		alto = height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0.2f, 8.7f, 1, 13);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e){
		float x = e.getX();
		float y = e.getY();
		float posx, posy;
		if (e.getAction() == MotionEvent.ACTION_DOWN && !victoria) {
			posx = (x * 9/ (float) ancho);
			posy = (14 - y * 14 / (float) alto);
			
			if (puntoEstaDentroDelRectangulo(posx, posy, 1, 4, 7, 8)){
				int fila = 0;
				int columna = (int)posx-1;
				fila = columnas[columna];
				if(fila <6){
					columnas[columna]++;
					if(cambioTurno)
						colores[columna][fila] = 1;
					else
						colores[columna][fila] = 2;
					requestRender();
					cambioTurno = !cambioTurno;
				}
				revisarVictoria(fila, columna, cambioTurno?2:1);
			}
//			Toast.makeText(contexto, "PRESIONO "+ posx + " " + posy, Toast.LENGTH_SHORT).show();
			requestRender();
		}
		return true;
	}
	private boolean puntoEstaDentroDelRectangulo(float posx, float posy, int x, int y, int ancho, int alto){	
		return (x < posx && posx < x + ancho && 
				y < posy && posy < y + alto);
	}
	public void reiniciar(){
		cambioTurno = true;
		victoria = false;
		for(int i = 0; i < 7;i++)
			for(int j = 0; j < 6;j++)
				fichas[i][j] = new Circulo(0.5f, 20, true);
		for(int i = 0; i < 7;i++)
			for(int j = 0; j < 6;j++)
				colores[i][j] = 0;
		for(int i = 0; i < 7;i++)
			columnas[i] = 0;
		requestRender();
	}
	public void revisarVictoria(int fila, int columna, int color){
		horizontal(fila, color);
		vertical(columna, color);
		diagonal(fila, columna, color);
	}
	public void horizontal(int x, int color){
		int cont = 0;
		for(int i = 0;i < 7; i++){
			
			if(colores[i][x] == color){
				cont++;
				if(cont == 4){
					Toast.makeText(contexto, cambioTurno?"GANA EL AZUL":"GANA EL ROJO", Toast.LENGTH_SHORT).show();
					victoria = true;
					break;
				}
			}
			else
				cont = 0;
		}
	}
	public void vertical(int x, int color){
		int cont = 0;
		for(int i = 0;i < 6; i++){
			if(colores[x][i] == color){
				cont++;
				if(cont == 4){
					Toast.makeText(contexto, cambioTurno?"GANA EL AZUL":"GANA EL ROJO", Toast.LENGTH_SHORT).show();
					victoria = true;
					break;
				}
			}
			else
				cont = 0;
		}
	}
	public void diagonal(int fila, int columna, int color){
		diagonalIzq(fila, columna, color);
		diagonalDer(fila, columna, color);
	}
	public void diagonalIzq(int fila, int columna, int color){
		while(fila >0 && columna >0){
			fila--;columna--;
		}
		int cont = 0;
		while(fila<6&&columna<7){
			if(colores[columna][fila] == color){
				cont++;
				if(cont == 4){
					Toast.makeText(contexto, cambioTurno?"GANA EL AZUL":"GANA EL ROJO", Toast.LENGTH_SHORT).show();
					victoria = true;
					break;
				}
			}
			else
				cont = 0;
			columna++;
			fila++;
		}
	}
	public void diagonalDer(int fila, int columna, int color){
		while(fila >0 && columna <6){
			fila--;columna++;
		}
		Log.w("EEEEEERRRRORR","F: "+ fila + "C: "+ columna);
		int cont = 0;
		while(fila<6&&columna>=0){
			Log.w("EEEEEERRRRORR","F: "+ fila + "C: "+ columna);
			if(colores[columna][fila] == color){
				cont++;
				if(cont == 4){
					Toast.makeText(contexto, cambioTurno?"GANA EL AZUL":"GANA EL ROJO", Toast.LENGTH_SHORT).show();
					victoria = true;
					break;
				} 
			}
			else
				cont = 0;
			columna--;
			fila++;
		}
	}
}