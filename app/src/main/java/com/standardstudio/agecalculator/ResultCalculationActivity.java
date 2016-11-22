package com.standardstudio.agecalculator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Weeks;

import java.util.Calendar;

public class ResultCalculationActivity extends AppCompatActivity {

    private TextView todayDate;
    private ImageView todayDateSelector;
    private DatePicker todayDateDialog;
    private TextView yourBirthdayDate;
    private ImageView yourBirthdayDateSelector;
    private int year;
    private int month;
    private int day;
    private int startYear = 2017;
    private int startMonth = 4;
    private int startDay = 19;
    private int currentdate, curentmonth, curentyear, birthdaydate, birtdate, birhmonth, bithyear;
    int choser;
    static final int DATE_START_DIALOG_ID = 0;
    public static final int DATE_PICKER_FRAGMENT = 1;
    public static final int SECOND_DATE_PICKER_FRAGMENT = 2;
    private TextView currentBirthdayYear;
    private TextView currentBirthdayMonth;
    private TextView currentBirthdayDay;
    private TextView nextBirthdayYear;
    private TextView nextBirthdayMonth;
    private TextView nextBirthdayDay;
    private TextView totalYearResult;
    private TextView totalMonthResult;
    private TextView totalWeekResult;
    private TextView totalDayResult;
    private TextView totalHourResult;
    private TextView totalMinuteResult;
    private TextView totalSecondResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_calculation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());

        set_defaultdialogtime();
        currentBirthdayMonth = (TextView) findViewById(R.id.months_in_age);
        currentBirthdayDay = (TextView) findViewById(R.id.days_in_age);
        currentBirthdayYear = (TextView) findViewById(R.id.years_in_age);
        nextBirthdayYear = (TextView) findViewById(R.id.years_in_birthday);
        nextBirthdayMonth = (TextView) findViewById(R.id.months_in_birthday);
        nextBirthdayDay = (TextView) findViewById(R.id.days_in_birthday);
        totalYearResult = (TextView) findViewById(R.id.total_years_result);
        totalMonthResult = (TextView) findViewById(R.id.total_months_result);
        totalWeekResult = (TextView) findViewById(R.id.total_weeks_result);
        totalDayResult = (TextView) findViewById(R.id.total_days_result);
        totalHourResult = (TextView) findViewById(R.id.total_hours_result);
        totalMinuteResult = (TextView) findViewById(R.id.total_minutes_result);
        totalSecondResult = (TextView) findViewById(R.id.total_seconds_result);
        todayDate = (TextView) findViewById(R.id.display_today_date);
        todayDateSelector = (ImageView) findViewById(R.id.set_date_icon);
        yourBirthdayDate = (TextView) findViewById(R.id.display_birthday_date);
        yourBirthdayDateSelector = (ImageView) findViewById(R.id.set_birthday_icon);
        Button calculateButton = (Button) findViewById(R.id.button_calculate);
        Button clearButton = (Button) findViewById(R.id.button_clear);
        todayDate.setText(setCurrentDateOnView());
        yourBirthdayDate.setText(setCurrentDateOnView());
        todayDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choser = 1;
                showDialog(DATE_START_DIALOG_ID);
                /*DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");*/


            }
        });
        yourBirthdayDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");*/
                choser = 2;
                showDialog(DATE_START_DIALOG_ID);
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTodayDate = todayDate.getText().toString();
                String getBirthdayDate = yourBirthdayDate.getText().toString();
                DateTime todayDateTime = convertToDateTime(getTodayDate);
                DateTime birthdayDateTime = convertToDateTime(getBirthdayDate);
                displayCurrentBirthday(todayDateTime, birthdayDateTime);
                displayNextBirthday(todayDateTime, birthdayDateTime);
                displayAgeAnalysis(todayDateTime, birthdayDateTime);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBirthdayDay.setText(Html.fromHtml("<h4>Days</h4>"));
                currentBirthdayMonth.setText(Html.fromHtml("<h4>Months</h4>"));
                currentBirthdayYear.setText(Html.fromHtml("<h4>Years</h4>"));
                nextBirthdayDay.setText(Html.fromHtml("<h4>Days</h4>"));
                nextBirthdayMonth.setText(Html.fromHtml("<h4>Months</h4>"));
                nextBirthdayYear.setText(Html.fromHtml("<h4>Years</h4>"));
                totalDayResult.setText("");
                totalWeekResult.setText("");
                totalMonthResult.setText("");
                totalYearResult.setText("");
                totalHourResult.setText("");
                totalMinuteResult.setText("");
                totalSecondResult.setText("");
                todayDate.setText(setCurrentDateOnView());
                yourBirthdayDate.setText(setCurrentDateOnView());
                set_defaultdialogtime();
            }
        });


    }

    private void displayAgeAnalysis(DateTime dateToday, DateTime birthdayDate) {
        Period dateDifferencePeriod = displayBirthdayResult(dateToday, birthdayDate);
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInWeeks = Weeks.weeksBetween(new DateTime(birthdayDate), new DateTime(dateToday)).getWeeks();
        ;
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        int mDay = getDateInWeeks * 7;
        int mMonth = getDateInMonths + (getDateInYears * 12);
        int hours = mDay * 24;
        int minutes = mDay * 24 * 60;
        int seconds = mDay * 24 * 60 * 60;
        totalDayResult.setText(Html.fromHtml(String.valueOf(mDay)));
        totalWeekResult.setText(Html.fromHtml(String.valueOf(getDateInWeeks)));
        totalMonthResult.setText(Html.fromHtml(String.valueOf(mMonth)));
        totalYearResult.setText(Html.fromHtml(String.valueOf(getDateInYears)));
        totalHourResult.setText(Html.fromHtml(String.valueOf(hours)));
        totalMinuteResult.setText(Html.fromHtml(String.valueOf(minutes)));
        totalSecondResult.setText(Html.fromHtml(String.valueOf(seconds)));
    }

    private void displayNextBirthday(DateTime dateToday, DateTime birthdayDate) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        DateTime nextBirthday = birthdayDate.withYear(year);
        //Toast.makeText(this, "Birthday " + nextBirthday.getYear(), Toast.LENGTH_LONG).show();
        Period dateDifferencePeriod = displayBirthdayResult(nextBirthday, dateToday);
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        nextBirthdayDay.setText(Html.fromHtml("<h4>Days</h4>" + getDateInDays));
        nextBirthdayMonth.setText(Html.fromHtml("<h4>Months</h4>" + getDateInMonths));
        nextBirthdayYear.setText(Html.fromHtml("<h4>Years</h4>" + getDateInYears));
    }

    private void displayCurrentBirthday(DateTime dateToday, DateTime birthdayDate) {
        Period dateDifferencePeriod = displayBirthdayResult(dateToday, birthdayDate);
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        currentBirthdayDay.setText(Html.fromHtml("<h4>Days</h4>" + getDateInDays));
        currentBirthdayMonth.setText(Html.fromHtml("<h4>Months</h4>" + getDateInMonths));
        currentBirthdayYear.setText(Html.fromHtml("<h4>Years</h4>" + getDateInYears));
    }

    private Period displayBirthdayResult(DateTime dateToday, DateTime birthdayDate) {
        Period dateDifferencePeriod = new Period(birthdayDate, dateToday, PeriodType.yearMonthDayTime());
        return dateDifferencePeriod;
    }

    private DateTime convertToDateTime(String stringToConvert) {
        String[] newStringArray = convertStringToArray(stringToConvert);
        int year = Integer.parseInt(newStringArray[2].trim());
        int day = Integer.parseInt(newStringArray[0].trim());
        int month = Integer.parseInt(newStringArray[1].trim());
        LocalDate mLocalDate = new LocalDate(year, month, day);
        DateTime firstDateTime = mLocalDate.toDateTime(LocalTime.fromDateFields(mLocalDate.toDate()));
        return firstDateTime;
    }

    private String[] convertStringToArray(String stringToConvert) {
        String[] newStringArray = stringToConvert.split("-");
        return newStringArray;
    }

    public String setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        StringBuilder displayStringBuilder = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ");
        return displayStringBuilder.toString();
    }

    public void set_defaultdialogtime() {
        final Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        startDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DATE_PICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String resultDate = bundle.getString("RETURNED_DATE", "error");
                    todayDate.setText(resultDate);
                }
                break;
            case SECOND_DATE_PICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String resultDate = bundle.getString("RETURNED_DATE", "error");
                    yourBirthdayDate.setText(resultDate);
                }
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_START_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, startYear,
                        startMonth, startDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {


            if (choser == 1) {
                curentyear = selectedYear;
                curentmonth = selectedMonth + 1;
                currentdate = selectedDay;
                todayDate.setText(currentdate + "-" + curentmonth + "-" + curentyear);
            } else if (choser == 2) {
                bithyear = selectedYear;
                birhmonth = selectedMonth + 1;
                birtdate = selectedDay;
                yourBirthdayDate.setText(birtdate + "-" + birhmonth + "-" + bithyear);

            }

        }
    };

}
