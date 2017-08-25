package cn.xinyuan.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SequenceValueItem {
    private String seqName;

    private BigDecimal seqId;

    private Date lastUpdatedStamp;

    private Date createdStamp;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName == null ? null : seqName.trim();
    }

    public BigDecimal getSeqId() {
        return seqId;
    }

    public void setSeqId(BigDecimal seqId) {
        this.seqId = seqId;
    }

    public Date getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(Date lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public Date getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Date createdStamp) {
        this.createdStamp = createdStamp;
    }
}