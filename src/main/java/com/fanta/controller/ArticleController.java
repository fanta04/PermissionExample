package com.fanta.controller;


import com.alibaba.fastjson.JSONObject;
import com.fanta.service.ArticleService;
import com.fanta.util.CommonUtil;
import javafx.geometry.Pos;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequiresPermissions("article:list")
    @GetMapping("list")
    public JSONObject listArticles(HttpServletRequest request){
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return articleService.listArticle(jsonObject);
    }

    @RequiresPermissions("article:add")
    @PostMapping("add")
    public JSONObject addArticle(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"content");
        return articleService.addArticle(jsonObject);
    }

    @RequiresPermissions("article:update")
    @PostMapping("update")
    public JSONObject updateArticle(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"id,content");
        return articleService.updateArticle(jsonObject);
    }

    @RequiresPermissions("article:delete")
    @PostMapping("delete")
    public JSONObject deleteArticle(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"id");
        return articleService.deleteArticle(jsonObject);
    }
}
