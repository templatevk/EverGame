package pp.game.observers;

public interface IObservable<T> {
	void addObservable(IObserver<T> observer);
	void removeObservable(IObserver<T> observer);
}
