package com.fanta.service;

import com.alibaba.fastjson.JSONObject;

public interface ArticleService {
    JSONObject listArticle(JSONObject jsonObject);
    JSONObject addArticle(JSONObject jsonObject);
    JSONObject updateArticle(JSONObject jsonObject);
    JSONObject deleteArticle(JSONObject jsonObject);
}
