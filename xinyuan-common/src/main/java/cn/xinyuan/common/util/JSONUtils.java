package cn.xinyuan.common.util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User:josli li
 * Date:17/8/17
 * Time:上午10:41
 * Mail:josli@kargocard.com
 */
public class JSONUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
    /**
     * 对象转json
     * @param obj
     * @return
     */
    public static String ObjToJson(Object obj){
        try {
            return MAPPER.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            e.getMessage();
            logger.error(e.getMessage());
        }
        return null;
    }
}
