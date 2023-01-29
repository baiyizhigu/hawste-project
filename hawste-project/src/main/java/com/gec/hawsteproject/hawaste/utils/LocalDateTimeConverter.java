package com.gec.hawsteproject.hawaste.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义LocalDateTime的excel转换器
 */
@Component
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    /**
     * 从配置文件中注入配置属性，可以让Date和LocalDate统一json格式
     */
    @Value("${spring.jackson.date-format}")
    private String pattern;

    /*
    * 设置转换的Java的属性类型
    * */
    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    /*设置excel中的需要转换LocalDateTime的类型*/
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /*
    * 设置excel转java对象操作
    * */
    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //DateTimeFormatter.ofPattern(pattern) 设置解析格式
        //cellData.getStringValue()取出单元格中的值
        return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 设置java对象转换excel操作
     * @param localDateTime
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CellData<String> convertToExcelData(LocalDateTime localDateTime, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(localDateTime.format( DateTimeFormatter.ofPattern(pattern)));
    }
}
