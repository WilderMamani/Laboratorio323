package com.example.laboratorio2_4enlinea;
import android.R.layout;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		/*PARA LOGRAR LA INTERACCION EL LIENZO DEBE SER LA CLASE RENDERIZA*/
		
//		GLSurfaceView superficie = new GLSurfaceView(this);
		
		final GLSurfaceView superficie = new Renderiza(this);
		
//		Renderiza renderiza = new Renderiza();
//		superficie.setRenderer(renderiza);
		Button boton = new Button(this);
		LinearLayout frame = new LinearLayout(this);
		frame.setOrientation(LinearLayout.VERTICAL);
		frame.addView(boton);
		frame.addView(superficie);
		boton.setText("Reiniciar");
		boton.setPadding(0, 0, 0, 0);
		boton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Renderiza r = (Renderiza)superficie;
				r.reiniciar();
				return false;
			}
		});
		setContentView(frame);

	}
}