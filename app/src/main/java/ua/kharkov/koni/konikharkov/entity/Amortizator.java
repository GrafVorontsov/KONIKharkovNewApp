package ua.kharkov.koni.konikharkov.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import ua.kharkov.koni.konikharkov.greenDAO.AmortizatorDao;
import ua.kharkov.koni.konikharkov.greenDAO.DaoSession;

@Entity(active = true, nameInDb = "AMORTIZATORS")
public class Amortizator implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    private Long id;
    private String marka_name;
    private String model_name;
    private String car_name;
    private String correction;
    @NotNull
    private String year;
    @NotNull
    private String range;
    @NotNull
    private String install;
    @NotNull
    private String art_number;
    private String info;
    private String info_lowering;
    private String jpg;
    private String pdf;
    @NotNull
    private String status;
    @NotNull
    private String price_euro;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2109394019)
    private transient AmortizatorDao myDao;


    public Amortizator(String marka_name, String model_name, String car_name, String correction, @NotNull String year, @NotNull String range, @NotNull String install, @NotNull String art_number, String info, String info_lowering, String jpg,
                       String pdf, @NotNull String status, @NotNull String price_euro) {
        this.marka_name = marka_name;
        this.model_name = model_name;
        this.car_name = car_name;
        this.correction = correction;
        this.year = year;
        this.range = range;
        this.install = install;
        this.art_number = art_number;
        this.info = info;
        this.info_lowering = info_lowering;
        this.jpg = jpg;
        this.pdf = pdf;
        this.status = status;
        this.price_euro = price_euro;
    }

    @Generated(hash = 1479664403)
    public Amortizator(Long id, String marka_name, String model_name, String car_name, String correction, @NotNull String year, @NotNull String range, @NotNull String install, @NotNull String art_number, String info, String info_lowering, String jpg,
            String pdf, @NotNull String status, @NotNull String price_euro) {
        this.id = id;
        this.marka_name = marka_name;
        this.model_name = model_name;
        this.car_name = car_name;
        this.correction = correction;
        this.year = year;
        this.range = range;
        this.install = install;
        this.art_number = art_number;
        this.info = info;
        this.info_lowering = info_lowering;
        this.jpg = jpg;
        this.pdf = pdf;
        this.status = status;
        this.price_euro = price_euro;
    }

    @Generated(hash = 1582105123)
    public Amortizator() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarka_name() {
        return marka_name.toUpperCase().charAt(0) + marka_name.substring(1);
    }

    public void setMarka_name(String marka_name) {
        this.marka_name = marka_name;
    }

    public String getModel_name() {
        return model_name.toUpperCase().charAt(0) + model_name.substring(1);
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getInstall() {
        return install;
    }

    public void setInstall(String install) {
        this.install = install;
    }

    public String getArt_number() {
        return art_number;
    }

    public void setArt_number(String art_number) {
        this.art_number = art_number;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo_lowering() {
        return info_lowering;
    }

    public void setInfo_lowering(String info_lowering) {
        this.info_lowering = info_lowering;
    }

    public String getJpg() {
        return jpg;
    }

    public void setJpg(String jpg) {
        this.jpg = jpg;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice_euro() {
        return price_euro;
    }

    public void setPrice_euro(String price_euro) {
        this.price_euro = price_euro;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 184572526)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAmortizatorDao() : null;
    }


}