package com.example.beer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {
    private static final String api_url = "https://api.punkapi.com/v2/beers";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText editText_search = findViewById(R.id.editText_search);
        final EditText editText_date1 = findViewById(R.id.editTextDate);
        final EditText editText_date2 = findViewById(R.id.editTextDate2);
        final Switch switch_highPoint = findViewById(R.id.switch1);
        final Button button_search = findViewById(R.id.button_next);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();

                String text_search = editText_search.getText().toString().trim();
                if (!text_search.isEmpty())
                    params.put("beer_name",text_search);

                String date1 = editText_date1.getText().toString().trim();
                if (!date1.isEmpty()){
                    // check regex, if incorrect, error pop up
                    if (validDate(date1))
                        params.put("brewed_after",date1.replace('/', '-'));
                    else
                        errorPopUp();
                }

                String date2 = editText_date2.getText().toString().trim();
                if (!date2.isEmpty()){
                    // check regex, if incorrect, error pop up
                    if (validDate(date2))
                        params.put("brewed_before",date2.replace('/', '-'));
                    else
                        errorPopUp();
                }

                if (switch_highPoint.isChecked())
                    params.put("abv_get", 4.0);

                client.addHeader("Accept","application/json");
                client.get(api_url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("success", new String(responseBody));
                        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                        intent.putExtra("data", new String(responseBody));
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("error", new String(responseBody));
                    }
                });
            }
        });
    }
    private boolean validDate(String date){
        String date_regex = "^(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(date_regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    private void errorPopUp() {
        AlertDialog dialog = new AlertDialog.Builder(SecondActivity.this, R.style.Theme_AppCompat_Light)
                .setTitle("Error")
                .setMessage("Incorrect Date Format")
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }
}
