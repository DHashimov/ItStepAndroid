package demoapp.itsteps.com.callbacks;

public interface Callback<T> {
    void onSuccess(T data);

    void onError(String error);
}
