package client.mylittledoctor.formation.arlebois.com.mylittledoctor;

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

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.atelier.AtelierActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.formateur.CentralFormateurActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur.UtilisateurCreateActivity;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Utilisateur;

public class MainActivity extends MyLittleDoctorActivity {

    EditText nom;
    EditText prenom;
    EditText mdp;
    Button loginButton;
    EditText resultat;

    private View.OnClickListener utilisateurListener = new View.OnClickListener() {
        public void onClick(View v) {
            StringRequest getRequest = new StringRequest(Request.Method.POST, url + "/user/getByNomAndPrenomAndMdp",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Utilisateur bean = new Gson().fromJson(response, Utilisateur.class);
                            if (bean != null) {
                                utilisateurId = bean.getId();
                                role = bean.getRole();
                                if (bean.getAtelierEnCours() != null) {
                                    atelierId = bean.getAtelierEnCours().getId();
                                } else {
                                    atelierId = -1L;
                                }
                                nextActivity();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                            errorManager.onErrorResponse(error);
                            if (errorManager.getStatusCode() == 404) {
                                resultat.setText("Utilisateur/Mot de passe incorrect");
                            } else {
                                resultat.setText("Erreur !!! code : " + errorManager.getStatusCode() + " Message : " + errorManager.getErrorMessage());
                            }
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nom", nom.getText().toString());
                    params.put("prenom", prenom.getText().toString());
                    params.put("mdp", mdp.getText().toString());
                    return params;
                }
            };
            RequestSingleton.getInstance(MainActivity.this).addToRequestQueue(getRequest);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        mdp = findViewById(R.id.mdp);
        resultat = findViewById(R.id.resultat);

        loginButton = findViewById(R.id.selectAtelier);
        loginButton.setOnClickListener(utilisateurListener);
    }

    /**
     * Called when the user taps the Send button
     */
    public void toUtilisateurActivity(View view) {
        configNextActivity(this, UtilisateurCreateActivity.class);
    }

    private void nextActivity() {
        if (role == 1) {
            configNextActivity(this, CentralFormateurActivity.class);
        } else {
            if (atelierId != -1) {
                configNextActivity(this, AtelierActivity.class);
            } else {
                configNextActivity(this, CentralActivity.class);
            }
        }
    }
}
