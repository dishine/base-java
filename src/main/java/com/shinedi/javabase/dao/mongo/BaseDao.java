package com.shinedi.javabase.dao.mongo;

import com.alibaba.fastjson.JSONObject;
import com.shinedi.javabase.common.api.http.CortexApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author D-S
 * Created on 2019/12/9 11:00 上午
 *
 * MrWind  调用cortex最后合理解决方案 调用全部放到dao
 */
@Repository
public class BaseDao {


    //调用cortex接口
    @Resource
    protected CortexApi cortexApi;

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private Object e;

    /**
     * 相应的结构体转化为 List<String>
     * @param tClass tClass
     * @return List<String>
     */
    public <T> List<String> toString(Class<T> tClass){
        List<String> strings  = new ArrayList<>();
        for (Field field : tClass.getDeclaredFields()){
            String fieldName = field.getName();
            if (fieldName.equals("id") || fieldName.equals("status")) {
                continue;
            }
            strings.add(fieldName);
        }
        return strings;
    }

//
//    /**
//     * 转换为对应的结构体
//     * @param getObjectResultDTO cortex返回的数据
//     * @param tClass 要转换对应的结构体
//     * @param <T>
//     * @return list装换后的结构
//     */
//    public   <T> List<T>  toCass(List<Object> getObjectResultDTO, Class<T> tClass){
//        List<T>  classes = new ArrayList<>();
//        for (Object getObjectResultDTO1 : getObjectResultDTO){
//            if(getObjectResultDTO1.getCurrentSLabel().equals("REMOVED")){
//                continue;
//            }
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id",getObjectResultDTO1.getId());
//            for (CortexReadResultDTO.Data dataKVDTO : getObjectResultDTO1.getDatas()){
//                jsonObject.put(dataKVDTO.getDataTypeLabel(),dataKVDTO.getDataValue());
//            }
//            classes.add(jsonObject.toJavaObject(tClass));
//        }
//        return classes;
//    }
//
//    /**
//     * 转换为对应的结构体
//     * @param cortex5WriteDTOS cortex返回的数据
//     * @param tClass 要转换对应的结构体
//     * @param <t> T
//     * @return list装换后的结构
//     */
//    public   <t> List<t>  toCassWrite(List<Cortex5WriteDTO> cortex5WriteDTOS, Class<t> tClass){
//        List<t>  classes = new ArrayList<>();
//
//        for (Cortex5WriteDTO cortex5WriteDTO : cortex5WriteDTOS){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id",cortex5WriteDTO.getTargetId());
//            for (DataKV dataKVDTO : cortex5WriteDTO.getTargetData()){
//                jsonObject.put(dataKVDTO.getDataTypeLabel(),dataKVDTO.getDataValue());
//            }
//            classes.add(jsonObject.toJavaObject(tClass));
//        }
//        return classes;
//    }
//
//
//    /**
//     * 结构体装list<DataKV></>
//     * @param clz class
//     * @return list<DataKV></>
//     */
//    public <T>   List<DataKV>   toDataKVS(T clz) throws IllegalAccessException {
//        List<DataKV> dataKVS = new ArrayList<>();
//        for (Field field: clz.getClass().getDeclaredFields()){
//            if (field.isAnnotationPresent(CortexId.class) || field.isAnnotationPresent(CortexStatus.class) || field.isAnnotationPresent(CortexIgnore.class)){
//                continue;
//            }
//            DataKV dataKV = new DataKV();
//            field.setAccessible(true);
//            String name = field.getName();
//            dataKV.setDataTypeLabel(name);
//            dataKV.setDataValue(field.get(clz));
//            dataKVS.add(dataKV);
//        }
//        return dataKVS;
//    }
//
//
//    /**
//     * 获取Id
//     * @param cortex5WriteDTOS cortex返回的数据
//     * @return id
//     */
//
//    public String  toGetId(List<Cortex5WriteDTO> cortex5WriteDTOS){
//        if ( cortex5WriteDTOS == null || cortex5WriteDTOS.isEmpty()){
//            throw new BusinessException(EmBusinessError.ERROR_CORTEX);
//        }else {
//            Cortex5WriteDTO  cortex5WriteDTO =   cortex5WriteDTOS.get(0);
//            if (StringUtils.isBlank(cortex5WriteDTO.getTargetId())){
//                throw new BusinessException(EmBusinessError.ERROR_CORTEX);
//            }else {
//                return cortex5WriteDTO.getTargetId();
//            }
//        }
//    }
//
//    /**
//     * update
//     * @param Class  T
//     * @param project 项目名
//     * @param id id
//     * @param nowStatus  现在状态
//     * @param fromStatus  以前的状态
//     * @param reason 原因
//     * @param <t> T
//     * @return  T
//     * @throws IllegalAccessException  exception
//     */
//    public <t> t update(t Class, @NotNull String project, String id, @NotNull String nowStatus, String fromStatus, String reason) throws IllegalAccessException, InstantiationException {
//        Cortex5WriteDTO cortex5WriteDTO = new Cortex5WriteDTO();
//        cortex5WriteDTO.setObjectTypeLable(project);
//        if (StringUtils.isNotBlank(fromStatus)){
//            cortex5WriteDTO.setTargetFormerStatus(fromStatus);
//        }
//        cortex5WriteDTO.setTargetCurrentStatus(nowStatus);
//        if (StringUtils.isNotBlank(id)){
//            cortex5WriteDTO.setTargetId(id);
//        }
//        if(Class != null){
//            cortex5WriteDTO.setTargetData(toDataKVS(Class));
//        }
//        cortex5WriteDTO.setReason(reason);
//        String idc = toGetId(cortexApi.write(Collections.singletonList(cortex5WriteDTO)));
//        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(idc) && idc.equals(id)){
//            return Class;
//        }
//        if (StringUtils.isBlank(id) && StringUtils.isNotBlank(idc)){
//            //fixme 不更新数据只更新 状态
//            if (Class != null){
//                return setId(Class,idc);
//            }else {
//                return Class;
//            }
//        }
//        throw new BusinessException(EmBusinessError.ERROR_CORTEX,"更新失败");
//    }
//
//    /**
//     *  批量更新
//     * @param clzs list<E></>
//     * @param project  项目名
//     * @param nowStatus 现在状态
//     * @param fromStatus  以前的状态
//     * @param reason 原因
//     * @param <T> list<E></>
//     * @return list<E></>
//     * @throws IllegalAccessException   exception
//     */
//    public <T>  List<T>  updateList(List<T> clzs, @NotNull String project,@NotNull String nowStatus, String fromStatus, String reason) throws IllegalAccessException{
//        List<Cortex5WriteDTO> cortex5WriteDTOS = new ArrayList<>();
//        for (T e : clzs){
//            Cortex5WriteDTO cortex5WriteDTO = new Cortex5WriteDTO();
//            cortex5WriteDTO.setObjectTypeLable(project);
//            if (StringUtils.isNotBlank(fromStatus)){
//                cortex5WriteDTO.setTargetFormerStatus(fromStatus);
//            }
//            cortex5WriteDTO.setTargetCurrentStatus(nowStatus);
//            String cortexId = getId(e);
//            if (cortexId != null){
//                cortex5WriteDTO.setTargetId(cortexId);
//            }
//            cortex5WriteDTO.setTargetData(toDataKVS(e));
//            cortex5WriteDTO.setReason(reason);
//            cortex5WriteDTOS.add(cortex5WriteDTO);
//        }
//        List<Cortex5WriteDTO> cortex5WriteDTOSResult =  cortexApi.write(cortex5WriteDTOS);
//        return (List<T>) toCassWrite(cortex5WriteDTOSResult,clzs.get(0).getClass());
//    }
//
//    private <T> String  getId(T c) throws IllegalAccessException {
//        for (Field field: c.getClass().getDeclaredFields()){
//            if (field.isAnnotationPresent(CortexId.class) ) {
//                return (String) field.get(c);
//            }
//        }
//        return null;
//    }
//
//    private <T> T setId(T Class, String id) throws IllegalAccessException, InstantiationException {
//        for (Field field: Class.getClass().getDeclaredFields()){
//            if (field.isAnnotationPresent(CortexId.class) ) {
//                field.setAccessible(true);
//                field.set(Class, id);
//                return Class;
//            }
//        }
//        return Class;
//    }
}