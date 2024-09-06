package mert.kadakal.battleofships;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

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
            params.setMargins(0, 0, 0, 0);
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
        HashMap<Integer, String> yon = new HashMap<>();
        yon.put(0, "Dikey");
        yon.put(1, "Yatay");
        //1. oyuncu için yerleştirme
        HashMap<ArrayList<Integer>, String> dolu_hucreler_oyuncu1 = new HashMap<>();
        //carrier
        while (true) {
            String dikey_yatay = yon.get(Math.random())
        }
    }
}
