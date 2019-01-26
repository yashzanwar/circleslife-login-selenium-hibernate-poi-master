package Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class EnvironmentParameterData {
    @Id
    @Column
    String rowid;
    @Column
    String testcaseid;
    @Column
    String baseurl;
    @Column
    String browsername;
    @Column
    String emailAddress;
    @Column
    String testgroup;
    /**
     * @return the rowid
     */
    public String getRowid() {
        return rowid;
    }
    /**
     * @param rowid the rowid to set
     */
    public void setRowid(String rowid) {
        this.rowid = rowid;
    }
    /**
     * @return the baseurl
     */
    public String getBaseurl() {
        return baseurl;
    }
    /**
     * @param baseurl the baseurl to set
     */
    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }
    /**
     * @return the browsername
     */
    public String getBrowsername() {
        return browsername;
    }
    /**
     * @param browsername the browsername to set
     */
    public void setBrowsername(String browsername) {
        this.browsername = browsername;
    }
    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }
    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    /**
     * @return the testgroup
     */
    public String getTestgroup() {
        return testgroup;
    }
    /**
     * @param testgroup the testgroup to set
     */
    public void setTestgroup(String testgroup) {
        this.testgroup = testgroup;
    }





}


