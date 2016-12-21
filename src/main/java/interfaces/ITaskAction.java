package interfaces;

import objects.Task;

/**
 * Created by snels on 19.12.2016.
 */
public interface ITaskAction {

    void add(Task task);
    void open(Task task);
    void remove(Task task);
    void play(Task task);
    void edit(Task task);
    void start(Task task);
}
