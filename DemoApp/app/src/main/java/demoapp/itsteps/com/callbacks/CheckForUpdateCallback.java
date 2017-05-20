package demoapp.itsteps.com.callbacks;

/**
 * Created by dhashimov on 5/20/17.
 */

public interface CheckForUpdateCallback<T> extends Callback<T> {

     public void doValidation(boolean validate);
}
