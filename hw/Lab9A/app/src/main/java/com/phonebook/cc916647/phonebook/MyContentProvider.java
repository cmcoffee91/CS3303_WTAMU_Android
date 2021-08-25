package com.phonebook.cc916647.phonebook;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private final String CONTENT_AUTHORITY = "com.phonebook.cc916647.phonebook.MyContentProvider";
    private Context mContext;
    private MyDBHandler mContactHelper;

    private static final int CONTACTS = 100;

    private static final int CONTACT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String PATH_CONTACTS = "contacts";


    private final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;


    private final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;



    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        //todo: should we delete rows by making them query for person, then append to uri, or allow them to
        // use where clause?
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                // Delete a single row given by the ID in the URI
                return mContactHelper.deleteContactProvider(selectionArgs[0],selectionArgs[1]);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                return CONTENT_LIST_TYPE;
            case CONTACT_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + sUriMatcher.match(uri));
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        //todo: if it returns null, then match for my contact not found
        //todo: maybe throw exception to make sure they check for null?
        //check it in our app
        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                return mContactHelper.addContactProvider(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri + ", with matcher code of: " + sUriMatcher.match(uri));
        }
        /*
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
        */
    }



    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mContext = getContext().getApplicationContext();
        mContactHelper = new MyDBHandler(mContext);

        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_CONTACTS, CONTACTS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_CONTACTS + "/#", CONTACT_ID);


        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //todo: contact_id should match for id
        //todo: maybe another query for CONTACTS if there are selection args

        switch (sUriMatcher.match(uri))
        {
            case CONTACTS:
                return mContactHelper.findAllProvider(null,null,null,null);
            case CONTACT_ID:
                return mContactHelper.findOneProvider(projection,selection,selectionArgs,sortOrder);
            default:
                // TODO: Implement this to handle query requests from clients.
                throw new UnsupportedOperationException("Not yet implemented");
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
