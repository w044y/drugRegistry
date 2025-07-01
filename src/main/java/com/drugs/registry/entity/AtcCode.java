// AtcCode.java
package com.drugs.registry.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "atc_codes")
@Data
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
}
