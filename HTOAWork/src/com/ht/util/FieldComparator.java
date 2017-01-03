package com.ht.util;

import java.lang.reflect.Field;
import java.util.Comparator;


public class FieldComparator<T> implements Comparator<Object> {

	public int compare(Object arg0, Object arg1) {
        Field fieldOne = (Field)arg0;
        Field fieldTwo = (Field)arg1;
        ExcelAnnotation annoOne = fieldOne.getAnnotation(ExcelAnnotation.class);
        ExcelAnnotation annoTwo = fieldTwo.getAnnotation(ExcelAnnotation.class);
        if(annoOne == null || annoTwo == null){
            return 0;
        }
        return annoOne.id()-annoTwo.id();
    }

}
