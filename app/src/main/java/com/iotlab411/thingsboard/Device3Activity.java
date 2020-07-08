package com.iotlab411.thingsboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.iotlab411.thingsboard.Model.ModelTemp;
import com.iotlab411.thingsboard.Utils.SharePreferenceUtils;
import com.iotlab411.thingsboard.Utils.Utils;
import com.iotlab411.thingsboard.adapter.ImageAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Device3Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ToggleButton toggleNew;
    private ToggleButton toggleHistory;
    private RecyclerView recyclerViewList;
    private ToggleButton toggleTemp;
    private ToggleButton toggleHumidity;
    private LinearLayout linearDate;
    private ImageAdapter imageAdapter;
    private ApiInterface apiInterface;
    private EditText edtFrom;
    private EditText edtTo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ModelTemp.Image> images = new ArrayList<>();

    private String token = "";
    private Boolean isTemp = true;
    private Boolean isNew = true;

    private String currentDate;
    Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device3);
        token = SharePreferenceUtils.getStringData(Device3Activity.this, "token");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            apiInterface = retrofit.create(ApiInterface.class);
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

        edtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePickerTo();
            }
        });
        if(isNew) {
            callApiNew();
        } else {
            callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
        }
    }

    public void showDateTimePickerFrom() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(Device3Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(Device3Activity.this, new TimePickerDialog.OnTimeSetListener() {
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
        new DatePickerDialog(Device3Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(Device3Activity.this, new TimePickerDialog.OnTimeSetListener() {
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

    private void callApiNew() {
        images.clear();
        Utils.showProgressDialog(Device3Activity.this);
        apiInterface.getLastTemp3("Image", "Bearer " + token).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Log.d("Main", "onResponse: " + response);
                Utils.dismissProgressDialog();
                if (response.body().getImage() != null && response.body().getImage().size() > 0) {
                    images.addAll(response.body().getImage());
                    imageAdapter = new ImageAdapter(images, Device3Activity.this, new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onCLickItem(int position) {

                        }
                    });
                    recyclerViewList.setAdapter(imageAdapter);
                } else {
                    imageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {

            }
        });
    }

    private void callApiLast(Long start, Long end) {
        images.clear();
        Utils.showProgressDialog(Device3Activity.this);
        apiInterface.getTemp3("Image", start, end, 99999999999999L, 100L,
                "NONE", "Bearer " + token
        ).enqueue(new Callback<ModelTemp>() {
            @Override
            public void onResponse(Call<ModelTemp> call, Response<ModelTemp> response) {
                Utils.dismissProgressDialog();
                Log.d("Device3", "onResponse: " + response);
                if (response.body().getImage() != null && response.body().getImage().size() > 0) {
                    images.addAll(response.body().getImage());
                    imageAdapter = new ImageAdapter(images, Device3Activity.this, new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onCLickItem(int position) {

                        }
                    });
                    recyclerViewList.setAdapter(imageAdapter);
                } else {
                    imageAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ModelTemp> call, Throwable t) {

            }
        });
    }

    private void initViews() {

        toggleNew = findViewById(R.id.toggle_new);
        toggleHistory = findViewById(R.id.toggle_history);
        toggleTemp = findViewById(R.id.toggle_temp);
        toggleHumidity = findViewById(R.id.toggle_humidity);
        recyclerViewList = findViewById(R.id.rcv_list);
        linearDate = findViewById(R.id.linear_date);
        edtFrom = findViewById(R.id.edt_from);
        edtTo = findViewById(R.id.edt_to);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

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
                    linearDate.setVisibility(View.GONE);
                    callApiNew();
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
                    linearDate.setVisibility(View.VISIBLE);
                    callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    toggleNew.setChecked(false);
                } else {
                    if (!toggleNew.isChecked()) {
                        buttonView.setChecked(true);
                    }
                }
            }
        });

        toggleTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    toggleHumidity.setChecked(false);
//                    if (SharePreferenceUtils.getUserInfo(ProductDetailActivity.this) != null) {
//                        relayComment.setVisibility(View.VISIBLE);
//                    } else {
//                        relayComment.setVisibility(View.GONE);
//                    }

                    //getData();

                    isTemp = true;
                    if (isNew) {
                        callApiNew();
                    } else {
                        callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    }

                } else {
                    if (!toggleHumidity.isChecked()) {
                        buttonView.setChecked(true);
                    }
                }
            }
        });
        toggleHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
//                    relayComment.setVisibility(View.INVISIBLE);
//                    getReviewListReview();
                    isTemp = false;
                    if (isNew) {
                        callApiNew();
                    } else {
                        callApiLast(Utils.milliseconds(edtFrom.getText().toString()), Utils.milliseconds(edtTo.getText().toString()));
                    }
                    toggleTemp.setChecked(false);
                } else {
                    if (!toggleTemp.isChecked()) {
                        buttonView.setChecked(true);
                    }
                }
            }
        });


    }

    @Override
    public void onRefresh() {
        initData();
        swipeRefreshLayout.setRefreshing(false);
    }
}
