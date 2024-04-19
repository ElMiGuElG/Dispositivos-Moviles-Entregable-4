package co.galeano.dispositivosmovilesentregable_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailName;
    ImageView detailImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDescr);
        detailImage = findViewById(R.id.detailImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailName.setText(bundle.getString("Name"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

    }
}