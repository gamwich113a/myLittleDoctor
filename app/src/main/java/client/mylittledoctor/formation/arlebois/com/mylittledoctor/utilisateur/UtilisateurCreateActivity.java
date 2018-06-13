package client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.MainActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Utilisateur;


public class UtilisateurCreateActivity extends MyLittleDoctorActivity {

    Button createUtilisateur;
    EditText nom;
    EditText prenom;
    EditText mdp;
    Spinner anneeNaissance;
    Spinner moisNaissance;
    Spinner jourNaissance;
    RadioButton sexeHomme;
    RadioButton sexeFemme;
    RadioButton etudiant;
    RadioButton formateur;
    RadioGroup roleGroupe;
    EditText email;
    Spinner anneeDesList;
    EditText regionFormation;
    EditText hopital_id;
    EditText resultat;

    LinearLayout layoutAnneeDes;

    Utilisateur bean;

    private View.OnClickListener utilisateurListener = new View.OnClickListener() {
        public void onClick(View v) {
            StringRequest getRequest = new StringRequest(Request.Method.POST, url + "/user/create",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            resultat.setText("Response" + response);
                            bean = new Gson().fromJson(response, Utilisateur.class);
                            utilisateurId = bean.getId();
                            role = bean.getRole();
                            configNextActivity(UtilisateurCreateActivity.this, EvaluationGesteActivity.class);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                            errorManager.onErrorResponse(error);
                            resultat.setText("Error !!! Code : " + errorManager.getStatusCode() + " Msg : " + errorManager.getErrorMessage());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nom", nom.getText().toString());
                    params.put("prenom", prenom.getText().toString());
                    params.put("motDePasse", mdp.getText().toString());
                    String dateNaissance = anneeNaissance.getSelectedItem().toString() + moisNaissance.getSelectedItem().toString() + jourNaissance.getSelectedItem().toString();
                    params.put("dateNaissance", dateNaissance);
                    params.put("email", email.getText().toString());
                    if (sexeHomme.isChecked()) {
                        params.put("sexe", "1");
                    } else {
                        params.put("sexe", "2");
                    }
                    if (etudiant.isChecked()) {
                        params.put("role", "0");
                        params.put("anneeDes", anneeDesList.getSelectedItem().toString());
                        params.put("regionFormation", regionFormation.getText().toString());
                    } else {
                        params.put("role", "1");
                    }
                    params.put("hopital_id", hopital_id.getText().toString());

                    return params;
                }
            };
            RequestSingleton.getInstance(UtilisateurCreateActivity.this).addToRequestQueue(getRequest);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur_create);

        // Get the Intent that started this activity and extract the string
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        mdp = findViewById(R.id.mdp);
        anneeNaissance = findViewById(R.id.anneeNaissance);
        moisNaissance = findViewById(R.id.moisNaissance);
        jourNaissance = findViewById(R.id.jourNaissance);
        sexeHomme = findViewById(R.id.sexeHomme);
        sexeFemme = findViewById(R.id.sexeFemme);
        etudiant = findViewById(R.id.etudiant);
        formateur = findViewById(R.id.formateur);
        roleGroupe = findViewById(R.id.roleGroupe);
        email = findViewById(R.id.email);
        anneeDesList = findViewById(R.id.anneeDesList);
        layoutAnneeDes = findViewById(R.id.layoutAnneeDes);
        regionFormation = findViewById(R.id.regionFormation);
        hopital_id = findViewById(R.id.hopital_id);
        resultat = findViewById(R.id.resultat);

        createUtilisateur = findViewById(R.id.createUtilisateur);
        createUtilisateur.setOnClickListener(utilisateurListener);

        roleGroupe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if ("Formateur".equals( ( (RadioButton) findViewById(checkedId)).getText())) {
                    layoutAnneeDes.setVisibility(View.INVISIBLE);
                    regionFormation.setVisibility(View.INVISIBLE);
                } else {
                    layoutAnneeDes.setVisibility(View.VISIBLE);
                    regionFormation.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}
