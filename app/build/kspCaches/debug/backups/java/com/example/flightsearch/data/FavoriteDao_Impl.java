package com.example.flightsearch.data;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Favorite> __insertionAdapterOfFavorite;

  private final EntityDeletionOrUpdateAdapter<Favorite> __deletionAdapterOfFavorite;

  public FavoriteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavorite = new EntityInsertionAdapter<Favorite>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `favorite` (`id`,`departure_code`,`destination_code`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Favorite value) {
        stmt.bindLong(1, value.getId());
        if (value.getDepartureCode() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDepartureCode());
        }
        if (value.getDestinationCode() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDestinationCode());
        }
      }
    };
    this.__deletionAdapterOfFavorite = new EntityDeletionOrUpdateAdapter<Favorite>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `favorite` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Favorite value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public Object addToFavorite(final Favorite flight,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavorite.insert(flight);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object removeFromFavorite(final Favorite flight,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavorite.handle(flight);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Favorite>> getFavorites() {
    final String _sql = "SELECT * from favorite";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"favorite"}, new Callable<List<Favorite>>() {
      @Override
      public List<Favorite> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDepartureCode = CursorUtil.getColumnIndexOrThrow(_cursor, "departure_code");
          final int _cursorIndexOfDestinationCode = CursorUtil.getColumnIndexOrThrow(_cursor, "destination_code");
          final List<Favorite> _result = new ArrayList<Favorite>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Favorite _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpDepartureCode;
            if (_cursor.isNull(_cursorIndexOfDepartureCode)) {
              _tmpDepartureCode = null;
            } else {
              _tmpDepartureCode = _cursor.getString(_cursorIndexOfDepartureCode);
            }
            final String _tmpDestinationCode;
            if (_cursor.isNull(_cursorIndexOfDestinationCode)) {
              _tmpDestinationCode = null;
            } else {
              _tmpDestinationCode = _cursor.getString(_cursorIndexOfDestinationCode);
            }
            _item = new Favorite(_tmpId,_tmpDepartureCode,_tmpDestinationCode);
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
