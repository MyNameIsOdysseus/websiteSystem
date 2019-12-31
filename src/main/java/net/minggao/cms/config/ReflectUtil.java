package net.minggao.cms.config;

import java.lang.reflect.Field;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */

public class ReflectUtil {

    public static Object reflect(Object o){
        Class cla = o.getClass();
        Field field[] =cla.getDeclaredFields();
        //System.out.println("------执行反射------");
        for(int a=0;a<field.length;a++){
            Field field1 = field[a];
            field1.setAccessible(true);
            try {
                //System.out.println("反射拿到的属性名称是："+field1.getName()+" 属性值是："+field1.get(o));
                Class<?> filedType =field1.getType();
                //System.out.println("反射拿到的属性的类型是"+filedType);
                Boolean ssss = filedType==String.class;
                if(ssss && field1.get(o)!=null){
                    field1.set(o, HttpParamSecurityUtils.decodeForHTML(field1.get(o).toString()));
                   // System.out.println("执行逆解析后属性名称是："+field1.getName()+" 属性值是："+field1.get(o));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

}
