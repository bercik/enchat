package message;

import java.util.ArrayList;

/**
 * Created by tochur on 16.04.15.
 */
public class Message {
    private int id;
    private int errorId;
    private int packageAmount;
    private ArrayList<Pack> packages = new ArrayList<Pack>();

    Message(int id, int errorId, int packageAmount, ArrayList<Pack> packages){
        this.id = id;
        this.errorId = errorId;
        this.packageAmount = packageAmount;
        this.packages = packages;
    }

    public int getId() {
        return id;
    }

    public int getErrorId() {
        return errorId;
    }

    public int getPackageAmount() {
        return packageAmount;
    }

    public ArrayList<Pack> getPackages() {
        return packages;
    }
}
