package com.fanta.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDao {
    int countArticles();

    List<JSONObject> listArticle(JSONObject jsonObject);
    int addArticle(JSONObject jsonObject);
    int updateArticle(JSONObject jsonObject);
    int deleteArticle(JSONObject jsonObject);
}
