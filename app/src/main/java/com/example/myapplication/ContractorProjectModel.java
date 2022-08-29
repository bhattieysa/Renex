package com.example.myapplication;

public class ContractorProjectModel {
    String jobtitle,jobdesc,jobloc,jobtime,jobbudget,image,imageUrl,image2,image3,imageurl2,imageurl3,jobid;

    public ContractorProjectModel(){


    }




    public ContractorProjectModel( String jobtitle, String jobdesc, String jobloc, String jobtime, String jobbudget,
                                  String image, String imageUrl, String image2, String image3, String imageurl2, String imageurl3,String jobid) {

        this.jobtitle = jobtitle;
        this.jobdesc = jobdesc;
        this.jobloc = jobloc;
        this.jobtime = jobtime;
        this.jobbudget = jobbudget;
        this.image = image;
        this.imageUrl = imageUrl;
        this.image2 = image2;
        this.imageurl2 = imageurl2;
        this.image3 = image3;
        this.imageurl3 = imageurl3;
        this.jobid = jobid;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getJobdesc() {
        return jobdesc;
    }

    public void setJobdesc(String jobdesc) {
        this.jobdesc = jobdesc;
    }

    public String getJobloc() {
        return jobloc;
    }

    public void setJobloc(String jobloc) {
        this.jobloc = jobloc;
    }

    public String getJobtime() {
        return jobtime;
    }

    public void setJobtime(String jobtime) {
        this.jobtime = jobtime;
    }

    public String getJobbudget() {
        return jobbudget;
    }

    public void setJobbudget(String jobbudget) {
        this.jobbudget = jobbudget;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImageurl2() {
        return imageurl2;
    }

    public void setImageurl2(String imageurl2) {
        this.imageurl2 = imageurl2;
    }

    public String getImageurl3() {
        return imageurl3;
    }

    public void setImageurl3(String imageurl3) {
        this.imageurl3 = imageurl3;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }


}
