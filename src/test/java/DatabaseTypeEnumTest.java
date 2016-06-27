import junit.framework.TestCase;
import net.fewald.jfeedreader.DatabaseTypeEnum;
import org.junit.Assert;

/**
 * Created by fe on 27.06.16.
 */
public class DatabaseTypeEnumTest extends TestCase {
    public void testMongodb() {
        DatabaseTypeEnum databaseTypeEnum = DatabaseTypeEnum.mongodb;
        Assert.assertEquals(databaseTypeEnum.mongodb, databaseTypeEnum);
    }

    public void testParse1() {
        DatabaseTypeEnum databaseTypeEnum = DatabaseTypeEnum.fromString("postgres");
        Assert.assertEquals(DatabaseTypeEnum.postgres, databaseTypeEnum);
    }

    public void testParse2() {
        DatabaseTypeEnum databaseTypeEnum = DatabaseTypeEnum.fromString("mongodb");
        Assert.assertEquals(DatabaseTypeEnum.mongodb, databaseTypeEnum);
    }
}
