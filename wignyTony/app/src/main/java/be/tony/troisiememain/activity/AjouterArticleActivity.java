package be.tony.troisiememain.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import be.tony.troisiememain.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.tony.troisiememain.assynchrone.AsyncRestClient;
import cz.msebera.android.httpclient.Header;

public class AjouterArticleActivity extends AppCompatActivity {
    private TextView erreur;
    private Button valider_article, retour;
    private String title;
    private EditText nom, description, prix, ville;
    private RadioGroup etat, reception;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_article);
        title = "Nouvel article";
        setTitle(title);

        erreur = (TextView) findViewById(R.id.erreur_ajouter_article);

        valider_article = (Button) findViewById(R.id.valider_nouvel_article);
        retour = (Button) findViewById(R.id.retour_nouveau_accueil);

        nom = (EditText) findViewById(R.id.nom_nouvel_article);
        description = (EditText) findViewById(R.id.description_nouvel_article);
        prix = (EditText) findViewById(R.id.prix_nouvel_article);
        ville = (EditText) findViewById(R.id.ville_nouvel_article);

        etat = (RadioGroup) findViewById(R.id.etat_nouvel_article);
        reception = (RadioGroup) findViewById(R.id.recuperation_nouvel_article);

        valider_article.setOnClickListener(ValiderEtAccueilActivite);
        retour.setOnClickListener(AccueilActivite);
    }

    View.OnClickListener AccueilActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener ValiderEtAccueilActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                if( nom.getText().length() > 0 && description.getText().length() > 0 && prix.getText().length() > 0 &&
                    ville.getText().length() > 0 && etat.getCheckedRadioButtonId() != -1  && reception.getCheckedRadioButtonId() !=-1){

                    postArticle();

                }
                else{
                    erreur.setText("Il manque des informations!");
                    erreur.setTextColor(Color.RED);
                }
            }
            catch(Exception e){
                erreur.setText(e.getMessage());
                erreur.setTextColor(Color.RED);
            }
        }
    };

    public void postArticle() throws JSONException {

        RequestParams paramettres = new RequestParams();
        paramettres.add("nom",nom.getText().toString());
        paramettres.add("description",description.getText().toString());
        paramettres.add("prix",prix.getText().toString());

        if(etat.getCheckedRadioButtonId()== R.id.etat_neuf_nouvel_article){
            paramettres.add("etat","1");
        }
        else{
            paramettres.add("etat","2");
        }

        paramettres.add("ville",ville.getText().toString());

        if(reception.getCheckedRadioButtonId() == R.id.poste_nouvel_article){
            paramettres.add("recuperation","1");
        }
        else{
            paramettres.add("recuperation","2");
        }

        AsyncRestClient.post("article.php",paramettres, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(AjouterArticleActivity.this, AccueilActivity.class);
                intent.putExtra("ajou_article",nom.getText()+" à été créé avec succès");
                startActivity(intent);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Intent intent = new Intent(AjouterArticleActivity.this, AccueilActivity.class);
                intent.putExtra("ajou_article",nom.getText()+" à été créé avec succès");
                startActivity(intent);
            }

        });
    }

}
