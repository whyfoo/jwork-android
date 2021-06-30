package com.haidarh.jwork_android.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Kelas Invoice
 *
 * @author Haidar Hanif
 */
public class Invoice
{
    private int id;
    private String jobs;
    private String date;
    private String fee;
    private String totalFee;
    private String jobseeker;
    private String invoiceStatus;
    private String paymentType;
    private String refferalCode;
    private String bonus;

    /**
     * Constructor untuk Invoice
     *
     * @param id            the id
     * @param jobs          the jobs
     * @param date          the date
     * @param fee           the fee
     * @param totalFee      the total fee
     * @param jobseeker     the jobseeker
     * @param invoiceStatus the invoice status
     * @param paymentType   the payment type
     * @param refferalCode  the refferal code
     * @param bonus         the bonus
     */
    public Invoice(int id, String jobs, String date, String fee,String totalFee, String jobseeker, String invoiceStatus, String paymentType, String refferalCode, String bonus)
    {
        this.id = id;
        this.jobs = jobs;
        this.date = date;
        this.fee = fee;
        this.totalFee = totalFee;
        this.jobseeker = jobseeker;
        this.invoiceStatus = invoiceStatus;
        this.paymentType = paymentType;
        this.refferalCode = refferalCode;
        this.bonus = bonus;
    }

    /**
     * getter untuk mengambil id
     *
     * @return id dari invoice
     */
    public int getId()
    {
        return id;
    }

    /**
     * getter untuk mengambil idJob
     *
     * @return idJob pada invoice
     */
    public String getJobs()
    {
        return jobs;
    }

    /**
     * getter untuk mengambil tanggal
     *
     * @return tanggal dari invoice (date)
     */
    public String getDate()
    {
        return date;
    }

    /**
     * getter untuk mengambi total fee
     *
     * @return fee pada job dalam invoice
     */
    public String getFee()
    {
        return fee;
    }

    /**
     * getter untuk mengambil total fee
     *
     * @return total fee pada invoice
     */
    public String getTotalFee()
    {
        return totalFee;
    }

    /**
     * getter untuk mengambil data Jobseeker
     *
     * @return Jobseeker pada invoice
     */
    public String getJobseeker()
    {
        return jobseeker;
    }


    /**
     * getter untuk mengambil data status invoice
     *
     * @return Status pada invoice
     */
    public String getInvoiceStatus()
    {
        return invoiceStatus;
    }

    /**
     * getter untuk mengambil data Payment Type
     *
     * @return Payment Type pada invoice
     */
    public String getPaymentType()
    {
        return paymentType;
    }

    /**
     * getter untuk mengambil data Referral code
     *
     * @return Referral code pada invoice
     */
    public String getRefferalCode()
    {
        return refferalCode;
    }

    /**
     * getter untuk mengambil data Referral code
     *
     * @return Referral code pada invoice
     */
    public String getBonus()
    {
        return bonus;
    }
}

