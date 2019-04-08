package org.izv.aad.doshilos;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "xyzTagzyx";

    private Button btDo;
    private TextView tvTexto1;
    private TextView tvTexto2;

    private Handler handler = new Handler();

    private static final int TOPE_SUPERIOR = 20, TOPE_INFERIOR = 0;

    private class HebraContadora extends Thread {
        @Override
        public void run() {
            cuentaHaciaAdelante();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, Thread.currentThread().getName());
        init();
    }

    private void init() {
        this.tvTexto2 = findViewById(R.id.tvTexto2);
        this.tvTexto1 = findViewById(R.id.tvTexto1);
        this.btDo = findViewById(R.id.btDo);

        this.btDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread hebra = new Thread() {
                    @Override
                    public void run() {
                        cuentaHaciaAtrás();
                    }
                };
                HebraContadora h = new HebraContadora();
                h.start();
                hebra.start();
                //cuentaHaciaAdelante();
                //cuentaHaciaAtrás();
            }
        });
    }

    private void cuentaHaciaAdelante() {
        int cuentaAdelante = TOPE_INFERIOR;
        Log.v(TAG, Thread.currentThread().getName());
        //tvTexto1.setText("");
        setTextAsynchronous1(tvTexto1, "");
        for (cuentaAdelante = TOPE_INFERIOR; cuentaAdelante < TOPE_SUPERIOR; cuentaAdelante++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
            //tvTexto1.append(cuentaAdelante + "\n");
            appendTextAsynchronous1(tvTexto1, cuentaAdelante + "\n");
        }
    }

    private void cuentaHaciaAtrás() {
        int cuentaAtras = TOPE_INFERIOR;
        Log.v(TAG, Thread.currentThread().getName());
        //tvTexto2.setText("");
        setTextAsynchronous2(tvTexto2, "");
        for (cuentaAtras = TOPE_SUPERIOR; cuentaAtras > TOPE_INFERIOR; cuentaAtras--) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
            }
            //tvTexto2.append(cuentaAtras + "\n");
            appendTextAsynchronous2(tvTexto2, cuentaAtras + "\n");
        }
    }

    private void appendTextAsynchronous1(TextView v, String text) {
        v.post(getRunnableAppend(v, text));
    }

    private void appendTextAsynchronous2(TextView v, String text) {
        this.runOnUiThread(getRunnableAppend(v, text));
    }

    private void setTextAsynchronous1(TextView v, String text) {
        handler.post(getRunnableSet(v, text));
    }

    private void setTextAsynchronous2(TextView v, String text) {
        v.postDelayed(getRunnableSet(v, text), 100);
    }

    private Runnable getRunnableAppend(final TextView v, final String text) {
        return new Runnable() {
            @Override
            public void run() {
                Log.v(TAG, Thread.currentThread().getName());
                v.append(text);
            }
        };
    }

    private Runnable getRunnableSet(final TextView v, final String text) {
        return new Runnable() {
            @Override
            public void run() {
                Log.v(TAG, Thread.currentThread().getName());
                v.setText(text);
            }
        };
    }
}
