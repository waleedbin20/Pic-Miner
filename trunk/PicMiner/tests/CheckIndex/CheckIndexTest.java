package CheckIndex;

import org.junit.*;
import javax.swing.filechooser.FileSystemView;
import static org.junit.Assert.*;

public class CheckIndexTest {
    static CheckIndex checkIndex;

    @Before
    public void setUp() throws Exception {
        checkIndex = new CheckIndex();
    }

    @After
    public void tearDown() throws Exception {
        checkIndex.resetCsv();
        checkIndex.resetIndex();
    }

    @Test
    public void testConstructor() {
        assertNotNull(checkIndex);
        assertEquals(checkIndex.getCsv(), FileSystemView.getFileSystemView()
                .getDefaultDirectory().getPath() + "/PicMiner/images/img_indexing.csv");
        assertNotEquals(checkIndex.getCsv(), "");
    }

    @Test
    public void fileIndexCheck() {}

    @Test
    public void compareMetadata() {
        assertTrue(checkIndex.compareMetadata("C:/ce320_10/trunk/PicMiner/test_repository/test_doc/ce320_word_test.docx"));
        assertFalse(checkIndex.compareMetadata("incorrect"));
    }

    @Test
    public void appendToIndex() {}

    @Test
    public void outputToCSV() {
        assertNotNull(checkIndex.getIndex());
    }
}