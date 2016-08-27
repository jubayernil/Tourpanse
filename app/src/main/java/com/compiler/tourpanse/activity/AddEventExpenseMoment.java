package com.compiler.tourpanse.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.adapter.ExpenseAdapter;
import com.compiler.tourpanse.adapter.MomentAdapter;
import com.compiler.tourpanse.dbhelper.EventDataSource;
import com.compiler.tourpanse.dbhelper.ExpenseDatasource;
import com.compiler.tourpanse.dbhelper.MomentDataSource;
import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.models.Event;
import com.compiler.tourpanse.models.Expense;
import com.compiler.tourpanse.models.Moment;

import java.io.File;
import java.util.ArrayList;

public class AddEventExpenseMoment extends AppCompatActivity {

    private TextView showEventTitle;
    private Button addExpense;
    private Button listExpense;
    private Button addMoment;
    private Button listMoment;
    private TextView imagePathTextView;

    private SaveUserCredentialsToSharedPreference sfData;
    private EventDataSource eventDataSource;
    private ExpenseDatasource expenseDatasource;
    private Event event;
    private Expense expense;
    private ArrayList<Expense> expenses;
    private ArrayList<Moment> moments;
    private Moment moment;
    private MomentDataSource momentDataSource;


    private int eventId;
    private int userId;
    private boolean status;


    private final static int RESULT_LOAD_IMAGE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_expense_moment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sfData = new SaveUserCredentialsToSharedPreference(AddEventExpenseMoment.this);
        userId = sfData.getUserCredentials();
        eventId = getIntent().getIntExtra("eventId", 0);
        eventDataSource = new EventDataSource(AddEventExpenseMoment.this);
        expenseDatasource = new ExpenseDatasource(AddEventExpenseMoment.this);
        event = eventDataSource.findEventById(eventId);
        momentDataSource = new MomentDataSource(AddEventExpenseMoment.this);

        showEventTitle = (TextView) findViewById(R.id.showEventTitle);
        addExpense = (Button) findViewById(R.id.addExpense);
        listExpense = (Button) findViewById(R.id.listExpense);
        addMoment = (Button) findViewById(R.id.addMoment);
        listMoment = (Button) findViewById(R.id.viewMoment);
        showEventTitle.setText(event.getEventLocation());

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(AddEventExpenseMoment.this).inflate(R.layout.add_expense, null);
                final EditText expensePurposeET = (EditText) view.findViewById(R.id.expensePurpose);
                final EditText expenseAmountET = (EditText) view.findViewById(R.id.expenseAmount);
                final Button saveExpense = (Button) view.findViewById(R.id.saveExpense);

                saveExpense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String expensePurpose = expensePurposeET.getText().toString().trim();
                        String expenseAmount = expenseAmountET.getText().toString().trim();
                        if (expensePurpose.equals("")) {
                            expensePurposeET.setError("Purpose is required.");
                        } else if (expenseAmount.equals("")) {
                            expenseAmountET.setError("Amount is required.");
                        } else {
                            double amount = Double.parseDouble(expenseAmount);
                            expense = new Expense(expensePurpose, amount, eventId, userId);
                            status = expenseDatasource.saveExpense(expense);
                            if (status) {
                                Toast.makeText(getApplicationContext(), "Expense Added", Toast.LENGTH_LONG).show();
                                expensePurposeET.setText("");
                                expenseAmountET.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                AlertDialog.Builder addNewExpenseAlertBuilder = new AlertDialog.Builder(AddEventExpenseMoment.this);

                addNewExpenseAlertBuilder
                        .setView(view)
                        .setCancelable(true)
                        .setPositiveButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog addNewExpenseAlertDialog = addNewExpenseAlertBuilder.create();
                //addNewExpenseAlertDialog.setTitle("Add New Expense.");
                addNewExpenseAlertDialog.show();
            }
        });


        listExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(AddEventExpenseMoment.this).inflate(R.layout.list_of_expense, null);
                expenses = expenseDatasource.getAllExpenseByEventId(eventId);
                ExpenseAdapter expenseAdapter = new ExpenseAdapter(AddEventExpenseMoment.this, expenses);

                final ListView showListOfExpenseOfEvent = (ListView) view.findViewById(R.id.showListOfExpenseOfEvent);
                final TextView showEventEstimatedBudgetTV = (TextView) view.findViewById(R.id.showEventEstimatedBudgetTV);
                final TextView showEventSpentAmountTV = (TextView) view.findViewById(R.id.showEventSpentAmountTV);
                final TextView showRemainingAmountTV = (TextView) view.findViewById(R.id.showRemainingAmountTV);

                double cost = 0;
                for (Expense expense : expenses) {
                    cost += expense.getAmount();
                }
                int totalMoney = Integer.parseInt(event.getEstimatedBudget());
                double remainingAmount = Double.parseDouble(event.getEstimatedBudget()) - cost;
                showEventEstimatedBudgetTV.setText("Budget\n" + event.getEstimatedBudget());
                showEventSpentAmountTV.setText("Spent\n" + cost);
                showRemainingAmountTV.setText("Remaining\n" + remainingAmount);
                if (remainingAmount > cost){
                    showRemainingAmountTV.setBackgroundResource(R.color.successGreen);
                } else {
                    showRemainingAmountTV.setBackgroundResource(R.color.colorRed);
                }

                showListOfExpenseOfEvent.setAdapter(expenseAdapter);
                AlertDialog.Builder showExpensesAlertBuilder = new AlertDialog.Builder(AddEventExpenseMoment.this);

                showExpensesAlertBuilder
                        .setView(view)
                        .setCancelable(true)
                        .setPositiveButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog showExpenseAlertDialog = showExpensesAlertBuilder.create();
                showExpenseAlertDialog.setTitle("List of expenses of tour " + event.getEventLocation() + ".");
                showExpenseAlertDialog.show();
            }
        });


        addMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(AddEventExpenseMoment.this).inflate(R.layout.add_moment, null);

                final EditText momentLocationET = (EditText) view.findViewById(R.id.momentLocation);
                final EditText momentRemarkET = (EditText) view.findViewById(R.id.momentRemark);
                final EditText momentImageCaptionET = (EditText) view.findViewById(R.id.momentImageCaption);
                final TextView imageSelectorTV = (TextView) view.findViewById(R.id.imageSelectorTV);
                final Button saveMoment = (Button) view.findViewById(R.id.saveMoment);

                imagePathTextView = (TextView) view.findViewById(R.id.imagePathTextView);

                imageSelectorTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), RESULT_LOAD_IMAGE);
                    }
                });

                saveMoment.setOnClickListener(new View.OnClickListener()

                                              {
                                                  @Override
                                                  public void onClick(View view) {
                                                      String momentLocation = momentLocationET.getText().toString().trim();
                                                      String momentRemark = momentRemarkET.getText().toString().trim();
                                                      String momentImageCaption = momentImageCaptionET.getText().toString().trim();

                                                      if (momentLocation.equals("")) {
                                                          momentLocationET.setError("Enter location.");
                                                      } else if (momentImageCaption.equals("")) {
                                                          momentImageCaptionET.setError("Enter image caption.");
                                                      } else {
                                                          moment = new Moment(eventId, momentLocation, momentRemark, "pic", momentImageCaption, userId);
                                                          status = momentDataSource.saveEvent(moment);
                                                          if (status) {
                                                              momentLocationET.setText(null);
                                                              momentRemarkET.setText(null);
                                                              momentImageCaptionET.setText(null);
                                                              Toast.makeText(getApplicationContext(), "Moment Added", Toast.LENGTH_LONG).show();
                                                          } else {
                                                              Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                                          }
                                                      }
                                                  }
                                              }

                );

                AlertDialog.Builder addNewMomentAlertBuilder = new AlertDialog.Builder(AddEventExpenseMoment.this);
                addNewMomentAlertBuilder
                        .setView(view)
                        .

                                setCancelable(true)

                        .

                                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                ).

                        setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }

                        );
                AlertDialog addNewMomentAlertDialog = addNewMomentAlertBuilder.create();
                //addNewMomentAlertDialog.setTitle("Add New Moment.");
                addNewMomentAlertDialog.show();
            }
        });


        listMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(AddEventExpenseMoment.this).inflate(R.layout.list_of_moments, null);
                moments = momentDataSource.getAllMoment(eventId);
                MomentAdapter momentAdapter = new MomentAdapter(AddEventExpenseMoment.this, moments);

                final ListView showListOfMomentsOfEvent = (ListView) view.findViewById(R.id.showListOfMomentsOfEvent);
                showListOfMomentsOfEvent.setAdapter(momentAdapter);
                AlertDialog.Builder showMomentAlertBuilder = new AlertDialog.Builder(AddEventExpenseMoment.this);

                showMomentAlertBuilder
                        .setView(view)
                        .setCancelable(true)
                        .setPositiveButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog showMomentAlertDialog = showMomentAlertBuilder.create();
                showMomentAlertDialog.setTitle("Moments of tour " + event.getEventLocation() + ".");
                showMomentAlertDialog.show();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            //  imagePathTextView.setText(picturePath);
            cursor.close();
            // String picturePath contains the path of selected Image
        }

    }

    private void doTakePhotoAction() {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File fileDir = new File(Environment.getExternalStorageDirectory() + "/zuijiao");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String imagePath = Environment.getExternalStorageDirectory() + "/zuijiao/" + System.currentTimeMillis() + ".jpg";
        File carmeraFile = new File(imagePath);
        Uri imageCarmeraUri = Uri.fromFile(carmeraFile);
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageCarmeraUri);
        try {
            cameraIntent.putExtra("return-data", true);
            startActivityForResult(cameraIntent, 1);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }
    }

    public void goToPlace(View view) {
        startActivity(new Intent(AddEventExpenseMoment.this, PlaceActivity.class));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                SaveUserCredentialsToSharedPreference saveUserCredentialsToSharedPreference = null;
                saveUserCredentialsToSharedPreference.saveUserCredentials(0);
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent Act2Intent = new Intent(this, MainActivity.class);
            startActivity(Act2Intent);
            finish();
            return true;
        }
        return false;
    }
}
