package edu.sjsu.cmpe.fourhorsemen.connectivity.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.sjsu.cmpe.fourhorsemen.connectivity.R;
import edu.sjsu.cmpe.fourhorsemen.connectivity.customviews.PINEditText;

/**
 * Created by gauravchodwadia on 5/7/17.
 */

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG = "VerificationActivity";

    @Bind(R.id.msg_email) TextView msgEmail;
    @Bind(R.id.pin_first_edittext) EditText etPin0;
    @Bind(R.id.pin_second_edittext) EditText etPin1;
    @Bind(R.id.pin_third_edittext) EditText etPin2;
    @Bind(R.id.pin_forth_edittext) EditText etPin3;
    @Bind(R.id.btn_verify) Button btnVerify;
    @Bind(R.id.link_send_code) TextView linkResendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);

        setFocusOnPinPos(0);

        etPin0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    setFocusOnPinPos(1);
                }
            }
        });

        etPin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    setFocusOnPinPos(2);
                }else{
                    setFocusOnPinPos(0);
                }
            }
        });

        etPin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    setFocusOnPinPos(3);
                }else{
                    setFocusOnPinPos(1);
                }
            }
        });

        etPin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    etPin3.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput (InputMethodManager.SHOW_FORCED, 0);
                    btnVerify.requestFocus();
                }else{
                    setFocusOnPinPos(2);
                }
            }
        });


        btnVerify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //verifying the code on click
                //TODO: logic to verify the code
                verifyCode();

            }
        });
    }



    private void setFocusOnPinPos(int pinPos) {

        switch (pinPos){
            case 0:
                etPin0.requestFocus();
                break;
            case 1:
                etPin1.requestFocus();
                break;
            case 2:
                etPin2.requestFocus();
                break;
            case 3:
                etPin3.requestFocus();
                break;
            default:
                break;
        }
    }

    private void verifyCode(){
        //-----------------------------------------------------------------------------------------
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.0.17:3000/verifyAccount";
        final String pincode_string = etPin0.getText().toString()
                +etPin1.getText().toString()
                +etPin2.getText().toString()
                +etPin3.getText();

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Log.d("--------------------",response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("email","naikrushin@gmail.com");
                params.put("code", pincode_string);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(strRequest);
//-----------------------------------------------------------------------------------------

    }

}
