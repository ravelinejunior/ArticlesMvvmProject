package br.com.example.articlesmvvmproject.data.repository.datasource_impl

import br.com.example.articlesmvvmproject.data.db.dao.ArticleDao
import br.com.example.articlesmvvmproject.data.model.Article
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsLocalDataSource

class NewsLocalDataSourceImpl(private val articleDao: ArticleDao):NewsLocalDataSource {
    override suspend fun saveArticleDb(article: Article) {
        articleDao.insertArticle(article)
    }

    override suspend fun deleteArticleDb(article: Article) {
        articleDao.deleteArticle(article)
    }
}