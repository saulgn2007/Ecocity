package interfaz;

public interface Mantenible {
    void reparar();
    void desgastar(int cantidad);

    default boolean necesitaMantenimiento() {
        return false;
    }
}