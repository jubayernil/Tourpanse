package com.compiler.tourpanse.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 8/8/2016.
 */
public class Weather {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;
    private String iconUrl = "http://openweathermap.org/img/w/";

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The main
     */
    public String getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The icon
     */
    public String getIcon() {
        iconUrl = "http://openweathermap.org/img/w/";
        String iconType = ".png";
        icon = iconUrl+icon+iconType;
        return icon;
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

}
