package pp.game.observers;

public interface IObserver<T> {
	void onChanged(T observable);
}
