import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//This class allows the game state to be saved
public class State implements Serializable {
  public int losses;
  public int wins;

  public int getLos(){
    return losses;
  }

  public void addLos(){
    losses += 1;
  }

  public int getWins(){
    return wins;
  }

  public void addWin(){
    wins +=1;
  }

  public String toString () {
    return "Wins: " + wins + "\nLosses: " + losses;
  }

  // Save non-transient state to a local file
  public boolean save () {

    String fileName = "State.ser";
    
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.close();
      fos.close();
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }

  // Returns a State class from serialized state stored in the
  // file name + "State.ser", or null if unable to deserialize 
  public static State restore () {
    String fileName = "State.ser";
    
    try {
		  FileInputStream fis = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fis);
      State s = (State) ois.readObject();
	    ois.close();
	    fis.close();
      return s;
	  } catch(Exception e) { // IOException, ClassNotFoundException
	    return null;
	  }
  }
}