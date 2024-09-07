package mert.kadakal.battleofships;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class oyun extends AppCompatActivity {

    HashMap<Integer, String> oyuncu_isimleri;
    int turn;
    TextView adetler_1o;
    TextView adetler_2o;
    HashMap<String, Integer> adetler_1;
    HashMap<String, Integer> adetler_2;
    ArrayList<ArrayList<Integer>> tablo_1_gorunurluk;
    ArrayList<ArrayList<Integer>> tablo_2_gorunurluk;
    ArrayList<ArrayList<String>> tablo_1;
    ArrayList<ArrayList<String>> tablo_2;
    int imageResId;
    int filledSquareId;
    GridLayout gridLayout;
    TextView kim_saldiriyor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oyun);

        //oyun esnasında kullanılacak veriler
        oyuncu_isimleri = new HashMap<>();
        oyuncu_isimleri.put(0, getIntent().getStringExtra("1.oyuncu_ismi"));
        oyuncu_isimleri.put(1, getIntent().getStringExtra("2.oyuncu_ismi"));
        turn = 0;
        kim_saldiriyor = findViewById(R.id.kimin_saldirdigi);
        kim_saldiriyor.setText(Html.fromHtml(String.format("<b>%s</b> saldırıyor!", oyuncu_isimleri.get(turn))));

        //10x10 tablo oluştur
        //---------------------------------------------------------------------------------
        gridLayout = findViewById(R.id.grid_layout);
        int numRows = 10;
        int numColumns = 10;

        // Görsel kaynak
        imageResId = R.drawable.empty_square;
        filledSquareId = R.drawable.filled_square;

        // GridLayout ayarları
        gridLayout.setRowCount(numRows);
        gridLayout.setColumnCount(numColumns);

        // Her hücre için ImageView ekle
        for (int i = 0; i < numRows * numColumns; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId);

            // Hücre boyutlarını ayarla
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0; // Sütun genişliği
            params.height = 0; // Satır yüksekliği
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            imageView.setLayoutParams(params);

            // Hangi ImageView'a tıklandığını belirlemek için tag ayarla
            imageView.setTag(i);  // Tag olarak sırasını kullanıyoruz

            //tıklanan hücreyi algıla, saldırıyı gerçekleştir
            View.OnClickListener imageClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Tıklanan ImageView'ı tag'ine göre belirleme
                    int position = (int) v.getTag();  // Tag ile sırasını alıyoruz
                    Log.d("turn", String.valueOf(turn));
                    saldir(position/10, position%10);
                }
            };

            // Tıklama dinleyicisi ekle
            imageView.setOnClickListener(imageClickListener);

            // ImageView'ı GridLayout'a ekle
            gridLayout.addView(imageView);
        }
        //---------------------------------------------------------------------------------

        //oyuncuların gemilerini tablolarına yerleştir
        Random random = new Random();
        ArrayList<String> gemi_isimleri = new ArrayList<>();
        gemi_isimleri.add("carrier");
        gemi_isimleri.add("battleship");
        gemi_isimleri.add("destroyer");
        gemi_isimleri.add("submarine");
        gemi_isimleri.add("patrol boat");
        HashMap<Integer, String> yon = new HashMap<>(); //rastgele yünler
        yon.put(0, "Dikey");
        yon.put(1, "Yatay");
        HashMap<String, Integer> max_ilk_hucreler = new HashMap<>(); //gemilerin kontrole başlanabileceği max hücre
        max_ilk_hucreler.put("carrier", 5);
        max_ilk_hucreler.put("battleship", 6);
        max_ilk_hucreler.put("destroyer", 7);
        max_ilk_hucreler.put("submarine", 7);
        max_ilk_hucreler.put("patrol boat", 8);
        HashMap<String, Integer> uzunluklar = new HashMap<>(); //gemilerin uzunlukları
        uzunluklar.put("carrier", 5);
        uzunluklar.put("battleship", 4);
        uzunluklar.put("destroyer", 3);
        uzunluklar.put("submarine", 3);
        uzunluklar.put("patrol boat", 2);
        HashMap<String, Integer> adetler = new HashMap<>(); //gemilerin sayıları
        adetler.put("carrier", 1);
        adetler.put("battleship", 2);
        adetler.put("destroyer", 1);
        adetler.put("submarine", 1);
        adetler.put("patrol boat", 4);
        adetler_1 = new HashMap<>(); //gemilerin sayıları
        adetler_1.put("carrier", 1);
        adetler_1.put("battleship", 2);
        adetler_1.put("destroyer", 1);
        adetler_1.put("submarine", 1);
        adetler_1.put("patrol boat", 4);
        adetler_2 = new HashMap<>(); //gemilerin sayıları
        adetler_2.put("carrier", 1);
        adetler_2.put("battleship", 2);
        adetler_2.put("destroyer", 1);
        adetler_2.put("submarine", 1);
        adetler_2.put("patrol boat", 4);

        //oyuncuların boş tablolarını oluştur
        tablo_1 = new ArrayList<>();
        tablo_2 = new ArrayList<>();
        tablo_1_gorunurluk = new ArrayList<>();
        tablo_2_gorunurluk = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String> satir1 = new ArrayList<>();
            ArrayList<Integer> satir2 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                satir1.add("x");
                satir2.add(0);
            }
            tablo_1.add(satir1);
            tablo_1_gorunurluk.add(satir2);
        }
        for (int i = 0; i < 10; i++) {
            ArrayList<String> satir1 = new ArrayList<>();
            ArrayList<Integer> satir2 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                satir1.add("x");
                satir2.add(0);
            }
            tablo_2.add(satir1);
            tablo_2_gorunurluk.add(satir2);
        }

        //her gemi için adetleri kadar yerleştirme yap
        HashMap<Integer, ArrayList<ArrayList<String>>> oyuncu_tabloları = new HashMap<>();
        oyuncu_tabloları.put(0, tablo_1);
        oyuncu_tabloları.put(1, tablo_2);
        for (int oyuncu=0; oyuncu<2; oyuncu++) {
            for (String gemi_ismi : gemi_isimleri) {
                for (int j=0; j<adetler.get(gemi_ismi); j++) {
                    while (true) {
                        String dikey_yatay = yon.get(random.nextInt(2));
                        int ilk_satir = random.nextInt(max_ilk_hucreler.get(gemi_ismi));
                        int ilk_sutun = random.nextInt(max_ilk_hucreler.get(gemi_ismi));
                        int uzunluk = uzunluklar.get(gemi_ismi);
                        ArrayList<ArrayList<Integer>> dolduralacak_hucreler = new ArrayList<>();
                        if (dikey_yatay.equals("Dikey")) {
                            for (int i = 0; i < uzunluk; i++) {
                                ArrayList<Integer> konum = new ArrayList<>();
                                konum.add(ilk_satir+i);
                                konum.add(ilk_sutun);
                                dolduralacak_hucreler.add(konum);
                            }
                        } else {
                            for (int i = 0; i < uzunluk; i++) {
                                ArrayList<Integer> konum = new ArrayList<>();
                                konum.add(ilk_satir);
                                konum.add(ilk_sutun+i);
                                dolduralacak_hucreler.add(konum);
                            }
                        }
                        if (check_if_placable(dolduralacak_hucreler, oyuncu_tabloları.get(oyuncu))) {
                            for (ArrayList<Integer> item : dolduralacak_hucreler) {
                                oyuncu_tabloları.get(oyuncu).get(item.get(0)).set(item.get(1), String.valueOf(gemi_ismi.charAt(0)));
                            }
                            break;
                        }
                    }
                }
            }
        }

        //oyuncuların kaçar gemisi kaldığını tablo altına yaz
        adetleri_guncelle();
    }

    private void saldir(int sat, int sut) {
        if (turn == 0) {
            if (tablo_2_gorunurluk.get(sat).get(sut) == 0) {
                tablo_2_gorunurluk.get(sat).set(sut, 1); //tablo_2'nin saldırılan konumunu görünür yap
            }
        } else {
            if (tablo_1_gorunurluk.get(sat).get(sut) == 0) {
                tablo_1_gorunurluk.get(sat).set(sut, 1); //tablo_1'nin saldırılan konumunu görünür yap
            }
        }
        turn = 1 - turn;
        tabloyu_yukle(turn);
    }

    private void tabloyu_yukle(int turn) {
        gridLayout.removeAllViews();
        kim_saldiriyor.setText(Html.fromHtml(String.format("<b>%s</b> saldırıyor!", oyuncu_isimleri.get(turn))));
        for (int i = 0; i < 100; i++) {
            ImageView imageView = new ImageView(this);

            if (turn == 0) {
                if (tablo_1_gorunurluk.get(i/10).get(i%10) == 1) {
                    imageView.setImageResource(filledSquareId);
                } else {
                    imageView.setImageResource(imageResId);
                }
            } else {
                if (tablo_2_gorunurluk.get(i/10).get(i%10) == 1) {
                    imageView.setImageResource(filledSquareId);
                } else {
                    imageView.setImageResource(imageResId);
                }
            }

            // Hücre boyutlarını ayarla
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0; // Sütun genişliği
            params.height = 0; // Satır yüksekliği
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            imageView.setLayoutParams(params);

            // Hangi ImageView'a tıklandığını belirlemek için tag ayarla
            imageView.setTag(i);  // Tag olarak sırasını kullanıyoruz
            gridLayout.addView(imageView);
        }
    }

    private void adetleri_guncelle() {
        adetler_1o = findViewById(R.id.adetler_1);
        adetler_2o = findViewById(R.id.adetler_2);
        adetler_1o.setText(Html.fromHtml(String.format("<b>%s</b><br><br>" +
                "Carrier: %s<br>" +
                "Battleship: %s<br>" +
                "Destroyer: %s<br>" +
                "Submarine: %s<br>" +
                "Patrol Boat: %s<br>",
                oyuncu_isimleri.get(0),
                "●".repeat(adetler_1.get("carrier")),
                "●".repeat(adetler_1.get("battleship")),
                "●".repeat(adetler_1.get("destroyer")),
                "●".repeat(adetler_1.get("submarine")),
                "●".repeat(adetler_1.get("patrol boat")))));
        adetler_2o.setText(Html.fromHtml(String.format("<b>%s</b><br><br>" +
                "Carrier: %s<br>" +
                "Battleship: %s<br>" +
                "Destroyer: %s<br>" +
                "Submarine: %s<br>" +
                "Patrol Boat: %s<br>",
                oyuncu_isimleri.get(1),
                "●".repeat(adetler_2.get("carrier")),
                "●".repeat(adetler_2.get("battleship")),
                "●".repeat(adetler_2.get("destroyer")),
                "●".repeat(adetler_2.get("submarine")),
                "●".repeat(adetler_2.get("patrol boat")))));
    }

    public boolean check_if_placable(ArrayList<ArrayList<Integer>> list, ArrayList<ArrayList<String>> tablo) {
        for (ArrayList<Integer> item : list) {
            if (!(tablo.get(item.get(0)).get(item.get(1)).equals("x"))) {
                return false;
            }
        }
        return true;
    }

    private String formatTablo(ArrayList<ArrayList<String>> tablo) {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<String> row : tablo) {
            for (String cell : row) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}