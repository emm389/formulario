package com.darkzide389.formulario;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.darkzide389.formulario.model.ObjetoPOJO;
import com.darkzide389.formulario.util.Snippets;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Emmanuel Gonzalez
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout formulario;
    private View sombra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            En el xml de MainActivity hay dos elementos con id sombra y formulario,
            que son los que nos ayudaran a darle las animaciones correspondientes e
            inflar el xml customizado dentro del FrameLayout
         */

        //Boton para abrir el formulario
        TextView btnAbrirFormulario = (TextView)findViewById(R.id.btnAbrirFormulario);

        // Le seteamos el OnClickListener que implementamos en la clase
        btnAbrirFormulario.setOnClickListener(this);

        //FrameLayout donde inflaremos el xml
        formulario = (FrameLayout)findViewById(R.id.formulario);
        //View para mostrar la sombra
        sombra = findViewById(R.id.sombra);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAbrirFormulario:
                // Este metodo limpia nuestro FrameLayout de xml's inflados anteriormente
                formulario.removeAllViews();

                // Creamos una instancia de LayoutInflater
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

                // Aqui es donde guardamos en una variable tdo nuestro xml para poder
                // accesar a los id's
                View v = inflater.inflate(R.layout.agregar_negocio, null, false);
                // Una vez inflado nuestro xml se lo pasamos a nuestro FrameLayout para que
                // se muestre
                formulario.addView(v, 0);

                //********* INICIO DE ANIMACION PARA LA SOMBRA ***********
                int colorFrom = ContextCompat.getColor(MainActivity.this, R.color.transparente);
                int colorTo = ContextCompat.getColor(MainActivity.this, R.color.negroTransparente);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(500); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        // Aqui le vamos seteando la sombra gradualmente
                        sombra.setBackgroundColor((int) animator.getAnimatedValue());
                    }

                });
                colorAnimation.start();
                //************ FIN DE ANIMACION PARA LA SOMRA
                // Ahora realizamos la animacion de nuestro FrameLayout
                Snippets.expand(formulario, 1);

                // Este array es sugerido, si se va a hacer una peticion al servidor
                // para obtener datos y mostrarlos en nuestro xml inflado
                List<ObjetoPOJO> listaObjetos = cargarFormulario();

                // Obtenemos las referencias de todos los elementos que tengamos en el xml
                final TextView btnCerrar = (TextView) v.findViewById(R.id.btnCerrar);
                TextView btnCrear = (TextView) v.findViewById(R.id.btnCrear);
                TextView titulo = (TextView) v.findViewById(R.id.titulo);

                titulo.setText(R.string.agregar_negocio);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnCerrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //************ BLOQUE PARA ESCONDER EL TECLADO CUANDO LA VISTA SEA CERRADA **********
                                InputMethodManager inputManager = (InputMethodManager)
                                        getSystemService(Context.INPUT_METHOD_SERVICE);

                                if (getCurrentFocus() != null) {
                                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                            InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                                //************ FIN DE BLOQUE **************

                                // Escondemos nuestro FrameLayout
                                Snippets.collapse(formulario, 1);

                                //***************** INICIO DE ANIMACION PARA QUITAR LA SOMBRA **************
                                int colorFrom = ContextCompat.getColor(MainActivity.this, R.color.negroTransparente);
                                int colorTo = ContextCompat.getColor(MainActivity.this, R.color.transparente);
                                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                                colorAnimation.setDuration(650); // milliseconds
                                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animator) {
                                        sombra.setBackgroundColor((int) animator.getAnimatedValue());
                                    }

                                });
                                colorAnimation.start();
                                //****************** FIN DE ANIMACION
                            }
                        });
                    }
                });

                btnCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //************** INICIO DE BLOQUE PARA OCULTAR TECLADO ***************
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        if (getCurrentFocus() != null) {
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        //************** FIN DE BLOQUE *****************

                        // Escondemos nuestro FrameLayout
                        Snippets.collapse(formulario, 1);

                        //************** INICIO DE ANIMACION DE SOMBRA ****************
                        int colorFrom = ContextCompat.getColor(MainActivity.this, R.color.negroTransparente);
                        int colorTo = ContextCompat.getColor(MainActivity.this, R.color.transparente);
                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                        colorAnimation.setDuration(650); // milliseconds
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                sombra.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorAnimation.start();
                        //************** FIN DE ANIMACION DE SOMBRA *****************
                    }
                });
                break;
        }
    }

    // Metodo donde haremos nuestro webservice para llenar el xml inflado
    private List<ObjetoPOJO> cargarFormulario(){

        return new ArrayList<>();
    }

    //Metodo necesario para mostrar los iconos del xml inflado (Libreria Android Iconics,
    //ver build.gradle para ver las librerias importadas(Iconics y CardView))
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
