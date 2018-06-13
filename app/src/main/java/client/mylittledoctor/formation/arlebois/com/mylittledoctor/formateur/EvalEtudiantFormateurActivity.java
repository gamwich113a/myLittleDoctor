package client.mylittledoctor.formation.arlebois.com.mylittledoctor.formateur;

import android.content.Intent;
import android.os.Bundle;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorActivity;

public class EvalEtudiantFormateurActivity extends MyLittleDoctorActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formateur_eval_etudiant);

        Intent intent = getIntent();
        getContext(intent);
    }


}
