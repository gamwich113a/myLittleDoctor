package client.mylittledoctor.formation.arlebois.com.mylittledoctor.formateur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.MainActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur.EvaluationGesteActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur.UtilisateurCreateActivity;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Atelier;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Evaluation;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Geste;

public class SelectAtelierFormateurActivity extends MyLittleDoctorActivity {

    Map<String, Atelier> atelierMap = new HashMap<>();
    TableLayout atelierGrid;
    ScrollView scrollView;
    Button sauvegarde;
    EditText resultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formateur_select_atelier);

        Intent intent = getIntent();
        getContext(intent);

        resultat = findViewById(R.id.resultat);
        atelierGrid = findViewById(R.id.atelierGrid);
        sauvegarde = findViewById(R.id.update);
        scrollView = findViewById(R.id.scrollView);

        mapAtelier();
    }

    public void mapAtelier() {
        StringRequest atelierRequest = new StringRequest(Request.Method.POST, url + "/atelier/findAll", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                createAtelierView(response);
        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                System.out.println("Error: Code: " + errorManager.getStatusCode() + "Msg : " + errorManager.getErrorMessage());
            }
        });
        RequestSingleton.getInstance(this).addToRequestQueue(atelierRequest);
    }

    private void createAtelierView(final String response) {
        List<Atelier> listAtelier = Arrays.asList(new Gson().fromJson(response, Atelier[].class));
        for (Atelier atelier : listAtelier) {
            atelierMap.put(atelier.getTitre(), atelier);
        }
        dessinGrille();
    }

    private void dessinGrille() {
        for (Atelier atelier : atelierMap.values()) {
            TableRow trAtelier = initAtelier(atelier);

            /* Add row to TableLayout. */
            atelierGrid.addView(trAtelier, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

        // Put the scrollBar to top
        scrollView.pageScroll(View.FOCUS_UP);
    }

    private TableRow initAtelier(final Atelier atelier) {
        TableRow trAtelier = new TableRow(this);

        TextView atelierTitre = new TextView(this);
        atelierTitre.setText(atelier.getTitre());

        CheckBox checkBox = new CheckBox(this);
        checkBox.setEnabled(true);
        checkBox.setActivated(true);
        if (atelier.getFormateur() != null) {
            if (atelier.getFormateur().getId() ==  utilisateurId) {
                checkBox.setChecked(true);
            } else {
                checkBox.setActivated(false);
                checkBox.setEnabled(false);
            }
        }

        trAtelier.addView(atelierTitre);
        trAtelier.addView(checkBox);

        return trAtelier;
    }

    public void toSaveLinkAtelierFormateur(View view) {

        for (int i = 0; i < atelierGrid.getChildCount(); i++) {
            String titre = ((TextView) ((TableRow) atelierGrid.getChildAt(i)).getChildAt(0)).getText().toString();
            CheckBox checkBox = ((CheckBox) ((TableRow) atelierGrid.getChildAt(i)).getChildAt(1));
            Atelier atelier = atelierMap.get(titre);
            if (atelier != null && checkBox.isActivated()) {
                updateAtelierLink(atelier, checkBox.isChecked());
            }
        }
        configNextActivity(this,CentralFormateurActivity.class);
    }

    private void updateAtelierLink(final Atelier atelier, final Boolean check) {
        StringRequest atelierLinkRequest = new StringRequest(Request.Method.POST, url + "/atelier/linkAtelier", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                System.out.println("Error: Code: " + errorManager.getStatusCode() + "Msg : " + errorManager.getErrorMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("formateur_id", utilisateurId.toString());
                params.put("atelier_id", atelier.getId().toString());
                params.put("checked", check ? "1" : "0");
                return params;
            }
        };
        RequestSingleton.getInstance(this).addToRequestQueue(atelierLinkRequest);
    }

}
