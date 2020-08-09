package com.example.saver;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class StatisticsFragment extends Fragment {

    PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        /*
         * Calculations
         */
        // Calculates respective total, day, month statistics
        Double monthSum = 0.0;
        Double todaySum = 0.0;
        Double totalSum = 0.0;

        // Gets and formats current date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = sdf.format(date);
        String currentMonth = currentDate.substring(0, 2);
        String currentDay = currentDate.substring(3, 5);
        String currentYear = currentDate.substring(6);

        Double foodTotal = 0.0;
        Double paidFriendsTotal = 0.0;
        Double personalItemsTotal = 0.0;
        Double entertainmentTotal = 0.0;
        Double transportationTotal = 0.0;

        //Calculates total for each category
        for (int i = 0; i < ListContainer.paidItems.size(); i++) {
            // Variables for each item
            String itemMonth = ListContainer.paidItems.get(i).getDate().substring(0, 2);
            String itemDay = ListContainer.paidItems.get(i).getDate().substring(3, 5);
            String itemYear = ListContainer.paidItems.get(i).getDate().substring(6);

            Double cost = Double.parseDouble(ListContainer.paidItems.get(i).getCost());

            // Month Sum
            if (itemMonth.equals(currentMonth) && itemYear.equals(currentYear)) {
                monthSum += cost;
            }
            // Today Sum
            if (itemDay.equals(currentDay) && itemMonth.equals(currentMonth) && itemYear.equals(currentYear)) {
                todaySum += cost;
            }
            // Total Sum
            totalSum += cost;

            // Gets the sum for each category
            String category = ListContainer.paidItems.get(i).getCategory();
            if (category.equals(getString(R.string.category_item_1))) {
                foodTotal += cost;
            } else if (category.equals(getString(R.string.category_item_2))) {
                paidFriendsTotal += cost;
            } else if (category.equals(getString(R.string.category_item_3))) {
                personalItemsTotal += cost;
            } else if (category.equals(getString(R.string.category_item_4))) {
                entertainmentTotal += cost;
            } else if (category.equals(getString(R.string.category_item_5))) {
                transportationTotal += cost;
            }
        }

        /*
         * Pie Chart Setup
         */
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        ArrayList<PieEntry> entries = new ArrayList<>();

        // TODO Get category total by month
        // Adds totals to the pie chart if greater than 0
        if (foodTotal > 0) {
            entries.add(new PieEntry((float) (foodTotal / 1.0), getString(R.string.category_item_1)));
        }
        if (paidFriendsTotal > 0) {
            entries.add(new PieEntry((float) (paidFriendsTotal / 1.0), getString(R.string.category_item_2)));
        }
        if (personalItemsTotal > 0) {
            entries.add(new PieEntry((float) (personalItemsTotal / 1.0), getString(R.string.category_item_3)));
        }
        if (entertainmentTotal > 0) {
            entries.add(new PieEntry((float) (entertainmentTotal/1.0), getString(R.string.category_item_4)));
        }
        if (transportationTotal > 0) {
            entries.add(new PieEntry((float) (transportationTotal/1.0), getString(R.string.category_item_5)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // Creates custom colors from an int array
        int[] myColors = new int[5];
        myColors[0] = Color.parseColor("#2962FF");
        myColors[1] = Color.parseColor("#1565C0");
        myColors[2] = Color.parseColor("#536DFE");
        myColors[3] = Color.parseColor("#1E88E5");
        myColors[4] = Color.parseColor("#42A5F5");
        dataSet.setColors(myColors);

        PieData data = new PieData(dataSet);
        // Value of each category text size and color
        data.setValueTextSize(15);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        /*
         * Numbers Statistics Setup
         */
        // Month sum to TextView
        TextView monthSumTextView = view.findViewById(R.id.month_total_holder);
        monthSumTextView.setText("$ " + String.format("%,.2f", monthSum));

        // Today sum to TextView
        TextView todaySumTextView = view.findViewById(R.id.today_total_holder);
        todaySumTextView.setText("$ " + String.format("%,.2f", todaySum));

        // All Time sum to TextView
        TextView totalSumTextView = view.findViewById(R.id.all_total_holder);
        totalSumTextView.setText("$ " + String.format("%,.2f", totalSum));

        /*
         * Categories TextView Setup
         */
        // Food
        TextView categoryOneTextView = view.findViewById(R.id.category_holder_1);
        categoryOneTextView.setText("$ " + String.format("%,.2f", foodTotal));
        // Paid Friends/Family
        TextView categoryTwoTextView = view.findViewById(R.id.category_holder_2);
        categoryTwoTextView.setText("$ " + String.format("%,.2f", paidFriendsTotal));
        // Personal Items
        TextView categoryThreeTextView = view.findViewById(R.id.category_holder_3);
        categoryThreeTextView.setText("$ " + String.format("%,.2f", personalItemsTotal));
        // Entertainment
        TextView categoryFourTextView = view.findViewById(R.id.category_holder_4);
        categoryFourTextView.setText("$ " + String.format("%,.2f", entertainmentTotal));
        // Transportation
        TextView categoryFiveTextView = view.findViewById(R.id.category_holder_5);
        categoryFiveTextView.setText("$ " + String.format("%,.2f", transportationTotal));

        return view;
    }
}
