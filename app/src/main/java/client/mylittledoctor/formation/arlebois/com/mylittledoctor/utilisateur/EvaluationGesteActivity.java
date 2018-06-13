package client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.CentralActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.MainActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.formateur.CentralFormateurActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Evaluation;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Geste;


public class EvaluationGesteActivity extends MyLittleDoctorActivity {

    Button sauvegardeEvaluationGeste;
    EditText resultat;
    List<Evaluation> listEvaluation;
    List<Geste> listGeste;
    Map<Long, Evaluation> evaluationMap = new HashMap<>();
    TableLayout evaluationGrid;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geste);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        getContext(intent);

        resultat = findViewById(R.id.resultat);
        evaluationGrid = findViewById(R.id.evaluationGrid);
        sauvegardeEvaluationGeste = findViewById(R.id.sauvegardeEvaluationGeste);
        scrollView = findViewById(R.id.scrollView);

        findGeste(atelierId);

        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });

    }

    public void toSaveEvaluation(View view) {
        convertUserDataToObject(evaluationGrid.getChildCount());

        // bien moche mais on avance. Voir comment faire un appel de masse
        Iterator<Long> evalSet = evaluationMap.keySet().iterator();
        while (evalSet.hasNext()) {
            saveEvaluationByUtilisateur(evaluationMap.get(evalSet.next()));
        }


        if (role == 0)  {
            unLinkAtelier();
            configNextActivity(this, CentralActivity.class);
        } else {
            configNextActivity(this, CentralFormateurActivity.class);
        }

    }

    public void findGeste(final Long atelierId) {
        String request;
        if (atelierId == -1) {
            request = "/geste/findAll";
        } else {
            request = "/geste/findByAtelierId";
        }

        StringRequest gesteRequest = new StringRequest(Request.Method.POST, url + request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                generateData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                resultat.setText("Error: Code: " + errorManager.getStatusCode() + "Msg : " + errorManager.getErrorMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("atelier_id", atelierId.toString());
                return params;
            }
        };
        RequestSingleton.getInstance(EvaluationGesteActivity.this).addToRequestQueue(gesteRequest);
    }

    private void dessinGrille() {
        for (Evaluation eval : listEvaluation) {
            TableRow trGeste = initGeste(eval);
            TableRow trNote = null;
            TableRow trNbPratique = null;

            if (role == 0) {
                trNote = initNote();
            } else {
                trNbPratique = initNbPratique();
            }

            TableRow trCommentaire = initCommentaire();

            /* Add row to TableLayout. */
            evaluationGrid.addView(trGeste, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            if (role == 0) {
                evaluationGrid.addView(trNote);
            } else {
                evaluationGrid.addView(trNbPratique);
            }
            evaluationGrid.addView(trCommentaire, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

        // Put the scrollBar to top
        scrollView.pageScroll(View.FOCUS_UP);
    }

    private TableRow initGeste(final Evaluation eval) {
        TableRow trGeste = new TableRow(this);

        TextView gesteDescription = new TextView(this);
        gesteDescription.setText(eval.getGeste().getDescription());

        trGeste.addView(gesteDescription);

        return trGeste;
    }

    private TableRow initNbPratique() {
        TableRow trNbPratique= new TableRow(this);

        List<Integer> listNbPratique = new ArrayList<>();

        listNbPratique.add(0);
        listNbPratique.add(1);
        listNbPratique.add(2);
        listNbPratique.add(5);
        listNbPratique.add(10);
        listNbPratique.add(20);
        listNbPratique.add(50);
        listNbPratique.add(100);

        Spinner nbPratique = new Spinner(this);
        nbPratique.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNbPratique));

        TextView libelle = new TextView(this);
        libelle.setText("Nombre de pratique annuelle : ");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.addView(libelle);
        layout.addView(nbPratique);

        trNbPratique.addView(layout);

        return trNbPratique;
    }

    private TableRow initNote() {
        TableRow trNote= new TableRow(this);

        RatingBar note = new RatingBar(this);
        note.setNumStars(4);
        note.setStepSize(1);

        LinearLayout layout = new LinearLayout(this);
        layout.addView(note);

        trNote.addView(layout);

        return trNote;
    }


    private TableRow initCommentaire() {

        TableRow trCommentaire = new TableRow(this);

        EditText commentaire = new EditText(this);
        commentaire.setText("Commentaire");
        commentaire.setMinimumWidth(500);
        commentaire.setSelectAllOnFocus(true);

        trCommentaire.addView(commentaire);

        return trCommentaire;
    }


    private void mapToObject(String geste, Integer note, Integer nbPratique, String commentaire) {
        Long evaluationKey = -1L;
        for (Long key : evaluationMap.keySet()) {
            String gesteDescription = evaluationMap.get(key).getGeste().getDescription();
            if (gesteDescription.equals(geste)) {
                evaluationKey = key;
                break;
            }
        }
        Evaluation eval = evaluationMap.get(evaluationKey);
        if (eval != null) {
            if (role == 0) {
                eval.setNote(note);
            } else {
                eval.setNombrePratiqueAn(nbPratique);
            }
            eval.setCommentaire(commentaire);
        }
    }

    private void saveEvaluationByUtilisateur(final Evaluation eval) {
        StringRequest saveRequest = new StringRequest(Request.Method.POST, url + "/evaluation/sauvegardeEvaluationSurGeste", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                resultat.setText("Error: Code: " + errorManager.getStatusCode() + "Msg : " + errorManager.getErrorMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                if (role == 0) {
                    params.put("note", "" + eval.getNote());
                } else {
                    params.put("nbPratique", "" + eval.getNombrePratiqueAn());
                }
                params.put("commentaire", eval.getCommentaire());
                params.put("geste_id", "" + eval.getGeste().getId());
                params.put("utilisateur_id", utilisateurId.toString());
                if (atelierId != -1) {
                    params.put("atelier_id", atelierId.toString());
                }

                return params;
            }
        };
        RequestSingleton.getInstance(EvaluationGesteActivity.this).addToRequestQueue(saveRequest);
    }

    private List<Evaluation> createEvaluationList(final List<Geste> gestes) {
        List<Evaluation> evalutations = new ArrayList();
        Long i = 0L;
        for (Geste geste : gestes) {

            Evaluation eval = new Evaluation(null, 0, null, null, null);
            eval.setGeste(geste);
            eval.setId(i);
            i++;

            evalutations.add(eval);
        }
        return evalutations;
    }

    private void unLinkAtelier() {
        StringRequest linkToAtelier = new StringRequest(Request.Method.POST, url + "/user/linkToAtelier", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 System.out.println("unLinkAtelier");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                resultat.setText("Error: Code: " + errorManager.getStatusCode() + "Msg : " + errorManager.getErrorMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("utilisateur_id", utilisateurId.toString());
                params.put("atelier_id", "-1");
                return params;
            }
        };
        RequestSingleton.getInstance(this).addToRequestQueue(linkToAtelier);
    }

    private void convertUserDataToObject(int nbRow) {
        int currentRow = 0;
        int currentObject = 0;
        int id = 0;

        String gesteString = null;
        Integer note = null;
        Integer nbPratique = null;
        String commentaire = null;

        while(currentRow < nbRow) {
            TableRow rowtable = ((TableRow) evaluationGrid.getChildAt(currentRow));
            if (currentObject == 0) {
                gesteString = ((TextView) rowtable.getChildAt(0)).getText().toString();
                currentObject++;
            } else if (currentObject == 1) {
                if (role == 0) {
                    note = (int) ((RatingBar) (((LinearLayout) rowtable.getChildAt(0)).getChildAt(0))).getRating();
                } else {
                    nbPratique = Integer.parseInt((((Spinner) (((LinearLayout) rowtable.getChildAt(0)).getChildAt(1))).getSelectedItem().toString()));
                }
                currentObject++;
            } else if (currentObject == 2) {
                commentaire = ((EditText) rowtable.getChildAt(0)).getText().toString();
                mapToObject(gesteString, note, nbPratique, commentaire);
                id++;
                currentObject = 0;
            }
            currentRow++;
        }
    }

    private void generateData(String response) {
        listGeste = Arrays.asList(new Gson().fromJson(response, Geste[].class));
        listEvaluation =  createEvaluationList(listGeste);
        for (Evaluation evaluation : listEvaluation) {
            evaluationMap.put(evaluation.getId(), evaluation);
        }
        dessinGrille();
    }
}