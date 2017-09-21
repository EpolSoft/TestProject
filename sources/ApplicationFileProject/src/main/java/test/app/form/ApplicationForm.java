package test.app.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.sqlite.SQLiteConfig;

public class Applicationform
{

    private static final Logger LOGGER = Logger.getLogger(ApplicationForm.class.getName());

    public static void main(final String[] args)
    {
        System.out.println("\r\nFill in the following application form Anketa_EpolSoft_PA1.docx");
        System.out.println("and send it to this email address: " + getEmailAddress());
    }

    private static String getEmailAddress()
    {
        LOGGER.finest("getEmailAddress");
        String emailAddress = "";
        try
        {
            SQLiteConfig config = new SQLiteConfig();
            config.setReadOnly(true);
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite::resource:project_db", config.toProperties());
            Statement sta = conn.createStatement();
            ResultSet res = sta.executeQuery("SELECT mail FROM contacts WHERE id=1");
            emailAddress = res.getString("email");
            emailAddress = new String(Base64.decodeBase64(emailAddress));
            res.close();
            sta.close();
            conn.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return emailAddress;
    }

}
