package com.example.temperocaseiro1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class MapaApoioView extends View {

    private Paint paint;
    private Path rua;

    public MapaApoioView(Context context) {
        super(context);
        inicializar();
    }

    public MapaApoioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar();
    }

    public MapaApoioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inicializar();
    }

    private void inicializar() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rua = new Path();

        setBackgroundColor(Color.rgb(239, 236, 228));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // Fundo do mapa
        canvas.drawColor(Color.rgb(239, 236, 228));

        // Ruas principais
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);

        rua.reset();
        rua.moveTo(0, 80);
        rua.lineTo(getWidth(), 35);
        canvas.drawPath(rua, paint);

        rua.reset();
        rua.moveTo(40, 0);
        rua.lineTo(180, getHeight());
        canvas.drawPath(rua, paint);

        rua.reset();
        rua.moveTo(210, 0);
        rua.lineTo(320, getHeight());
        canvas.drawPath(rua, paint);

        rua.reset();
        rua.moveTo(0, 130);
        rua.lineTo(getWidth(), 95);
        canvas.drawPath(rua, paint);

        // Ruas menores
        paint.setStrokeWidth(4);
        paint.setColor(Color.rgb(218, 215, 207));

        rua.reset();
        rua.moveTo(0, 25);
        rua.lineTo(getWidth(), 125);
        canvas.drawPath(rua, paint);

        rua.reset();
        rua.moveTo(120, 0);
        rua.lineTo(70, getHeight());
        canvas.drawPath(rua, paint);

        rua.reset();
        rua.moveTo(290, 0);
        rua.lineTo(250, getHeight());
        canvas.drawPath(rua, paint);

        // Marcador roxo
        desenharMarcador(
                canvas,
                80,
                45,
                Color.rgb(137, 82, 184)
        );

        // Marcador vermelho
        desenharMarcador(
                canvas,
                150,
                25,
                Color.rgb(232, 74, 38)
        );

        // Marcador verde
        desenharMarcador(
                canvas,
                230,
                70,
                Color.rgb(69, 107, 22)
        );
    }

    private void desenharMarcador(
            Canvas canvas,
            float x,
            float y,
            int cor
    ) {

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(cor);

        Path marcador = new Path();

        marcador.moveTo(x, y + 20);
        marcador.cubicTo(
                x - 18,
                y,
                x - 14,
                y - 18,
                x,
                y - 18
        );

        marcador.cubicTo(
                x + 14,
                y - 18,
                x + 18,
                y,
                x,
                y + 20
        );

        canvas.drawPath(marcador, paint);

        // Bolinha branca no centro
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y - 6, 5, paint);
    }
}