package com.example.to_do_list_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, MyAdapter.OnNoteListener {

    FloatingActionButton enter;
    Button register,time1,date,speech;
    TextView t,t2;
    TextInputEditText todo;
    DBHelper dbHelper;
    int hour,minute,year,month,day;
    String currentDateString,cTime;
    TextToSpeech textToSpeech;
    ArrayList<Todo> todolist;
    LinearLayout view2,view1;
    ListView list;
    Todo todo1;
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<String> l;

    private final int REQ_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbHelper = new DBHelper(this);

        enter = findViewById(R.id.enter1);
        view2 = findViewById(R.id.view2);
        view1 = findViewById(R.id.view1);
        list = findViewById(R.id.list);
        recyclerView = findViewById(R.id.recycle1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todolist = new ArrayList<>();
        Cursor data = dbHelper.getAllData();
        int numrows = data.getCount();
        if(numrows == 0){
            view2.setVisibility(View.VISIBLE);
        }else{
            while(data.moveToNext()){
                view2.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);

                todo1 = new Todo(data.getString(0),data.getString(1),data.getString(2),data.getString(3));
                todolist.add(todo1);
            }
            adapter = new MyAdapter(this, todolist, this);
            recyclerView.setAdapter(adapter);
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int i){
                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Main2Activity.this, R.style.DialogStyle);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                register = bottomSheetDialog.findViewById(R.id.register);
                time1 = bottomSheetDialog.findViewById(R.id.time1);
                date = (bottomSheetDialog).findViewById(R.id.date);
                t = bottomSheetDialog.findViewById(R.id.text);
                t2 = bottomSheetDialog.findViewById(R.id.text1);
                speech = bottomSheetDialog.findViewById(R.id.mic);
                todo = bottomSheetDialog.findViewById(R.id.todo);

                Calendar c = Calendar.getInstance();
                currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                t.setText(currentDateString);
                cTime = DateFormat.getTimeInstance(DateFormat.DEFAULT).format(c.getTime());
                t2.setText(cTime);

                speech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"What you want to do?");
                        try {
                            startActivityForResult(intent,REQ_CODE);
                        }catch (ActivityNotFoundException e){

                        }
                    }
                });

                time1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment timepicker = new TimeFragment();
                        timepicker.show(getSupportFragmentManager(), "Time Picker");
                    }
                });

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment datepicker = new DateDialog();
                        datepicker.show(getSupportFragmentManager(), "Date Picker");

                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(todo.getText().toString().isEmpty()){
                            Toast.makeText(Main2Activity.this,"Please Fillup this form",Toast.LENGTH_SHORT).show();
                        }else{
                            if(dbHelper.insert(todo.getText().toString(),t.getText().toString(),t2.getText().toString())){
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setCancelable(false);
                                builder.setTitle("Successful");
                                builder.setMessage("Added to the list");

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which){
                                        dialog.dismiss();
                                        bottomSheetDialog.dismiss();
                                        recreate();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                    }
                });
                bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE + WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                bottomSheetDialog.show();
            }
        });
    }
    String Deleted = null, del,del1,del2,del3;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    Deleted = todolist.get(position).getDescription();
                    del1 = todolist.get(position).getDateofrecord();
                    del2 = todolist.get(position).getTimeofrecord();
                    del = viewHolder.itemView.getTag().toString();


                    Snackbar.make(recyclerView, Deleted,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).addCallback(new Snackbar.Callback(){
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);
                            switch (event) {
                                case Snackbar.Callback.DISMISS_EVENT_ACTION:

                                    adapter.notifyItemChanged(position);
                                    break;
                                default:
                                    todolist.remove(position);
                                    dbHelper.deleteTodo(del);
                                    adapter.notifyItemRemoved(position);
                                    break;
                            }
                        }
                    }).show();
                    break;

                case ItemTouchHelper.RIGHT:
                    adapter.notifyItemChanged(position);
                        break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Main2Activity.this, R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        t.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        cTime = DateFormat.getTimeInstance(DateFormat.DEFAULT).format(c.getTime());
        t2.setVisibility(View.VISIBLE);
        t2.setText(cTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE:{
                if(resultCode == RESULT_OK && null != data){
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    todo.setText(result.get(0).toString());
                }
            }
            default:{

            }
        }
    }

    @Override
    public void onNoteClick(int position) {
        String desc,datemonth,timeof;
        todolist.get(position);
        desc = todolist.get(position).getDescription();
        datemonth = todolist.get(position).getDateofrecord();
        timeof = todolist.get(position).getTimeofrecord();
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
        intent.putExtra("desc",desc);
        intent.putExtra("date",datemonth);
        intent.putExtra("time",timeof);
        startActivity(intent);
    }
}
