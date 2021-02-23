package com.fanta.util;


import com.alibaba.fastjson.JSONObject;
import com.fanta.config.exceptions.CommonJsonException;
import com.fanta.util.constants.Constants;
import com.fanta.util.constants.ErrorEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

//常用的json工具类
public class CommonUtil {

    /**
     * 返回一个状态为200的json
     * */
    public static JSONObject successJson(Object info){
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", Constants.SUCCESS_CODE);
        resultJson.put("msg",Constants.SUCCESS_MSG);
        resultJson.put("info",info);
        return resultJson;
    }


    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", errorEnum.getErrorCode());
        resultJson.put("msg", errorEnum.getErrorMsg());
        resultJson.put("info", new JSONObject());
        return resultJson;
    }

    /**
     * 将request转换为json
     * */
    public static JSONObject request2Json(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String paramName = (String) parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<parameterValues.length;i++){
                if(parameterValues[i].length()>0){
                    if(i>0){
                        sb.append(",");
                    }
                    sb.append(parameterValues[i]);
                }
            }
            jsonObject.put(paramName,sb.toString());
        }
        return jsonObject;
    }


    /**
     * 在分页查询之前，为查询条件里加上分页参数：
     * 取出JSONObject里的参数，如果为空就赋上默认值
     * 并且加上offset参数，offset为目前查询到第几条
     * */
    public static void fillPageParam(final JSONObject paramObject){

        int defaultPageRow = 10;
        int pageNum = paramObject.getIntValue("pageNum");
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = paramObject.getIntValue("pageRow");
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        paramObject.put("offSet",(pageNum-1)*pageRow);
        paramObject.put("pageRow",pageRow);
        paramObject.put("pageNum",pageNum);
        //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
        paramObject.remove("pageSize");
    }


    /**
     * 获取总页数
     * */
    private static int getPageCount(int pageRow,int itemCount){
        if(itemCount == 0)return 1;
        else{
            return itemCount % pageRow == 0?
                    itemCount / pageRow:
                    itemCount / pageRow + 1;
        }
    }


    /**
     * 分页查询之后的封装工具:
     * 给查询结果加上总页数和总条目数之后一并返回
     * */
    public static JSONObject successPage(final JSONObject jsonObject, List<JSONObject> list, int count){
        int pageRow = jsonObject.getIntValue("pageRow");
        int totalPage = getPageCount(pageRow,count);
        JSONObject result = new JSONObject();
        result.put("list",list);
        result.put("totalPage",totalPage);
        result.put("totalCount",count);
        return result;
    }

    public static JSONObject successPage(List<JSONObject> list){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        jsonObject.put("code",Constants.SUCCESS_CODE);
        jsonObject.put("msg", Constants.SUCCESS_MSG);
        return jsonObject;
    }

    /**
     * 验证是否含有全部必填字段
     * */
    public static void hasAllRequiredParam(final JSONObject jsonObject,String requiredParams){
        if(StringTools.isNullOrEmpty(requiredParams)){
            String [] columns = requiredParams.split(",");
            String missParam = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if(StringTools.isNullOrEmpty(val)){
                    missParam += column+" ";
                }
            }
            if(!StringTools.isNullOrEmpty(missParam)){
                jsonObject.clear();
                jsonObject.put("code", ErrorEnum.E_90003.getErrorCode());
                jsonObject.put("msg",ErrorEnum.E_90003.getErrorMsg());
                jsonObject.put("info",new JSONObject());
                throw  new CommonJsonException(jsonObject);
            }
        }
    }
}
