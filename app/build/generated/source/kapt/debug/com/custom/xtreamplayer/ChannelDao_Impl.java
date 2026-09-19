package com.custom.xtreamplayer;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChannelDao_Impl implements ChannelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChannelEntity> __insertionAdapterOfChannelEntity;

  private final SharedSQLiteStatement __preparedStmtOfSetFavorite;

  private final SharedSQLiteStatement __preparedStmtOfClearSection;

  private final SharedSQLiteStatement __preparedStmtOfClearCategory;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public ChannelDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChannelEntity = new EntityInsertionAdapter<ChannelEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `channels` (`streamId`,`name`,`sectionType`,`categoryId`,`iconUrl`,`containerExtension`,`isFavorite`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChannelEntity entity) {
        if (entity.getStreamId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getStreamId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getSectionType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSectionType());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCategoryId());
        }
        if (entity.getIconUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getIconUrl());
        }
        if (entity.getContainerExtension() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getContainerExtension());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__preparedStmtOfSetFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE channels SET isFavorite = ? WHERE streamId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearSection = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM channels WHERE sectionType = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearCategory = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM channels WHERE sectionType = ? AND categoryId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM channels";
        return _query;
      }
    };
  }

  @Override
  public Object insertChannels(final List<ChannelEntity> channels,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChannelEntity.insert(channels);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setFavorite(final String streamId, final boolean isFav,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = isFav ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (streamId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, streamId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearSection(final String type, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearSection.acquire();
        int _argIndex = 1;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, type);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearSection.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearCategory(final String type, final String catId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearCategory.acquire();
        int _argIndex = 1;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, type);
        }
        _argIndex = 2;
        if (catId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, catId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearCategory.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getChannelsByCategory(final String type, final String catId,
      final Continuation<? super List<ChannelEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM channels\n"
            + "        WHERE sectionType = ? AND categoryId = ?\n"
            + "        ORDER BY name COLLATE NOCASE ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    _argIndex = 2;
    if (catId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, catId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ChannelEntity>>() {
      @Override
      @NonNull
      public List<ChannelEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStreamId = CursorUtil.getColumnIndexOrThrow(_cursor, "streamId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "sectionType");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfIconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "iconUrl");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<ChannelEntity> _result = new ArrayList<ChannelEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChannelEntity _item;
            final String _tmpStreamId;
            if (_cursor.isNull(_cursorIndexOfStreamId)) {
              _tmpStreamId = null;
            } else {
              _tmpStreamId = _cursor.getString(_cursorIndexOfStreamId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSectionType;
            if (_cursor.isNull(_cursorIndexOfSectionType)) {
              _tmpSectionType = null;
            } else {
              _tmpSectionType = _cursor.getString(_cursorIndexOfSectionType);
            }
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpIconUrl;
            if (_cursor.isNull(_cursorIndexOfIconUrl)) {
              _tmpIconUrl = null;
            } else {
              _tmpIconUrl = _cursor.getString(_cursorIndexOfIconUrl);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new ChannelEntity(_tmpStreamId,_tmpName,_tmpSectionType,_tmpCategoryId,_tmpIconUrl,_tmpContainerExtension,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getFavorites(final String type,
      final Continuation<? super List<ChannelEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM channels\n"
            + "        WHERE sectionType = ? AND isFavorite = 1\n"
            + "        ORDER BY name COLLATE NOCASE ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ChannelEntity>>() {
      @Override
      @NonNull
      public List<ChannelEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStreamId = CursorUtil.getColumnIndexOrThrow(_cursor, "streamId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "sectionType");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfIconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "iconUrl");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<ChannelEntity> _result = new ArrayList<ChannelEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChannelEntity _item;
            final String _tmpStreamId;
            if (_cursor.isNull(_cursorIndexOfStreamId)) {
              _tmpStreamId = null;
            } else {
              _tmpStreamId = _cursor.getString(_cursorIndexOfStreamId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSectionType;
            if (_cursor.isNull(_cursorIndexOfSectionType)) {
              _tmpSectionType = null;
            } else {
              _tmpSectionType = _cursor.getString(_cursorIndexOfSectionType);
            }
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpIconUrl;
            if (_cursor.isNull(_cursorIndexOfIconUrl)) {
              _tmpIconUrl = null;
            } else {
              _tmpIconUrl = _cursor.getString(_cursorIndexOfIconUrl);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new ChannelEntity(_tmpStreamId,_tmpName,_tmpSectionType,_tmpCategoryId,_tmpIconUrl,_tmpContainerExtension,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getFavoriteIds(final String type,
      final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT streamId FROM channels WHERE sectionType = ? AND isFavorite = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
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
    }, $completion);
  }

  @Override
  public Object isFavorite(final String streamId, final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT isFavorite FROM channels WHERE streamId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (streamId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, streamId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @Nullable
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object searchChannels(final String query,
      final Continuation<? super List<ChannelEntity>> $completion) {
    final String _sql = "SELECT * FROM channels WHERE name LIKE '%' || ? || '%' ORDER BY name COLLATE NOCASE ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ChannelEntity>>() {
      @Override
      @NonNull
      public List<ChannelEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStreamId = CursorUtil.getColumnIndexOrThrow(_cursor, "streamId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "sectionType");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfIconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "iconUrl");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<ChannelEntity> _result = new ArrayList<ChannelEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChannelEntity _item;
            final String _tmpStreamId;
            if (_cursor.isNull(_cursorIndexOfStreamId)) {
              _tmpStreamId = null;
            } else {
              _tmpStreamId = _cursor.getString(_cursorIndexOfStreamId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSectionType;
            if (_cursor.isNull(_cursorIndexOfSectionType)) {
              _tmpSectionType = null;
            } else {
              _tmpSectionType = _cursor.getString(_cursorIndexOfSectionType);
            }
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpIconUrl;
            if (_cursor.isNull(_cursorIndexOfIconUrl)) {
              _tmpIconUrl = null;
            } else {
              _tmpIconUrl = _cursor.getString(_cursorIndexOfIconUrl);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new ChannelEntity(_tmpStreamId,_tmpName,_tmpSectionType,_tmpCategoryId,_tmpIconUrl,_tmpContainerExtension,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
