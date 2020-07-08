package com.iotlab411.thingsboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iotlab411.thingsboard.Model.ModelTemp;
import com.iotlab411.thingsboard.Utils.DemoBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Chart2Activity extends DemoBase implements OnChartValueSelectedListener {

    private LineChart chart;
    // private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private ArrayList<ModelTemp.Dust> dusts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart2);
//        if (temperatures.size() > 0) {
//            intent.putParcelableArrayListExtra("temp", temperatures);
//        }
//        if (humidities.size() > 0) {
//            intent.putParcelableArrayListExtra("hum", humidities);
//        }
        dusts = getIntent().getParcelableArrayListExtra("dusts");
        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

//        seekBarX = findViewById(R.id.seekBar1);
//        seekBarX.setOnSeekBarChangeListener(this);
//
//        seekBarY = findViewById(R.id.seekBar2);
//        seekBarY.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);

        // add data
//        seekBarX.setProgress(20);
//        seekBarY.setProgress(30);

        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);

        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        if (dusts != null) {
            ModelTemp.Dust maxObject = Collections.max(dusts, new Comparator<ModelTemp.Dust>() {
                @Override
                public int compare(ModelTemp.Dust o1, ModelTemp.Dust o2) {
                    if ((Float.parseFloat(o1.getValue()) == (Float.parseFloat(o2.getValue())))) {
                        return 0;
                    } else if (Float.parseFloat(o1.getValue()) > Float.parseFloat(o2.getValue())) {
                        return -1;
                    } else if (Float.parseFloat(o1.getValue()) < Float.parseFloat(o2.getValue())) {
                        return 1;
                    }
                    return 0;
                }
            });

            leftAxis.setAxisMaximum(Float.parseFloat(maxObject.getValue()) + 40);
            rightAxis.setAxisMaximum(Float.parseFloat(maxObject.getValue())+ 40);
        }
//        tvX.setText(String.valueOf(seekBarX.getProgress()));
//        tvY.setText(String.valueOf(seekBarY.getProgress()));

        if (dusts != null) {
            setData(dusts.size(), dusts);
        }


        // redraw
        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {

    }

    private void setData(int count, ArrayList<ModelTemp.Dust> temperatureArrayList) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (Float.parseFloat((temperatureArrayList.get(i).getValue())));
            values1.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);

            set1.setValues(values1);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type

            //set2.setFillFormatter(new MyFillFormatter(900f));


            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {

    }
}