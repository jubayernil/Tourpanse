package com.compiler.tourpanse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.dbhelper.ExpenseDatasource;
import com.compiler.tourpanse.models.Expense;

import java.util.ArrayList;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private Context context;
    private ArrayList<Expense> expenses;
    private Expense expense;
    private Intent intent;
    private ExpenseDatasource expenseDatasource;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, R.layout.list_of_expenses_custom_list_view, expenses);
        this.context = context;
        this.expenses = expenses;
    }

    private static class ViewHolder{
        private TextView showExpensePurposeTV;
        private TextView showExpenseAmountTV;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_of_expenses_custom_list_view, null, false);
            viewHolder = new ViewHolder();
            viewHolder.showExpensePurposeTV = (TextView) convertView.findViewById(R.id.showExpensePurposeTV);
            viewHolder.showExpenseAmountTV = (TextView) convertView.findViewById(R.id.showExpenseAmountTV);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.showExpensePurposeTV.setText(expenses.get(position).getExpensePurpose());
        viewHolder.showExpenseAmountTV.setText(""+expenses.get(position).getAmount());
        return convertView;
    }



}
