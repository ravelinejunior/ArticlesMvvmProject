package br.com.example.articlesmvvmproject.presentation.di

import android.app.Application
import androidx.room.Room
import br.com.example.articlesmvvmproject.data.db.ArticleDatabase
import br.com.example.articlesmvvmproject.data.db.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(articleArticleDatabase: ArticleDatabase):ArticleDao{
        return articleArticleDatabase.getArticleDao()
    }
}