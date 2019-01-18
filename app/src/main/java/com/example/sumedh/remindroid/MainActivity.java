package com.example.sumedh.remindroid;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String todo, rem_title,fname,lname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragToday ftoday = new FragToday();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.container, ftoday, "Frg1");
        //ft.addToBackStack(null);
        ft.commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            //Toast.makeText(MainActivity.this,"shg",Toast.LENGTH_SHORT).show();

            final AlertDialog diag = new AlertDialog.Builder(this)
                    .setTitle("Choose an action...")
                    .setView(R.layout.customdialog)
                    .create();
            diag.show();

            Button rem_button = diag.findViewById(R.id.rem_button);
            Button todo_button = diag.findViewById(R.id.todo_button);
            Button bday_button = diag.findViewById(R.id.bday_button);

            rem_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    diag.dismiss();
                    final AlertDialog diag_rem = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Set Reminder")
                            .setView(R.layout.dialog_reminder)
                            .create();
                    diag_rem.show();

                    Button btnDate = diag_rem.findViewById(R.id.btnDate);
                    Button btnTime = diag_rem.findViewById(R.id.btnTime);
                    Button cancel = diag_rem.findViewById(R.id.cancel);
                    Button set = diag_rem.findViewById(R.id.set);
                    final TextView txtDate = diag_rem.findViewById(R.id.txtDate);
                    final TextView txtTime  = diag_rem.findViewById(R.id.txtTime);
                    final EditText remtitle  = diag_rem.findViewById(R.id.remtitle);
                    final String reminderdata[] = new String[3];
                    final int[] date = new int[5]; // dd/mm/yyyy hr:min

                    btnDate.setOnClickListener(new View.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this);
                            datePickerDialog.show();
                            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                    date[2] = y;
                                    date[1] = m;
                                    date[0] = d;
                                    reminderdata[1] = Integer.toString(d) + "/" + Integer.toString(m+1) + "/" + Integer.toString(y);
                                   Toast.makeText(MainActivity.this,reminderdata[1],Toast.LENGTH_SHORT).show();
                                   txtDate.setText(reminderdata[1]);
                                }
                            });
                        }
                    });

                    btnTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int h, int m) {
                                    date[3] = h;
                                    date[4] = m;
                                    reminderdata[2] = Integer.toString(h)+":"+Integer.toString(m);
                                    Toast.makeText(MainActivity.this,reminderdata[2],Toast.LENGTH_SHORT).show();
                                    txtTime.setText(reminderdata[2]);
                                }
                            },Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE),false);
                            timePickerDialog.show();
                        }
                    });

                    set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            reminderdata[0] = remtitle.getText().toString();
                            DBHelper dbHelper = new DBHelper(MainActivity.this);
                            dbHelper.addReminder(reminderdata[0],reminderdata[1],reminderdata[2]);
                            diag_rem.dismiss();

                            setAlarm(date,reminderdata[0],"You have a reminder!!");

                            }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            diag_rem.dismiss();
                        }
                    });
                }
            });



            todo_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    diag.dismiss();
                    final AlertDialog diag_todo = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Set Reminder")
                            .setView(R.layout.dialog_todo)
                            .create();
                    diag_todo.show();


                    Button btn_todo_date = diag_todo.findViewById(R.id.btn_todo_date);
                    Button btn_todo_time = diag_todo.findViewById(R.id.btn_todo_time);
                    Button btn_todo_cancel = diag_todo.findViewById(R.id.btn_todo_cancel);
                    Button btn_todo_set = diag_todo.findViewById(R.id.btn_todo_set);
                    final TextView txt_todo_date = diag_todo.findViewById(R.id.txt_todo_date);;
                    final EditText todo_title = diag_todo.findViewById(R.id.todo_title);
                    final TextView txt_todo_time = diag_todo.findViewById(R.id.txt_todo_time);
                    CheckBox todo_is_set = diag_todo.findViewById(R.id.todo_is_set);
                    final int[] date = new int[5]; // dd/mm/yyyy hr:min

                    final boolean[] checked = new boolean[1];
                    final String tododata[] = new String[3];

                    btn_todo_date.setOnClickListener(new View.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this);
                            datePickerDialog.show();
                            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                    date[0] = d;
                                    date[1] = m;
                                    date[2] = y;
                                    tododata[1] = Integer.toString(d) + "/" + Integer.toString(m+1) + "/" + Integer.toString(y);
                                    Toast.makeText(MainActivity.this,tododata[1],Toast.LENGTH_SHORT).show();
                                    txt_todo_date.setText(tododata[1]);
                                }
                            });
                        }
                    });

                    btn_todo_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int h, int m) {
                                    date[3] = h;
                                    date[4] = m;
                                    tododata[2] = Integer.toString(h)+":"+Integer.toString(m);
                                    Toast.makeText(MainActivity.this,tododata[2],Toast.LENGTH_SHORT).show();
                                    txt_todo_time.setText(tododata[2]);
                                }
                            },Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE),false);
                            timePickerDialog.show();
                        }
                    });

                    todo_is_set.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            checked[0] = b;
                        }
                    });

                    btn_todo_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            diag_todo.dismiss();
                        }
                    });

                    btn_todo_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tododata[0] = todo_title.getText().toString();
                            DBHelper dbHelper = new DBHelper(MainActivity.this);
                            dbHelper.addTodo( tododata[0], tododata[1], tododata[2],(checked[0]) ? 1 : 0);
                            Toast.makeText(MainActivity.this, tododata[0],Toast.LENGTH_LONG).show();
                            diag_todo.dismiss();
                            if(checked[0])
                            {
                                setAlarm(date, tododata[0],"You've got something on your To-do list!");
                            }
                        }
                    });
                }
            });

            bday_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    diag.dismiss();
                    final AlertDialog diag_bday = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Set Reminder")
                            .setView(R.layout.dialog_bday)
                            .create();
                    diag_bday.show();

                    Button btnCalendar = diag_bday.findViewById(R.id.btnCalendar);
                    Button btncan = diag_bday.findViewById(R.id.btncan);
                    Button btnsave = diag_bday.findViewById(R.id.btnsave);
                    final EditText fname = diag_bday.findViewById(R.id.fname);
                    final EditText lname = diag_bday.findViewById(R.id.lname);
                    final TextView txtDate = diag_bday.findViewById(R.id.txtDate);
                    final int[] date = new int[5]; // dd/mm/yyyy hr:min

                    final String bdaydata[] = new String[3];

                    btnCalendar.setOnClickListener(new View.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this);
                            datePickerDialog.show();
                            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                    date[0] = d;
                                    date[1] = m;
                                    date[2] = y;
                                    date[3] = 0;
                                    date[4] = 0;
                                    bdaydata[2] = Integer.toString(d) + "/" + Integer.toString(m+1) + "/" + Integer.toString(y);

                                    txtDate.setText(bdaydata[2]);
                                }
                            });
                        }
                    });

                    btncan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            diag_bday.dismiss();
                        }
                    });

                    btnsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bdaydata[0] = fname.getText().toString();
                            bdaydata[1] = lname.getText().toString();
                            DBHelper dbHelper = new DBHelper(MainActivity.this);
                            dbHelper.addBday( bdaydata[0], bdaydata[1], bdaydata[2]);
                            diag_bday.dismiss();
                            setAlarm(date, "It's "+bdaydata[0]+" "+bdaydata[1]+"'s birthday today!","Birthday Alert!!");
                        }
                    });

                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        if (id == R.id.today) {

            FragToday ftoday = new FragToday();
            ft.replace(R.id.container, ftoday, "Frg1");
            //ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.reminder) {

            FragRem frem = new FragRem();
            ft.replace(R.id.container, frem , "Frag2");
            //ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.todo) {
            FragTodo ftodo = new FragTodo();
            ft.replace(R.id.container, ftodo , "Frag3");
            //ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.bday) {
            FragBday fbday = new FragBday();
            ft.replace(R.id.container, fbday , "Frag4");
           // ft.addToBackStack(null);
            ft.commit();

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setAlarm(int[] date, String desc,String title)
    {
        Calendar Calendar_Object = Calendar.getInstance();
        Calendar_Object.set(Calendar.MONTH, date[1]);
        Calendar_Object.set(Calendar.YEAR, date[2]);
        Calendar_Object.set(Calendar.DAY_OF_MONTH, date[0]);
        Calendar_Object.set(Calendar.HOUR_OF_DAY, date[3]); // specify in 24 hrs format
        Calendar_Object.set(Calendar.MINUTE, date[4]);
        Calendar_Object.set(Calendar.SECOND, 0);

        AlarmManager am=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent( MainActivity.this,AlarmReceiver.class);
        myIntent.putExtra("desc",desc);
        myIntent.putExtra("title",title);
        PendingIntent pI= PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, Calendar_Object.getTimeInMillis(),pI);
    }
}
