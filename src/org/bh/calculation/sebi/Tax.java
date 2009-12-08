package org.bh.calculation.sebi;


public class Tax {
	
	// Business Tax (Gewerbesteuer)
	private Calculable sg;
	
	// Corporate Income Tax incl. solidarity tax (K�rperschaftssteuer incl. Soli) 
	private Calculable sks;
	
	// Aggregated Taxes (German Law)
	private Calculable aggregatedTaxG;
	
	public Tax(Calculable sg, Calculable sks){
		this.sg = sg;
		this.sks = sks;
		calcGermanTax(sg, sks);
	}
	
	private void calcGermanTax(Calculable sg, Calculable sks){
		//this.gesamt = (0.5 * sg * (1 - sks) + sks); 
		this.aggregatedTaxG = (Calculable) sg.mul(new Double(0.5)).mul(sks.mul(new Double(-1)).add(new Double(1)));
		this.aggregatedTaxG = (Calculable) aggregatedTaxG.add(sks);
	}
	public Calculable getSg() {
		return sg;
	}

	public Calculable getSks() {
		return sks;
	}

	public Calculable getGermanTax() {
		return aggregatedTaxG;
	}
}
