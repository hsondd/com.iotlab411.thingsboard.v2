package com.iotlab411.thingsboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iotlab411.thingsboard.Model.ModelToken;
import com.iotlab411.thingsboard.Utils.SharePreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<ModelToken> {
    private ApiInterface apiInterface;
    private ModelToken modelToken;
    private Button btnDevice1;
    private Button btnDevice2;
    private Button btnDevice3;
    private Button btnNguong;
    private Button btnDevice11;
    private Button btnDevice12;
    private Button btnDevice13;
    private Button btnDevice14;

    private Button btnDevice21;
    private Button btnDevice22;
    private Button btnDevice23;
    private Button btnDevice24;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {

    }

    private void initViews() {
        btnDevice1 = findViewById(R.id.btn_device1);
        btnDevice2 = findViewById(R.id.btn_device2);
        btnDevice3 = findViewById(R.id.btn_device3);

        btnDevice11 = findViewById(R.id.btn_device1_1);
        btnDevice12 = findViewById(R.id.btn_device1_2);
        btnDevice13 = findViewById(R.id.btn_device1_3);
        btnDevice14 = findViewById(R.id.btn_device1_4);

        btnDevice21 = findViewById(R.id.btn_device2_1);
        btnDevice22 = findViewById(R.id.btn_device2_2);
        btnDevice23 = findViewById(R.id.btn_device2_3);
        btnDevice24 = findViewById(R.id.btn_device2_4);

        btnDevice3 = findViewById(R.id.btn_device3);
        btnDevice3 = findViewById(R.id.btn_device3);
        btnDevice3 = findViewById(R.id.btn_device3);
        btnDevice3 = findViewById(R.id.btn_device3);

        btnNguong = findViewById(R.id.btn_nguong);
        btnNguong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LimitActivity.class);
                startActivity(intent);
            }
        });

        btnDevice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device1Activity.class);
                intent.putExtra("number",0);
                startActivity(intent);
            }
        });

        btnDevice11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device1Activity.class);
                intent.putExtra("number",1);
                startActivity(intent);
            }
        });
        btnDevice12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device1Activity.class);
                intent.putExtra("number",2);
                startActivity(intent);
            }
        });
        btnDevice13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device1Activity.class);
                intent.putExtra("number",3);
                startActivity(intent);
            }
        });

        btnDevice14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device1Activity.class);
                intent.putExtra("number",4);
                startActivity(intent);
            }
        });




        btnDevice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device2Activity.class);
                intent.putExtra("number",0);
                startActivity(intent);

            }
        });

        btnDevice21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device2Activity.class);
                intent.putExtra("number",1);
                startActivity(intent);

            }
        });

        btnDevice22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device2Activity.class);
                intent.putExtra("number",2);
                startActivity(intent);

            }
        });

        btnDevice23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device2Activity.class);
                intent.putExtra("number",3);
                startActivity(intent);

            }
        });

        btnDevice24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device2Activity.class);
                intent.putExtra("number",4);
                startActivity(intent);

            }
        });


        btnDevice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Device3Activity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
        // prepare call in Retrofit 2.0
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("username", "enl.lab411@gmail.com");
            paramObject.put("password", "ktttlab411");

            Call<ModelToken> userCall = apiInterface.login(paramObject.toString());
            userCall.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
        Log.d("Main", "onResponse: " + response);
        modelToken = response.body();
        SharePreferenceUtils.insertStringData(MainActivity.this, "token", modelToken.getToken());
        //callApiTemp1(modelToken);
    }

    @Override
    public void onFailure(Call<ModelToken> call, Throwable t) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
