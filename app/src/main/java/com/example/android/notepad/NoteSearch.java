package com.example.android.notepad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class NoteSearch extends Activity implements SearchView.OnQueryTextListener {
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    private static final String TAG = "NoteSearch";

    /**
     * The columns needed by the cursor adapter
     */
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            //扩展 显示时间戳
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
            //扩展 显示笔记背景颜色
            NotePad.Notes.COLUMN_NAME_BACK_COLOR,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search);

        SearchView searchView = findViewById(R.id.search_view);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        listView = findViewById(R.id.list_view);

        try {
            sqLiteDatabase = new NotePadProvider.DatabaseHelper(this).getReadableDatabase();
        } catch (Exception e) {
            Log.e(TAG, "Database connection error: " + e.getMessage());
            Toast.makeText(this, "Database connection error", Toast.LENGTH_LONG).show();
            return;
        }

        // 设置该 SearchView 显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        // 设置该 SearchView 内默认显示的提示文本
        searchView.setQueryHint("查找");
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "您选择的是：" + query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String string) {
        String selection1 = NotePad.Notes.COLUMN_NAME_TITLE + " like ? or " + NotePad.Notes.COLUMN_NAME_NOTE + " like ?";
        String[] selection2 = {"%" + string + "%", "%" + string + "%"};

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    NotePad.Notes.TABLE_NAME,
                    PROJECTION, // The columns to return from the query
                    selection1, // The columns for the where clause
                    selection2, // The values for the where clause
                    null,       // don't group the rows
                    null,       // don't filter by row groups
                    NotePad.Notes.DEFAULT_SORT_ORDER // The sort order
            );
        } catch (Exception e) {
            Log.e(TAG, "Query error: " + e.getMessage());
            Toast.makeText(this, "Query error", Toast.LENGTH_LONG).show();
            return false;
        }

        if (cursor != null) {
            // The names of the cursor columns to display in the view, initialized to the title column
            String[] dataColumns = {
                    NotePad.Notes.COLUMN_NAME_TITLE,
                    NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
            };
            // The view IDs that will display the cursor columns, initialized to the TextView in
            // noteslist_item.xml
            int[] viewIDs = {
                    android.R.id.text1,
                    android.R.id.text2
            };
            // Creates the backing adapter for the ListView.
            MyCursorAdapter adapter
                    = new MyCursorAdapter(
                    this,
                    R.layout.noteslist_item,
                    cursor,
                    dataColumns,
                    viewIDs
            );

            // Sets the ListView's adapter to be the cursor adapter that was just created.
            listView.setAdapter(adapter);
        }
        return true;
    }
}
