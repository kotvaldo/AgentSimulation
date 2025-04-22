package Observer;



public interface ISubject {
    void notifyObservers();
    void attachObserver(IObserver observer);
    void detachObserver(IObserver observer);
    void setState(IState state);
    IState getState();
}
