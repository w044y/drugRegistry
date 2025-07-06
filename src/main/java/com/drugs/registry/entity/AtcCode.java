// AtcCode.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "atc_codes")
public class AtcCode {

    @Id
    @Column(name = "atc_code", length = 20)
    private String atcCode;

    @Column(name = "atc_level1", length = 10)
    private String atcLevel1;

    @Column(name = "atc_level1_name", length = 200)
    private String atcLevel1Name;

    @Column(name = "atc_level2", length = 10)
    private String atcLevel2;

    @Column(name = "atc_level2_name", length = 200)
    private String atcLevel2Name;

    @Column(name = "atc_level3", length = 10)
    private String atcLevel3;

    @Column(name = "atc_level3_name", length = 200)
    private String atcLevel3Name;

    @Column(name = "atc_level4", length = 10)
    private String atcLevel4;

    @Column(name = "atc_level4_name", length = 200)
    private String atcLevel4Name;

    @Column(name = "atc_level5", length = 10)
    private String atcLevel5;

    @Column(name = "atc_level5_name", length = 200)
    private String atcLevel5Name;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public String getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getAtcLevel1() {
        return atcLevel1;
    }

    public void setAtcLevel1(String atcLevel1) {
        this.atcLevel1 = atcLevel1;
    }

    public String getAtcLevel1Name() {
        return atcLevel1Name;
    }

    public void setAtcLevel1Name(String atcLevel1Name) {
        this.atcLevel1Name = atcLevel1Name;
    }

    public String getAtcLevel2() {
        return atcLevel2;
    }

    public void setAtcLevel2(String atcLevel2) {
        this.atcLevel2 = atcLevel2;
    }

    public String getAtcLevel2Name() {
        return atcLevel2Name;
    }

    public void setAtcLevel2Name(String atcLevel2Name) {
        this.atcLevel2Name = atcLevel2Name;
    }

    public String getAtcLevel3() {
        return atcLevel3;
    }

    public void setAtcLevel3(String atcLevel3) {
        this.atcLevel3 = atcLevel3;
    }

    public String getAtcLevel3Name() {
        return atcLevel3Name;
    }

    public void setAtcLevel3Name(String atcLevel3Name) {
        this.atcLevel3Name = atcLevel3Name;
    }

    public String getAtcLevel4() {
        return atcLevel4;
    }

    public void setAtcLevel4(String atcLevel4) {
        this.atcLevel4 = atcLevel4;
    }

    public String getAtcLevel4Name() {
        return atcLevel4Name;
    }

    public void setAtcLevel4Name(String atcLevel4Name) {
        this.atcLevel4Name = atcLevel4Name;
    }

    public String getAtcLevel5() {
        return atcLevel5;
    }

    public void setAtcLevel5(String atcLevel5) {
        this.atcLevel5 = atcLevel5;
    }

    public String getAtcLevel5Name() {
        return atcLevel5Name;
    }

    public void setAtcLevel5Name(String atcLevel5Name) {
        this.atcLevel5Name = atcLevel5Name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
