package com.example.simplechart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import github.bandrews568.robinhoodchartclone.DataPoint;
import github.bandrews568.robinhoodchartclone.RobinhoodChartClone;
import github.bandrews568.robinhoodchartclone.TimePeriod;
import github.bandrews568.robinhoodchartclone.TimePeriodChangeListener;

public class MainActivity extends AppCompatActivity implements TimePeriodChangeListener {
    RobinhoodChartClone robinhood_chart_clone;

    private Random random = new Random();

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
        robinhood_chart_clone.updateDataPoints(getDummyData((timePeriod)));
    }

    private void generateDummyData() {

    }

    private List<DataPoint> getDummyData(TimePeriod timePeriod) {
        long epochTime = 1612526965L;
        long timeIncrease, total;

        if (timePeriod == TimePeriod.DAY) {
            timeIncrease = TimeUnit.MINUTES.toSeconds(30);
            total = 48;
        } else if (timePeriod == TimePeriod.WEEK) {
            timeIncrease = TimeUnit.HOURS.toSeconds(4);
            total = 42;
        } else if (timePeriod == TimePeriod.MONTH || timePeriod == TimePeriod.THREE_MONTH) {
            timeIncrease = TimeUnit.DAYS.toSeconds(1);
            total = 30;
        } else {
            timeIncrease = TimeUnit.DAYS.toSeconds(7);
            total = 52;
        }

        ArrayList<DataPoint> res = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            epochTime += timeIncrease;
            res.add(new DataPoint(15 + random.nextFloat() * 5, epochTime));
        }

        return res;
    }
}