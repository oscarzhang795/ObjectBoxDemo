package com.sdadg.roomviewmodeldemo.data.migrations

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

class Migration1To2: Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //Alter table sql
        database.execSQL("ALTER TABLE Post ADD COLUMN textColor INTEGER");

    }
}