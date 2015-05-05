package no.notetaker.jou.notetaker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListNotesActivity extends ActionBarActivity {

    private List<Note> notes = new ArrayList<Note>();
    private ListView notesListView;
    private int editingNoteId = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Serializable extra = data.getSerializableExtra("Note");
            if (extra != null) {
                Note newNote = (Note) extra;
                if (editingNoteId > -1) {
                    notes.set(editingNoteId, newNote);
                    editingNoteId = -1;
                } else {
                    notes.add(newNote);
                }
                populateList();
            }
        }else{
            editingNoteId = -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        notesListView = (ListView)findViewById(R.id.notesListView);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int itemNumber, long id) {
                Intent editnoteItent = new Intent(view.getContext(), EditNoteActivity.class);
                editnoteItent.putExtra("Note", notes.get(itemNumber));
                editingNoteId = itemNumber;
                startActivityForResult(editnoteItent, 1);
            }
        });

        notes.add(new Note("First note", "This is the first of many notes", new Date()));
        notes.add(new Note("Second note", "Second best note in the area", new Date()));
        notes.add(new Note("Third note", "The Three stooges", new Date()));
        notes.add(new Note("Fourth note", "I am number four!", new Date()));
        notes.add(new Note("Fifth note", "Lenny Krawitz: 5", new Date()));

        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addNoteItem) {
            Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
            startActivityForResult(editNoteIntent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // function to
    private void populateList(){

        List<String> values = new ArrayList<String>();

        for(Note note : notes){
            values.add(note.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        notesListView.setAdapter(adapter);
    }
}
