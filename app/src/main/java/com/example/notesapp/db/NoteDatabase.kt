package com.example.notesapp.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [Note::class, City::class],
    version = 3
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
    abstract fun getCityDao(): CityDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "noteDb"
        ).addCallback(zipPopulate)
            .fallbackToDestructiveMigration()
            .build()

        var zipPopulate: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                // do something after database has been created

                super.onCreate(db)
                PopulateDBAT(instance).execute()
            }
        }
    }

}

private class PopulateDBAT(db: NoteDatabase?) :
    AsyncTask<Void?, Void?, Void?>() {
    private val locationDao: CityDao
    override fun doInBackground(vararg params: Void?): Void? {
        //TODO: change to on create
        locationDao.addCity(City(1, "Delhi", 28))
        locationDao.addCity(City(2, "Kolkata", 22))
        locationDao.addCity(City(3, "Mumbai", 19))
        locationDao.addCity(City(4, "Bengaluru", 12))
        locationDao.addCity(City(5, "Pune", 18))
        locationDao.addCity(City(6, "Lucknow", 17))
        return null
    }

    init {
        locationDao =db!!.getCityDao()
    }
}