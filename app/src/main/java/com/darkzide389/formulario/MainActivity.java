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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout formulario;
    private View sombra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView btnAbrirFormulario = (TextView)findViewById(R.id.btnAbrirFormulario);
        btnAbrirFormulario.setOnClickListener(this);

        formulario = (FrameLayout)findViewById(R.id.formulario);
        sombra = findViewById(R.id.sombra);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAbrirFormulario:
                formulario.removeAllViews();
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

                View v = inflater.inflate(R.layout.agregar_negocio, null, false);
                formulario.addView(v, 0);

                int colorFrom = ContextCompat.getColor(MainActivity.this, R.color.transparente);
                int colorTo = ContextCompat.getColor(MainActivity.this, R.color.negroTransparente);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(500); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        sombra.setBackgroundColor((int) animator.getAnimatedValue());
                    }

                });
                colorAnimation.start();

                Snippets.expand(formulario, 1);

                List<ObjetoPOJO> listaObjetos = cargarFormulario();

                final TextView btnCerrar = (TextView)v.findViewById(R.id.btnCerrar);
                TextView btnCrear = (TextView) v.findViewById(R.id.btnCrear);
                TextView titulo = (TextView) v.findViewById(R.id.titulo);

                titulo.setText(R.string.agregar_negocio);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnCerrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                InputMethodManager inputManager = (InputMethodManager)
                                        getSystemService(Context.INPUT_METHOD_SERVICE);

                                if (getCurrentFocus() != null) {
                                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                            InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                                Snippets.collapse(formulario, 1);
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
                            }
                        });
                    }
                });

                btnCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        if (getCurrentFocus() != null) {
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        Snippets.collapse(formulario, 1);
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
                    }
                });
                break;
        }
    }

    private List<ObjetoPOJO> cargarFormulario(){

        return new ArrayList<>();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
