package net.minggao.cms.config;

import net.minggao.cms.model.Article;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */

@Component
public  class  SimpleUtil {

    @Autowired
    private ConfigProperties configProperties;
    private static ConfigProperties con;
    @PostConstruct
    public void init(){
        con = this.configProperties;
    }


    private HttpClient client;

    public String address;

    private static final String CHARSET_NAME = "UTF-8";

    public static Long getuuid(){
        return (Long) (new Date().getTime())+(int)(1+Math.random()*1000);
    }

    public static List getPageSize(int currpage,int pagesize,List list){
        int startindex=(currpage-1)*pagesize;
        int endindex=currpage*pagesize;
        if(list.size()>pagesize && endindex<=list.size()){
            return list.subList(startindex,endindex);
        }else if(list.size()>pagesize && endindex>list.size() && list.size()>startindex){
            return list.subList(startindex,list.size());
        }
        /*else if(endindex>list.size() && startindex>list.size() ){
            return list=null;
        }*/
        else{
            return list.subList(0,list.size());
        }
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    public static String doPost(String url, String params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 超时时间30秒
            int timeout = 30 * 1000;
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.connect();
            if (params != null) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), CHARSET_NAME);
                out.write(params);
                out.flush();
                out.close();
            }
            InputStreamReader r = new InputStreamReader(conn.getInputStream(), CHARSET_NAME);
            BufferedReader reader = new BufferedReader(r);
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }


    public  String sendXMLDataByPost(String url, String xmlData) throws Exception {
        if (client == null){
            client = HttpClients.createDefault();
        }
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("xml", xmlData));
        post.setEntity(new UrlEncodedFormEntity(parameters,"UTF-8"));
        HttpResponse response = client.execute(post);
        System.out.println(response.toString());
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }

    public static String testToConnection(Article article){
        System.out.println("节点信息"+con.getESServices());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><type>mgcms</type>" +
                "<columnlist>" +
                "<colum name=\"id\" value=\"idvalue\"/>" +
                "<colum name=\"name\" value=\"namevalue\"/>" +
                "<colum name=\"date\" value=\"datevalue\"/>" +
                "</columnlist></root>";

        xml=xml.replaceAll("idvalue",article.getArticleId().toString());
        xml=xml.replaceAll("namevalue",article.getNewssubhead());
        xml=xml.replaceAll("datevalue",formatter.format(article.getPublishdate()));
        String xxx="";
        try {
            xxx = new SimpleUtil().sendXMLDataByPost(con.getESServices()+"/addRecord",xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xxx;
    }

    public static String testToUpdateConnection(Article article){
        System.out.println("节点信息"+con.getESServices());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><type>mgcms</type>" +
                "<columnlist>" +
                "<colum name=\"id\" value=\"idvalue\"/>" +
                "<colum name=\"name\" value=\"namevalue\"/>" +
                "<colum name=\"date\" value=\"datevalue\"/>" +
                "</columnlist></root>";

        xml=xml.replaceAll("idvalue",article.getArticleId().toString());
        xml=xml.replaceAll("namevalue",article.getNewssubhead());
        xml=xml.replaceAll("datevalue",formatter.format(article.getPublishdate()));
        String xxx="";
        try {
            xxx = new SimpleUtil().sendXMLDataByPost(con.getESServices()+"/uptRecord",xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xxx;
    }

    public static String testToDeleteConnection(String articleId){
        System.out.println("节点信息"+con.getESServices());
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><type>mgcms</type>" +
                "<columnlist>" +
                "<colum name=\"id\" value=\"idvalue\"/>" +
                "</columnlist></root>";

        xml=xml.replaceAll("idvalue",articleId);
        String xxx="";
        try {
            xxx = new SimpleUtil().sendXMLDataByPost(con.getESServices()+"/delRecord",xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xxx;
    }

    public static boolean isInteger(String str) {
        if (str == null)
            return false;
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(str).matches();

    }

}
