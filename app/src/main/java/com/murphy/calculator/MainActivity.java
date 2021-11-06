package com.murphy.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button b_1,b_2,b_3,b_4,b_5,b_6,b_7,b_8,b_9,b_0,b_koma;
    Button b_samadengan,b_tambah,b_kurang,b_kali,b_bagi;
    Button b_ac,b_c,b_kuadrat,b_akar,b_luas,b_keliling;
    TextView hitung,hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Angka kalkulator
        b_1 = findViewById(R.id.b_1);
        b_2 = findViewById(R.id.b_2);
        b_3 = findViewById(R.id.b_3);
        b_4 = findViewById(R.id.b_4);
        b_5 = findViewById(R.id.b_5);
        b_6 = findViewById(R.id.b_6);
        b_7 = findViewById(R.id.b_7);
        b_8 = findViewById(R.id.b_8);
        b_9 = findViewById(R.id.b_9);
        b_0 = findViewById(R.id.b_0);
        b_koma = findViewById(R.id.b_koma);

        //Operasi kalkulator
        b_samadengan = findViewById(R.id.b_samadengan);
        b_tambah = findViewById(R.id.b_tambah);
        b_kurang = findViewById(R.id.b_kurang);
        b_kali = findViewById(R.id.b_kali);
        b_bagi = findViewById(R.id.b_bagi);
        b_ac = findViewById(R.id.b_ac);
        b_c = findViewById(R.id.b_c);
        b_kuadrat = findViewById(R.id.b_kuadrat);
        b_akar = findViewById(R.id.b_akar);
        b_luas = findViewById(R.id.b_luas);
        b_keliling = findViewById(R.id.b_keliling);

        //Tampilan perhitungan dan hasil
        hitung = findViewById(R.id.hitung);
        hasil = findViewById(R.id.hasil);

        //Angka kalkulator
        b_1.setOnClickListener(v -> hitung.setText(String.format("%s1", hitung.getText())));
        b_2.setOnClickListener(v -> hitung.setText(String.format("%s2", hitung.getText())));
        b_3.setOnClickListener(v -> hitung.setText(String.format("%s3", hitung.getText())));
        b_4.setOnClickListener(v -> hitung.setText(String.format("%s4", hitung.getText())));
        b_5.setOnClickListener(v -> hitung.setText(String.format("%s5", hitung.getText())));
        b_6.setOnClickListener(v -> hitung.setText(String.format("%s6", hitung.getText())));
        b_7.setOnClickListener(v -> hitung.setText(String.format("%s7", hitung.getText())));
        b_8.setOnClickListener(v -> hitung.setText(String.format("%s8", hitung.getText())));
        b_9.setOnClickListener(v -> hitung.setText(String.format("%s9", hitung.getText())));
        b_0.setOnClickListener(v -> hitung.setText(String.format("%s0", hitung.getText())));
        b_koma.setOnClickListener(v -> hitung.setText(String.format("%s.", hitung.getText())));

        //Operasi kalkulator
        b_tambah.setOnClickListener(v -> hitung.setText(String.format("%s+", hitung.getText())));
        b_kurang.setOnClickListener(v -> hitung.setText(String.format("%s-", hitung.getText())));
        b_kali.setOnClickListener(v -> hitung.setText(String.format("%s×", hitung.getText())));
        b_bagi.setOnClickListener(v -> hitung.setText(String.format("%s÷", hitung.getText())));

        b_akar.setOnClickListener(v -> {
            String val = hitung.getText().toString();
            double r = akar(Double.parseDouble(val));
            hitung.setText(String.valueOf(r));
        });
        b_kuadrat.setOnClickListener(v -> {
            double d = Double.parseDouble(hitung.getText().toString());
            double square = d*d;
            hitung.setText(String.valueOf(square));
            hasil.setText(String.format("%s²", d));
        });
        b_luas.setOnClickListener(v -> { //Apabila luas lingkaran
            double r = Double.parseDouble(hitung.getText().toString());
            double square = 3.14159265 * r * r;
            hitung.setText(String.valueOf(square));
            hasil.setText(String.format("jari-jari = %s", r));
        });
        b_keliling.setOnClickListener(v -> { //Apabila keliling lingkaran
            double r = Double.parseDouble(hitung.getText().toString());
            double square = 2 * 3.14159265 * r;
            hitung.setText(String.valueOf(square));
            hasil.setText(String.format("jari-jari = %s", r));
        });
        b_ac.setOnClickListener(v -> {
            hitung.setText("");
            hasil.setText("");
        });
        b_c.setOnClickListener(v -> {
            String val = hitung.getText().toString();
            val = val.substring(0, val.length() - 1);
            hitung.setText(val);
        });
        b_samadengan.setOnClickListener(v -> {
            String val = hitung.getText().toString();
            String replacedstr = val.replace('÷','/').replace('×','*');
            double result = eval(replacedstr);
            hitung.setText(String.valueOf(result));
            hasil.setText(val);
        });

    }

    public static Double akar(double x) {
        double t;
        double akardua = x/3;
        do{
            t = akardua;
            akardua = (t+(x/t))/3;
        }while((t-akardua) !=0);

        return akardua;
    }

    //eval function
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // tambahan
                    else if (eat('-')) x -= parseTerm(); // pengurangan
                    else return x;
                }
            }

            //Jika dikali dan dibagi
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // perkalian
                    else if (eat('/')) x /= parseFactor(); // pembagian
                    else return x;
                }
            }

            //Pelakukan parse input jika terdapat koma, postitif atau negatif
            double parseFactor() {
                if (eat('+')) return parseFactor(); // operator tambah
                if (eat('-')) return -parseFactor(); // operator kurang

                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') { // melakukan pengecekan button yang ditekan
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}