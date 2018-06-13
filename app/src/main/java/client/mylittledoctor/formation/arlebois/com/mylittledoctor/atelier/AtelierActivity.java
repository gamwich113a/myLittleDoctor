package client.mylittledoctor.formation.arlebois.com.mylittledoctor.atelier;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arlebois.formation.mylittledoctor.entite.Topo;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.MainActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.MyLittleDoctorVolleyError;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical.RequestSingleton;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.utilisateur.EvaluationGesteActivity;
import client.mylittledoctor.formation.com.mylittledoctor.entite.TopoUrl;


public class AtelierActivity extends YouTubeBaseActivity //implements YouTubePlayer.OnInitializedListener
{

    TextView titreAtelier;

    TextView descriptionTopo;
    WebView myWebView;
    YouTubePlayerView youTubeView;

    EditText resultat;

    Button previous;
    Button next;

    Long utilisateurId;
    Long atelierId;
    Integer role;

    Long etape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atelier_centrale);

        titreAtelier = findViewById(R.id.titreAtelier);
        descriptionTopo = findViewById(R.id.descriptionTopo);

        Intent intent = getIntent();
        utilisateurId = intent.getLongExtra(MyLittleDoctorActivity.UTILISATEUR_ID, -1);
        atelierId = intent.getLongExtra(MyLittleDoctorActivity.ATELIER_ID, -1);
        etape = intent.getLongExtra(MyLittleDoctorActivity.ETAPE_ID, -1);
        role = intent.getIntExtra(MyLittleDoctorActivity.ROLE, -1);

        youTubeView = findViewById(R.id.videoView);

        myWebView = findViewById(R.id.web);
        myWebView.setWebViewClient(new WebViewClient());

        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);

        previous.setActivated(!(etape == -1 || etape == 0));
        previous.setClickable(!(etape == -1 || etape == 0));
        next.setActivated(false);
        next.setClickable(false);

        loadTopo();
        searchNext();
    }

    private void loadTopo() {
        StringRequest getAtelierDetail = new StringRequest(Request.Method.POST, MainActivity.url + "/topo/getTopoByAtelierIdAndEtapeId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Topo topo = new Gson().fromJson(response, Topo.class);
                String labelEtape = "";
                if (topo.getEtape() != null) {
                    labelEtape = " - Etape : " + topo.getEtape().toString();
                }
                titreAtelier.setText(topo.getAtelier().getTitre() + labelEtape);
                descriptionTopo.setText(Html.fromHtml(topo.getDescription()));
                LinearLayout.LayoutParams layout = setLayoutSize(topo.getTopoUrlList().size());
                for (TopoUrl url : topo.getTopoUrlList()) {
                    if (url.getType().equals(1)) {
                        myWebView.setLayoutParams(layout);
                        myWebView.loadUrl(url.getUrl());
                    } else {
                        loadYouTube(url.getUrl(), layout);
                    }
                }
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
                if (etape != -1 && etape != 0) {
                    params.put("etape", etape.toString());
                }
                return params;
            }
        };
        RequestSingleton.getInstance(AtelierActivity.this).addToRequestQueue(getAtelierDetail);
    }

    private void loadYouTube(final String url, final LinearLayout.LayoutParams layout) {

        youTubeView.setLayoutParams(layout);

        youTubeView.initialize(MainActivity.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {

                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(url);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                System.out.println(youTubeInitializationResult);
            }
        });
    }

    private LinearLayout.LayoutParams setLayoutSize(final int nbView) {

        float sizeView = 0.6f / nbView;

        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                sizeView);
    }

    private void searchNext() {
        StringRequest nextTopo = new StringRequest(Request.Method.POST, MainActivity.url + "/topo/getTopoByAtelierIdAndEtapeId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Topo topo = new Gson().fromJson(response, Topo.class);
                if (topo != null) {
                    next.setActivated(true);
                    next.setClickable(true);
                }
             }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyLittleDoctorVolleyError errorManager = new MyLittleDoctorVolleyError();
                errorManager.onErrorResponse(error);
                // il ny a plus de prochain step donc fin atelier
                if(errorManager.getStatusCode() == 404) {
                    next.setActivated(true);
                    next.setClickable(true);
                    next.setText("Fin de l'atelier");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("atelier_id", atelierId.toString());
                if (etape != -1) {
                    Long nextEtape = etape + 1;
                    params.put("etape", nextEtape.toString());
                } else {
                    params.put("etape", "1");
                }
                return params;
            }
        };
        RequestSingleton.getInstance(AtelierActivity.this).addToRequestQueue(nextTopo);
    }

    public void goPreviousStep(View view) {
        setButtom(false);
        loadEtape(etape-1);
    }

    private void setButtom(Boolean isActive) {
        next.setActivated(isActive);
        next.setClickable(isActive);
        previous.setActivated(isActive);
        previous.setClickable(isActive);
    }

    public void goNextStep(View view) {
        setButtom(false);
        if ("Fin de l'atelier".equals(next.getText())) {
            loadNotationActivity();
        } else {
            if (etape + 1 < 1) {
                loadEtape(1L);
            } else {
                loadEtape(etape + 1);
            }
        }
    }

    private void loadEtape(Long etape) {
        Intent intent = nextStep(AtelierActivity.class);
        intent.putExtra(MyLittleDoctorActivity.ETAPE_ID, etape);

        startActivity(intent);
    }

    private void loadNotationActivity() {
        Intent intent = nextStep(EvaluationGesteActivity.class);

        startActivity(intent);
    }

    private Intent nextStep(Class to) {
        Intent intent = new Intent(AtelierActivity.this, to);
        intent.putExtra(MyLittleDoctorActivity.UTILISATEUR_ID, utilisateurId);
        intent.putExtra(MyLittleDoctorActivity.ATELIER_ID, atelierId);
        intent.putExtra(MyLittleDoctorActivity.ROLE, role);
        return intent;
    }

}
