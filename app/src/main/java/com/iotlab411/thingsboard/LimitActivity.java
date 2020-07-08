package com.iotlab411.thingsboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iotlab411.thingsboard.Utils.SharePreferenceUtils;

public class LimitActivity extends AppCompatActivity {

    private Button btnSaveTemp;
    private Button btnSaveHum;
    private Button btnSaveM3;
    private EditText edtTemp;
    private EditText edtHum;
    private EditText edtM3;
    private TextView tvTemp;
    private TextView tvHum;
    private TextView tvM3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit);

        edtTemp = findViewById(R.id.edt_temp);
        edtHum = findViewById(R.id.edt_hum);
        edtM3 = findViewById(R.id.edt_m3);
        tvTemp = findViewById(R.id.tv_max_temp);
        tvHum = findViewById(R.id.tv_max_hum);
        tvM3 = findViewById(R.id.tv_max_m3);
        btnSaveTemp = findViewById(R.id.btn_save_temp);
        btnSaveHum = findViewById(R.id.btn_save_hum);
        btnSaveM3 = findViewById(R.id.btn_save_m3);
        tvTemp.setText("Ngưỡng nhiệt độ: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxTemp"));
        tvHum.setText("Ngưỡng độ ẩm: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxHum"));
        tvM3.setText("Ngưỡng bụi PM2.5: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxM3"));
        btnSaveTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTemp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LimitActivity.this, "Vui lòng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LimitActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    SharePreferenceUtils.insertFloatData(LimitActivity.this, "maxTemp", Float.parseFloat(edtTemp.getText().toString()));
                    tvTemp.setText("Ngưỡng nhiệt độ: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxTemp"));
                    tvHum.setText("Ngưỡng độ ẩm: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxHum"));
                    tvM3.setText("Ngưỡng bụi PM2.5: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxM3"));
                    Intent intent = new Intent();
                    intent.putExtra("id", "Some Value Here to return");
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        btnSaveHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtHum.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LimitActivity.this, "Vui lòng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LimitActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    SharePreferenceUtils.insertFloatData(LimitActivity.this, "maxHum", Float.parseFloat(edtHum.getText().toString()));
                    tvTemp.setText("Ngưỡng nhiệt độ: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxTemp"));
                    tvHum.setText("Ngưỡng độ ẩm: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxHum"));
                    tvM3.setText("Ngưỡng bụi PM2.5: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxM3"));
                    Intent intent = new Intent();
                    intent.putExtra("id", "Some Value Here to return");
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        btnSaveM3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtM3.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LimitActivity.this, "Vui lòng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LimitActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    SharePreferenceUtils.insertFloatData(LimitActivity.this, "maxM3", Float.parseFloat(edtM3.getText().toString()));
                    tvTemp.setText("Ngưỡng nhiệt độ: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxTemp"));
                    tvHum.setText("Ngưỡng độ ẩm: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxHum"));
                    tvM3.setText("Ngưỡng bụi PM2.5: " + SharePreferenceUtils.getFloatData(LimitActivity.this, "maxM3"));
                    Intent intent = new Intent();
                    intent.putExtra("id", "Some Value Here to return");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}