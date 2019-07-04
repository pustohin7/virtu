package ru.virtu.systems.util.model;


import org.apache.wicket.model.IModel;
import ru.virtu.systems.util.functional.SerializableConsumer;
import ru.virtu.systems.util.functional.SerializableSupplier;

/**
 * @author Alexey Pustohin
 */
public class LambdaModel<T> implements IModel<T> {

    private SerializableSupplier<T> getter;
    private SerializableConsumer<T> setter;

    public LambdaModel(SerializableSupplier<T> getter) {
        this.getter = getter;
    }

    public LambdaModel(SerializableSupplier<T> getter, SerializableConsumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public T getObject() {
        return getter.get();
    }

    @Override
    public void setObject(T object) {
        if (setter == null) {
            throw new UnsupportedOperationException("This model is read-only");
        }

        setter.accept(object);
    }

    @Override
    public void detach() {
        // do nothing
    }
}
