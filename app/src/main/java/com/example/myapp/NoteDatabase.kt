package com.example.myapp

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

@Database(entities = [Note::class], version=1)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDao() : NoteDao
    companion object {
        @Volatile
         var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "Note__Database"

                    ).addCallback(roomCallBack1)
                            .build()
                    INSTANCE = instance

                    //println("Monkke")
                }

                return instance
            }

        }

          private val roomCallBack1=object:RoomDatabase.Callback(){

            override fun onCreate(db: SupportSQLiteDatabase) {

                super.onCreate(db)
                println("bbq")
                db.execSQL("insert into note_table(title,description,priority) values ('Note 1','This is Note 1',1);")
                db.execSQL("insert into note_table(title,description,priority) values ('Note 2','This is Note 2',2);")
                db.execSQL("insert into note_table(title,description,priority) values ('Note 3','This is Note 3',3);")


            }

        }
//        suspend fun populate(db: SupportSQLiteDatabase)
//        {
//            withContext(Dispatchers.IO)
//            {
//                db.execSQL("insert into note_table(Title,Description) values ('Note 1','This is Note 2');")
//                db.execSQL("insert into note_table(Title,Description) values ('Note 2','This is Note 2');")
//                db.execSQL("insert into note_table(Title,Description) values ('Note 2','This is Note 2');")
//            }
//        }
    }

}