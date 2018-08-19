package be.tony.troisiememain.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import be.tony.troisiememain.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import be.tony.troisiememain.Classe.Article;
import be.tony.troisiememain.Classe.ArticleList;
import be.tony.troisiememain.assynchrone.AsyncRestClient;
import be.tony.troisiememain.wizzard.villeWizzard;
import cz.msebera.android.httpclient.Header;

public class RechercherVilleActivity extends AppCompatActivity {

    private String title, nom_art, nom_ville;
    private Button valider_rech_ville, retour_rech_ville, wizzard_ville;
    private EditText nom, ville;
    private TextView erreur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_ville);

        title = "Nouvel article";
        setTitle(title);

        valider_rech_ville = (Button)  findViewById(R.id.valider_ville);
        retour_rech_ville = (Button)  findViewById(R.id.retour_ville);
        wizzard_ville = (Button)  findViewById(R.id.recherche_ville_wizz);

        nom = (EditText)findViewById(R.id.nom_ville);
        ville = (EditText) findViewById(R.id.txt_res_recherche);


        erreur = (TextView) findViewById(R.id.ville_err);

        valider_rech_ville.setOnClickListener(ValiderRechVilleActivite);
        retour_rech_ville.setOnClickListener(AccueilActivite);
        wizzard_ville.setOnClickListener(wizzardVille);

        Intent recup_nom = getIntent();
        nom_art = recup_nom.getStringExtra("nom_article");
        nom_ville = recup_nom.getStringExtra("nom_ville");
        if(nom_art != null){
            nom.setText(nom_art.toString());
        }
        if(nom_ville != null){
            ville.setText(nom_ville.toString());
        }
    }

    View.OnClickListener AccueilActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener ValiderRechVilleActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{

                if(nom.getText().length() > 0 && ville.getText().length() > 0){
                    AsyncRestClient.get("article.php?nom="+ nom.getText()+"&ville="+ville.getText(),null, new JsonHttpResponseHandler(){

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            System.out.println("o");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            try{
                                String tmp_nom, tmp_desc, tmp_ville;
                                int tmp_etat, tmp_recept;
                                double tmp_prix;

                                ArticleList lst = new ArticleList();
                                for (int i = 0; i < response.length(); i++) {
                                    tmp_nom = (response.getJSONObject(i).get("nom").toString());
                                    tmp_desc = (response.getJSONObject(i).get("description").toString());
                                    tmp_prix= Double.parseDouble(response.getJSONObject(i).get("prix").toString());
                                    tmp_etat = Integer.parseInt(response.getJSONObject(i).get("etat").toString());
                                    tmp_ville = (response.getJSONObject(i).get("ville").toString());
                                    tmp_recept = Integer.parseInt(response.getJSONObject(i).get("recuperation").toString());

                                    Article a = new Article(tmp_nom,tmp_desc,tmp_prix,tmp_etat,tmp_ville,tmp_recept);
                                    lst.add(a);
                                }

                                Intent intent = new Intent(RechercherVilleActivity.this, AffichageResultatActivity.class);
                                intent.putExtra("listeArticle",(Parcelable)lst);
                                startActivity(intent);
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                        }

                    });
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

    View.OnClickListener wizzardVille = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try{
                Intent intent = new Intent(RechercherVilleActivity.this, villeWizzard.class);
                intent.putExtra("nom_article", nom.getText().toString());
                startActivity(intent);
            }

            catch(Exception e){
                erreur.setText(e.getMessage());
                erreur.setTextColor(Color.RED);
            }
        }
    };
}
