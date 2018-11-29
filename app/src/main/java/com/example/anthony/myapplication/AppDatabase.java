package com.example.anthony.myapplication;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * References:
 * https://android.jlelse.eu/pre-populate-room-database-6920f9acc870
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/
 * https://developer.android.com/training/data-storage/room/
 */
@Database(entities = {Weather.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            //nothing to write if table not altered;
        }
    };
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "Weather")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            //.addMigrations(MIGRATION_3_4)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    Log.d("*--*-**--*-*-*- callback", "before population*-*-*-*-*-*-");
                                    super.onCreate(db);
                                    new PopulateDbAsync(INSTANCE, context).execute();
                                    Log.d("*--*-**--*-*-*- callback", "DONE *-*-*-*-*-*-");
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WeatherDao weatherDao();

    /**
     * Populate the database in the background.
     * Populating the DB with weather data from  2018
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WeatherDao mDao;
        private Context context;

        PopulateDbAsync(AppDatabase db, Context context) {
            mDao = db.weatherDao();
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {

            Log.d("****BACKGROUDND****", "==== OnCreate: Populating DB ====");
            Weather[] wArray = WeatherDataRetriever.getWeatherArrayFromJsonFile(context);
            mDao.insertAll(wArray);
            return null;
        }
    }
}