package com.爬取搜狗美女图片;


import com.alibaba.fastjson.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * 爬取图片类
 * @description:
 * @authror: snaker
 * @date: create:2021/12/11 下午 11:19
 * @version: V1.0
 * @modified By:
 */
public class SougouImgProcessor {
    private String url;

    private SougouImgPipeline pipeline;
    private List<JSONObject> dataList;
    private List<String> urlList;
    private String word;

public SougouImgProcessor(String url,String word){
        this.url = url;
        this.word = word;
        this.pipeline = new SougouImgPipeline();
        this.dataList = new ArrayList<>();
        this.urlList = new ArrayList<>();
    }

    public void process(int idx, int size,String mv) {
        String format = String.format(this.url, idx, size, mv, this.word);
        System.out.println(format);
        String res = HttpClientUtils.get(format);
        System.out.println(res);
        JSONObject object = JSONObject.parseObject(res);
//        List<JSONObject> items = (List<JSONObject>)((JSONObject)object.get("data")).get("items");
        List<JSONObject> items = (List<JSONObject>) object.get("data");

        for(JSONObject item : items){
//            this.urlList.add(item.getString("picUrl"));

            this.urlList.add(item.getString("middleURL"));
    }
        this.dataList.addAll(items);
    }

    // 下载
       public void pipelineData(){
   // 多线程
       pipeline.processSync(this.urlList,this.word);
  }


    public static void main(String[] args) {
//        String url = "https://pic.sogou.com/napi/pc/searchList?mode=1&start=%s&xml_len=%s&query=%s";

        String url = "https://image.baidu.com/search/acjson?tn=resultjson_com&logid=5256468679945141988&ipn=rj&pn=1&rn=5&ct=201326592&is=&fp=result&fr=ala&word=%s&cg=girl&queryWord=%s&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=&z=&ic=&hd=&latest=&copyright=&s=&se=&tab=&width=&height=&face=&istype=&qc=&nc=&expermode=&nojc=&isAsync=&gsm=1e&1640323635601=";
//        String url = "https://image.baidu.com/search/albumsdata?pn=30&rn=30&tn=albumsdetail&word=%E6%B8%90%E5%8F%98%E9%A3%8E%E6%A0%BC%E6%8F%92%E7%94%BB&album_tab=%E8%AE%BE%E8%AE%A1%E7%B4%A0%E6%9D%90&album_id=105&ic=0&curPageNum=1";
   String mv ="美女";
        SougouImgProcessor processor = new SougouImgProcessor(url,"美女");

        int start = 0,size = 50,limit =1000;// 定义爬取开始索引、每次爬取数量、总共爬取数量

        for(int i=start;i<start+limit;i+=size)
        processor.process(i,size,mv);

        processor.pipelineData();
    }
}

