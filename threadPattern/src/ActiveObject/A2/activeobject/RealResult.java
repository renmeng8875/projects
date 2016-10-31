package ActiveObject.A2.activeobject;

class RealResult extends Result {
    private final Object resultValue;
    public RealResult(Object resultValue) {
        this.resultValue = resultValue;
    }
    public Object getResultValue() {
        return resultValue;
    }
}
