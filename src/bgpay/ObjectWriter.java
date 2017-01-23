package bgpay;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ObjectWriter {

	File savedDataFile;
	ObjectOutputStream output;
	ObjectInputStream input;

	public ObjectWriter() {
		try {
			savedDataFile = new File("savedDataFile");
			input = new ObjectInputStream(new FileInputStream(savedDataFile));
			output = new ObjectOutputStream(new FileOutputStream(savedDataFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
