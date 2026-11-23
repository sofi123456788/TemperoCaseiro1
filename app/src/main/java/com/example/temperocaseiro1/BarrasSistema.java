package com.example.temperocaseiro1;

import android.app.Activity;
import android.graphics.Color;
import androidx.core.view.WindowCompat;

/** Mantém o relógio e os ícones da barra superior legíveis sobre o fundo claro. */
public final class BarrasSistema {
    private BarrasSistema() { }

    public static void aplicar(Activity activity) {
        activity.getWindow().setStatusBarColor(Color.parseColor("#F3E4CD"));
        WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView())
                .setAppearanceLightStatusBars(true);
    }
}
