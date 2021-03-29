package com.example.week10_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SymbolTable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.CheckedInputStream;

public class MainActivity extends AppCompatActivity {

    WebView web;
    EditText text;
    String osoite;
    String edellinen;
    String seuraava;
    ArrayList <String> historia = new ArrayList<>();
    int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = findViewById(R.id.webView);
        text = findViewById(R.id.osoiterivi);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        text.setInputType(InputType.TYPE_NULL);


        text.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    osoite = (text.getText().toString());
                    if(osoite.equals("index.html")){
                        web.loadUrl("file:///android_asset/"+osoite);
                    }
                    else{
                        if(historia.size() < limit) {
                            web.loadUrl("https:/" + osoite);
                            historia.add(osoite);
                        }
                        else if(historia.size() == limit){
                            historia.remove(0);
                            historia.add(osoite);

                        }
                        for (int i = 0; i < historia.size(); i++){
                            System.out.println(historia.get(i));
                        }
                        int koko = historia.size()-1;
                        for(int i = 0; i < koko; i++){
                            if(edellinen != null && edellinen.equals(historia.get(i))){
                                System.out.println(i);
                                int poistettu = 0;
                                while(poistettu < (koko-i-1)) {
                                    historia.remove(historia.size()-2);
                                    poistettu++;
                                }
                                break;
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void Press(View v){
        if(osoite.equals("index.html")){
            web.loadUrl("file:///android_asset/"+osoite);
            text.setText("index.html");
        }
        else{
            web.loadUrl("https:/"+osoite);
            text.setText(osoite);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ShoutOut(View v){
        web.evaluateJavascript("javascript:shoutOut()", null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Initialize(View v){
        web.evaluateJavascript("javascript:initialize()", null);
    }

    public void GoBack(View v){
        for (int i = 0; i < historia.size(); i++){
            if(historia.get(i).equals(osoite)){
                if(i == 0){
                    break;
                }
                else {
                    edellinen = historia.get(i - 1);
                    web.loadUrl("https:/" + edellinen);
                    text.setText(edellinen);
                    osoite = edellinen;
                    //System.out.println(osoite);
                }
            }
        }
    }

    public void GoNext(View v){
        int j = 0;
        for (String i : historia){
            j++;
            if(osoite.equals(i)){
                if((j) == historia.size()){
                    System.out.println("Tuleeko tänne");
                    return;
                }
                else {
                    //System.out.println(i);
                    seuraava = historia.get(j);
                    //System.out.println("#############################"+seuraava);
                    web.loadUrl("https:/" + seuraava);
                    text.setText(seuraava);
                }
            }
        } osoite = seuraava;
    }
}