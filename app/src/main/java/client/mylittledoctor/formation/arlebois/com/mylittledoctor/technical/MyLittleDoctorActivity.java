package client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public class MyLittleDoctorActivity extends AppCompatActivity {

    public static final String UTILISATEUR_ID = "Utilisateur";
    public static final String ATELIER_ID = "Atelier";
    public static final String ROLE = "Role";
    public static final String RAPPORT_MESSAGE = "Rapport";
    public static final String ETAPE_ID = "Etape";

    //public static final String url = "http://mylittledoctor-env.eu-west-3.elasticbeanstalk.com/user/create";
    public static final String url = "http://10.0.2.2:8080";
    //public static final String url = "http://86.252.111.135:8080";

    public static final String DEVELOPER_KEY = "AIzaSyAboGYy-u4Ff5MErwB1FK7FAgOtxq9ffgs";

    public Long atelierId;
    public Long utilisateurId;
    public Integer role;

    public void configNextActivity(final Context fromClass, final Class toClass) {

        Intent intent = new Intent(fromClass, toClass);

        intent.putExtra(UTILISATEUR_ID, utilisateurId);
        intent.putExtra(ATELIER_ID, atelierId);
        intent.putExtra(ROLE, role);

        startActivity(intent);
    }

    public void getContext(final Intent intent) {
        utilisateurId = intent.getLongExtra(UTILISATEUR_ID, -1);
        atelierId = intent.getLongExtra(ATELIER_ID, -1);
        role = intent.getIntExtra(ROLE, -1);
    }
}
