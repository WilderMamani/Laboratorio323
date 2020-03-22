package com.example.laboratorio2_4enlinea;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
public class Rectangulo {
	/**
	 * 2
	 * /\
	 * / \
	 * / \
	 * / \
	 * /________\
	 * 0 1
	 */
	/* Coordenadas cartesianas (x, y) */
	private float vertices[] = new float [] {
			-2, -2, // 0
			 2, -2, // 1
			 2,  2, // 2
			-2,  2
	};
	byte maxColor = (byte) 255;
	private byte colores [] = {
			0, 0, maxColor, 1,
			0, 0, maxColor, 1,
			maxColor, 0, 0, 1,
			maxColor, 0, 0, 1
	};
	private short indices[] = new short []{
		0, 1, 2,	//T1
		0, 1, 3		//T2
	};
	FloatBuffer bufVertices;
	
	ByteBuffer bufColores;
	
	ShortBuffer bufIndices;
	
	public Rectangulo() {
		/* Lee los vértices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufVertices.put(vertices);
		bufVertices.rewind(); // puntero al principio del buffer
		
		/*Lee los colores*/
		bufColores = ByteBuffer.allocateDirect(colores.length); // Convierte de byte a float
		bufColores.put(colores);
		bufColores.position(0);
		
		/* Lee los indices*/
		bufByte = ByteBuffer.allocateDirect(indices.length * 2);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte nativo
		bufIndices = bufByte.asShortBuffer(); // Convierte de byte a float
		bufIndices.put(indices);
		bufIndices.rewind(); // puntero al principio del buffer
		
	}
	
	public void dibuja(GL10 gl) {

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);
		
		gl.glColor4f(1, 0, 0, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		//gl.glDrawElements(GL10.GL_TRIANGLE_FAN, indices.length, GL10.GL_UNSIGNED_SHORT, bufIndices);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}