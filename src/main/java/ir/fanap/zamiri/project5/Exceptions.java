package ir.fanap.zamiri.project5;

public class Exceptions {
    public static class notMasterInList extends Exception {
        public notMasterInList(String message) {
            super(message);
        }
    }
}
