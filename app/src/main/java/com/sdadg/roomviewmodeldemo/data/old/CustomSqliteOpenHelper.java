package com.sdadg.roomviewmodeldemo.data.old;

import com.sdadg.roomviewmodeldemo.data.entities.Comment;
import com.sdadg.roomviewmodeldemo.data.entities.Post;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomSqliteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "post_db.db";

    //Tables and column names
    private static final String TABLE_POST = "post";
    private static final String COLUMN_POST_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_POSTED_AT = "postedAt";
    private static final String[] ALL_POST_COLUMNS = {COLUMN_POST_ID, COLUMN_TITLE, COLUMN_POSTED_AT};

    private static final String TABLE_COMMENT = "comment";
    private static final String COLUMN_COMMENT_ID = "_id";
    private static final String COLUMN_COMMENT = "comment";
    private static final String COLUMN_COMMENTED_AT = "commentedAt";
    private static final String COLUMN_COMMENT_POST_ID = "postId";
    private static final String[] ALL_COMMENT_COLUMNS = {COLUMN_COMMENT_ID, COLUMN_COMMENT, COLUMN_COMMENTED_AT, COLUMN_COMMENT_POST_ID};

    public CustomSqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_post_table = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT, %4$s INTEGER)", TABLE_POST, COLUMN_POST_ID, COLUMN_TITLE, COLUMN_POSTED_AT);

        db.execSQL(create_post_table);

        String create_comment_table = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT, %4$s INTEGER, %5$s INTEGER)", TABLE_COMMENT, COLUMN_COMMENT_ID, COLUMN_COMMENT, COLUMN_COMMENTED_AT, COLUMN_COMMENT_POST_ID);

        db.execSQL(create_comment_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);

        onCreate(db);
    }

    public void insertPost(Post post) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, post.getTitle());
        values.put(COLUMN_POSTED_AT, post.getPostedAt());

        db.insertOrThrow(TABLE_POST, null, values);

        db.close();
    }

    public List<Post> getAllPosts() {
        List<Post> returnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_POST, ALL_POST_COLUMNS, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            returnList.add(new Post(cursor.getLong(cursor.getColumnIndex(COLUMN_POST_ID)),
                                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                                    cursor.getLong(cursor.getColumnIndex(COLUMN_POSTED_AT)),
                                    android.R.color.white
                                    )
                            );
            while (cursor.moveToNext()) {
                returnList.add(new Post(cursor.getLong(cursor.getColumnIndex(COLUMN_POST_ID)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                                cursor.getLong(cursor.getColumnIndex(COLUMN_POSTED_AT)),
                                android.R.color.white
                        )
                );
            }

            cursor.close();
        }

        db.close();

        return returnList;
    }

    public void insertComment(Comment comment) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT, comment.getComment());
        values.put(COLUMN_COMMENTED_AT, comment.getCommentedAt());
        values.put(COLUMN_COMMENT_POST_ID, comment.getPostId());

        db.insertOrThrow(TABLE_COMMENT, null, values);

        db.close();
    }

    public List<Comment> getAllCommentsByPostId(Long postId) {
        List<Comment> returnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMMENT, ALL_COMMENT_COLUMNS, COLUMN_COMMENT_POST_ID + " = ?", new String[] {String.valueOf(postId)}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            returnList.add(new Comment(cursor.getLong(cursor.getColumnIndex(COLUMN_COMMENT_ID)),
                    cursor.getLong(cursor.getColumnIndex(COLUMN_COMMENT_POST_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)),
                            cursor.getLong(cursor.getColumnIndex(COLUMN_COMMENTED_AT))
                    )
            );
            while (cursor.moveToNext()) {
                returnList.add(new Comment(cursor.getLong(cursor.getColumnIndex(COLUMN_COMMENT_ID)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_COMMENT_POST_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_COMMENTED_AT))
                        )
                );
            }

            cursor.close();
        }

        db.close();

        return returnList;
    }

    public void deletePosts() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE from " + TABLE_POST);
    }

    public void deleteComment(Long commentId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE from " + TABLE_COMMENT + " Where " + COLUMN_COMMENT_ID + " = " + commentId);
    }
}
