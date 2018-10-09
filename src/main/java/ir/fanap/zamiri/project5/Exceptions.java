package ir.fanap.zamiri.project5;

public class Exceptions {

    public static class NotMasterInList extends Exception {
        public NotMasterInList(String message) {
            super(message);
        }
    }

    public static class NotFound extends Exception {
        public NotFound(String message) {            super(message);        }
    }
}
