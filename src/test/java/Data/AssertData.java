package Data;

public class AssertData {

    String WorkSheetName;
    String SheetName;
    String ResultFiledName;
    String TestcaseDesc;
    String jenKinsURL;

    /**
     * @return the testcaseDesc
     */
    public String getTestcaseDesc() {
        return TestcaseDesc;
    }
    /**
     * @param testcaseDesc the testcaseDesc to set
     */
    public void setTestcaseDesc(String testcaseDesc) {
        TestcaseDesc = testcaseDesc;
    }
    /**
     * @param workSheetName
     * @param sheetName
     * @param resultFiledName
     * @param JenkinsEnvURL
     */
    public AssertData(String workSheetName, String sheetName, String resultFiledName,String TestDescriptionField, String JenkinsEnvURL) {
        super();
        WorkSheetName = workSheetName;
        SheetName = sheetName;
        ResultFiledName = resultFiledName;
        TestcaseDesc = TestDescriptionField;
        jenKinsURL = JenkinsEnvURL;
    }
    /**
     * @return the resultFiledName
     */
    public String getResultFiledName() {
        return ResultFiledName;
    }
    /**
     * @param resultFiledName the resultFiledName to set
     */
    public void setResultFiledName(String resultFiledName) {
        ResultFiledName = resultFiledName;
    }
    /**
     * @return the workSheetName
     */
    public String getWorkSheetName() {
        return WorkSheetName;
    }
    /**
     * @param workSheetName the workSheetName to set
     */
    public void setWorkSheetName(String workSheetName) {
        WorkSheetName = workSheetName;
    }
    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return SheetName;
    }
    /**
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        SheetName = sheetName;
    }
    /**
     * @return the jenKinsURL
     */
    public String getJenKinsURL() {
        return jenKinsURL;
    }
    /**
     * @param jenKinsURL the jenKinsURL to set
     */
    public void setJenKinsURL(String jenKinsURL) {
        this.jenKinsURL = jenKinsURL;
    }




}

