package com.example.myapp

import android.app.Application
import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.loader.content.AsyncTaskLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NoteRepository(application: Application) {
    public var database = NoteDatabase.getInstance(application)
    private var noteDao = database.noteDao()
    private var allNotes = noteDao.getAllNotes()
    //private var noteDao:NoteDao?=null
   // private var allNotes: LiveData<List<Note>>?=null

//    fun NoteRepository(application: Application?) {
//        val database = NoteDatabase.getInstance(application!!)
//        noteDao = database.noteDao
//        allNotes = noteDao!!.getAllNotes()
//    }

   suspend fun insert(note: Note) {

        withContext(Dispatchers.IO)
        {
            noteDao.insert(note)
        }

    }

    suspend fun update(note: Note) {

        withContext(Dispatchers.IO)
        {
            noteDao.update(note)
        }
    }

    suspend fun delete(note: Note) {
       // DeleteNoteAsyncTask(noteDao!!).execute(note)

        withContext(Dispatchers.IO)
        {
            noteDao.delete(note)
        }
    }

    suspend fun deleteAllNotes() {
        //DeleteAllNotesAsyncTask(noteDao!!).execute()
        withContext(Dispatchers.IO)
        {
            noteDao.DeleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Note>> {

            return allNotes

    }
//    private class InsertNoteAsyncTask : AsyncTask{
//
//    }



}