package com.iotlab411.thingsboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.iotlab411.thingsboard.Model.ModelTemp;
import com.iotlab411.thingsboard.Utils.SharePreferenceUtils;
import com.iotlab411.thingsboard.Utils.Utils;
import com.iotlab411.thingsboard.adapter.DustAdapter;
import com.iotlab411.thingsboard.adapter.HumidityAdapter;
import com.iotlab411.thingsboard.adapter.TempAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Device1Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ToggleButton toggleNew;
    private ToggleButton toggleHistory;
    private Button toggleTemp;
    private Button toggleHumidity;
    private Button toggleDust;
    private EditText edtFrom;
    private EditText edtTo;
    private Button btnChart;
    private Button btnNguong;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayoutDate;
    private RecyclerView recyclerViewList;
    private TempAdapter tempAdapter;
    private HumidityAdapter humidityAdapter;
    private DustAdapter dustAdapter;
    private ApiInterface apiInterface;
    private ArrayList<ModelTemp.Temperature> temperatures = new ArrayList<>();
    private ArrayList<ModelTemp.Humidity> humidities = new ArrayList<>();
    private ArrayList<ModelTemp.Dust> dusts = new ArrayList<>();
    private String token = "";
    private Integer isTemp = 0;
    private Boolean isNew = true;
    private String currentDate;
    Calendar date;
    private Float maxM3;
    private Float maxTemp;
    private Float maxHum;
    private int number = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device1);
        token = SharePreferenceUtils.getStringData(Device1Activity.this, "token");
        number = getIntent().getIntExtra("number", 0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        maxTemp = SharePreferenceUtils.getFloatData(Device1Activity.this, "maxTemp");
        maxHum = SharePreferenceUtils.getFloatData(Device1Activity.this, "maxHum");
        maxM3 = SharePreferenceUtils.getFloatData(Device1Activity.this, "maxM3");

        initViews();
        initData();
    }

    private void initData() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        currentDate = dateformat.format(c.getTime());
        edtTo.setText(currentDate);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        System.out.println(format.format(cal.getTime()));

        edtFrom.setText(format.format(cal.getTime()));
        edtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Device1Activity.this, "lkk", Toast.LENGTH_SHORT).show();
                showDateTimePickerFrom();
            }
        });
        if (isNew) {
            if (number == 0) {
                callApiNew();
            } else if (number == 1) {
                callApiNew1();
            } else if (number == 2) {
                callApiNew2();
            } else if (number == 3) {
                callApiNew3();
            } else if (number == 4) {
                callApiNew4();
            }

        } else {
            if (number == 0) {
                callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
            } else if (number == 1) {
                callApiLast1(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
            } else if (number == 2) {
                callApiLast2(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
            } else if (number == 3) {
                callApiLast3(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
            } else if (number == 4) {
                callApiLast4(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
            }
        }

        edtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePickerTo();
            }
        });
    }

    public void showDateTimePickerFrom() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(Device1Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(Device1Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v("TAG", "The choosen one " + date.getTime());
                        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                        edtFrom.setText(dateformat.format(date.getTime()));
                        callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void showDateTimePickerTo() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(Device1Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(Device1Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v("TAG", "The choosen one " + date.getTime());
                        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                        edtTo.setText(dateformat.format(date.getTime()));
                        callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void callApiNew() {
        temperatures.clear();
        humidities.clear();
        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getLastTemp1("humidity,temperature,dust", "Bearer " + token).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Log.d("Main", "onResponse: " + response);
                Utils.dismissProgressDialog();
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        if ((maxTemp - Float.parseFloat(temperatures.get(0).getValue()) < 0) && (maxTemp != 0)) {
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng");
                            showNotification(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));
                        }
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        if ((maxHum - Float.parseFloat(humidities.get(0).getValue()) < 0) && (maxHum != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        if ((maxM3 - Float.parseFloat(dusts.get(0).getValue()) < 0) && (maxM3 != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {
                Log.d("Main", "onFailure: " + t.getMessage());
            }
        });
    }

    private void callApiNew1() {
        temperatures.clear();
        humidities.clear();
        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getLastTemp11("humidity,temperature,dust", "Bearer " + token).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Log.d("Main", "onResponse: " + response);
                Utils.dismissProgressDialog();
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        if ((maxTemp - Float.parseFloat(temperatures.get(0).getValue()) < 0) && (maxTemp != 0)) {
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng");
                            showNotification(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));
                        }
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        if ((maxHum - Float.parseFloat(humidities.get(0).getValue()) < 0) && (maxHum != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        if ((maxM3 - Float.parseFloat(dusts.get(0).getValue()) < 0) && (maxM3 != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {
                Log.d("Main", "onFailure: " + t.getMessage());
            }
        });
    }

    private void callApiNew2() {
        temperatures.clear();
        humidities.clear();
        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getLastTemp12("humidity,temperature,dust", "Bearer " + token).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Log.d("Main", "onResponse: " + response);
                Utils.dismissProgressDialog();
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        if ((maxTemp - Float.parseFloat(temperatures.get(0).getValue()) < 0) && (maxTemp != 0)) {
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng");
                            showNotification(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));
                        }
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        if ((maxHum - Float.parseFloat(humidities.get(0).getValue()) < 0) && (maxHum != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        if ((maxM3 - Float.parseFloat(dusts.get(0).getValue()) < 0) && (maxM3 != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {
                Log.d("Main", "onFailure: " + t.getMessage());
            }
        });
    }

    private void callApiNew3() {
        temperatures.clear();
        humidities.clear();
        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getLastTemp13("humidity,temperature,dust", "Bearer " + token).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Log.d("Main", "onResponse: " + response);
                Utils.dismissProgressDialog();
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        if ((maxTemp - Float.parseFloat(temperatures.get(0).getValue()) < 0) && (maxTemp != 0)) {
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng");
                            showNotification(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));
                        }
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                }  else if(isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        if ((maxHum - Float.parseFloat(humidities.get(0).getValue()) < 0) && (maxHum != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        if ((maxM3 - Float.parseFloat(dusts.get(0).getValue()) < 0) && (maxM3 != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {
                Log.d("Main", "onFailure: " + t.getMessage());
            }
        });
    }

    private void callApiNew4() {
        temperatures.clear();
        humidities.clear();
        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getLastTemp14("humidity,temperature,dust", "Bearer " + token).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Log.d("Main", "onResponse: " + response);
                Utils.dismissProgressDialog();
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        if ((maxTemp - Float.parseFloat(temperatures.get(0).getValue()) < 0) && (maxTemp != 0)) {
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng");
                            showNotification(Device1Activity.this, "Thông báo", "Nhiệt độ vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));
                        }
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        if ((maxHum - Float.parseFloat(humidities.get(0).getValue()) < 0) && (maxHum != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Độ ẩm vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        if ((maxM3 - Float.parseFloat(dusts.get(0).getValue()) < 0) && (maxM3 != 0)) {
                            Utils.dialogQuaNguong(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng");
                            Toast.makeText(Device1Activity.this, "Cảnh báo", Toast.LENGTH_SHORT).show();
                            showNotification(Device1Activity.this, "Thông báo", "Bụi vượt ngưỡng", new Intent(Device1Activity.this, MainActivity.class));

                        }
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {
                Log.d("Main", "onFailure: " + t.getMessage());
            }
        });
    }

    private void callApiLast(Long start, Long end) {
        temperatures.clear();
        humidities.clear();

        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getTemp1("humidity,temperature,dust", start, end, 99999999999999L, 100L,
                "NONE", "Bearer " + token
        ).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Utils.dismissProgressDialog();
                Log.d("Device1", "onResponse: " + response);
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {

            }
        });
    }

    private void callApiLast1(Long start, Long end) {
        temperatures.clear();
        humidities.clear();

        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getTemp11("humidity,temperature,dust", start, end, 99999999999999L, 100L,
                "NONE", "Bearer " + token
        ).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Utils.dismissProgressDialog();
                Log.d("Device1", "onResponse: " + response);
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {

            }
        });
    }

    private void callApiLast2(Long start, Long end) {
        temperatures.clear();
        humidities.clear();

        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getTemp12("humidity,temperature,dust", start, end, 99999999999999L, 100L,
                "NONE", "Bearer " + token
        ).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Utils.dismissProgressDialog();
                Log.d("Device1", "onResponse: " + response);
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {

            }
        });
    }

    private void callApiLast3(Long start, Long end) {
        temperatures.clear();
        humidities.clear();

        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getTemp13("humidity,temperature,dust", start, end, 99999999999999L, 100L,
                "NONE", "Bearer " + token
        ).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Utils.dismissProgressDialog();
                Log.d("Device1", "onResponse: " + response);
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {

            }
        });
    }


    private void callApiLast4(Long start, Long end) {
        temperatures.clear();
        humidities.clear();

        Utils.showProgressDialog(Device1Activity.this);
        apiInterface.getTemp14("humidity,temperature,dust", start, end, 99999999999999L, 100L,
                "NONE", "Bearer " + token
        ).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Utils.dismissProgressDialog();
                Log.d("Device1", "onResponse: " + response);
                if (isTemp == 0) {
                    if (response.body().getTemperature() != null && response.body().getTemperature().size() > 0) {
                        temperatures.addAll(response.body().getTemperature());
                        tempAdapter = new TempAdapter(temperatures, Device1Activity.this, new TempAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(tempAdapter);
                    } else {
                        tempAdapter.notifyDataSetChanged();
                    }
                } else if(isTemp == 1) {
                    if (response.body().getHumidity() != null && response.body().getHumidity().size() > 0) {
                        humidities.addAll(response.body().getHumidity());
                        humidityAdapter = new HumidityAdapter(humidities, Device1Activity.this, new HumidityAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(humidityAdapter);
                    } else {
                        humidityAdapter.notifyDataSetChanged();
                    }
                } else if (isTemp == 2) {
                    if (response.body().getDust() != null && response.body().getDust().size() > 0) {
                        dusts.addAll(response.body().getDust());
                        dustAdapter = new DustAdapter(dusts, Device1Activity.this, new DustAdapter.OnItemClickListener() {
                            @Override
                            public void onCLickItem(int position) {

                            }
                        });
                        recyclerViewList.setAdapter(dustAdapter);
                    } else {
                        dustAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {

            }
        });
    }

    private void initViews() {

        toggleNew = findViewById(R.id.toggle_new);
        btnChart = findViewById(R.id.btn_chart);
        btnNguong = findViewById(R.id.btn_nguong);
        toggleHistory = findViewById(R.id.toggle_history);
        toggleTemp = findViewById(R.id.toggle_temp);
        toggleHumidity = findViewById(R.id.toggle_humidity);
        toggleDust = findViewById(R.id.toggle_dust);
        recyclerViewList = findViewById(R.id.rcv_list);
        linearLayoutDate = findViewById(R.id.linear_date);
        swipeRefreshLayout = findViewById(R.id.swipe);
        edtFrom = findViewById(R.id.edt_from);
        edtTo = findViewById(R.id.edt_to);
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Device1Activity.this, Chart1Activity.class);
                if (temperatures.size() > 0) {
                    intent.putParcelableArrayListExtra("temp", temperatures);
                }
                if (humidities.size() > 0) {
                    intent.putParcelableArrayListExtra("hum", humidities);
                }

                startActivity(intent);
            }
        });
        btnNguong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Device1Activity.this, LimitActivity.class);
                startActivityForResult(intent, 99);
            }
        });
        toggleNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    toggleHistory.setChecked(false);
//                    if (SharePreferenceUtils.getUserInfo(ProductDetailActivity.this) != null) {
//                        relayComment.setVisibility(View.VISIBLE);
//                    } else {
//                        relayComment.setVisibility(View.GONE);
//                    }

                    //getData();

                    isNew = true;
                    if (number == 0) {
                        callApiNew();
                    } else if (number == 1) {
                        callApiNew1();
                    } else if (number == 2) {
                        callApiNew2();
                    } else if (number == 3) {
                        callApiNew3();
                    } else if (number == 4) {
                        callApiNew4();
                    }
                    linearLayoutDate.setVisibility(View.GONE);
                } else {
                    if (!toggleHistory.isChecked()) {
                        buttonView.setChecked(true);
                    }
                }
            }
        });
        toggleHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
//                    relayComment.setVisibility(View.INVISIBLE);
//                    getReviewListReview();
                    isNew = false;
                    linearLayoutDate.setVisibility(View.VISIBLE);
                    if (number == 0) {
                        callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    } else if (number == 1) {
                        callApiLast1(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    } else if (number == 2) {
                        callApiLast2(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    } else if (number == 3) {
                        callApiLast3(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    } else if (number == 4) {
                        callApiLast4(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    }
                    toggleNew.setChecked(false);
                } else {
                    if (!toggleNew.isChecked()) {
                        buttonView.setChecked(true);
                    }
                }
            }
        });

        toggleTemp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                toggleTemp.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.colorAccent));
                toggleHumidity.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.white));
                toggleDust.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.white));
                isTemp = 0;
                if (isNew) {
                    callApiNew();
                } else {
                    callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                }
            }
        });

        toggleHumidity.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                isTemp = 1;
                toggleHumidity.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.colorAccent));
                toggleTemp.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.white));
                toggleDust.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.white));
                if (isNew) {
                    callApiNew();
                } else {
                    callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                }
            }
        });

        toggleDust.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                isTemp = 2;
                toggleDust.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.colorAccent));
                toggleTemp.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.white));
                toggleHumidity.setBackgroundColor(ContextCompat.getColor(Device1Activity.this,R.color.white));
                if (isNew) {
                    callApiNew();
                } else {
                    callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                }
            }
        });


    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        int id = createID();
        notificationManager.notify(id, mBuilder.build());
    }

    public int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
        return id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                maxTemp = SharePreferenceUtils.getFloatData(Device1Activity.this, "maxTemp");
                maxHum = SharePreferenceUtils.getFloatData(Device1Activity.this, "maxHum");
                initData();
            }
        }
    }


    @Override
    public void onRefresh() {
        initData();
        swipeRefreshLayout.setRefreshing(false);
    }
}
