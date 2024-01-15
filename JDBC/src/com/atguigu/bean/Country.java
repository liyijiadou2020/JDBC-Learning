package com.atguigu.bean;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2024-01-2024/1/15
 * @description: 对应数据表 countries 的一条记录
 */
public class Country {
    private String country_id;
    private String country_name;
    private Integer region_id;

    public Country(String country_id, String country_name, Integer region_id) {
        this.country_id = country_id;
        this.country_name = country_name;
        this.region_id = region_id;
    }

    public Country() {
    }

    @Override
    public String toString() {
        return "Country [" +
                "country_id='" + country_id + '\'' +
                ", country_name='" + country_name + '\'' +
                ", region_id='" + region_id + '\'' +
                ']';
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }
}
