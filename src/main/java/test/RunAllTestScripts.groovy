package test
import junit.framework.TestSuite

public class MyTestSuite extends TestSuite {
     // Since Eclipse launches tests relative to the project root,
     // declare the relative path to the test scripts for convenience
     private static final String TEST_ROOT = "src/main/java/test/";
     public static TestSuite suite() throws Exception {
         TestSuite suite = new TestSuite();
         GroovyTestSuite gsuite = new GroovyTestSuite();
		 suite.addTestSuite(gsuite.compile(TEST_ROOT + "SomeTest.groovy"));
         return suite;
     }
}