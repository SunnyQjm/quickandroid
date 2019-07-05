package cn.qjm253.quick_android_base.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

/**
 * Created by Sunny on 2017/4/19 0019.
 */
object GsonUtil {
    val instance: Gson = Gson()


    fun <T> json2Bean(json: String, beanClass: Class<T>): T? {
        var bean: T? = null
        try {
            bean = instance.fromJson(json, beanClass)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return bean
    }

    fun <T> json2Bean(json: String): T? {
        var bean: T? = null
        val type = object : TypeToken<ArrayList<T>>() {

        }.type
        try {
            bean = instance.fromJson<T>(json, type)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }

        return bean
    }

    //    public static ArrayList<Tag> json2TagList(String json) {
    //        Type type = new TypeToken<ArrayList<Tag>>(){}.getType();
    //        ArrayList<Tag> bean = new ArrayList<>();
    //        try {
    //            bean = getInstance().fromJson(json, type);
    //        } catch (JsonSyntaxException e) {
    //            e.printStackTrace();
    //        }
    //        return bean;
    //    }
    //
    //    public static ArrayList<Record> json2RecordList(String json) {
    //        Type type = new TypeToken<ArrayList<Record>>(){}.getType();
    //        ArrayList<Record> bean = new ArrayList<>();
    //        try {
    //            bean = getInstance().fromJson(json, type);
    //        } catch (JsonSyntaxException e) {
    //            e.printStackTrace();
    //        }
    //        return bean;
    //    }
    //
    //    public static ArrayList<Image> json2ImageList(String json) {
    //        Type type = new TypeToken<ArrayList<Image>>(){}.getType();
    //        ArrayList<Image> bean = new ArrayList<>();
    //        try {
    //            bean = getInstance().fromJson(json, type);
    //        } catch (JsonSyntaxException e) {
    //            e.printStackTrace();
    //        }
    //        return bean;
    //    }


    fun bean2Json(`object`: Any): String {
        return instance.toJson(`object`)
    }
}