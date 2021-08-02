package com.example.myapp

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class AddEditNoteActivity : AppCompatActivity() {
    var EXTRA_TITLE = "com.codinginflow.architectureexample.EXTRA_TITLE"
    var EXTRA_DESCRIPTION = "com.codinginflow.architectureexample.EXTRA_DESCRIPTION"
    var EXTRA_PRIORITY = "com.codinginflow.architectureexample.EXTRA_PRIORITY"
    private var editTextTitle: EditText? = null
    private var editTextDescription: EditText? = null
    private var numberPickerPriority: NumberPicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        numberPickerPriority!!.minValue=1
        numberPickerPriority!!.maxValue=10;
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close);
        val intent = getIntent()
        if(intent.hasExtra("pos"))
        {
            setTitle("Edit Note");
            editTextTitle!!.setText(intent.getStringExtra("title"));
            editTextDescription!!.setText(intent.getStringExtra("description"));
            numberPickerPriority!!.value = intent.getIntExtra("priority", 1);
        }
        else
        {
            title = "Add Note";
        }

    }
    private fun saveNote()
    {
        val title = editTextTitle!!.text.toString()
        val description = editTextDescription!!.text.toString()
        val priority = numberPickerPriority!!.value
        if (title.trim { it <= ' ' }.isEmpty() || description.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra("abc", title)
        data.putExtra("def", description)
        data.putExtra("ghi", priority)

        val id = intent.getIntExtra("pos", -1)
        if (id != -1) {
            data.putExtra("idd", id)
        }
        setResult(RESULT_OK, data)
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else-> super.onOptionsItemSelected(item)

        }

    }


}