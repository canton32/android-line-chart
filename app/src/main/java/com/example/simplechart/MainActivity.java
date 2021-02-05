package com.example.simplechart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import github.bandrews568.robinhoodchartclone.DataPoint;
import github.bandrews568.robinhoodchartclone.RobinhoodChartClone;
import github.bandrews568.robinhoodchartclone.TimePeriod;
import github.bandrews568.robinhoodchartclone.TimePeriodChangeListener;

public class MainActivity extends AppCompatActivity implements TimePeriodChangeListener {
    private RobinhoodChartClone robinhood_chart_clone;
    private Random random = new Random();
    private Map<TimePeriod, List<DataPoint>> dummyData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        robinhood_chart_clone = findViewById(R.id.robinhood_chart_clone);

        generateDummyData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        robinhood_chart_clone.setTimePeriodChangeListener(this);
        this.timePeriodChange(TimePeriod.DAY);
    }

    @Override
    public void timePeriodChange(@NotNull TimePeriod timePeriod) {
        robinhood_chart_clone.updateDataPoints(dummyData.get(timePeriod));
    }

    private void generateDummyData() {
        dummyData.put(TimePeriod.DAY, generateDummyData(TimePeriod.DAY));
        dummyData.put(TimePeriod.WEEK, generateDummyData(TimePeriod.WEEK));
        dummyData.put(TimePeriod.MONTH, generateDummyData(TimePeriod.MONTH));
        dummyData.put(TimePeriod.THREE_MONTH, generateDummyData(TimePeriod.THREE_MONTH));

        List<DataPoint> yearData = generateDummyData(TimePeriod.YEAR);
        dummyData.put(TimePeriod.YEAR, yearData);
        dummyData.put(TimePeriod.ALL, yearData);
    }

    private List<DataPoint> generateDummyData(TimePeriod timePeriod) {
        long epochTime = System.currentTimeMillis()/1000;
        long timeIncrease, total;

        if (timePeriod == TimePeriod.DAY) {
            timeIncrease = TimeUnit.MINUTES.toSeconds(30);
            total = 48;
        } else if (timePeriod == TimePeriod.WEEK) {
            timeIncrease = TimeUnit.HOURS.toSeconds(4);
            total = 42;
        } else if (timePeriod == TimePeriod.MONTH) {
            timeIncrease = TimeUnit.DAYS.toSeconds(3);
            total = 30;
        } else if (timePeriod == TimePeriod.THREE_MONTH) {
            timeIncrease = TimeUnit.DAYS.toSeconds(1);
            total = 30;
        } else {
            timeIncrease = TimeUnit.DAYS.toSeconds(7);
            total = 52;
        }

        ArrayList<DataPoint> res = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            res.add(0, new DataPoint(15 + random.nextFloat() * 5, epochTime));
            epochTime -= timeIncrease;
        }

        return res;
    }
}