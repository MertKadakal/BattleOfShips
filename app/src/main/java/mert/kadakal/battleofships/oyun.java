package mert.kadakal.battleofships;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class oyun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oyun);

        //10x10 tablo oluştur
        //---------------------------------------------------------------------------------
        GridLayout gridLayout = findViewById(R.id.grid_layout);
        int numRows = 10;
        int numColumns = 10;

        // Görsel kaynak
        int imageResId = R.drawable.empty_square;

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

        //oyuncuların boş tablolarını oluştur
        ArrayList<ArrayList<String>> tablo_1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String> satir1 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                satir1.add("x");
            }
            tablo_1.add(satir1);
        }
        ArrayList<ArrayList<String>> tablo_2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String> satir2 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                satir2.add("x");
            }
            tablo_2.add(satir2);
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
        Log.d("tablo", formatTablo(tablo_1));
        Log.d("tablo", formatTablo(tablo_2));
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