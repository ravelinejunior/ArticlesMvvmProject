package br.com.example.articlesmvvmproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.example.articlesmvvmproject.data.db.dao.ArticleDao
import br.com.example.articlesmvvmproject.data.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

}