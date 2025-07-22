package android.os.test;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * android.os.test.TestListenerEvent
 */
public class TestListenerEvent implements Parcelable {
    private int what;
    private String msg;

    public TestListenerEvent(int what, String msg) {
        this.what = what;
        this.msg = msg;
    }

    public TestListenerEvent(Parcel in) {
        what = in.readInt();
        msg = in.readString();
    }


    public void setWhat(int what) {
        this.what = what;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getWhat() {

        return what;
    }

    public String getMsg() {
        return msg;
    }

    public static final Creator<TestListenerEvent> CREATOR = new Creator<TestListenerEvent>() {
        @Override
        public TestListenerEvent createFromParcel(Parcel in) {
            return new TestListenerEvent(in);
        }

        @Override
        public TestListenerEvent[] newArray(int size) {
            return new TestListenerEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(what);
        dest.writeString(msg);
    }

    /**
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        what = dest.readInt();
        msg = dest.readString();
    }

}
