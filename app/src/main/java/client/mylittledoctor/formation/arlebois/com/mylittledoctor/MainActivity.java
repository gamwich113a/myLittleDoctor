package client.mylittledoctor.formation.arlebois.com.mylittledoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur.UtilisateurCreateActivity;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Utilisateur;

public class MainActivity extends AppCompatActivity {

    public static final String UTILISATEUR_ID = "Utilisateur";
    public static final String ATELIER_ID = "Atelier";
    public static final String ROLE = "Role";
    public static final String RAPPORT_MESSAGE = "Rapport";
    public static final String ETAPE_ID = "Etape";

    //public static final String url = "http://mylittledoctor-env.eu-west-3.elasticbeanstalk.com/user/create";
    public static final String url = "http://10.0.2.2:8080";
    //public static final String url = "http://86.252.111.135:8080";

    public static final String DEVELOPER_KEY = "AIzaSyAboGYy-u4Ff5MErwB1FK7FAgOtxq9ffgs";


    EditText nom;
    EditText prenom;
    EditText mdp;
    Button loginButton;
    EditText resultat;
    Long atelierId;
    Long utilisateurId;
    Integer role;

    private View.OnClickListener utilisateurListener = new View.OnClickListener() {
        public void onClick(View v) {
            StringRequest getRequest = new StringRequest(Request.Method.POST, MainActivity.url + "/user/getByNomAndPrenomAndMdp",
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

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(utilisateurListener);
    }

    /**
     * Called when the user taps the Send button
     */
    public void toUtilisateurActivity(View view) {
        Intent intent = new Intent(this, UtilisateurCreateActivity.class);
        startActivity(intent);
    }

    private void nextActivity() {
        Intent intent;
        if (atelierId != -1) {
            intent = new Intent(this, AtelierActivity.class);
        } else {
            intent = new Intent(this, CentralActivity.class);
        }

        intent.putExtra(MainActivity.UTILISATEUR_ID, utilisateurId);
        intent.putExtra(MainActivity.ATELIER_ID, atelierId);
        intent.putExtra(MainActivity.ROLE, role);

        startActivity(intent);
    }
}
