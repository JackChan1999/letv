package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

public class FastJsonJsonView extends AbstractView {
    public static final String DEFAULT_CONTENT_TYPE = "application/json";
    public static final Charset UTF8 = Charset.forName("UTF-8");
    private Charset charset = UTF8;
    private boolean disableCaching = true;
    private Set<String> renderedAttributes;
    private SerializerFeature[] serializerFeatures = new SerializerFeature[0];
    private boolean updateContentLength = false;

    public FastJsonJsonView() {
        setContentType("application/json");
        setExposePathVariables(false);
    }

    public void setRenderedAttributes(Set<String> renderedAttributes) {
        this.renderedAttributes = renderedAttributes;
    }

    @Deprecated
    public void setSerializerFeature(SerializerFeature... features) {
        setFeatures(features);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return this.serializerFeatures;
    }

    public void setFeatures(SerializerFeature... features) {
        this.serializerFeatures = features;
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] bytes = JSON.toJSONString(filterModel(model), this.serializerFeatures).getBytes(this.charset);
        OutputStream stream = this.updateContentLength ? createTemporaryOutputStream() : response.getOutputStream();
        stream.write(bytes);
        if (this.updateContentLength) {
            writeToResponse(response, (ByteArrayOutputStream) stream);
        }
    }

    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        setResponseContentType(request, response);
        response.setCharacterEncoding(UTF8.name());
        if (this.disableCaching) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader(HttpRequest.HEADER_CACHE_CONTROL, "no-cache, no-store, max-age=0");
            response.addDateHeader(HttpRequest.HEADER_EXPIRES, 1);
        }
    }

    public void setDisableCaching(boolean disableCaching) {
        this.disableCaching = disableCaching;
    }

    public void setUpdateContentLength(boolean updateContentLength) {
        this.updateContentLength = updateContentLength;
    }

    protected Object filterModel(Map<String, Object> model) {
        Map<String, Object> result = new HashMap(model.size());
        Set<String> renderedAttributes = !CollectionUtils.isEmpty(this.renderedAttributes) ? this.renderedAttributes : model.keySet();
        for (Entry<String, Object> entry : model.entrySet()) {
            if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
