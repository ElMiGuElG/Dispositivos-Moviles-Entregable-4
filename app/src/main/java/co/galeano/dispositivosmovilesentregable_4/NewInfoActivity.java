package co.galeano.dispositivosmovilesentregable_4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewInfoActivity extends AppCompatActivity {

    ImageView Image;
    Button Guardar;
    EditText Categoryproduct, descriptionProduct, nameProduct;
    String imageURL;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        Image = findViewById(R.id.image);
        nameProduct = findViewById(R.id.nameProduct);
        descriptionProduct = findViewById(R.id.descriptionProduct);
        Categoryproduct = findViewById(R.id.Categoryproduct);
        Guardar = findViewById(R.id.Guardar);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK){
                            Intent data = o.getData();
                            uri = data.getData();
                            Image.setImageURI(uri);
                        } else {
                            Toast.makeText(NewInfoActivity.this,"Seleccione Una Imagen",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photopick = new Intent(Intent.ACTION_PICK);
                photopick.setType("image/*");
                activityResultLauncher.launch(photopick);
            }
        });

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });
    }

    public void Save (){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(NewInfoActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                Upload();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void Upload(){
        String name = nameProduct.getText().toString();
        String desc = descriptionProduct.getText().toString();
        String category = Categoryproduct.getText().toString();

        InfoClass  dataClass = new InfoClass (name, desc, category,imageURL);
        FirebaseDatabase.getInstance().getReference("clave").child(name)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(NewInfoActivity.this,"Guardado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewInfoActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}