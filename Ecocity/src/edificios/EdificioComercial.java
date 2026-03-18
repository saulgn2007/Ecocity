package edificios;

import interfaces.GeneradorRecursos;

//Nivel 2 de la jerarquía: Edificio → EdificioComercial
public class EdificioComercial extends Edificio implements GeneradorRecursos {

    private int bonusFelicidad;
    private double ingresoBase;

    public EdificioComercial(String nombre, double costo, int bonusFelicidad, double ingresoBase) {
        super(nombre, costo, 3.5);
        this.bonusFelicidad = bonusFelicidad;
        this.ingresoBase = ingresoBase;
    }
    
    @Override
    public double producirRecurso() {
        return ingresoBase * (salud/ 100.0);
    }

    @Override
    public String getTipoRecurso() {
        return "Dinero (comercio $)";
    }

    @Override
    public void aplicarEfectoMensual() {
        double ingresos = producirRecurso();
        int felicidad = (int)(bonusFelicidad * (salud / 100.0));
        System.out.printf(
            "  Edificio Comercial %-20s → +%d felicidad | +$%.0f ingresos | -%.1f MW energía%n",
            nombre, felicidad, ingresos, consumoEnergia
        );
        desgastar(2);  // Los comercios se desgastan poco
    }

    public int getBonusFelicidad() { 
    	return bonusFelicidad; 
    	}
    public double getIngresoBase() { 
    	return ingresoBase; 
    	}

    @Override
    public String toString() {
        return super.toString() + String.format(
            "%n    └─ Edificio Comercial | Felicidad: +%d pts/mes | Ingresos: $%.0f/mes",
            bonusFelicidad, producirRecurso()
        );
    }
}