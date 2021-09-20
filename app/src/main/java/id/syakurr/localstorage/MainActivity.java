package id.syakurr.localstorage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button tombolNew;
    Button tombolOpen;
    Button tombolSave;
    EditText editKonten;
    EditText editJudul;
    File pathFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tombolNew = (Button) findViewById(R.id.tombol_new);
        tombolOpen = (Button) findViewById(R.id.tombol_open);
        tombolSave = (Button) findViewById(R.id.tombol_save);
        editKonten = (EditText) findViewById(R.id.edit_konten);
        editJudul = (EditText) findViewById(R.id.edit_judul);

        tombolNew.setOnClickListener(this);
        tombolOpen.setOnClickListener(this);
        tombolSave.setOnClickListener(this);
        pathFile = getFilesDir();
    }

    public void newFile() {
        editJudul.setText("");
        editKonten.setText("");
        Toast.makeText(this, "Membuat file baru", Toast.LENGTH_SHORT).show();
    }

    private void loadFile(String title) {
        String text = FileHandler.readFile(this, title);
        editJudul.setText(title);
        editKonten.setText(text);
        Toast.makeText(this, "Membuka " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : pathFile.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang tersedia");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadFile(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() {
        if (editJudul.getText().toString().isEmpty()) {
            Toast.makeText(this, "Judul file harus diisi", Toast.LENGTH_SHORT).show();
        } else {
            String title = editJudul.getText().toString();
            String text = editKonten.getText().toString();
            FileHandler.writeFile(title, text, this);
            Toast.makeText(this, "Menyimpan " + editJudul.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tombol_new:
                newFile();
                break;
            case R.id.tombol_open:
                openFile();
                break;
            case R.id.tombol_save:
                saveFile();
                break;
        }
    }
}