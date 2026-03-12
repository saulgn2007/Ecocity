package ecocity;

/* Edificio residencial: genera habitantes y recauda impuestos.*/
public class EdificioResidencial extends Edificio {
 
    private int habitantes;
    private double tasaImpuesto;
    private double consumoAgua;
 
    public EdificioResidencial(String nombre, int habitantes, double tasaImpuesto) {
        super(nombre, 5000 + (habitantes * 100), 2.0 + (habitantes * 0.1));
        this.habitantes = habitantes;
        this.tasaImpuesto = tasaImpuesto;
        this.consumoAgua = habitantes * 0.5;
    }
 
    @Override
    public void aplicarEfectoMensual() {
        if (estaDestruido()) {
            // Cambiado a concatenación manual con +
            System.out.println("   🏚️ " + getNombre() + " está destruido y no genera ingresos ni consume agua.");
            return;
        }
        
        double ingresos = habitantes * tasaImpuesto;
        EstadoCiudad estado = EstadoCiudad.getInstance();
        
        estado.agregarDinero(ingresos);
        estado.agregarAgua(-consumoAgua);
        estado.agregarHabitantes(habitantes);
        desgastar(3); // desgaste mensual normal
        
        // Cambiado a concatenación manual con + (la forma menos eficiente pero directa)
        System.out.println("   🏠 " + getNombre() + " generó $" + ingresos + " en impuestos y consumió " + consumoAgua + " unidades de agua.");
    }

    public int getHabitantes() { return habitantes; }
    public double getConsumoAgua() { return consumoAgua; }
 
    @Override
    public String toString() {
        // También cambiamos el String.format por una suma de Strings
        return super.toString() + " | 👥 " + habitantes + " hab.";
    }
}