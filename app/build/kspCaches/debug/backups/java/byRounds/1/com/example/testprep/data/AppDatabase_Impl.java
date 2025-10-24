package com.example.testprep.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.testprep.data.dao.MistakeDao;
import com.example.testprep.data.dao.MistakeDao_Impl;
import com.example.testprep.data.dao.QuestionDao;
import com.example.testprep.data.dao.QuestionDao_Impl;
import com.example.testprep.data.dao.SessionDao;
import com.example.testprep.data.dao.SessionDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile QuestionDao _questionDao;

  private volatile MistakeDao _mistakeDao;

  private volatile SessionDao _sessionDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `questions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `questionText` TEXT NOT NULL, `option1` TEXT NOT NULL, `option2` TEXT NOT NULL, `option3` TEXT NOT NULL, `correctIndex` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `mistakes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `questionId` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_mistakes_questionId` ON `mistakes` (`questionId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `mode` TEXT NOT NULL, `total` INTEGER NOT NULL, `correct` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '11316a4cad60fae0894ba4130dbd2e8f')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `questions`");
        db.execSQL("DROP TABLE IF EXISTS `mistakes`");
        db.execSQL("DROP TABLE IF EXISTS `sessions`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsQuestions = new HashMap<String, TableInfo.Column>(6);
        _columnsQuestions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("questionText", new TableInfo.Column("questionText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("option1", new TableInfo.Column("option1", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("option2", new TableInfo.Column("option2", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("option3", new TableInfo.Column("option3", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("correctIndex", new TableInfo.Column("correctIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuestions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuestions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuestions = new TableInfo("questions", _columnsQuestions, _foreignKeysQuestions, _indicesQuestions);
        final TableInfo _existingQuestions = TableInfo.read(db, "questions");
        if (!_infoQuestions.equals(_existingQuestions)) {
          return new RoomOpenHelper.ValidationResult(false, "questions(com.example.testprep.data.entity.QuestionEntity).\n"
                  + " Expected:\n" + _infoQuestions + "\n"
                  + " Found:\n" + _existingQuestions);
        }
        final HashMap<String, TableInfo.Column> _columnsMistakes = new HashMap<String, TableInfo.Column>(2);
        _columnsMistakes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMistakes.put("questionId", new TableInfo.Column("questionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMistakes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMistakes = new HashSet<TableInfo.Index>(1);
        _indicesMistakes.add(new TableInfo.Index("index_mistakes_questionId", true, Arrays.asList("questionId"), Arrays.asList("ASC")));
        final TableInfo _infoMistakes = new TableInfo("mistakes", _columnsMistakes, _foreignKeysMistakes, _indicesMistakes);
        final TableInfo _existingMistakes = TableInfo.read(db, "mistakes");
        if (!_infoMistakes.equals(_existingMistakes)) {
          return new RoomOpenHelper.ValidationResult(false, "mistakes(com.example.testprep.data.entity.MistakeEntity).\n"
                  + " Expected:\n" + _infoMistakes + "\n"
                  + " Found:\n" + _existingMistakes);
        }
        final HashMap<String, TableInfo.Column> _columnsSessions = new HashMap<String, TableInfo.Column>(5);
        _columnsSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("mode", new TableInfo.Column("mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("total", new TableInfo.Column("total", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("correct", new TableInfo.Column("correct", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSessions = new TableInfo("sessions", _columnsSessions, _foreignKeysSessions, _indicesSessions);
        final TableInfo _existingSessions = TableInfo.read(db, "sessions");
        if (!_infoSessions.equals(_existingSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "sessions(com.example.testprep.data.entity.SessionEntity).\n"
                  + " Expected:\n" + _infoSessions + "\n"
                  + " Found:\n" + _existingSessions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "11316a4cad60fae0894ba4130dbd2e8f", "110cb0f470291a89cf4a1542e60ac2f9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "questions","mistakes","sessions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `questions`");
      _db.execSQL("DELETE FROM `mistakes`");
      _db.execSQL("DELETE FROM `sessions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(QuestionDao.class, QuestionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MistakeDao.class, MistakeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SessionDao.class, SessionDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public QuestionDao questionDao() {
    if (_questionDao != null) {
      return _questionDao;
    } else {
      synchronized(this) {
        if(_questionDao == null) {
          _questionDao = new QuestionDao_Impl(this);
        }
        return _questionDao;
      }
    }
  }

  @Override
  public MistakeDao mistakeDao() {
    if (_mistakeDao != null) {
      return _mistakeDao;
    } else {
      synchronized(this) {
        if(_mistakeDao == null) {
          _mistakeDao = new MistakeDao_Impl(this);
        }
        return _mistakeDao;
      }
    }
  }

  @Override
  public SessionDao sessionDao() {
    if (_sessionDao != null) {
      return _sessionDao;
    } else {
      synchronized(this) {
        if(_sessionDao == null) {
          _sessionDao = new SessionDao_Impl(this);
        }
        return _sessionDao;
      }
    }
  }
}
