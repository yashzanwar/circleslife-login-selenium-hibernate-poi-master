package Framework;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataBaseActivity {

    static Connection conn = null;
    static List<XSSFSheet> listOfshits;
    GoogleSheets GWorkSheet;

    /**
     * dumps Excel Data into Derby database which is virtually exist (EmbededDatabase)
     * Sheets as Database table
     * @throws Throwable
     */

    public static void StartConnection() throws SQLException {
        try {
            conn = DriverManager.getConnection("jdbc:derby:testmdb1;create=true", "test", "test");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getCause().getMessage());
            if(e.getCause().getMessage().equalsIgnoreCase("Failed to start database"))
                conn =DriverManager.getConnection("jdbc:derby:;shutdown=true");//closing database
            conn = DriverManager.getConnection("jdbc:derby:testmdb1;create=true", "test", "test");//starting database
        }
    }

    private static String getAppendedGHeaderData(StringBuilder headerNameOfsheet, ListEntry headers) {
        Set<String> tags = headers.getCustomElements().getTags();

        for (String tag:tags) {
            String headerName = "" +tag+ " varchar(30000)";
            headerNameOfsheet = headerNameOfsheet.append(headerName+",");
        }
        return headerNameOfsheet.substring(0,headerNameOfsheet.length()-1);
    }

    private static String getAppendedGRowData(StringBuilder rowNameOfsheet, ListEntry row) {
        rowNameOfsheet = new StringBuilder();
        for (String tag : row.getCustomElements().getTags())
        {
            rowNameOfsheet = rowNameOfsheet.append("'" + row.getCustomElements().getValue(tag) + "',");
        }


        return rowNameOfsheet.substring(0,rowNameOfsheet.length()-1);
    }

    private static void creatHeader(StringBuilder headerCellValue, String Tablename) throws SQLException {
        // TODO Auto-generated method stub
        String createSQL = "create table " + Tablename + " (" + headerCellValue + ")";
        System.out.println(createSQL);

        try {
            conn.createStatement().execute(createSQL);
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static void insertDataIntoTable(StringBuilder rowCellValue, String Tablename) throws SQLException {
        String insertTabledata = "insert into " + Tablename + " values(" + rowCellValue + ")";
        System.out.println(insertTabledata);
        conn.createStatement().execute(insertTabledata);

    }

    public static void dropTable(String tableName) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "drop table " + tableName + "";
        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }

    public void dropTableFromGsheet() throws IOException, URISyntaxException, Throwable{
        GWorkSheet = new GoogleSheets();
        SpreadsheetService service = GWorkSheet.getService();
        SpreadsheetEntry GWorkbook = GWorkSheet.getSpreadSheet("Circles Life");
        List<WorksheetEntry> Sheets = GWorkbook.getWorksheets();
        for (WorksheetEntry sheet : Sheets) {
            String Tablename = sheet.getTitle().getPlainText();
            dropTable(Tablename);
        }
    }
    @Test
    public void createTableFromGSpreadsheets() throws Throwable
    {     StartConnection();

        GWorkSheet = new GoogleSheets();
        SpreadsheetService service = GWorkSheet.getService();
        SpreadsheetEntry GWorkbook = GWorkSheet.getSpreadSheet("Circles Life");
        List<WorksheetEntry> Sheets = GWorkbook.getWorksheets();
        for (WorksheetEntry sheet : Sheets) {
            String Tablename = sheet.getTitle().getPlainText();
            System.out.println(Tablename);
            URL SheetlistFeedUrl = sheet.getListFeedUrl();
            ListFeed RowList = service.getFeed(SheetlistFeedUrl, ListFeed.class);
            ListEntry headers = RowList.getEntries().get(0);
            StringBuilder HeaderCellValue = new StringBuilder();
            String headerrow = getAppendedGHeaderData(HeaderCellValue, headers);
            StringBuilder headerrows = new StringBuilder(headerrow);
            creatHeader(headerrows, Tablename);
            StringBuilder RowCellsheet = new StringBuilder();

            for (ListEntry row : RowList.getEntries()) {

                String Row = getAppendedGRowData(RowCellsheet, row);

                StringBuilder	rowValue = new StringBuilder(Row);
                insertDataIntoTable(rowValue, Tablename);

            }

        }

    }



}

