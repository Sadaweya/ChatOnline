package dnd.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;

public interface Exec {
    void exec(LinkedList<String> attributes) throws IOException;
}
