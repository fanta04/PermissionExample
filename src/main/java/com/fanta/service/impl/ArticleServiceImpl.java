package com.fanta.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanta.dao.ArticleDao;
import com.fanta.service.ArticleService;
import com.fanta.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public JSONObject listArticle(JSONObject jsonObject) {
        //将jsonObject的参数（页数，每页几行）填上默认值
        CommonUtil.fillPageParam(jsonObject);
        //统计文章数量
        int count = articleDao.countArticles();
        //返回一个JSONObject的列表，每一个JSONObject代表一篇文章
        List<JSONObject> list = articleDao.listArticle(jsonObject);
        //第一个参数只是为了拿到jsonObject中的pageRow好计算总页数...
        return CommonUtil.successPage(jsonObject,list,count);
    }

    @Override
    public JSONObject addArticle(JSONObject jsonObject) {
        articleDao.addArticle(jsonObject);
        return CommonUtil.successJson(jsonObject);
    }

    @Override
    public JSONObject updateArticle(JSONObject jsonObject) {
        articleDao.updateArticle(jsonObject);
        return CommonUtil.successJson(jsonObject);
    }

    @Override
    public JSONObject deleteArticle(JSONObject jsonObject) {
        articleDao.deleteArticle(jsonObject);
        return CommonUtil.successJson(jsonObject);
    }
}
