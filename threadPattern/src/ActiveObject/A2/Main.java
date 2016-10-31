package ActiveObject.A2;

import ActiveObject.A2.activeobject.ActiveObject;
import ActiveObject.A2.activeobject.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new AddClientThread("Diana", activeObject).start();
    }
}
