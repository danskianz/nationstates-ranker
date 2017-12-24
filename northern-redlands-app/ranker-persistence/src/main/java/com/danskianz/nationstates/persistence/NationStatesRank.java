package com.danskianz.nationstates.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity of a persisted historic rank record.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@NamedQueries({
    @NamedQuery(
            name = "NationStatesRank.findLatestRankingsByNationName",
            query = "SELECT a FROM NationStatesRank a "
                    + "WHERE a.rankTimestamp in ("
                        + "SELECT max(b.rankTimestamp) "
                        + "FROM NationStatesRank b "
                        + "WHERE a.nation = b.nation"
                    + ") AND a.nation = :nation"
    ),
    @NamedQuery(
            name = "NationStatesRank.findLatestRankings",
            query = "SELECT a from NationStatesRank a "
                    + "WHERE a.rankTimestamp in ("
                        + "SELECT max(b.rankTimestamp) "
                        + "FROM NationStatesRank b "
                        + "WHERE a.nation = b.nation"
                    + ")"
    ),
    @NamedQuery(name = "NationStatesRank.findAll",
            query = "SELECT n FROM NationStatesRank n")
    ,@NamedQuery(name = "NationStatesRank.findByNsrId",
            query = "SELECT n FROM NationStatesRank n WHERE n.nsrId = :nsrId")
    ,@NamedQuery(name = "NationStatesRank.findByNationName",
            query = "SELECT n FROM NationStatesRank n WHERE n.nation = :nation")
    ,@NamedQuery(name = "NationStatesRank.findByNationRank",
            query = "SELECT n FROM NationStatesRank n WHERE n.rank = :rank")
    ,@NamedQuery(name = "NationStatesRank.findByRankTimestamp",
            query = "SELECT n FROM NationStatesRank n WHERE n.rankTimestamp = :rankTimestamp")
})
@Entity
@Table(name = "NATION_STATES_RANK")
@XmlRootElement
public class NationStatesRank implements Serializable {

    private static final long serialVersionUID = -8668528039352590964L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "XNSR_SEQ_GEN")
    @SequenceGenerator(name = "XNSR_SEQ_GEN", 
            sequenceName = "XNSR_SEQ",
            allocationSize = 5)
    @Column(name = "NSR_ID")
    private int nsrId;

    @Basic(optional = false)
    @Column(name = "NATION_NAME")
    private String nation;

    @Basic(optional = false)
    @Column(name = "NATION_RANK")
    private double rank;

    @Basic(optional = false)
    @Column(name = "RANK_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rankTimestamp;

    public NationStatesRank() {
    }

    public NationStatesRank(Integer nsrId) {
        this.nsrId = nsrId;
    }

    public NationStatesRank(Integer nsrId, String nationName,
            int nationRank, Date rankTimestamp) {
        this.nsrId = nsrId;
        this.nation = nationName;
        this.rank = nationRank;
        this.rankTimestamp = rankTimestamp;
    }
    
    public int getNsrId() {
        return nsrId;
    }

    public void setNsrId(int nsrId) {
        this.nsrId = nsrId;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public Date getRankTimestamp() {
        return rankTimestamp;
    }

    public void setRankTimestamp(Date rankTimestamp) {
        this.rankTimestamp = rankTimestamp;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(nsrId);
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof NationStatesRank)) {
            return false;
        }
        NationStatesRank other = (NationStatesRank) object;
        
        return Objects.equals(this.nsrId, other.nsrId);
    }

    @Override
    public String toString() {
        return "NationStatesRank{" + "nsrId=" + nsrId
                + ", nation=" + nation + ", rank=" + rank
                + ", rankTimestamp=" + rankTimestamp + '}';
    }

}
