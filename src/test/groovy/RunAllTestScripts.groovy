import junit.framework.TestSuite;

public class MyTestSuite extends TestSuite {
     private static final String TEST_ROOT = "src/test/groovy/com/glebb/osxsmartfolder/tests/";
     public static TestSuite suite() throws Exception {
         TestSuite suite = new TestSuite();
         GroovyTestSuite gsuite = new GroovyTestSuite();
		 suite.addTestSuite(gsuite.compile(TEST_ROOT + "SmartFolderTest.groovy"));
         return suite;
     }
}