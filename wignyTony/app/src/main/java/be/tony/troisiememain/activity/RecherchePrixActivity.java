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
import cz.msebera.android.httpclient.Header;

public class RecherchePrixActivity extends AppCompatActivity {

    private Button valider_rech_prix, retour_rech_prix;
    private EditText nom, prix_min, prix_max;
    private String title;
    private TextView erreur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_prix);

        title = "Recherche par prix";
        setTitle(title);

        valider_rech_prix = (Button) findViewById(R.id.valider_prix);
        retour_rech_prix = (Button) findViewById(R.id.retour_prix);

        nom = (EditText)findViewById(R.id.nom_prix);
        prix_min = (EditText)findViewById(R.id.min_prix);
        prix_max = (EditText)findViewById(R.id.max_prix);

        erreur = (TextView) findViewById(R.id.prix_err);


        valider_rech_prix.setOnClickListener(ValiderRechPrixActivite);
        retour_rech_prix.setOnClickListener(AccueilActivite);
    }

    View.OnClickListener AccueilActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           finish();
        }
    };

    View.OnClickListener ValiderRechPrixActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                if(nom.getText().length()!= 0 && prix_min.getText().length()>0 && prix_max.getText().length() >0){
                    if(Long.valueOf(prix_min.getText().toString())< Long.valueOf(prix_max.getText().toString())){
                        AsyncRestClient.get("article.php?nom="+ nom.getText()+"&min="+prix_min.getText()+"&max="+prix_max.getText(),null, new JsonHttpResponseHandler(){

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

                                    Intent intent = new Intent(RecherchePrixActivity.this, AffichageResultatActivity.class);
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
                        erreur.setText("Le prix minimum ne peut être suppérieur au prix maximum!");
                        erreur.setTextColor(Color.RED);
                    }
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
}
