package com.haidarh.jwork_android.classes;

import com.haidarh.jwork_android.classes.Location;

public class Recruiter
{
    // variable instance
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;

    /**
     * Constructor untuk Recruiter
     */
    public Recruiter(int id, String name, String email, String phoneNumber, Location location)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * getter untuk mengambil id
     *
     * @return    id milik recruiter
     */
    public int getId()
    {
        return id;
    }

    /**
     * getter untuk mengambil nama
     *
     * @return    nama milik recruiter
     */
    public String getName()
    {
        return name;
    }

    /**
     * getter untuk mengambil email
     *
     * @return    email milik recruiter
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * getter untuk mengambil phoneNumber
     *
     * @return    phone number milik recruiter
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * getter untuk mengambil lokasi
     *
     * @return    lokasi milik recruiter
     */
    public Location getLocation(){
        return location;
    }

    /**
     * setter untuk mengatur nilai id
     *
     * @param  id  parameter untuk mengganti id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * setter untuk mengatur nilai name
     *
     * @param  name  parameter untuk mengganti nama
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * setter untuk mengatur nilai email
     *
     * @param  email  parameter untuk mengganti email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * setter untuk mengatur nilai phoneNumber
     *
     * @param  phoneNumber  parameter untuk mengganti phoneNumber
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * setter untuk mengatur data lokasi
     *
     * @param  location  parameter untuk mengganti lokasi
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * metode untuk mencetak data
     */
    public String toString(){
        return  "===================== Recruiter =====================" +
                "\nId = " + id +
                "\nNama = " + name +
                "\nPhoneNumber = " + phoneNumber +
                "\nLocation = " + location.getCity() +
                "\nEmail = " + email;
    }

}
