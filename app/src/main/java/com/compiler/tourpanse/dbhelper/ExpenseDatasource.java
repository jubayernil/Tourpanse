package com.compiler.tourpanse.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.models.Expense;

import java.util.ArrayList;

public class ExpenseDatasource {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Expense expense;
    private SaveUserCredentialsToSharedPreference sfData;
    private Context context;

    public ExpenseDatasource(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public ArrayList<Expense> getAllExpenseByEventId(int eventId) {
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        this.open();
        sfData = new SaveUserCredentialsToSharedPreference(context);
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_EXPENSES + " where " +
                DatabaseHelper.COL_USER_ID + " = "+ sfData.getUserCredentials() + " AND "+
                DatabaseHelper.COL_EVENT_ID + " = "+ eventId, null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++) {
                int expenseId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String expensePurpose = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EXPENSE_PURPOSE));
                double amount = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COL_AMOUNT));
                int expenseEventId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_EVENT_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                expense = new Expense(expenseId, expensePurpose, amount, expenseEventId, userId);
                cursor.moveToNext();
                expenses.add(expense);
            }
        }
        cursor.close();
        this.close();
        return expenses;
    }


    public boolean saveExpense(Expense expense) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_EXPENSE_PURPOSE, expense.getExpensePurpose());
        contentValues.put(DatabaseHelper.COL_AMOUNT, expense.getAmount());
        contentValues.put(DatabaseHelper.COL_EVENT_ID, expense.getEventId());
        contentValues.put(DatabaseHelper.COL_USER_ID, expense.getUserId());
        long inserted = database.insert(DatabaseHelper.TABLE_EXPENSES, null, contentValues);
        this.close();
        if(inserted>0) {
            return true;
        } else {
            return false;
        }
    }



    public Expense findExpenseById(int id) {
        this.open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EXPENSES, new String[]{DatabaseHelper.COL_ID,DatabaseHelper.COL_EXPENSE_PURPOSE,
                DatabaseHelper.COL_AMOUNT, DatabaseHelper.COL_EVENT_ID, DatabaseHelper.COL_USER_ID}, DatabaseHelper.COL_ID + "="+ id, null, null, null, null);
        cursor.moveToFirst();


        int expenseId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
        String expensePurpose = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EXPENSE_PURPOSE));
        double amount = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COL_AMOUNT));
        int expenseEventId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_EVENT_ID));
        int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
        expense = new Expense(expenseId, expensePurpose, amount, expenseEventId, userId);
        this.close();
        return  expense;
    }

    public boolean update(int id, Expense expense) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_EXPENSE_PURPOSE, expense.getExpensePurpose());
        contentValues.put(DatabaseHelper.COL_AMOUNT, expense.getAmount());
        contentValues.put(DatabaseHelper.COL_EVENT_ID, expense.getEventId());
        contentValues.put(DatabaseHelper.COL_USER_ID, expense.getUserId());
        long updated = database.update(DatabaseHelper.TABLE_EXPENSES, contentValues, DatabaseHelper.COL_ID+"="+id, null);
        this.close();
        if(updated>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(int id) {
        this.open();
        long deleted = database.delete(DatabaseHelper.TABLE_EXPENSES, DatabaseHelper.COL_ID+"="+id, null);
        this.close();
        if(deleted>0) {
            return true;
        } else {
            return false;
        }
    }

}
