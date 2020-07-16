/*

Name: Francisco Ozuna Diaz
Assignment: CS 7455 Lab 8
Lab Date: Due July 19, 2020 at 11:59 PM

 */

package com.example.lab8a;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StudentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.lab8a";

    public static final String PATH_STUDENT_LIST = "STUDENT_LIST";

    public static final Uri CONTENT_URI_1 = Uri.parse("content://" + AUTHORITY + "/" + PATH_STUDENT_LIST);

    public static final String CONTENT_TYPE_1 = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PATH_STUDENT_LIST;

    public static final int STUDENT_LIST = 1;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, PATH_STUDENT_LIST, STUDENT_LIST);
    }

    public static final String MIME_TYPE_1 = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd.com.lab8a.students";

    private StudentListDBAdapter studentListDBAdapter;

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case STUDENT_LIST: return MIME_TYPE_1;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        studentListDBAdapter = StudentListDBAdapter.getStudentListDBAdapterInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (MATCHER.match(uri)) {
            case STUDENT_LIST: cursor = studentListDBAdapter.getCursorsForAllToDos(); break;
            default: cursor = null; break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) throws UnsupportedOperationException{
        Uri returnUri = null;
        switch (MATCHER.match(uri)){
            case STUDENT_LIST: returnUri= insertStudent(uri,contentValues);break;
            default: new UnsupportedOperationException("insert operation not supported"); break;
        }

        return returnUri ;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private Uri insertStudent(Uri uri, ContentValues contentValues){
        long id = studentListDBAdapter.insert(contentValues);
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse("content://"+AUTHORITY+"/"+ PATH_STUDENT_LIST +"/"+id);
    }
}
