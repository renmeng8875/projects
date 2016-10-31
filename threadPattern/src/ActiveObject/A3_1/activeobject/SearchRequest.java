package ActiveObject.A3_1.activeobject;

class SearchRequest extends MethodRequest {
    private final String word;
    public SearchRequest(Servant servant, FutureResult future, String word) {
        super(servant, future);
        this.word = word;
    }
    public void execute() {
        Result result = servant.search(word);
        future.setResult(result);
    }
}
