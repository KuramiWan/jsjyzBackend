package ga.jsjyz.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUtil  {
    //服务器地址
    final String baseUrl="http://localhost:8888";
    //获取编译文件夹下的static路径
    final String basePath = Objects.requireNonNull(getClass().getClassLoader().getResource("static")).getPath();
    public String uploadListReturnStr(String filePath, List<MultipartFile> voFileList){
        File file=new File(basePath+filePath);
        if(!file.exists()){//文件夹不存在
            file.mkdirs();//创建文件夹
        }
        //文件输出流
        FileOutputStream fileOutputStream=null;
        //缓冲输出流
        BufferedOutputStream bufferedOutputStream=null;
        //前端限制好，图片为空不让上传
        //获取前端的图片文件
//		MultipartFile voFile=vo.getImg();
        //要上传的图片文件
        File imgFile=null;
        //要上传的图片名
        String imgName=null;
        //上传后图片的url
        String imgUrl=null;
        //单个文件
        MultipartFile voFile=null;
        //图片地址列表
        StringBuilder imgUrlList= new StringBuilder();
        try {
            for (int i = 0; i < voFileList.size(); i++) {
                voFile=voFileList.get(i);
                //获取图片二进制数据
                byte[] bytes=voFile.getBytes();
                imgName= UUID.randomUUID()+".jpg";
                imgFile=new File(file,imgName);
                //文件输出流对象
                fileOutputStream=new FileOutputStream(imgFile);
                //缓冲流对象
                bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
                //将图片数据写入文件
                bufferedOutputStream.write(bytes);
                //数据推出管道流
                bufferedOutputStream.flush();
                //关闭输出流
                bufferedOutputStream.close();
                //将图片的url写到类中
                imgUrl=baseUrl+filePath+"/"+imgName;
                imgUrlList.append(imgUrl).append(";");
            }
            return imgUrlList.toString();
        }catch (IOException e){//发生IO异常
            e.printStackTrace();
            return null;
        }
    }
}
