package com.haidarh.jwork_android.classes;

/**
 * Kelas Location.
 *
 * @author Haidar Hanif
 */
public class Location
{
    private String province;
    private String city;
    private String description;

    /**
     * Constructor untuk Location
     *
     * @param province    the province
     * @param city        the city
     * @param description the description
     */
    public Location(String province, String city, String description)
    {
        this.province = province;
        this.city = city;
        this.description = description;
    }

    /**
     * getter untuk mengambil provinsi
     *
     * @return province dari location
     */
    public String getProvince()
    {
        return province;
    }

    /**
     * getter untuk mengambil city
     *
     * @return nilai city dari location
     */
    public String getCity()
    {
        return city;
    }

    /**
     * getter untuk mengambil description
     *
     * @return deskripsi dari location
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * setter untuk mengatur nilai province
     *
     * @param province parameter untuk mengganti province
     */
    public void setProvince(String province)
    {
        this.province = province;
    }

    /**
     * setter untuk mengatur nilai city
     *
     * @param city parameter untuk mengganti city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * setter untuk mengatur deskripsi
     *
     * @param description parameter untuk mengganti deskripsi
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * metode untuk mencetak data
     */
    public String toString(){
        return  "===================== Location =====================" +
                "\nProvince = " + province +
                "\nCity = " + city +
                "\nDescription = " + description;
    }
}

