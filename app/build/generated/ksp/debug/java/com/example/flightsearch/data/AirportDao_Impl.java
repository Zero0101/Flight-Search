package com.example.flightsearch.data;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AirportDao_Impl implements AirportDao {
  private final RoomDatabase __db;

  public AirportDao_Impl(RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public String getNameByIataCode(final String iataCode) {
    final String _sql = "SELECT name FROM airport WHERE iata_code LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (iataCode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, iataCode);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getString(0);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getIataCodes(final String iataCode) {
    final String _sql = "SELECT iata_code FROM airport WHERE iata_code != ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (iataCode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, iataCode);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getString(0);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Flow<List<Airport>> getItems(final String searchRequest) {
    final String _sql = "SELECT * FROM airport WHERE iata_code LIKE ? OR name LIKE ? ORDER BY passengers DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (searchRequest == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchRequest);
    }
    _argIndex = 2;
    if (searchRequest == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchRequest);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"airport"}, new Callable<List<Airport>>() {
      @Override
      public List<Airport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIataCode = CursorUtil.getColumnIndexOrThrow(_cursor, "iata_code");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPassengers = CursorUtil.getColumnIndexOrThrow(_cursor, "passengers");
          final List<Airport> _result = new ArrayList<Airport>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Airport _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpIataCode;
            if (_cursor.isNull(_cursorIndexOfIataCode)) {
              _tmpIataCode = null;
            } else {
              _tmpIataCode = _cursor.getString(_cursorIndexOfIataCode);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpPassengers;
            _tmpPassengers = _cursor.getInt(_cursorIndexOfPassengers);
            _item = new Airport(_tmpId,_tmpIataCode,_tmpName,_tmpPassengers);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
