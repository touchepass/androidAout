package be.tony.troisiememain.wizzard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;

import be.tony.troisiememain.R;
import be.tony.troisiememain.assynchrone.AsyncRestClient;
import be.tony.troisiememain.activity.RechercherVilleActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class villeWizzard extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {

    private TextView erreur;
    private Spinner resultat;
    private Button valider_recherche, retour;
    private EditText recherche;
    private String title, nom, ville;
    private String [] res_recherche;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ville_wizzard);

        erreur = (TextView) findViewById(R.id.search_err);

        resultat = (Spinner) findViewById(R.id.search_result);

        valider_recherche = (Button) findViewById(R.id.search_valider);
        valider_recherche.setVisibility(View.INVISIBLE);
        retour = (Button) findViewById(R.id.search_retour);

        recherche = (EditText) findViewById(R.id.search);

        title = "Nouvel article";
        setTitle(title);

        valider_recherche.setOnClickListener(ValiderRecherche);
        retour.setOnClickListener(RetourRecherrcheParVille);
        recherche.addTextChangedListener(this);

    }

    View.OnClickListener RetourRecherrcheParVille = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener ValiderRecherche = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(villeWizzard.this, RechercherVilleActivity.class);
            intent.putExtra("nom_article", nom);
            intent.putExtra("nom_ville", resultat.getSelectedItem().toString());
            startActivity(intent);
        }
    };


    public void getCityCP(String name) throws JSONException {
        AsyncRestClient.get("city.php?nom=" + name, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);
                // C'est comme de l'ajax ici hien
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response);
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSeq, int arg1, int arg2,int arg3) {
        System.out.println("y");
    }

    @Override
    public void onTextChanged(CharSequence charSeq, int arg1, int arg2, int arg3) {
        boolean estNombre = false;
        try{
            int cp= Integer.parseInt(recherche.getText().toString());
            estNombre = true;
        }
        catch(Exception e){

        }

        AsyncRestClient.get( (estNombre ? "city.php?cp=" : "city.php?nom=") + recherche.getText() , null, new JsonHttpResponseHandler() {
           /*
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);
                // C'est comme de l'ajax ici hien
            }
            */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {
                    String[] items = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        String tmp = (response.getJSONObject(i).get("nom").toString());
                        items[i]= tmp;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(villeWizzard.this,android.R.layout.simple_spinner_item,items);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    resultat.setAdapter(adapter);
                    resultat.setOnItemSelectedListener(villeWizzard.this);

                } catch ( Exception e ) {

                }
            }

        });
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        valider_recherche.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        valider_recherche.setVisibility(View.INVISIBLE);
    }
}
