package objects;

/**
 * Created by snels on 15.12.2016.
 */
public class Task {
    private String taskName;
    private String taskURL;
    private int taskRefTime;
    private boolean taskFilter;
    private boolean changed = false;
    private String filterElement;
    private String filterElementName;
    private boolean started;
    int error;

    public Task(String taskName, String taskURL, int taskRefTime, boolean taskFilter, int error) {
        this.taskName = taskName;
        this.taskURL = taskURL;
        this.taskRefTime = taskRefTime;
        this.taskFilter = taskFilter;
        this.error = error;
    }

    public Task(String taskName, String taskURL, int taskRefTime, boolean taskFilter, String filterElement, String filterElementName, int error) {
        this.taskName = taskName;
        this.taskURL = taskURL;
        this.taskRefTime = taskRefTime;
        this.taskFilter = taskFilter;
        this.filterElement = filterElement;
        this.filterElementName = filterElementName;
        this.error = error;
    }

    public void setNull() {
        this.taskName = null;
        this.taskURL = null;
        this.filterElement = null;
        this.filterElementName = null;
        this.error = 0;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskURL(String taskURL) {
        this.taskURL = taskURL;
    }

    public void setTaskRefTime(int taskRefTime) {
        this.taskRefTime = taskRefTime;
    }

    public void setTaskFilter(boolean taskFilter) {
        this.taskFilter = taskFilter;
    }

    public void setFilterElement(String filterElement) {
        this.filterElement = filterElement;
    }

    public void setFilterElementName(String filterElementName) {
        this.filterElementName = filterElementName;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskURL() {
        return taskURL;
    }

    public int getTaskRefTime() {
        return taskRefTime;
    }

    public boolean isTaskFilter() {
        return taskFilter;
    }

    public String getFilterElement() {
        return filterElement;
    }

    public String getFilterElementName() {
        return filterElementName;
    }

    public boolean isStarted() {
        return started;
    }

    public int getError() {
        return error;
    }

    public boolean isChanged() {
        return changed;
    }
}
