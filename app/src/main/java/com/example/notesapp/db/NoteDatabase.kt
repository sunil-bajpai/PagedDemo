package com.example.notesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.db.ioThread


@Database(
    entities = [Note::class, City::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
    abstract fun getCityDao(): CityDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null
        val PREPOPULATE_DATA = listOf(
            City(1, "Lucknow",226010),
            City(2, "Delhi",110001),
            City(3, "Bengaluru",560001),
            City(4, "Kolkata",700001),
            City(5, "Mumbai",400001),
            City(6, "Pune",411001))

        fun getInstance(context: Context): NoteDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "noteDb"
                    ).allowMainThreadQueries()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // insert the data on the IO Thread
                                ioThread {
                                    getInstance(context).getCityDao().addCity(PREPOPULATE_DATA)
                                }
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
//    companion object {
//        @Volatile
//        private var instance: NoteDatabase? = null
//
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            NoteDatabase::class.java,
//            "noteDb"
//        ).addCallback(zipPopulate)
//            .fallbackToDestructiveMigration()
//            .build()
//
//        var zipPopulate: RoomDatabase.Callback = object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                // do something after database has been created
//
//                super.onCreate(db)
//                PopulateDBAT(instance).execute()
//            }
//        }
//    }
//}
//
//private class PopulateDBAT(db: NoteDatabase?) :
//    AsyncTask<Void?, Void?, Void?>() {
//    private val locationDao: CityDao
//    override fun doInBackground(vararg params: Void?): Void? {
//        //TODO: change to on create
//        locationDao.addCity(City(1, "Delhi", 28))
//        locationDao.addCity(City(2, "Kolkata", 22))
//        locationDao.addCity(City(3, "Mumbai", 19))
//        locationDao.addCity(City(4, "Bengaluru", 12))
//        locationDao.addCity(City(5, "Pune", 18))
//        locationDao.addCity(City(6, "Lucknow", 17))
//        return null
//    }
//    init {
//        locationDao = db!!.getCityDao()
//    }
//}