package com.wdbyte.bing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author niujinpeng
 * @date 2021/02/08
 * @link https://github.com/niumoo
 */
public class Wallpaper {

    // BING API
    private static String BING_API = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=10&nc=1612409408851&pid=hp&FORM=BEHPTB&uhd=1&uhdwidth=3840&uhdheight=2160";

    private static String BING_URL = "https://image.baidu.com/";
    private static SimpleDateFormat sdfTime=new SimpleDateFormat("yyyy-MM-dd");
    public static void main(String[] args) throws IOException {
        String httpContent = HttpUtls.getHttpContent(BING_API);
        Document doc = Jsoup.connect("https://image.baidu.com/").get();
        String url=doc.select("div.wrapper_skin_box").attr("style").split(":url\\(")[1].replace(");","");
        String copyright=doc.select("div.wrapper_imgfrom_box").text();
//        JSONObject jsonObject = JSON.parseObject(httpContent);
//        JSONArray jsonArray = jsonObject.getJSONArray("images");
//
//        jsonObject = (JSONObject)jsonArray.get(0);
//        // 图片地址
//        String url = BING_URL + (String)jsonObject.get("url");
//        url = url.substring(0, url.indexOf("&"));

        // 图片时间
        Date date=new Date();
        String enddate = sdfTime.format(date);

        // 图片版权
//        String copyright = (String)jsonObject.get("copyright");

        List<Images> imagesList = FileUtils.readBing();
        imagesList.set(0,new Images(copyright, enddate, url));
        imagesList = imagesList.stream().distinct().collect(Collectors.toList());
        FileUtils.writeBing(imagesList);
        FileUtils.writeReadme(imagesList);
        FileUtils.writeMonthInfo(imagesList);
    }

}
