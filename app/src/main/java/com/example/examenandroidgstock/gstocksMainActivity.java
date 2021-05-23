package com.example.examenandroidgstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


public class gstocksMainActivity extends AppCompatActivity {

    Cursor cur;
    SQLiteDatabase db;
    LinearLayout layNaviguer, layRecherche, layR;
    EditText _txtIdProduit, _txtLibelle, _txtTypeP, _txtCodeBarre, _txtNote, _txtRechercheStock, _txtCode;
    ImageButton _btnRecherche, _btnRCodeBarre;
    Button _btnPrevious, _btnNext;
    Button _btnAdd, _btnUpdate, _btnDelete;
    Button _btnCancel,_btnSave;
    int op = 0;
    String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gstocks_main);

        layNaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        layRecherche = (LinearLayout) findViewById(R.id.layRecherche);
        layR= (LinearLayout) findViewById(R.id.LayR);

        _txtCode = (EditText) findViewById(R.id.txtCode);
        _btnRCodeBarre = (ImageButton) findViewById(R.id.btnRCodeBarre);

        _txtRechercheStock = (EditText) findViewById(R.id.txtRechercheStock);
        _txtIdProduit = (EditText) findViewById(R.id.txtIdProduit);
        _txtLibelle = (EditText) findViewById(R.id.txtLibelle);
        _txtCodeBarre = (EditText) findViewById(R.id.txtCodeBarre);
        _txtNote = (EditText) findViewById(R.id.txtNote);


        _btnAdd = (Button) findViewById(R.id.btnAdd);
        _btnUpdate = (Button) findViewById(R.id.btnUpdate);
        _btnDelete = (Button) findViewById(R.id.btnDelete);

        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);

        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);
        _btnCancel = (Button) findViewById(R.id.btnCancel);
        _btnSave = (Button) findViewById(R.id.btnSave);

        //Ouverture d'une connexion vers la base de données
        db = openOrCreateDatabase("bdgstocks",MODE_PRIVATE,null);

        //Création de la table gestion du stocks
        db.execSQL("CREATE TABLE IF NOT EXISTS Gestions (idP integer primary key autoincrement, Libelle VARCHAR(255), TypeP VARCHAR(255), CodeBarre REAL, Note INTEGER);");
        layNaviguer.setVisibility(View.INVISIBLE);
        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = db.rawQuery("select * from Gestions where Libelle like ?", new String[]{ _txtRechercheStock.getText().toString()});
                try {
                    cur.moveToFirst();
                    _txtIdProduit.setText(cur.getString(1));
                    _txtLibelle.setText(cur.getString(2));
                    _txtTypeP.setText(cur.getString(3));
                    _txtCodeBarre.setText(cur.getString(4));
                    _txtNote.setText(cur.getString(5));
                    if(cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun résultat.", Toast.LENGTH_SHORT).show();
                    _txtIdProduit.setText("");
                    _txtLibelle.setText("");
                    _txtTypeP.setText("");
                    _txtCodeBarre.setText("");
                    _txtNote.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });

        _btnRCodeBarre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cur = db.rawQuery("select * from Gestions where codeBarre between ? and ? ", new String[]{_txtCode.getText().toString()});

                try {
                    cur.moveToFirst();

                    _txtLibelle.setText(cur.getString(1));
                    _txtTypeP.setText(cur.getString(2));
                    _txtCodeBarre.setText(cur.getString(3));
                    _txtNote.setText(cur.getString(4));
                    if (cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucune résultat.",Toast.LENGTH_SHORT).show();

                    _txtLibelle.setText("");
                    _txtTypeP.setText("");
                    _txtCodeBarre.setText("");
                    _txtNote.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });
        // Button Suivant

        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToNext();
                    _txtIdProduit.setText(cur.getString(1));
                    _txtLibelle.setText(cur.getString(2));
                    _txtTypeP.setText(cur.getString(3));
                    _txtCodeBarre.setText(cur.getString(4));
                    _txtNote.setText(cur.getString(5));
                    _btnPrevious.setEnabled(true);
                    if (cur.isLast()){
                        _btnNext.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        // Button Precedent

        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToPrevious();
                    _txtIdProduit.setText(cur.getString(1));
                    _txtLibelle.setText(cur.getString(2));
                    _txtTypeP.setText(cur.getString(3));
                    _txtCodeBarre.setText(cur.getString(4));
                    _txtNote.setText(cur.getString(5));
                    _btnNext.setEnabled(true);
                    if (cur.isFirst()){
                        _btnPrevious.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        //Button Ajouter

        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 1;
                _txtIdProduit.setText("");
                _txtLibelle.setText("");
                _txtTypeP.setText("");
                _txtCodeBarre.setText("");
                _txtNote.setText("");
                _btnSave.setVisibility(View.VISIBLE);
                _btnCancel.setVisibility(View.VISIBLE);
                _btnUpdate.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnAdd.setEnabled(false);
                layNaviguer.setVisibility(View.INVISIBLE);
                layRecherche.setVisibility(View.INVISIBLE);
            }
        });

        //Button Modifier

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tester si les champs ne sont pas vides
                try {
                    x = cur.getString(0);
                    op = 2;

                    _btnSave.setVisibility(View.VISIBLE);
                    _btnCancel.setVisibility(View.VISIBLE);

                    _btnDelete.setVisibility(View.INVISIBLE);
                    _btnUpdate.setEnabled(false);
                    _btnAdd.setVisibility(View.INVISIBLE);

                    layNaviguer.setVisibility(View.INVISIBLE);
                    layRecherche.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"vous devez Sélectionnez une gestion ",Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Button Enregistrer

        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (op == 1){

                    db.execSQL("insert into Gestions (Libelle,TypeP,CodeBarre,Note) values (?,?,?,?);", new String[] {_txtLibelle.getText().toString(), _txtTypeP.getText().toString(),_txtCodeBarre.getText().toString(),_txtNote.getText().toString()});
                } else if (op == 2) {

                    db.execSQL("update Gestions set Libelle=?, TypeP=?, CodeBarre=?, Note=? where idProduit=?;", new String[] {_txtLibelle.getText().toString(), _txtTypeP.getText().toString(),_txtCodeBarre.getText().toString(),_txtNote.getText().toString(),x});
                }

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                _btnUpdate.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAdd.setVisibility(View.VISIBLE);
                _btnAdd.setEnabled(true);
                _btnUpdate.setEnabled(true);
                _btnRecherche.performClick();
                layRecherche.setVisibility(View.VISIBLE);
            }
        });

        //Button Annuler

        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 0;

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                _btnUpdate.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAdd.setVisibility(View.VISIBLE);
                _btnAdd.setEnabled(true);
                _btnUpdate.setEnabled(true);

                layRecherche.setVisibility(View.VISIBLE);
            }
        });

        // Button Supprimer

        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    x=  cur.getString(0);
                    AlertDialog dial = MesOptions();
                    dial.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Sélectionner une gestions ensuite appyuer sur le bouton de suppresssion",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    private AlertDialog MesOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("ok")
                .setMessage("Est ce que vous voulez supprimer cette gestion?")
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.execSQL("delete from Gestions where idProduit=?;",new String[] {cur.getString(0)});
                        _txtLibelle.setText("");
                        _txtTypeP.setText("");
                        _txtCodeBarre.setText("");
                        _txtNote.setText("");
                        layNaviguer.setVisibility(View.INVISIBLE);
                        cur.close();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return MiDia;

    }
}