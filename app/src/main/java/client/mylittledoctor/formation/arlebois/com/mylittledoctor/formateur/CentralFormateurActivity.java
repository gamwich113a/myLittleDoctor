package client.mylittledoctor.formation.arlebois.com.mylittledoctor.formateur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.CentralActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.atelier.AtelierActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur.UtilisateurCreateActivity;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Utilisateur;

public class CentralFormateurActivity extends MyLittleDoctorActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formateur_main);

        Intent intent = getIntent();
        getContext(intent);
    }

    public void toSelectAtelier(View view) {
        configNextActivity(this, SelectAtelierFormateurActivity.class);
    }

    public void toUpdateTopo(View view) {
        configNextActivity(this, UpdateTopoFormateurActivity.class);
    }

    public void toEvaluatEtudiant(View view) {
        configNextActivity(this, EvalEtudiantFormateurActivity.class);
    }

}
