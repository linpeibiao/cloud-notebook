package com.xiaohu.cloud_notebook.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.AppendObjectRequest;
import com.qcloud.cos.model.AppendObjectResult;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.region.Region;
import com.xiaohu.cloud_notebook.service.TencentCosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiaohu
 * @date 2022/09/03/ 22:42
 * @description 图床业务接口实现类
 */
@Service
public class TencentCosServiceImpl implements TencentCosService {
    private static final Logger LOG = LoggerFactory.getLogger(TencentCosServiceImpl.class);

    @Value("${spring.cos.accessKey}")
    private String accessKey;

    @Value("${spring.cos.secretKey}")
    private String secretKey;

    @Value("${spring.cos.regionName}")
    private String regionName;

    @Value("${spring.cos.bucketName}")
    private String bucketName;

    @Value("${spring.cos.keyName}")
    private String keyName;

    /**
     * 上传图片对接腾讯CDN
     *
     * @param fileDataFileName
     * @param request
     * @param response
     * @return
     */
    @Override
    public String ContentCOS(MultipartFile fileDataFileName,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(regionName));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        // 获取文件类型
        String name = fileDataFileName.getOriginalFilename();
        String fileType = name.substring(name.lastIndexOf(".") + 1);

        // bucket名需包含appid
        UUID uuid = UUID.randomUUID();
        String key = keyName + "/" + uuid + "." + fileType;
        String url = null;
        try {
            // 处理文件路径
            String filePath = request.getSession().getServletContext().getRealPath("/") + fileDataFileName.getOriginalFilename();
            fileDataFileName.transferTo(new File(filePath));
            File localFile = new File(filePath);

            // 报错请求对象
            AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key, localFile);
            // 设置节点
            appendObjectRequest.setPosition(0L);
            AppendObjectResult appendObjectResult = cosclient.appendObject(appendObjectRequest);
            // 文件大小
            long nextAppendPosition = appendObjectResult.getNextAppendPosition();
            LOG.info("文件大小：{}", nextAppendPosition);

            // 获取返回对象
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            COSObject cosObject = cosclient.getObject(getObjectRequest);
            url = cosObject.getObjectContent().getHttpRequest().getURI().toString();
            LOG.info("COS对象地址：{}", url);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cosclient.shutdown();
        }
        return url;
    }
}
