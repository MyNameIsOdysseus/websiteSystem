package net.minggao.core.framework.validate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;


/**
 *  @null 	        验证对象是否为空
	@notnull 	验证对象是否为非空
	@asserttrue 	验证 boolean 对象是否为 true
	@assertfalse 	验证 boolean 对象是否为 false
	@min 	        验证 number 和 string 对象是否大等于指定的值
	@max 	        验证 number 和 string 对象是否小等于指定的值
	@decimalmin 	验证 number 和 string 对象是否大等于指定的值，小数存在精度
	@decimalmax 	验证 number 和 string 对象是否小等于指定的值，小数存在精度
	@size 	        验证对象（array,collection,map,string）长度是否在给定的范围之内
	@digits 	验证 number 和 string 的构成是否合法
	@past 	        验证 date 和 calendar 对象是否在当前时间之前
	@future 	验证 date 和 calendar 对象是否在当前时间之后
	@pattern 	验证 string 对象是否符合正则表达式的规则 
 * 
 * @author         liumc
 * @version        V1.0  
 * @Date           2017-3-28 下午3:51:29
 */
public class ValidatorUtil {
	private static Validator validator = Validation.buildDefaultValidatorFactory()  
            .getValidator();  
      
    public static <T> Map<String,StringBuffer> validate(T obj){  
        Map<String,StringBuffer> errorMap = null;  
        Set<ConstraintViolation<T>> set = validator.validate(obj,Default.class);  
        if(set != null && set.size() >0 ){  
            errorMap = new HashMap<String,StringBuffer>();  
            String property = null;  
            for(ConstraintViolation<T> cv : set){  
                //这里循环获取错误信息，可以自定义格式  
                property = cv.getPropertyPath().toString();  
                if(errorMap.get(property) != null){  
                    errorMap.get(property).append("," + cv.getMessage());  
                }else{  
                    StringBuffer sb = new StringBuffer();  
                    sb.append(cv.getMessage());  
                    errorMap.put(property, sb);  
                }  
            }  
        }  
        return errorMap;  
    }  
}
