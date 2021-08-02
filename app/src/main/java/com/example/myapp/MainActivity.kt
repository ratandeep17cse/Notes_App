package com.example.myapp

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(){
    companion object{
        var adapter1: NoteAdapter? =null
        var xx = 1}
    val RESULT_NOK = 1
    var recyclerView1: RecyclerView? = null

    private var noteViewModel: NoteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            run {
                if (result.resultCode == Activity.RESULT_OK) {
                    val noteData: Intent? = result.data
                    var title = noteData?.getStringExtra("abc")
                    var desc = noteData?.getStringExtra("def")
                    var pri = noteData?.getIntExtra("ghi", 0)
                    var note: Note? = null
                    if (title != null && desc != null && pri != null) {

                        note = Note(xx++, title, desc, pri)

                    }

                    noteViewModel!!.insert(note!!)
                    println(title.toString())
                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
                }
                else if(result.resultCode ==Activity.RESULT_OK)
                {

                }
                else {
                    Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
                }
            }
        }
        var button: FloatingActionButton = findViewById(R.id.button_add_note)
        button.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startForResult.launch(intent)

        }
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)



        noteViewModel = ViewModelProvider(this, NoteViewModelFactory(NoteRepository(application))).get(
                NoteViewModel::class.java
        )
        adapter1 = NoteAdapter(this)

        noteViewModel!!.getAllNotes().observe(this) {
            Toast.makeText(this@MainActivity, "onCHanged Observer", Toast.LENGTH_SHORT).show()
            //recyclerView.adapter=NoteAdapter(this, it)
         //   adapter1 = NoteAdapter(this, it)

            adapter1!!.setNotes(it)
            recyclerView.adapter = adapter1
            xx= adapter1!!.itemCount+1
        }


        //1
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                noteViewModel!!.delete(adapter1!!.getNoteAt(viewHolder.adapterPosition));

            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(findViewById(R.id.recycler_view))


      //  Toast.makeText(this, "Kon Touch Kara", Toast.LENGTH_SHORT).show()

        adapter1!!.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                intent.putExtra("pos", note.id)
                intent.putExtra("title", note.title)
                intent.putExtra("description", note.description)
                intent.putExtra("priority", note.priority)
                startForEdit.launch(intent)
                //  val RESULT_NOK = 0


                // startActivityForResult(intent, RESULT_NOK);
            }

        })



      //  setRecyclerViewItemTouchListener()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel!!.deleteAllNotes()
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }


    }
    val startForEdit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        run {
            if (result.resultCode == Activity.RESULT_OK) {
                val data:Intent?=result.data
                val id: Int? = data?.getIntExtra("idd", -1)
                if (id == -1) {
                    Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()

                }
                val title: String? = data?.getStringExtra("abc")
                val description: String? = data?.getStringExtra("def")
                val priority: Int? = data?.getIntExtra("ghi",-1)
                var note:Note?=null
                if (title != null && description != null && priority != null) {
                    if(id!=null)
                        note = Note(id,title, description, priority)
                    //xx++
                }

                if(note!=null)
                { noteViewModel!!.update(note)}
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}




