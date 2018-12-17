public enum MessageType {
    NAME("NAME"), BROADCASTING_MESSAGE("BROADCASTING_MESSAGE"), IS_ALIVE("IS_ALIVE");
    private  String message;
    MessageType(String message){
        this.message = message;
    }
    public  String getMessage(){
        return  message;
    }
}
